export class SSEService {
    private decoder: TextDecoder;

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
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(data),
            });

            const reader = response.body?.getReader();
            if (!reader) throw new Error("Readable stream is not supported.");

            let accumulatedMessage = '';

            // 处理流的每个数据块
            const processStream = async ({done, value}: ReadableStreamReadResult<Uint8Array>) => {
                if (done) {
                    onComplete(accumulatedMessage); // 通知完成
                    return;
                }

                // 确保 value 是 Uint8Array
                if (!(value instanceof Uint8Array)) {
                    console.error("Unexpected data type:", value);
                    return;
                }

                // 解码当前数据块
                const result = this.decoder.decode(value, {stream: true});
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
                const nextChunk = await reader.read();
                await processStream(nextChunk);
            };

            // 读取流的第一个数据块并开始处理
            const initialChunk = await reader.read();
            await processStream(initialChunk);
        } catch (error) {
            console.error("SSE Error:", error);
            onComplete(""); // 确保在错误发生时也调用 onComplete
        }
    }
}
