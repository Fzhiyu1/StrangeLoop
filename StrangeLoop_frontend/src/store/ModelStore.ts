import { defineStore } from 'pinia'
import  {ref} from "vue";

export const useModelStore = defineStore('model', () => {
    const modelIndex = ref<number>(0);
    const topNav = ref<{modelVersion:string,modelName:string}>({modelVersion:'',modelName:''})
    const currConversation = ref<CurrentConversation>({"id":0,"userId":0,"title":"","modelInfoId":0,"modelMessageArrayList":[]})
    const modelType = ref<number>(0)//在线模型和本地模型的切换按钮，0为本地模型，1为在线模型
    const conversationList = ref<any[]>([])//会话列表
    const modelList = ref<any[]>([])//模型列表,存储模型类型

    const currClickId = ref<number|null>(null)//当前点击的菜单id

    return {
        modelIndex,
        topNav,
        currConversation,
        modelType,
        conversationList,
        currClickId
    }
})

interface CurrentConversation {
    "id": number,
    "userId": number,
    "title": string,
    "modelInfoId": number,
    "modelMessageArrayList":any[]
}