<!-- 代码已包含 CSS：使用 TailwindCSS , 安装 TailwindCSS 后方可看到布局样式效果 -->

<template>
  <div v-if="modelInfoMange" class="flex h-full">
    <div class=" bg-white p-5 rounded-lg shadow-md w-full  overflow-y-scroll">
      <!-- 模型信息区 -->
      <div class="flex justify-start">
        <div class="w-24 h-24 bg-gray-200 rounded-full flex items-center justify-center text-3xl font-bold text-gray-600">
          AI
        </div>
        <div class="flex-1 text-left">
          <h2 class="text-2xl font-bold text-gray-900 mb-2">模型名称:{{ modelInfoMange.modelName }} </h2>
          <p class="text-gray-600 mb-1">模型描述: {{ modelInfoMange.description }}</p>
          <p class="text-gray-600 mb-1" v-if="modelInfoMange.linkType==0">基础模型: {{  modelInfoMange.localmodelName }}</p>
          <p class="text-gray-600 mb-1" v-if="modelInfoMange.linkType==1">基础模型: {{  modelInfoMange.modelAiOnline.aiName }}</p>
          <p class="text-gray-600 mb-1" v-if="modelInfoMange.linkType==0">模型版本: {{ modelInfoMange.localModelInfo.details.parameter_size }}</p>
          <p class="text-gray-600 mb-1" v-if="modelInfoMange.linkType==1">AI类型: {{ modelInfoMange.modelAiOnline.aiType ===1?'LLM': modelInfoMange.modelAiOnline.aiType ===2?'CHAT':'IMAGE' }}</p>
<!--          暂时注释 之后增加字段-->
<!--          <p class="text-gray-600">创建时间: {{ modelStore.currModelInfo.aiOnline.createdTime  }}</p>-->
        </div>
      </div>

      <!-- 参数设置区 -->
      <div class="mt-8">
        <h3 class="text-lg font-medium text-gray-900 mb-4">高级设置</h3>
        <div class="grid grid-cols-2 gap-6">
          <div>
            <label for="system-prompt" class="block text-sm font-medium text-gray-700 mb-1">系统提示词</label>
            <select id="system-prompt" class="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md">
              <option>默认</option>
              <option>专业</option>
              <option>友好</option>
            </select>
          </div>
          <div>
            <label for="mirostat" class="block text-sm font-medium text-gray-700 mb-1">Mirostat</label>
            <select v-model="modelInfoMange.modelFile.mirostat" id="mirostat" class="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md">
              <option value="0">关闭</option>
              <option value="1">启用1.0</option>
              <option value="2">启用2.0</option>
            </select>
          </div>
          <div>
            <label for="mirostat-learning-rate" class="block text-sm font-medium text-gray-700 mb-1">Mirostat学习速率</label>
            <input type="number" id="mirostat-learning-rate" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" v-model="modelInfoMange.modelFile.mirostatEat" step="0.1" min="0" max="1">
          </div>
          <div>
            <label for="mirostat-entropy" class="block text-sm font-medium text-gray-700 mb-1">Mirostat熵值</label>
            <input type="number" id="mirostat-entropy" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" v-model="modelInfoMange.modelFile.mirostatTau" step="0.1" min="0" max="10">
          </div>
          <div>
            <label for="context-length" class="block text-sm font-medium text-gray-700 mb-1">上下文长度</label>
            <input type="number" id="context-length" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" v-model="modelInfoMange.modelFile.numCtx" step="1" min="0">
          </div>
          <div>
            <label for="repeat-check-length" class="block text-sm font-medium text-gray-700 mb-1">检查重复长度</label>
            <input type="number" id="repeat-check-length" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" v-model="modelInfoMange.modelFile.repeatLastN" step="1" min="0">
          </div>
          <div>
            <label for="repeat-penalty" class="block text-sm font-medium text-gray-700 mb-1">重复token惩罚</label>
            <input type="number" id="repeat-penalty" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" v-model="modelInfoMange.modelFile.repeatPenalty" step="0.1" min="1">
          </div>
          <div>
            <label for="output-randomness" class="block text-sm font-medium text-gray-700 mb-1">输出随机度</label>
            <input type="number" id="output-randomness" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" v-model="modelInfoMange.modelFile.temperature" step="0.1" min="0" max="1">
          </div>
        </div>
        <div class="mt-4 flex items-center">
          <label for="tail-free-sampling" class="flex items-center">
            <input id="tail-free-sampling" type="checkbox" class="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded">
            <span class="ml-2 text-sm text-gray-600">Tail Free Sampling</span>
          </label>
          <input type="number" id="tail-free-sampling-value" class="ml-4 w-16 focus:ring-indigo-500 focus:border-indigo-500 shadow-sm sm:text-sm border-gray-300 rounded-md" v-model="modelInfoMange.modelFile.tfsZ" step="0.1" min="0" max="1">
        </div>
        <div class="mt-4">
          <label for="creativity-level" class="block text-sm font-medium text-gray-700 mb-1">创意等级</label>
          <div class="flex items-center">
            <label>0.0</label>
            <input type="range" id="creativity-level" class="w-full" min="0" max="10" step="0.1" value="5.0">
            <label>10.0</label>
          </div>
        </div>
      </div>

      <!-- 数据展示区 -->
      <div class="mt-8">
        <h3 class="text-lg font-medium text-gray-900 mb-4">样本回复</h3>
        <div class="overflow-x-auto">
          <table class="min-w-full  divide-y  divide-gray-200">
            <thead class="bg-gray-50">
            <tr>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">序号</th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">客户消息</th>
              <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">模型回复</th>
            </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
            <tr class="text-left" v-for="(item,index) in modelInfoMange.modelFile.modelFileEgmessageList" :key="index">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{item.messageId}}</td>
              <td class="px-6 py-4 text-sm text-gray-500">{{item.userMessage}}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">{{item.modelMessage}}</td>
            </tr>

            </tbody>
          </table>
        </div>
      </div>

      <!-- 保存按钮 -->
      <div class="mt-8 flex justify-center">
        <button @click="clickSaveModelInfo" type="button" class="!rounded-button whitespace-nowrap inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-md shadow-sm text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
          保存设置
        </button>
      </div>
    </div>
  </div>
  <div v-else class="h-full w-full"></div>
</template>

<script lang="ts" setup>
import {ref, onMounted, watch} from 'vue';
import {useModelStore} from "@/store/ModelStore.ts";
import {getModelInfo} from "@/api/manage.ts";
import {useRoute} from "vue-router";
import Cookies from "js-cookie";
import axios from "axios";
import {updateModelInfoAndModelFile} from "../../api/manageDetail.ts";
// 这里可以添加需要的响应式数据和方法
const creativityLevel = ref(5.0);

const route = useRoute();

const modelId = ref<string | undefined>(route.query.conversation_id as string);
const modelInfoMange = ref<ModelInfoMangeType|null>();

watch(() => route.query.modelId, (newId) => {
  modelId.value = newId as string;
  if (newId) {
    initManager();
  }// 当路由参数变化时调用initConversation函数
});
// 初始化设置面板
const initManager=async ()=>{
  let modelInfo = await getModelInfo({id:modelId.value});
  if (modelInfo.localmodelName) {
    modelInfo.localModelInfo=await showOllama(modelInfo.localmodelName);
  }
  modelInfoMange.value = modelInfo;
  console.log(modelInfoMange.value)
}
// 请求ollama接口
const ollamaUrl = Cookies.get("OllamaApi");
const showOllama = async (model) => {
  let res = (await axios.post(ollamaUrl + "/show", {
    "model": model
  })).data;
  return res;
}


const clickSaveModelInfo= async ()=>{
  console.log(modelInfoMange.value);
  let res = await updateModelInfoAndModelFile({data: modelInfoMange.value});
  console.log(res)
}

interface ModelInfoMangeType{
  aiolId: any
  baseModelName: any
  description: any
  disable: any
  linkType: any
  localModelInfo: {
    details:any
    model_info:any
    modelfile:any
    modified_at: any
    parameters: any
    system: any
    template:any
  } | null
  localmodelName: any
  modelAiOnline: any
  modelFile: {
    minP: any
    mirostat: any
    mirostatEat:any
    mirostatTau: any
    modelFileEgmessageList: any|[]
modelFileId:any
numCtx:any
numPredict:any
repeatLastN:any
repeatPenalty:any
seed:any
stop:any
temperature:any
tfsZ:any
topP:any} |any
  modelFileId:any
  modelId:any
  modelName: any
  modelVersion: any
  userId: any
}

</script>

<style scoped>
@tailwind base;
@tailwind components;
@tailwind utilities;
/* 自定义样式 */
.ai-assistant__card {
  transition: all 0.3s ease;
}

.ai-assistant__card:hover {
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
}

/* 确保输入框没有默认边框 */
input[type="number"] {
  -moz-appearance: textfield;
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* 自定义滑块样式 */
input[type="range"] {
  -webkit-appearance: none;
  width: 100%;
  height: 5px;
  border-radius: 5px;
  background: #ddd;
  outline: none;
  opacity: 0.7;
  transition: opacity .2s;
}

input[type="range"]:hover {
  opacity: 1;
}

input[type="range"]::-webkit-slider-thumb {
  -webkit-appearance: none;
  appearance: none;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: #4f46e5;
  cursor: pointer;
}

input[type="range"]::-moz-range-thumb {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: #4f46e5;
  cursor: pointer;
}
</style>

