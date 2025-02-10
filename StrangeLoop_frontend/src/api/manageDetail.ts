import request from "../utils/request.ts";

const baseUrl = "/model/manage/detail";

export function updateModelInfoAndModelFile({data}: {data: any}) {
    return request({
        method: 'put',
        url: baseUrl,
        data:data
    })
}
