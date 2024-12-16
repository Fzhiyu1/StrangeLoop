import request from "../utils/request.ts";


const baseUrl = '/sys/user'

export function loginUser({data}: { data: any }) {
    return request({
        url: baseUrl + "/login",
        method: 'post',
        data: data,

    })
}

export function createUser({data}: { data: any }) {
    return request({
        url: baseUrl + "/create",
        method: 'post',
        data: data,

    })
}
