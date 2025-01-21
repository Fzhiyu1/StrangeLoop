<script setup lang="ts">
import {reactive, ref,watchEffect} from "vue";
import ConversationList from "@/components/ConversationList.vue";
import DialogCom from "../../views/AiChat/DialogCom.vue";
import ModelList from "@/components/ModelList.vue";
import router from "@/router";
import {useModelStore} from "@/store/ModelStore.ts";
import {getModuleList} from "../../api/module.ts";

interface Module{
  topBtnName:string
  searchPlaceholder:string
  listComp:any
  bottomBtnName:string
}

const modelStore = useModelStore()
const activeMenu = ref<number>(1)
const dialog = ref<boolean>(false)
const isShow = ref<boolean>(false)
const leftList = ref<any>(null)
const module = reactive<Module[]>([
    {
      topBtnName:"新增对话",
      searchPlaceholder:"搜索历史记录",
      listComp:null,
      bottomBtnName:"管理对话记录"
    },
  {
    topBtnName:"新增模型",
    searchPlaceholder:"搜索历史记录",
    listComp:null,
    bottomBtnName:"管理基底模型"
  },
  {
    topBtnName:"",
    searchPlaceholder:"",
    listComp:null,
    bottomBtnName:""
  }
])

const choose = (index:number) => {
  isShow.value = false
  index ===0?router.push("/chooseAi"):index ===1?router.push("/chooseModel"):null
}
const search = () => {

}

const manageToggle = () => {
  if(modelStore.modelIndex===0){
    dialog.value = true
    return
  }
  if(modelStore.modelIndex===1){
    router.push("/ManageModel")
  }
}

watchEffect(async ()=>{
  if(modelStore.modelIndex===1){
    const res = await getModuleList()
    leftList.value = res.data.models
    console.log(leftList)
  }
})

</script>

<template>
<div class="container">
  <div class="top">
      <div class="box">
          <div class="addConv" @click="choose(modelStore.modelIndex)" :style="isShow?'width:60px':''">
            <svg  class="svg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024"><path fill="currentColor" d="M480 480V128a32 32 0 0 1 64 0v352h352a32 32 0 1 1 0 64H544v352a32 32 0 1 1-64 0V544H128a32 32 0 0 1 0-64z"></path></svg>
            <span v-show="!isShow">{{module[modelStore.modelIndex].topBtnName}}</span>
          </div>
           <div class="search" :class="!isShow?'hoverClass':''" @click="isShow = true" :style="isShow?'width:calc(100% - 60px);outline: 1px solid #d7d7d7;':''">
             <svg :style="isShow?'border:none':''"  class="svg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024"><path fill="currentColor" d="m795.904 750.72 124.992 124.928a32 32 0 0 1-45.248 45.248L750.656 795.904a416 416 0 1 1 45.248-45.248zM480 832a352 352 0 1 0 0-704 352 352 0 0 0 0 704"></path></svg>
              <input @keyup.enter="search" v-show="isShow"  type="text" :placeholder="module[modelStore.modelIndex].searchPlaceholder">
           </div>
      </div>
  </div>

  <hr color="#d7d7d7">

  <div class="middle">
    <ConversationList v-if="modelStore.modelIndex === 0" :class-style="activeMenu === i" @click="activeMenu = i" v-for="i in 3" :key="i"></ConversationList>
    <ModelList v-if="modelStore.modelIndex === 1" :aiName="item.name" :class-style="activeMenu === index" @click="activeMenu = index" v-for="(item,index) in leftList" :key="index"></ModelList>
  </div>

  <hr color="#d7d7d7">
  <div class="management">
    <div @click="manageToggle" class="managerBtn">
      <svg class="svg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024"><path fill="currentColor" d="M389.44 768a96.064 96.064 0 0 1 181.12 0H896v64H570.56a96.064 96.064 0 0 1-181.12 0H128v-64zm192-288a96.064 96.064 0 0 1 181.12 0H896v64H762.56a96.064 96.064 0 0 1-181.12 0H128v-64zm-320-288a96.064 96.064 0 0 1 181.12 0H896v64H442.56a96.064 96.064 0 0 1-181.12 0H128v-64z"></path></svg>
      <span>{{ module[modelStore.modelIndex].bottomBtnName }}</span>
    </div>
  </div>

<!--  管理面板-->
  <component class="customDialog" @close="dialog = false" v-show="dialog" :is="DialogCom"></component>
</div>
</template>

<style scoped>
.customDialog {
  position: fixed;
  z-index: 999;

}

  .container {
    width: 25%;
    display: flex;
    flex-direction: column;
    background-color: #f3f3f3;

    .top {
      height: 160px;
      display: flex;
      flex: 1.5;
      flex-direction: row;
      justify-content: center;
      align-items: center;
      font-size: 20px;
      font-weight: bolder;

      .box {
          display: flex;
          width: 90%;
          align-items: center;


          .addConv {
            border-radius: 30px;
            height: 60px;
            background-color: #5f6cab;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-wrap: nowrap;
            width: calc(100% - 60px);
            margin-right: 10px;
            color: #fff;
            transition:.1s linear;
            overflow: hidden;
            span {
              overflow: hidden;
            }
            &:hover {
              cursor: pointer;
              background:  #4152b4;
            }
          }
        
          .search {
            display: flex;
            align-items: center;
            width: 60px;
            height: 60px;
            transition:.1s linear;
            overflow: hidden;
            position: relative;
            border-radius: 30px;
            input{
              width: 100%;
              height: 100%;
              border-radius: 30px;
              outline: none;
              border: none;
              padding-left: 60px;
              font-size: large;
            }

            .svg{
              padding: 12px;
              border-radius: 50%;
              background-color: #fff;
              border: 1px solid #797979;
              position: absolute;
              left: 0;
            }

          }
      }
    }

    .middle{
      flex: 7;
    }

    .management {
      flex: 1.5;
      display: flex;
      justify-content: center;
      align-items: center;
      .managerBtn {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 60px;
        width: 90%;
        border-radius: 30px;
        border: 1px solid #d7d7d7;
        font-weight: bolder;
        &:hover {
          cursor: pointer;
          background-color: #e7e7e7;
        }
      }
      span {
        line-height: 60px;
        font-size: large;
      }
    }

  }



  .svg {
    width: 30px;
    height: 30px;
  }

  hr {
    width: 80%;
    border-color: #d7d7d7;
    border-width: 1px;
  }

  .hoverClass {
    &:hover {
      cursor: pointer;
    }
  }
</style>