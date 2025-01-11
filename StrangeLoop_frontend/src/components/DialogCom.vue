<!-- 管理对话记录弹窗 -->
<script lang="ts" setup>
import { ref, computed } from 'vue';

const dialogues = ref([
  { title: '学习方法交流会', date: '2024-12-07 16:40', selected: false },
  { title: '回答', date: '2024-12-07 16:40', selected: false },
  { title: 'java int的取值范围', date: '2024-12-07 16:40', selected: false },
  { title: '回答', date: '2024-12-07 16:40', selected: false },
  { title: '学习方法交流会', date: '2024-12-07 16:40', selected: false },
  { title: '回答', date: '2024-12-07 16:40', selected: false },
  { title: 'java int的取值范围', date: '2024-12-07 16:40', selected: false },
  { title: '回答', date: '2024-12-07 16:40', selected: false },
]);

const searchQuery = ref('');

const filteredDialogues = computed(() => {
  return dialogues.value.filter(dialogue =>
      dialogue.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const deleteDialogue = (index: number) => {
  dialogues.value.splice(index, 1);
};

const deleteSelected = () => {
  dialogues.value = dialogues.value.filter(dialogue => !dialogue.selected);
};

const cancelSelected = () => {
  dialogues.value.forEach(dialogue => dialogue.selected = false)
}

const emit = defineEmits(["close"])
</script>

<template>
  <div class="flex justify-center items-center  overflow-hidden bg-gray-500 bg-opacity-25 min-w-full min-h-screen ">
    <div class="bg-white w-[1000px] rounded-lg shadow-lg overflow-hidden relative ">
      <svg @click="emit('close')" class="absolute right-2 top-2 cursor-pointer w-10 h-10"  xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024"><path fill="currentColor" d="M764.288 214.592 512 466.88 259.712 214.592a31.936 31.936 0 0 0-45.12 45.12L466.752 512 214.528 764.224a31.936 31.936 0 1 0 45.12 45.184L512 557.184l252.288 252.288a31.936 31.936 0 0 0 45.12-45.12L557.12 512.064l252.288-252.352a31.936 31.936 0 1 0-45.12-45.184z"></path></svg>
      <!-- 标题栏 -->
      <div class="flex justify-between items-center p-4 border-b border-gray-200">
        <h2 class="text-xl font-semibold">管理对话记录：共 {{ dialogues.length }} 条</h2>
        <button class="text-gray-500 hover:text-gray-700">
          <i class="fas fa-times"></i>
        </button>
      </div>

      <!-- 搜索栏 -->
      <div class="p-4 border-b border-gray-200">
        <div class="relative">
          <input
              type="text"
              placeholder="搜索对话记录"
              class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-pink-500"
              v-model="searchQuery"
          />
          <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
        </div>
      </div>

      <!-- 对话列表 -->
      <div class="max-h-[400px] overflow-y-auto">
        <div v-for="(dialogue, index) in filteredDialogues" :key="index" class="flex items-center p-4 hover:bg-gray-50 border-b border-gray-200 last:border-b-0">
          <input type="checkbox" :id="'dialogue-' + index" v-model="dialogue.selected" class="mr-4 h-5 w-5 text-pink-500 focus:ring-pink-400 border-gray-300 rounded">
          <label :for="'dialogue-' + index" class="flex-grow cursor-pointer">
            <span class="font-medium">{{ dialogue.title }}</span>
          </label>
          <span class="text-sm text-gray-500 mr-4">{{ dialogue.date }}</span>
          <button @click="deleteDialogue(index)" class="text-gray-500 hover:text-red-500">
            <i class="fas fa-trash-alt"></i>
          </button>
        </div>
      </div>

      <!-- 底部按钮 -->
      <div class="flex justify-between p-4 border-t border-gray-200">
        <button @click="cancelSelected" class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-100 !rounded-button whitespace-nowrap">
          取消选中
        </button>
        <button @click="deleteSelected" class="px-4 py-2 bg-pink-500 text-white rounded-lg hover:bg-pink-600 !rounded-button whitespace-nowrap">
          删除对话
        </button>
      </div>
    </div>
  </div>
</template>


<style scoped>
@tailwind base;
@tailwind components;
@tailwind utilities;
/* 移除 input number 的默认箭头 */
input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type="number"] {
  -moz-appearance: textfield;
}

/* 自定义复选框样式 */
input[type="checkbox"] {
  appearance: none;
  -webkit-appearance: none;
  width: 1.25rem;
  height: 1.25rem;
  border: 2px solid #d1d5db;
  border-radius: 0.25rem;
  outline: none;
  cursor: pointer;
  position: relative;
}

input[type="checkbox"]:checked {
  background-color: #ec4899;
  border-color: #ec4899;
}

input[type="checkbox"]:checked::before {
  content: '\2714';
  font-size: 1rem;
  color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>

