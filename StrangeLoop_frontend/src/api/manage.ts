import request from "../utils/request.ts";
import {getModelDetail} from "./module.ts";

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
export async function getModelInfo({id}: {id: any}) {
    let modelInfo:any = {}
     const r = await request({
        method: 'get',
        url: baseUrl + '/' + id,
    })
    modelInfo.sqlData = r.data.data
    const res = await getModelDetail(r.data.data.modelVersion)
    modelInfo.localData = {details:res.data.details,info:res.data.model_info}
    return modelInfo
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
