<script setup lang="ts">

import ChatBubble from "./ChatBubble.vue";
import {reactive} from "vue";
import AiChatBubble from "./AiChatBubble.vue";
const messages=reactive([
  {
    send:'user',
    message:'你好呀11123123123123123112\n3123123123123123123123123123'
  },
  {
    send:'XiaoZhi',
    message:'2.创建 AiMessage.vue 组件'
  },
  {
    send:'XiaoZhi',
    message:'你好呀。'
  }

])
</script>

<template>
<div id="frame">
<!--  <div v-for="m in messages">-->
<!--    <chat-bubble  v-if="m.send=='user'" :message="m.message" id="chat"/>-->
<!--    <ai-chat-bubble v-if="m.send!='user'" :message="m.message"/>-->
<!--  </div>-->


</div>
</template>

<style scoped>
#frame {
  width: 1000px;
  height: 500px;
  background-color: rgb(255, 255, 255);
}
#chat {
  position: relative;

}
</style>
