import { defineStore } from 'pinia'
import {ref, watch} from "vue";
import {getModelInfo} from "../api/manage.ts";
import {getModelAiOline} from "../api/manageOl.ts";
import {getModelDetail} from "../api/module.ts";
import {ElMessage} from "element-plus";
import {listConversation} from "../api/conversation.ts";

export const useModelStore = defineStore('model', () => {
   // 0代笔对话，1代表模型管理，2代表AI工具箱
    const modelIndex = ref<number>(0);
    const topNav = ref<{modelVersion:string,modelName:string}>({modelVersion:'',modelName:''})
    const currConversation = ref<any>({"id":0,"userId":0,"title":"","modelInfoId":0,"modelMessageArrayList":[]})
    const modelType = ref<number>(0)//在线模型和本地模型的切换按钮，0为本地模型，1为在线模型
    const conversationList = ref<any[]>([])//会话列表
    const currModelInfo = ref<any>(null)//当前点击的模型信息
    const modelList = ref<any[]>([])//模型列表,存储模型类型
    const currClickId = ref<number|null>(null)//当前点击的菜单id
    const parameter = ref<any>({
        propsLocalModelName: null,
    })//参数

    watch(currClickId, (newVal)=>{
        console.log(currConversation.value)
        if(modelIndex.value !== 1){
            return
        }
        updateModelList(newVal)
    })

    const updateModelList = async (newVal) => {
        // if(!currModelInfo.value){
        //     currModelInfo.value = {}
        // }
        // console.log(newVal)
        // const {details,model_info,modified_at} = (await getModelDetail(parameter.value.propsLocalModelName)).data
        // currModelInfo.value.details = details
        // currModelInfo.value.model_info = model_info
        // currModelInfo.value.modified_at = modified_at
        // console.log(details)

        // console.log(newVal)
        // const info = await getModelInfo({id: newVal.value})
        // currModelInfo.value.info = info
        // const aiOnline =(await getModelAiOline({id:info.aiolId})).data.data
        // currModelInfo.value.aiOnline = aiOnline
        // console.log("currModelInfo",currModelInfo.value)
        // if(aiOnline === null){
        //     ElMessage.warning("缺少在线模型ID参数")
        // }
    }

    const updateConversationList = async () => {
        const res = await listConversation({});
        conversationList.value = res.data.data.items
    }

    return {
        modelIndex,
        topNav,
        currConversation,
        modelType,
        conversationList,
        currClickId,
        modelList,
        currModelInfo,
        parameter,
        updateModelList,
        updateConversationList
    }
})
