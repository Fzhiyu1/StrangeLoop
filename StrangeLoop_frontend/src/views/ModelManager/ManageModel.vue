
<script lang="ts" setup>
import { ref } from 'vue';
const apiUrl = ref('http://127.0.0.1:11434/api');
const modelList = ref([
  {
    name: 'qwen2.5',
    version: '7b',
    size: '4.5G'
  }
]);

const connectOllama = () => {
  // 连接 Ollama 的逻辑
  console.log('Connecting to Ollama...');
};

const deleteModel = (modelName: string) => {
  // 删除模型的逻辑
  console.log('Deleting model:', modelName);
};
</script>

<template>
  <div class="bg-gray-50 p-8">
    <div class="min-w-full text-[16px] max-h-full overflow-y-auto">
      <!-- 顶部操作区域 -->
      <div class="mb-6 rounded-lg bg-white p-6 shadow-sm ">
        <div class="mb-6">
          <label class="mb-2 block text-gray-700">Ollama API 地址:</label>
          <div class="relative">
            <input
                v-model="apiUrl"
                type="text"
                class="w-full rounded-lg border-gray-200 px-4 py-2.5 focus:border-blue-500 focus:ring-blue-500"
                placeholder="请输入 API 地址，例如: http://127.0.0.1:11434/api"
            />
          </div>
        </div>

        <button
            @click="connectOllama"
            class="!rounded-button flex items-center justify-center space-x-2 bg-indigo-600 px-6 py-2.5  font-medium text-white hover:bg-indigo-700 whitespace-nowrap"
        >
          <i class="fas fa-link"></i>
          <span>链接 Ollama</span>
        </button>

        <div class="mt-4 text-gray-600">
          <p>注意：</p>
          <p class="mt-1">当本地 Ollama 模型列表无法自动显示的时候，可以检查 Ollama 是否已经安装，端口是否有被屏蔽，或者更改 Ollama API 地址来进行尝试。</p>
        </div>
      </div>

      <!-- 模型列表区域 -->
      <div class="mb-6 rounded-lg bg-white p-6 shadow-sm">
        <h2 class="mb-4 text-lg font-medium">本地 Ollama 模型列表</h2>
        <div>
          <table class="min-w-full divide-y divide-gray-200">
            <thead>
            <tr class="bg-blue-50 ">
              <th class="px-4 py-3 text-left  font-medium text-gray-500">序号</th>
              <th class="px-4 py-3 text-left  font-medium text-gray-500">模型名称</th>
              <th class="px-4 py-3 text-left  font-medium text-gray-500">模型版本</th>
              <th class="px-4 py-3 text-left  font-medium text-gray-500">大小</th>
              <th class="px-4 py-3 text-left  font-medium text-gray-500">操作</th>
            </tr>
            </thead>
            <tbody class="divide-y divide-gray-200">
            <tr class="text-left" v-for="(model, index) in modelList" :key="index">
              <td class="whitespace-nowrap px-4 py-3  text-gray-900">{{ index + 1 }}</td>
              <td class="whitespace-nowrap px-4 py-3  text-gray-900">{{ model.name }}</td>
              <td class="whitespace-nowrap px-4 py-3  text-gray-900">{{ model.version }}</td>
              <td class="whitespace-nowrap px-4 py-3  text-gray-900">{{ model.size }}</td>
              <td class="whitespace-nowrap px-4 py-3 ">
                <button
                    @click="deleteModel(model.name)"
                    class="!rounded-button inline-flex items-center justify-center text-red-600 hover:text-red-700 whitespace-nowrap"
                >
                  <svg class="w-[15px] h-[15px]"  xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024"><path fill="currentColor" d="M352 192V95.936a32 32 0 0 1 32-32h256a32 32 0 0 1 32 32V192h256a32 32 0 1 1 0 64H96a32 32 0 0 1 0-64zm64 0h192v-64H416zM192 960a32 32 0 0 1-32-32V256h704v672a32 32 0 0 1-32 32zm224-192a32 32 0 0 0 32-32V416a32 32 0 0 0-64 0v320a32 32 0 0 0 32 32m192 0a32 32 0 0 0 32-32V416a32 32 0 0 0-64 0v320a32 32 0 0 0 32 32"></path></svg>
                </button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 在线模型管理区域 -->
      <div class="rounded-lg bg-white p-6 shadow-sm">
        <h2 class="mb-4 text-lg font-medium">在线模型管理</h2>
        <div class="h-64 rounded-lg border-2 border-dashed border-gray-200 p-4">
          <div class="flex  items-center justify-center">
            <p class=" text-gray-500">暂无可用的在线模型</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
@tailwind base;
@tailwind components;
@tailwind utilities;
</style>