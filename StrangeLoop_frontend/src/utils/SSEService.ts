export class SSEService {
    private decoder: TextDecoder;
    private loading: boolean;

    constructor() {
        this.decoder = new TextDecoder();
        this.loading = false;
    }

    /**
     * 使用 SSE 长连接发送请求
     * @param url 请求地址
     * @param data 请求的主体数据
     * @param onUpdate 回调函数，用于更新流式消息
     * @param onComplete 回调函数，当消息完成时触发
     */
    async send(url: string, data: any, onUpdate: (message: string) => void, onComplete: (finalMessage: string) => void): Promise<void> {
        if (this.loading) {
            console.warn("Request is still loading...");
            return;
        }

        this.loading = true;

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data),
            });

            const reader = response.body?.getReader();
            if (!reader) throw new Error("Readable stream is not supported.");

            let accumulatedMessage = '';

            const processStream = async ({ done, value }: ReadableStreamReadResult<Uint8Array>) => {
                if (done) {
                    this.loading = false;
                    onComplete(accumulatedMessage); // 通知完成
                    return;
                }

                const result = this.decoder.decode(value, { stream: true });
                const message = JSON.parse(result).message.content;

                accumulatedMessage += message; // 累加消息
                onUpdate(accumulatedMessage); // 更新实时消息

                const nextChunk = await reader.read();
                return processStream(nextChunk);
            };

            const initialChunk = await reader.read();
            await processStream(initialChunk);
        } catch (error) {
            console.error("SSE Error:", error);
            this.loading = false;
        }
    }
}
