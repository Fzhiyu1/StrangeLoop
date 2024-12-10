import request from './request';


// http请求封装
const http={
    /**
     * methods:请求
     * @param url 请求地址
     * @param params 请求参数
     */
    get({url, params}: { url: string, params: any }) {
        const config = {
            method: 'get',
            url: url,
            params: undefined
        }
        if (params) {
            config.params = params;
        }
        return request(config)
    },
    post({url, params}: { url: string, params: any }) {
        const config = {
            method: 'post',
            url: url,
            data:undefined
        }
        if (params) {
            config.data = params;
        }
        return request(config)
    },
    put({url, params}: { url: string, params: any }) {
        const config = {
            method: 'put',
            url: url,
            params: undefined
        };
        if(params) config.params = params;
        return request(config);
    },
    delete({url, params}: { url: string, params: any }) {
        const config = {
            method: 'delete',
            url: url,
            params: undefined
        }
        if(params) config.params = params;
        return request(config)
    }

}
export default http;
