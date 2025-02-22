<script setup lang="ts">
import {useModelStore} from "@/store/ModelStore.ts";
import DialogComp from "../../views/ModelManager/DialogComp.vue";
import {ref} from "vue";
import {useRouter} from "vue-router";
const router = useRouter();
const isShow = ref(false);
const modelStore = useModelStore()

</script>

<template>
  <div class="container">
    <DialogComp @cancel="isShow = false" @confirm="isShow = false" v-show="isShow" style="position:absolute;z-index: 999"></DialogComp>
    <template v-if="modelStore.modelIndex === 0">
      {{modelStore.topNav.modelName}} | {{modelStore.topNav.modelVersion}}
    </template>
    <template v-if="modelStore.modelIndex === 1 && router.currentRoute.value.path !== '/ManageModel'">
      {{modelStore.topNav.modelName}} | {{modelStore.topNav.modelVersion}}
      <div class="management">
        <div class="managerBtn" @click="isShow = true">
          <svg class="svg"  xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024"><path fill="currentColor" d="M832 512a32 32 0 1 1 64 0v352a32 32 0 0 1-32 32H160a32 32 0 0 1-32-32V160a32 32 0 0 1 32-32h352a32 32 0 0 1 0 64H192v640h640z"></path><path fill="currentColor" d="m469.952 554.24 52.8-7.552L847.104 222.4a32 32 0 1 0-45.248-45.248L477.44 501.44l-7.552 52.8zm422.4-422.4a96 96 0 0 1 0 135.808l-331.84 331.84a32 32 0 0 1-18.112 9.088L436.8 623.68a32 32 0 0 1-36.224-36.224l15.104-105.6a32 32 0 0 1 9.024-18.112l331.904-331.84a96 96 0 0 1 135.744 0z"></path></svg>
          <span style="text-wrap: nowrap">修改模型</span>
        </div>
      </div>
    </template>
    <template v-if="router.currentRoute.value.path === '/ManageModel'">
      管理基层模型
    </template>
  </div>
</template>

<style scoped>
.container {
  background-color: #fff;
  border-top-right-radius: 20px;
  font-size: large;
  font-weight: bolder;
  height: 100%;
  display: flex;
  align-items: center;
  padding-left: 2rem;
}

.management {
  margin-left: 2rem;
  flex: 1.5;
  display: flex;
  align-items: center;
  .managerBtn {
    display: flex;
    color: #fff;
    background: #5f6cab;
    justify-content: center;
    align-items: center;
    height: 60px;
    width: 15%;
    border-radius: 30px;
    border: 1px solid #d7d7d7;
    font-weight: bolder;
    &:hover {
      cursor: pointer;
      background-color: #4152b4;
    }
  }
  span {
    line-height: 60px;
    font-size: large;
  }
  .svg {
    width: 30px;
    height: 30px;
  }
}
</style>

