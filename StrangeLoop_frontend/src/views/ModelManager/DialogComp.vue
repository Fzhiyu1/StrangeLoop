<script lang="ts" setup>
import { ref } from 'vue';
import {getModelInfo, listModelInfo, updateModelInfo} from "../../api/manage.ts";
import {ElMessage} from "element-plus";

const isDropdownOpen = ref(false);

const emit = defineEmits(['confirm','cancel'])
const props = defineProps({
  id:{
    type:Number
  }
})

const form = ref({
  modelId:props.id,
  modelName:"",
  linkType:0,
  description:"",
  localmodelName:""
})
const modelOptions = ref([])

const init = () => {
  getModelInfo({id:props.id}).then(res=>{
    // form.value = {res.localmodelName,}
    form.value = {
      modelId:props.id,
      modelName:res.modelName,
      linkType:res.linkType,
      description:res.description,
      localmodelName:res.localmodelName
    }
  })
  listModelInfo({data:{}}).then(res2=>{
    modelOptions.value = res2.data.data.map(item=>item.modelVersion)
  })
  // modelOptions
}



const toggleModel = () => {
  form.value.linkType = !form.value.linkType;
};

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};

const selectModel = (model: string) => {
  form.value.localmodelName = model;
  isDropdownOpen.value = false;
};

const handleConfirm = () => {
  // 处理确认逻辑
  emit('confirm')
  updateModelInfo({data:form.value}).then(res=>{
    if(res.status === 200){
      ElMessage.success('修改成功')
      // getModelInfo()
    }else {
      ElMessage.error('修改失败')
    }
  })
};

const handleCancel = () => {
  emit('cancel')
  // 处理取消逻辑
};



defineExpose({
  init,
})


</script>

<template>
  <div class="absolute inset-0 bg-gray-500 bg-opacity-25 flex items-center justify-center w-[100vw]">
    <div class="bg-white rounded-lg w-[480px] shadow-lg">
      <!-- 标题 -->
      <div class="px-6 py-4 border-b border-gray-200">
        <h2 class="text-lg font-medium">修改模型</h2>
      </div>

      <!-- 表单内容 -->
      <div class="p-6">
        <div class="space-y-4">
          <!-- 模型名称 -->
          <div class="space-y-2">
            <label class="block text-sm font-medium text-gray-700">模型名称：</label>
            <input
                v-model="form.modelName"
                type="text"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="请输入模型名称"
            />
          </div>

          <!-- 基础模型 -->
          <div class="space-y-2">
            <div class="flex items-center justify-between">
              <label class="block text-sm font-medium text-gray-700">基础模型：</label>
              <div class="flex items-center space-x-2">
                <span class="text-sm text-gray-600">本地模型</span>
                <button
                    class="relative inline-flex h-6 w-11 flex-shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out focus:outline-none"
                    :class="[form.linkType===0 ? 'bg-blue-600' : 'bg-gray-200']"
                    @click="toggleModel"
                >
                  <span
                      class="pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow ring-0 transition duration-200 ease-in-out"
                      :class="[form.linkType===1 ? 'translate-x-5' : 'translate-x-0']"
                  />
                </button>
                <span class="text-sm text-gray-600">云端模型</span>
              </div>
            </div>
            <div class="relative">
              <button
                  @click="toggleDropdown"
                  class="w-full px-3 py-2 text-left border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 flex items-center justify-between"
              >
                <span>{{ form.localmodelName }}</span>
                <i class="fas fa-chevron-down text-gray-400"></i>
              </button>
              <div v-if="isDropdownOpen" class="absolute z-10 w-full mt-1 bg-white border border-gray-300 rounded-md shadow-lg">
                <div
                    v-for="model in modelOptions"
                    :key="model"
                    class="px-3 py-2 hover:bg-gray-100 cursor-pointer"
                    @click="selectModel(model)"
                >
                  {{ model }}
                </div>
              </div>
            </div>
          </div>

          <!-- 模型描述 -->
          <div class="space-y-2">
            <label class="block text-sm font-medium text-gray-700">模型描述：</label>
            <textarea
                v-model="form.description"
                rows="4"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="请输入模型描述"
            ></textarea>
          </div>
        </div>
      </div>

      <!-- 按钮组 -->
      <div class="px-6 py-4 bg-gray-50 rounded-b-lg flex justify-end space-x-4">
        <button
            @click="handleCancel"
            class="!rounded-button px-4 py-2 text-gray-700 bg-white border border-gray-300 hover:bg-gray-50 whitespace-nowrap"
        >
          取消
        </button>
        <button
            @click="handleConfirm"
            class="!rounded-button px-4 py-2 text-white bg-blue-600 hover:bg-blue-700 whitespace-nowrap"
        >
          确认
        </button>
      </div>
    </div>
  </div>
</template>


<style scoped>
@tailwind base;
@tailwind components;
@tailwind utilities;
.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.3s ease;
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}

textarea {
  resize: none;
}

input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
</style>

