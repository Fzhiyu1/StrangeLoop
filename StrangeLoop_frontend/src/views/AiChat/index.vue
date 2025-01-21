<script setup lang="ts">

import AiChatFrame from "../../components/AiChatFrame.vue";
import {ref} from "vue";
const messages = ref([{
  role: "assistant",
  content: "您好，我是您的智能AI小助手，有什么可以帮助您？",
  roleInfo: {
    assistant: {
      name: 'Assistant',
      avatar: 'https://lf3-static.bytednsdoc.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/other/logo.png'
    }
  }
}]);
const showArray=()=>{
  console.log(messages.value);
}
// const url = import.meta.env.
</script>

<template>
<!--  调用聊天框组件 注意：绑定历史记录应以这种格式 v-model:messages="messages"-->
<AiChatFrame v-model:messages="messages" :url="'https://api.bianxie.ai/v1/chat/completions'" :model="'gpt-3.5-turbo'" :token="'sk-qxoWrNw4JDPobNAZxkIV11rXP8cFFTtZa4kUbYthQAhxdJJq'" :ai-type="1"></AiChatFrame>
</template>

<style scoped>
 *{
   width: 100%;
 }
</style>
