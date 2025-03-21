import Cookies from "js-cookie"

const TokenKey = 'Admin-Token'

export function getToken() {
    return Cookies.get(TokenKey)
}

export function setToken({token}: { token: any }) {
    return Cookies.set(TokenKey, token,{expires:5/24})
}

export function removeToken() {
    return Cookies.remove(TokenKey)
}
