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
          <p v-if="modelInfoMange.linkType==0" class="text-gray-600 mb-1">基础模型: {{  modelInfoMange.localmodelName }}</p>
          <p v-if="modelInfoMange.linkType==1" class="text-gray-600 mb-1">基础模型: {{  modelInfoMange.modelAiOnline.aiName }}</p>
          <p v-if="modelInfoMange.linkType==0" class="text-gray-600 mb-1">模型版本: {{ modelInfoMange.localModelInfo.details.parameter_size }}</p>
          <p v-if="modelInfoMange.linkType==1" class="text-gray-600 mb-1">AI类型: {{ modelInfoMange.modelAiOnline.aiType ===1?'LLM': modelInfoMange.modelAiOnline.aiType ===2?'CHAT':'IMAGE' }}</p>
<!--          暂时注释 之后增加字段-->
<!--          <p class="text-gray-600">创建时间: {{ modelStore.currModelInfo.aiOnline.createdTime  }}</p>-->
        </div>
      </div>

      <!-- 参数设置区 -->
      <div class="mt-8">
        <h3 class="text-lg font-medium text-gray-900 mb-4">高级设置</h3>
        <div class="grid grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="system-prompt">系统提示词</label>
            <textarea id="system-prompt" v-model="modelInfoMange.modelFile.system" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" >{{ modelInfoMange.modelFile.system }}</textarea>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="mirostat">Mirostat</label>
            <select id="mirostat" v-model="modelInfoMange.modelFile.mirostat" class="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md">
              <option value="0">关闭</option>
              <option value="1">启用1.0</option>
              <option value="2">启用2.0</option>
            </select>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="mirostat-learning-rate">Mirostat学习速率</label>
            <input id="mirostat-learning-rate" v-model="modelInfoMange.modelFile.mirostatEat" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" max="1" min="0" step="0.1" type="number">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="mirostat-entropy">Mirostat熵值</label>
            <input id="mirostat-entropy" v-model="modelInfoMange.modelFile.mirostatTau" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" max="10" min="0" step="0.1" type="number">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="context-length">上下文长度</label>
            <input id="context-length" v-model="modelInfoMange.modelFile.numCtx" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" min="0" step="1" type="number">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="repeat-check-length">检查重复长度</label>
            <input id="repeat-check-length" v-model="modelInfoMange.modelFile.repeatLastN" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" min="0" step="1" type="number">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="repeat-penalty">重复token惩罚</label>
            <input id="repeat-penalty" v-model="modelInfoMange.modelFile.repeatPenalty" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" min="1" step="0.1" type="number">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="output-randomness">输出随机度</label>
            <input id="output-randomness" v-model="modelInfoMange.modelFile.temperature" class="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" max="1" min="0" step="0.1" type="number">
          </div>
        </div>
        <div class="mt-4 flex items-center">
          <label class="flex items-center" for="tail-free-sampling">
            <input id="tail-free-sampling" class="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded" type="checkbox">
            <span class="ml-2 text-sm text-gray-600">Tail Free Sampling</span>
          </label>
          <input id="tail-free-sampling-value" v-model="modelInfoMange.modelFile.tfsZ" class="ml-4 w-16 focus:ring-indigo-500 focus:border-indigo-500 shadow-sm sm:text-sm border-gray-300 rounded-md" max="1" min="0" step="0.1" type="number">
        </div>
        <div class="mt-4">
          <label class="block text-sm font-medium text-gray-700 mb-1" for="creativity-level">创意等级</label>
          <div class="flex items-center">
            <label>0.0</label>
            <input id="creativity-level" class="w-full" max="10" min="0" step="0.1" type="range" value="5.0">
            <label>10.0</label>
          </div>
        </div>
      </div>

      <!-- 数据展示区 -->
      <div class="mt-8">
        <h3 class="text-lg font-medium text-gray-900 mb-4">样本回复</h3>
        <div class="overflow-x-auto">
          <button class="bg-blue-50 flex left-0 hover:bg-blue-100 text-blue-700 font-bold py-2 px-4 rounded-md mt-4" @click="addModelFileEgmessage">添加样本回复</button>
          <table class="min-w-full  divide-y  divide-gray-200">
            <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider" scope="col">序号</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider" scope="col">客户消息</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider" scope="col">模型回复</th>
            </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="(item,index) in modelInfoMange.modelFile.modelFileEgmessageList" :key="index" class="text-left">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500" contenteditable="true">{{item.messageId}}</td>
              <td class="px-6 py-4 text-sm text-gray-500" contenteditable="true">{{item.userMessage}}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm font-medium" contenteditable="true">{{item.modelMessage}}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 保存按钮 -->
      <div class="mt-8 flex justify-center">
        <button class="!rounded-button whitespace-nowrap inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-md shadow-sm text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500" type="button" @click="clickSaveModelInfo">
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
import {ElMessage} from "element-plus";
import {addMessage} from "../../api/message.ts";
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
const initManager = async ()=>{
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
  if(res.data.code === 200){
    ElMessage.success("设置导入成功")
  }else {
    ElMessage.error("设置导入失败")
  }
}

const addModelFileEgmessage = () => {
  const listItem = new MessageList()
  modelInfoMange.value.modelFile.modelFileEgmessageList.push(listItem)
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
    system: any
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
class MessageList {
  messageId:string | number
  modelFileId: string | number
  modelMessage: string |""
  userMessage:string|""

  addMessage:()=>{

}

  constructor() {
    try {
      const length = modelInfoMange.value.modelFile.modelFileEgmessageList.length
      const item = modelInfoMange.value.modelFile.modelFileEgmessageList[length - 1]
      this.messageId = item.messageId +1
    }catch (e) {
      this.messageId = 1
    }
    this.modelFileId = modelInfoMange.value.modelFile.modelFileId
  }
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

