<script setup lang="ts">

import AiChatFrame from "../../components/AiChatFrame.vue";
import {ref} from "vue";
const messages = ref([

]);


const showArray=()=>{
  console.log(messages.value);
}
</script>

<template>
<AiChatFrame v-model:messages="messages" :url="'http://127.0.0.1:11434/api/chat'" :model="'XiaoZhi'" :ai-type="0"></AiChatFrame>
</template>

<style scoped>

</style>
