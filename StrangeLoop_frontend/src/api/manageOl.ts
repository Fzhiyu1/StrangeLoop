import request from "../utils/request.ts";

const baseUrl = "/model/ol/manage";

/**
 * 获取在线模型列表
 * @param data
 */
export function listModelAiOline({data}: {data: any}) {
    return request({
        method: 'get',
        url: baseUrl,
        data: data,
    })
}

/**
 * 获取在线模型详情
 * @param id
 */
export function getModelAiOline({id}: { id: any; }) {
    return request({
        method: 'get',
        url:baseUrl+'/'+id
    })

}

/**
 * 新增在线模型
 * @param data
 */
export function addModelAiOline({data}: {data: any}) {
    return request({
        method: 'post',
        url: baseUrl,
        data:data
    })
}

/**
 * 修改在线模型
 * @param data
 */
export function updateModelAiOline({data}: {data: any}) {
    return request({
        method: 'put',
        url: baseUrl,
        data:data
    })
}

export function deleteModelAiOline({id}: {id: any}) {
    return request({
        method: 'delete',
        url:baseUrl+'/'+id,
    })
}
