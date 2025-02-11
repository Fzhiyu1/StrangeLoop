import localRequest from "../utils/localRequest.ts";

export function getModuleList() {
    return localRequest({
        url: "/api/tags",
        method: 'get'
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
