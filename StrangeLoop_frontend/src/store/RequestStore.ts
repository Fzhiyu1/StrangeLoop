import { defineStore } from 'pinia'
import  {ref} from "vue";

export const useRequestStore = defineStore('request', () => {
    const isLoading = ref<boolean>(false);
    return {
        isLoading
    }
})