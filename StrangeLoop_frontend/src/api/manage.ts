import request from "../utils/request.ts";

const baseUrl = "/model/manage";

/**
 * 获取模型信息列表
 * @param data
 */
export function listModelInfo({data}: {data: any}) {
    return request({
        method: 'get',
        url: baseUrl,
        data:data
    })
}

/**
 * 获取模型详细信息
 * @param id
 */
export function getModelInfo({id}: {id: any}) {
    return request({
        method: 'get',
        url: baseUrl + '/' + id,
    })
}

/**
 * 新增模型信息
 * @param data
 */
export function addModelInfo({data}: {data: any}) {
    return request({
        method: 'post',
        url: baseUrl,
        data:data
    })
}

/**
 * 修改模型信息
 * @param data
 */
export function updateModelInfo({data}: {data: any}) {
    return request({
        method: 'put',
        url: baseUrl,
        data:data
    })
}

export function deleteModelInfo({id}: {id:any}) {
    return request({
        method: 'delete',
        url:baseUrl+'/'+id
    })
}
