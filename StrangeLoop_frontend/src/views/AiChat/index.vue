<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import AiChatFrame from "../../components/AiChatFrame.vue";
import { useModelStore } from "@/store/ModelStore.ts";
import {getModelInfo} from "../../api/manage.ts";
import {getConversation} from "../../api/conversation.ts";
import Cookies from "js-cookie";

// 获取store和数据初始化
const modelStore = useModelStore();



// 获取路由和查询参数
const route = useRoute();
const conversationId = ref<string | undefined>(route.query.conversation_id as string);

const conversation = ref();
const modelInfo = ref();
const ollamaUrl = Cookies.get("OllamaApi");
const url = ref("");
const model = ref("");
const token = ref("");
const linkType = ref();
const messages = ref([]);
console.log(ollamaUrl)




// 监听路由变化并更新 conversationId
watch(() => route.query.conversation_id, (newId) => {
  conversationId.value = newId as string; // 当路由参数变化时更新 conversationId
  initConversation();
});

// 组件初始化
onMounted(() => {

});

const initConversation =async () => {
  conversation.value = (await getConversation({id: conversationId.value})).data.data;
  messages.value = conversation.value.modelMessageArrayList;
  console.log(messages.value);
  modelInfo.value = (await getModelInfo({id: conversation.value.modelInfoId}));

  if (modelInfo.value.linkType == 1) {
    linkType.value = 1;
    url.value = modelInfo.value.modelAiOnline.aiApi;
    token.value = modelInfo.value.modelAiOnline.aiToken;
    model.value = modelInfo.value.modelAiOnline.aiName;
    console.log( token.value)

  }else {
    url.value = ollamaUrl+"/chat";
    model.value = modelInfo.value.localmodelName;
    linkType.value =0;
  }
  console.log(url.value);

}
</script>

<template>
  <!-- 显示当前的 conversationId，查看更新效果 -->


  <!-- 聊天框组件，绑定动态参数 -->
  <AiChatFrame
      v-model:messages="messages"
      :url="url"
      :model="model"
      :token="token"
      :ai-type="linkType">
  </AiChatFrame>
</template>

<style scoped>
* {
  width: 100%;
}
</style>
