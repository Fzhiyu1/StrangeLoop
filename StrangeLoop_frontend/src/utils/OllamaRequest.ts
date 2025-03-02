import axios from "axios";
import Cookies from "js-cookie";
import {ElMessage} from "element-plus";

/**
 * ollama请求类
 */
export class OllamaRequest {

    private ollamaUrl:any=Cookies.get("OllamaApi");
    private ollamaShowApi = "/show";  // 修正可能的拼写错误
    private ollamaTagsApi = "/tags";  // 保持API路径统一风格
    private ollamaModelList: any[] = [];  // 明确类型为数组



    /**
     * 获得基底模型列表
     */
    public async sendOllamaBackBase() {
        try {
            // 获取模型列表
            const tagsResponse = await axios.get(`${this.ollamaUrl}${this.ollamaTagsApi}`);
            this.ollamaModelList = tagsResponse.data.models;  // 假设返回结构包含models数组

            console.log(this.ollamaModelList)
            // 遍历模型进行检查
            const modelsToDelete: string[] = [];
            for (const modelInfo of this.ollamaModelList) {
                try {
                    const showResponse = await axios.post(
                        `${this.ollamaUrl}${this.ollamaShowApi}`,
                        { "model": modelInfo.model }  // 假设模型名称在name字段
                    );


                    if (showResponse.data.details.parent_model!="") {
                        modelsToDelete.push(modelInfo);
                    }
                } catch (error) {
                    console.error(`Error checking model ${modelInfo.model}:`, error);
                }
            }

            // 过滤掉需要删除的模型
            console.log(modelsToDelete)
            this.ollamaModelList = this.ollamaModelList.filter(
                model => !modelsToDelete.includes(model)
            );

            return this.ollamaModelList;

        } catch (error) {
            console.error("Error processing models:", error);
        }
    }

    /**
     * 获得修改后的模型列表
     */
    public async sendOllamaBackCustom() {
        try {
            // 获取模型列表
            const tagsResponse = await axios.get(`${this.ollamaUrl}${this.ollamaTagsApi}`);
            this.ollamaModelList = tagsResponse.data.models;  // 假设返回结构包含models数组

            // 遍历模型进行检查
            const modelsToDelete: string[] = [];
            for (const modelInfo of this.ollamaModelList) {
                try {
                    const showResponse = await axios.post(
                        `${this.ollamaUrl}${this.ollamaShowApi}`,
                        { "model": modelInfo.model }  // 假设模型名称在name字段
                    );


                    if (showResponse.data.details.parent_model!="") {
                        modelsToDelete.push(modelInfo);
                    }
                } catch (error) {
                    console.error(`Error checking model ${modelInfo.model}:`, error);
                }
            }


            return modelsToDelete;

        } catch (error) {
            console.error("Error processing models:", error);
        }
    }

    /**
     * 设置ollamaCookie
     * @param url
     * @private
     */
    private setOllamaToCookie(url:string){
        const expirationDays = 365 * 10; // 10 年
        Cookies.set("OllamaApi", url, {
            expires: expirationDays,
            path: "/"
        });
        this.updateCookie();
    }

    /**
     * 更新类里面的url
     * @private
     */
    private updateCookie() {
        this.ollamaUrl = Cookies.get("OllamaApi");
    }

    /**
     * 更改ollama连接
     * @param url
     */
    public async changeOllamaApi(url: string): Promise<boolean> {
        try {
            console.log(url);


            const response = await axios.get(`${url}${this.ollamaTagsApi}`);

            if (response.status === 200) {
                ElMessage.success("Ollama链接初始化成功！");
                this.setOllamaToCookie(url);
                return true; // ✅ 直接返回结果
            }

            ElMessage.error(`请求异常，状态码：${response.status}`);
            return false;
        } catch (error: any) {

            if (error.response) {
                ElMessage.error(`请检查ollamaAPI地址：${error.response.status}`);
            } else if (error.request) {
                ElMessage.error("无法连接到Ollama，请检查是否已安装并运行！");
            } else {
                ElMessage.error(`请求配置错误：${error.message}`);
            }
            return false;
        }
    }




}
