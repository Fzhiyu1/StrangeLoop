import request from "../utils/request.ts";

const baseUrl = "/model/message";

/**
 * 新增消息记录
 * @param data
 */
export function addMessage({data}: {data: any}) {
    return request({
        method: 'post',
        url: baseUrl,
        data:data
    })
}

/**
 * 删除消息记录
 * @param id
 */
export function deleteMessage({id}:{id:any}) {
    return request({
        method: 'delete',
        url: baseUrl + '/' + id
    })
}


