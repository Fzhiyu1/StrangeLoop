<script setup lang="ts">
import defaultAvatar from "@/assets/images/user/avatar.jpeg";
import {useModelStore} from "@/store/ModelStore.ts";
import {listModelInfo} from "@/api/manage.ts";
import {string} from "@kousum/semi-ui-vue/dist/PropTypes";
import {getModelDetail} from "@/api/module.ts";
import router from "@/router";
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
        type:[string,null]
      }
    })

const modelClick = async () => {
  modelStore.currClickId = props.id
  modelStore.parameter.propsLocalModelName = props.localmodelName
  modelStore.topNav = {modelName:props.aiName,modelVersion:props.aiType}
  await router.push("/modelManager")
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