<script setup lang="ts">
import {computed, onMounted, onUpdated, reactive, ref, watch, watchEffect} from "vue";
import ConversationList from "@/components/ConversationList.vue";
import DialogCom from "@/views/AiChat/DialogCom.vue";
import ModelList from "@/components/ModelList.vue";
import router from "@/router";
import {useModelStore} from "@/store/ModelStore.ts";
import {listConversation} from "@/api/conversation.ts";
import SvgIcon from "@/components/SvgIcon.vue";
// import {getModuleList} from "@/api/module.ts";

interface Module{
  topBtnName:string
  searchPlaceholder:string
  listComp:any
  bottomBtnName:string
}

const modelStore = useModelStore()

const conversationsActiveMenu = ref<number>(-1)
const modelActiveMenu = ref<number>(-1)
const dialog = ref<boolean>(false)
const isShow = ref<boolean>(false)
const leftList = ref<any>(null)
const searchText = ref<string>("")
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


// const AIListData = ref<any[]>([{
//   id: 1,
//   userId: 1,
//   title: "测试数据22212121",
//   aiName: "qwen2.5",
//   modelMessageArrayList: null,
//   modelInfo: {
//     "modelId": 1,
//     "modelVersion": "GPT_3",
//     "modelName": "小智",
//     "modelFileId": 1,
//     "disable": 0,
//     "userId": 1,
//     "aiolId": 3,
//     "linkType": 0,
//     "description": "就这样asdasd",
//     "modelFile": null,
//     "modelAiOnline": null
//   }
// }])
const choose = (index:number) => {
  isShow.value = false;
  if (index === 0 || index===2) {
    router.push("chooseAi");
    modelStore.modelIndex = 2;
  }else if (index === 1 || index === 3) {
    router.push("chooseModel");
    modelStore.modelIndex = 3;
  }

}
// 点击管理基底模型
const clickManageBaseModel=async ()=>{
  if (modelStore.modelIndex == 1 || modelStore.modelIndex == 3) {
    await router.push("/baseModel").catch(err => {
      console.error("路由跳转错误:", err)
    });
    console.log("点击管理基底模型");
    return;
  }
  dialog.value = true;

}
const searchConv = computed(()=>{
    return modelStore.conversationList.filter(item=>{
      try {
        return item.modelInfo.modelName.toLowerCase().includes(searchText.value)
      }catch (e){
        return true
      }
    })
})

const searchModel = computed(() => {
  return modelStore.modelList.filter(item => {
    try {
      return item.modelName.toLowerCase().includes(searchText.value)
    } catch (e) {
      return true
    }
  })
});



const init =  () => {
  modelStore.updateConversationList()
}
// 标题显示函数
const showName =()=>{

  switch (modelStore.modelIndex) {
    case 2:
      return module[0].topBtnName;
    case 3:
      return module[1].topBtnName;
    default:
      return module[modelStore.modelIndex].topBtnName;
  }
}

//显示搜索字函数
const showSearchText=()=>{

  switch (modelStore.modelIndex) {
    case 2:
      return module[0].searchPlaceholder;
    case 3:
      return module[1].searchPlaceholder;
    default:
      return module[modelStore.modelIndex].searchPlaceholder;
  }
}
// 显示底部按钮字函数
const showButtonName=()=>{

  switch (modelStore.modelIndex) {
    case 2:
      return module[0].bottomBtnName;
    case 3:
      return module[1].bottomBtnName;
    default:
      return module[modelStore.modelIndex].bottomBtnName;

  }
}


onMounted( async () => {
  init()
})

</script>

<template>
<div class="container">
  <div class="top">
      <div class="box">
          <div class="addConv" @click="choose(modelStore.modelIndex)" :style="isShow?'width:60px':''">
            <svg  style="width: 28px;height: 28px" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024"><path fill="currentColor" d="M480 480V128a32 32 0 0 1 64 0v352h352a32 32 0 1 1 0 64H544v352a32 32 0 1 1-64 0V544H128a32 32 0 0 1 0-64z"></path></svg>
            <span v-show="!isShow">{{showName()}}</span>
          </div>
           <div class="search" :class="!isShow?'hoverClass':''" @click="isShow = true" :style="isShow?'width:calc(100% - 60px);outline: 1px solid #d7d7d7;':''">
             <svg-icon :style="isShow?'border:none':''"  class="svg" icon-name="icon-search-2-copy"></svg-icon>
              <input v-model="searchText" v-show="isShow"  type="text" :placeholder="showSearchText()">
           </div>
      </div>
  </div>

  <hr color="#d7d7d7">

  <div class="middle">
    <ConversationList :id="item.id"
                      :ai-type="item.modelInfo?item.modelInfo.baseModelName:null"
                      :description="item.lastMessage?item.lastMessage:null"
                      :ai-name="item.modelInfo?item.modelInfo.modelName:null"
                      v-if="modelStore.modelIndex === 0 || modelStore.modelIndex===2"
                      :class-style="conversationsActiveMenu === i"
                      @click="conversationsActiveMenu = i"
                      v-for="(item, i) in searchConv"
                      :key="i"></ConversationList>

    <ModelList :localmodel-name="item.localmodelName"
               :model-ai-online="item.aiolId"
               :id="item.modelId"
               :ai-name="item.modelName"
               v-if="modelStore.modelIndex === 1 || modelStore.modelIndex==3"
               :class-style="modelActiveMenu === i"
               @click="modelActiveMenu = i"
               :ai-type="item.modelVersion"
               :model-info="item"
               v-for="(item,i) in searchModel"
               :key="i"></ModelList>
  </div>

  <hr color="#d7d7d7">
  <div class="management">
    <div @click="clickManageBaseModel" class="managerBtn">
      <svg class="svg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024"><path fill="currentColor" d="M389.44 768a96.064 96.064 0 0 1 181.12 0H896v64H570.56a96.064 96.064 0 0 1-181.12 0H128v-64zm192-288a96.064 96.064 0 0 1 181.12 0H896v64H762.56a96.064 96.064 0 0 1-181.12 0H128v-64zm-320-288a96.064 96.064 0 0 1 181.12 0H896v64H442.56a96.064 96.064 0 0 1-181.12 0H128v-64z"></path></svg>
      <span>{{ showButtonName() }}</span>
    </div>
  </div>

<!--  管理面板-->
  <component class="customDialog" @close="dialog = false;init()" @update="init()" v-show="dialog"  :is="DialogCom"></component>
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
      overflow-y: auto;
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

