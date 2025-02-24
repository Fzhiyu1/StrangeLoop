<script setup lang="ts">
import defaultAvatar from "@/assets/images/user/avatar.jpeg";
import {useModelStore} from "@/store/ModelStore.ts";
import {listModelInfo} from "@/api/manage.ts";
import {any, number, string} from "@kousum/semi-ui-vue/dist/PropTypes";
import {getModelDetail} from "@/api/module.ts";
import router from "@/router";
import Cookies from "js-cookie";
import axios from "axios";
import {getModelAiOline} from "../api/manageOl.ts";
import {getModelInfo} from "../api/manage.ts";
// import {useRouter} from "vue-router";
const modelStore = useModelStore()
// const router = useRouter();
const props = defineProps(
    {
      id:{
        type:Number
      },
      url: {
        type: String,
        default:defaultAvatar
      },
      aiName:{
        type:String,
        default:"AI小智"
      },
      aiType:{
        type:[String,null],
      },
      classStyle:{
        type:Boolean
      },
      localmodelName:{
        type:[string,null],
      },
      modelAiOnline:{
        type:[string,null,number]
      },
      modelInfo:{
        type:any
      }
    })
// 模型点击处理函数
const modelClick = async () => {
  // 在模型仓库中记录当前点击的模型ID
  modelStore.currClickId = props.id
  // 设置当前选择的本地模型名称到参数中
  modelStore.parameter.propsLocalModelName = props.localmodelName;

  // 获取模型详细信息
  let modelInfoTop = props.modelInfo;
  // 通过API获取完整的模型信息
  modelInfoTop = (await getModelInfo({id: modelInfoTop.modelId}));
  console.log('获取的完整模型信息:', modelInfoTop);

  let baseModel = "";
  // 根据链接类型确定基础模型名称
  if(modelInfoTop.linkType == 1) { // 1表示在线模型
    baseModel = modelInfoTop.modelAiOnline.aiName;
  } else { // 其他情况使用本地模型名称
    baseModel = modelInfoTop.localmodelName;
  }

  // 更新顶部导航显示的模型信息
  modelStore.topNav = {
    modelName: props.aiName,    // 显示AI名称
    modelVersion: baseModel,     // 显示基础模型版本
    modelId: props.id,          // 显示模型ID
  };

  // 跳转到模型管理页面并携带模型ID参数
  await router.push("/modelManager?modelId=" + props.id);
}


</script>

<template>
  <div :class="classStyle? 'active': ''" class="card" @click="modelClick">
    <img :src="url" alt="logo">
    <div class="rightSide">
      <div>
        <span class="aiName">{{aiName}}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card {
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px 0;
  text-wrap: nowrap;
  overflow: hidden;

  &:hover {
    background-color: #CCEBFF;
    cursor: pointer;
  }

  img {
    width: 60px;
    height: 60px;
    border-radius: 50%;
  }

  .rightSide{
    font-size: 18px;
    display: flex;
    width: calc(100% - 80px);
    flex-direction: column;
    justify-content: space-between;
    margin-left: 10px;

    div:nth-of-type(1) {
      display: flex;
      align-items: center;

      .date {
        margin-left: auto;
        font-size: 10px;
        color: #666;
      }
    }

    .description{
      overflow: clip;
      text-overflow: ellipsis;
      width: 90%;
      height: 1rem;
      font-size: 12px;
      color: #666;
    }
  }
}
.active {
  background:linear-gradient(to right, #f1f7fa,#CCEBFF);
  color: #02a7f0;
}
</style>
