import request from "../utils/request.ts";

const baseUrl = "/model/conversation";

/**
 * 获取会话列表
 * @param params
 */
export function listConversation({params}:{params:any}){
    return request(
        {
            method: 'get',
            url: baseUrl + "/list",
            params: params,
        }
    )
}

/**
 * 删除一条或者多条会话
 * @param id
 */
export function deleteConversation({ids}:{ids:[]}){
        let id = ""
        id = ids.join(",")
        return request({
            method:'delete',
            url: baseUrl +'/'+ id,
        })
}

/**
 * 新增会话记录
 * @param data
 */
export function addConversation({data}:{data: any}) {
    return request({
        method: 'post',
        url: baseUrl,
        data: data,
    })
}

/**
 * 修改绘画记录
 * @param data
 */
export function updateConversation({data}:{data: any}) {
    return request({
        method: 'put',
        url: baseUrl,
        data:data
    })
}

/**
 * 获取会话详情
 * @param id
 */
export function getConversation({id}:{id:any}){
    return request({
        method: 'get',
        url: baseUrl + '/detail/' + id
    })
}
