<script setup lang="ts">
// 导入必要的Vue模块和其他依赖
import {computed, onMounted, ref, watch} from "vue";
import { useRoute } from "vue-router"; // 用于获取路由信息
import AiChatFrame from "../../components/AiChatFrame.vue"; // AI聊天框组件
import { useModelStore } from "@/store/ModelStore.ts"; // 状态管理
import { getModelInfo } from "../../api/manage.ts"; // 获取模型信息的API
import { getConversation } from "../../api/conversation.ts"; // 获取对话的API
import {OllamaRequest} from "../../utils/OllamaRequest.ts"
import Cookies from "js-cookie";
import {pinyin} from "pinyin-pro";
import router from "../../router"; // 处理cookie

// 使用状态管理中的modelStore
const modelStore = useModelStore();

// 获取当前路由对象以及查询参数中的conversation_id，并将其存储在conversationId中
const route = useRoute();
const conversationId = ref<string | undefined>(route.query.conversation_id as string);

// 定义响应式变量：conversation、modelInfo等
const conversation = ref();
const modelInfo = ref();
const ollamaUrl = Cookies.get("OllamaApi"); // 从cookie中获取Ollama API地址
const url = ref("");
const model = ref("");
const token = ref("");
const linkType = ref();
const messages = ref([]);

console.log(ollamaUrl); // 打印Ollama API URL到控制台

// 监听路由中的conversation_id变化，一旦变化则更新conversationId并重新初始化对话
watch(() => route.query.conversation_id, (newId) => {
  conversationId.value = newId as string;
  initConversation(); // 当路由参数变化时调用initConversation函数
});

// 组件挂载后的钩子函数
onMounted(() => {

  if (conversationId.value) {
     initConversation(); // 在组件加载时如果conversationId存在，则初始化对话
  }
  // 根据记忆切换前的的页面跳转
  if (modelStore.indexAichat) {
    router.push("/aichat?conversation_id=" + modelStore.indexAichat);
  }else {
    router.push("/chooseAi");
  }
    const doms= document.querySelectorAll(".semi-chat-container")
    doms.forEach(dom => {
      dom.style.overflow = "auto"
    })
})

// 初始化对话的异步函数
const initConversation = async () => {
  // 根据conversationId获取对话详情
  modelStore.indexAichat = conversationId.value;
  conversation.value = (await getConversation({ id: conversationId.value })).data.data;
  messages.value = conversation.value?conversation.value.modelMessageArrayList:[]; // 更新消息列表

  // 根据对话中的modelInfoId获取模型信息
  modelInfo.value = await getModelInfo({ id: conversation.value?conversation.value.modelInfoId:null });

  // 判断链接类型，并设置相应的url、token和model
  if (modelInfo.value.linkType == 1) {
    linkType.value = 1;
    url.value = modelInfo.value.modelAiOnline.aiApi;
    token.value = modelInfo.value.modelAiOnline.aiToken;
    model.value = modelInfo.value.modelAiOnline.aiName;
    console.log(token.value);
  } else {
    url.value = ollamaUrl + "/chat";
    model.value = modelInfo.value.ollamaModelName;
    linkType.value = 0;
  }



}

const roleInfo = computed(()=>{
  return {
    user: {
      name: '我',
      avatar: 'https://lf3-static.bytednsdoc.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/docs-icon.png'
    },
    assistant: {
      name: modelStore.topNav.modelName,
      avatar: 'https://lf3-static.bytednsdoc.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/other/logo.png'
    },
    system: {
      name: 'System',
      avatar: 'https://lf3-static.bytednsdoc.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/other/logo.png'
    }
  }
})


</script>

<template>
  <!-- 使用AiChatFrame组件展示聊天界面 -->
  <AiChatFrame
      v-model:messages="messages"
      :url="url"
      :model="model"
      :token="token"
      :role-info="roleInfo"
      :ai-type="linkType">
  </AiChatFrame>
</template>

<style scoped>
* {
  width: 100%;
}
</style>
