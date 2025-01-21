import { defineStore } from 'pinia'
import  {ref} from "vue";

export const useModelStore = defineStore('model', () => {
   // 0代笔对话，1代表模型管理，2代表AI工具箱
    const modelIndex = ref<number>(0);
    return {
        modelIndex
    }
})