import localRequest from "../utils/localRequest.ts";
//本地的模型列表
export function getModuleList() {
    return localRequest({
        url: "/api/tags",
        method: 'get'
    })
}
//本地的模型详情
export function getModelDetail(modelVersion: string) {
    return localRequest({
        url: "/api/show",
        method: 'post',
        data: {
            model: modelVersion,
            verbose: true
        }
    })
}

export function getModuleDetail(data: ModuleDetailDTO) {
    return localRequest({
        url: "/api/show",
        method: 'post',
        data: data,
    })
}

export function createModule(data: CreateModuleDTO) {
    return localRequest({
        url: "/api/pull",
        method: 'post',
        data: data,
    })
}

export function deleteModule(data:DeleteModuleDTO ) {
    return localRequest({
        url: "/api/delete",
        method: 'delete',
        data: data,
    })
}
interface ModuleDetailDTO {
    model: string;
    verbose: boolean;
}

interface CreateModuleDTO {
    model:string;
    insecure?: boolean;
    stream?: boolean;
}
interface DeleteModuleDTO {
    model: string;
}
