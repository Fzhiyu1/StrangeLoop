

<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {listModelInfo} from "@/api/manage.ts";
import {addConversation} from "@/api/conversation.ts";
import {getCurrentUser} from "@/api/login.ts";
import {listModelAiOline} from "@/api/manageOl.ts";

const selectedAI = ref('');
const aiOptions = ref([
  {
    "modelId": 1,
    "modelVersion": "GPT_3",
    "modelName": "小智",
    "modelFileId": 1,
    "disable": 0,
    "userId": 1,
    "aiolId": 3,
    "linkType": 0,
    "description": "就这样asdasd",
    "modelFile": null,
    "modelAiOnline": null
  }
]);

const startChat = async () => {
  if (selectedAI.value) {

   const res = await getCurrentUser()
    await addConversation({data:{
      userId: res.data.data.id,
      aiName: selectedAI.value,
      title:"新会话"
    }})
  }
};

onMounted(async()=>{
  const res =await listModelInfo({})
  // const resOnline = await listModelAiOline({})
  aiOptions.value = []
  aiOptions.value = [...res.data.data]
})
</script>

<template>
  <div class="bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center px-4">
    <div class="max-w-md w-full bg-white rounded-lg shadow-lg p-8">
      <h1 class="text-3xl font-bold text-center text-gray-800 mb-6">选择一个专属于你的 AI，开始聊天吧！</h1>

      <div class="mb-6">
        <label for="ai-select" class="block text-lg font-medium text-gray-700 mb-2">选择 AI：</label>
        <div class="relative">
          <select
              id="ai-select"
              v-model="selectedAI"
              class="block w-full pl-3 pr-10 py-2 text-lg border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-lg rounded-md"
          >
            <option value="">请选择 AI 助手</option>
            <option v-for="ai in aiOptions" :key="ai.modelId" :value="ai.modelName ||ai.aiName">
              {{ ai.modelName || ai.aiName }} | {{ ai.modelVersion || ai.aiVersion }}
            </option>
          </select>
          <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
            <svg class="h-4 w-4" fill="currentColor" viewBox="0 0 20 20">
<!--              <path fill-rule="pnpmevenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />-->
            </svg>
          </div>
        </div>
      </div>

      <button
          @click="startChat"
          class="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded-md transition duration-300 ease-in-out transform hover:scale-105 !rounded-button whitespace-nowrap"
          :disabled="!selectedAI"
      >
        开始
      </button>
    </div>
  </div>
</template>

<style scoped>
@tailwind base;
@tailwind components;
@tailwind utilities;

body {
  font-family: 'Noto Sans SC', sans-serif;
}

select {
  appearance: none;
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 20 20'%3e%3cpath stroke='%236b7280' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='M6 8l4 4 4-4'/%3e%3c/svg%3e");
  background-position: right 0.5rem center;
  background-repeat: no-repeat;
  background-size: 1.5em 1.5em;
  padding-right: 2.5rem;
}

select::-ms-expand {
  display: none;
}

button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.bg-gradient-to-br {
  background-image: linear-gradient(to bottom right, var(--tw-gradient-stops));
}
</style>

