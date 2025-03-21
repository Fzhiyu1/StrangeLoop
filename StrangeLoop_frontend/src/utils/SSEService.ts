export class SSEService {
    private decoder: TextDecoder;
    private reader: ReadableStreamDefaultReader<Uint8Array> | null = null;
    private stopRequested: boolean = false;
    private token: string | null = null;

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
    async sendLocal(
        url: string,
        data: any,
        onUpdate: (message: string) => void,
        onComplete: (finalMessage: string) => void
    ): Promise<void> {
        this.stopRequested = false;
        let authorization = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + this.token
            },
            body: JSON.stringify(data),
        };

        // 新增：数据缓冲区
        let buffer = '';

        try {
            const response = await fetch(url, authorization);
            this.reader = response.body?.getReader() || null;
            if (!this.reader) throw new Error("Readable stream is not supported.");

            let accumulatedMessage = '';

            const processStream = async ({ done, value }: ReadableStreamReadResult<Uint8Array>) => {
                if (this.stopRequested || done) {
                    onComplete(accumulatedMessage);
                    return;
                }

                // 解码数据并追加到缓冲区
                const chunk = this.decoder.decode(value, { stream: true });
                buffer += chunk;

                // 分割并处理完整 JSON 对象
                let startIdx = 0;
                let braceCount = 0;
                let inString = false;

                for (let i = 0; i < buffer.length; i++) {
                    const char = buffer[i];

                    // 处理字符串内的转义字符
                    if (char === '"' && buffer[i - 1] !== '\\') {
                        inString = !inString;
                    }

                    if (!inString) {
                        if (char === '{') {
                            if (braceCount === 0) startIdx = i; // 记录 JSON 起始位置
                            braceCount++;
                        } else if (char === '}') {
                            braceCount--;
                            if (braceCount === 0) { // 找到一个完整 JSON 对象
                                const jsonStr = buffer.slice(startIdx, i + 1);
                                try {
                                    const parsed = JSON.parse(jsonStr);
                                    const content = parsed.message?.content || '';
                                    accumulatedMessage += content;
                                    onUpdate(accumulatedMessage);
                                } catch (err) {
                                    console.error("JSON 解析失败:", jsonStr);
                                }
                                startIdx = i + 1; // 更新起始位置
                            }
                        }
                    }
                }

                // 保留未处理完的数据
                buffer = buffer.slice(startIdx);

                // 继续读取下一个数据块
                const nextChunk = await this.reader.read();
                await processStream(nextChunk);
            };

            const initialChunk = await this.reader.read();
            await processStream(initialChunk);
        } catch (error) {
            console.error("SSE Error:", error);
            onComplete("");
        } finally {
            this.reader = null;
        }
    }

    async sendOnline(
        url: string,
        data: any,
        onUpdate: (message: string) => void,
        onComplete: (finalMessage: string) => void
    ): Promise<void> {
        this.stopRequested = false; // 每次发送前重置停止标志
        const authorization = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + this.token
            },
            body: JSON.stringify(data),
        };

        try {
            const response = await fetch(url, authorization);

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

                // 解码当前数据块并按行分割
                const chunk = this.decoder.decode(value, { stream: true });
                const lines = chunk.split("\n");

                for (const line of lines) {
                    const trimmedLine = line.trim();

                    // 处理 `data: ` 行
                    if (trimmedLine.startsWith("data: ")) {
                        const jsonData = trimmedLine.substring(6); // 去掉 `data: `

                        if (jsonData === "[DONE]") {
                            onComplete(accumulatedMessage); // 通知完成
                            return;
                        }

                        try {
                            const parsed = JSON.parse(jsonData);
                            const content = parsed.choices?.[0]?.delta?.content || '';

                            accumulatedMessage += content; // 累加内容
                            onUpdate(accumulatedMessage); // 更新实时内容
                        } catch (err) {
                            console.error("Failed to parse JSON:", err, trimmedLine);
                        }
                    }
                }

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

    // 设置token
    setToken(token: string): void {
        this.token = token;
    }


    async sendCreatedModel(
        url: string,
        data: any,
        onUpdate: (message: string) => void,
        onComplete: (finalMessage: string) => void
    ): Promise<void> {
        this.stopRequested = false;
        let authorization = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + this.token
            },
            body: JSON.stringify(data),
        };

        // 新增：数据缓冲区
        let buffer = '';

        try {
            const response = await fetch(url, authorization);
            this.reader = response.body?.getReader() || null;
            if (!this.reader) throw new Error("Readable stream is not supported.");

            let accumulatedMessage = '';

            const processStream = async ({ done, value }: ReadableStreamReadResult<Uint8Array>) => {
                if (this.stopRequested || done) {
                    onComplete(accumulatedMessage);
                    return;
                }

                // 解码数据并追加到缓冲区
                const chunk = this.decoder.decode(value, { stream: true });
                buffer += chunk;

                // 分割并处理完整 JSON 对象
                let startIdx = 0;
                let braceCount = 0;
                let inString = false;

                for (let i = 0; i < buffer.length; i++) {
                    const char = buffer[i];

                    // 处理字符串内的转义字符
                    if (char === '"' && buffer[i - 1] !== '\\') {
                        inString = !inString;
                    }

                    if (!inString) {
                        if (char === '{') {
                            if (braceCount === 0) startIdx = i; // 记录 JSON 起始位置
                            braceCount++;
                        } else if (char === '}') {
                            braceCount--;
                            if (braceCount === 0) { // 找到一个完整 JSON 对象
                                const jsonStr = buffer.slice(startIdx, i + 1);
                                try {
                                    const parsed = JSON.parse(jsonStr);



                                    onUpdate(parsed);
                                } catch (err) {
                                    console.error("JSON 解析失败:", jsonStr);
                                }
                                startIdx = i + 1; // 更新起始位置
                            }
                        }
                    }
                }

                // 保留未处理完的数据
                buffer = buffer.slice(startIdx);

                // 继续读取下一个数据块
                const nextChunk = await this.reader.read();
                await processStream(nextChunk);
            };

            const initialChunk = await this.reader.read();
            await processStream(initialChunk);
        } catch (error) {
            console.error("SSE Error:", error);
            onComplete("");
        } finally {
            this.reader = null;
        }
    }
}
