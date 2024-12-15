export class SSEService {
    private decoder: TextDecoder;
    private reader: ReadableStreamDefaultReader<Uint8Array> | null = null;
    private stopRequested: boolean = false;

    constructor() {
        this.decoder = new TextDecoder();
    }

    /**
     * 使用 SSE 长连接发送请求
     * @param url 请求地址
     * @param data 请求的主体数据
     * @param onUpdate 回调函数，用于更新流式消息
     * @param onComplete 回调函数，当消息完成时触发
     */
    async send(
        url: string,
        data: any,
        onUpdate: (message: string) => void,
        onComplete: (finalMessage: string) => void
    ): Promise<void> {
        this.stopRequested = false; // 每次发送前重置停止标志
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data),
            });

            this.reader = response.body?.getReader() || null;
            if (!this.reader) throw new Error("Readable stream is not supported.");

            let accumulatedMessage = '';

            // 处理流的每个数据块
            const processStream = async ({ done, value }: ReadableStreamReadResult<Uint8Array>) => {
                if (this.stopRequested || done) {
                    onComplete(accumulatedMessage); // 通知完成
                    return;
                }

                // 确保 value 是 Uint8Array
                if (!(value instanceof Uint8Array)) {
                    console.error("Unexpected data type:", value);
                    return;
                }

                // 解码当前数据块
                const result = this.decoder.decode(value, { stream: true });
                let message: string;

                try {
                    const parsed = JSON.parse(result);
                    message = parsed.message?.content || '';
                } catch (err) {
                    message = ''; // 如果解析失败，继续处理下一个数据块
                }

                // 累加消息并更新
                accumulatedMessage += message;

                onUpdate(accumulatedMessage);

                // 读取下一个数据块
                const nextChunk = await this.reader.read();
                await processStream(nextChunk);
            };

            // 读取流的第一个数据块并开始处理
            const initialChunk = await this.reader.read();
            await processStream(initialChunk);
        } catch (error) {
            console.error("SSE Error:", error);
            onComplete(""); // 确保在错误发生时也调用 onComplete
        } finally {
            this.reader = null; // 清理 reader 引用
        }
    }

    /**
     * 停止当前的 SSE 请求
     */
    stop(): void {
        this.stopRequested = true;
        if (this.reader) {
            this.reader.cancel().catch(err => {
                console.error("Error cancelling reader:", err);
            });
        }
    }
}
