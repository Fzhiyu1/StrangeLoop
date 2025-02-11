<script setup lang="ts">

import AiChatFrame from "../../components/AiChatFrame.vue";
import {useModelStore} from "@/store/ModelStore.ts";

const modelStore = useModelStore()

const showArray=()=>{

}


// onMounted(async () => {

// })

</script>

<template>
<!--  调用聊天框组件 注意：绑定历史记录应以这种格式 v-model:messages="messages"-->
<!--  https://api.bianxie.ai/v1/chat/completions-->
<AiChatFrame v-model:messages="modelStore.currConversation.modelMessageArrayList" :url="'http://localhost:11434/api/chat'" :model="'llama3.2:1b'" :token="'sk-qxoWrNw4JDPobNAZxkIV11rXP8cFFTtZa4kUbYthQAhxdJJq'" :ai-type="0"></AiChatFrame>
</template>

<style scoped>
 *{
   width: 100%;
 }
</style>
