import { defineStore } from 'pinia'
import  {ref} from "vue";

export const useModelStore = defineStore('model', () => {
    const modelIndex = ref<number>(0);
    return {
        modelIndex
    }
})