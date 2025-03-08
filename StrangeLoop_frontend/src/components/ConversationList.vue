<script setup lang="ts">
import defaultAvatar from "@/assets/images/user/avatar.jpeg";
import {useRouter} from "vue-router";
import {onMounted, onUpdated, ref, watch} from "vue";
import {getConversation} from "@/api/conversation.ts";
import {useModelStore} from "@/store/ModelStore.ts";

const router = useRouter();
const modelStore = useModelStore()
const props = defineProps(
    {
    id:{
      type:[String,Number],
       default:""
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
        type:String,
        default:"llama3.2:1b"
      },
      description: {
        type: String,
        default: "AI小智是一只可爱的小机器人，她的名字叫做小智。她的主人是一位叫做小明的程序员，她的目标是成为一名优秀的机器学习工程师。"
      },
      classStyle:{
        type:Boolean
      }
    })

const _data = ref({
  date:"2024-06-01"
})

const conversationClick = async () => {
  modelStore.currConversation= (await getConversation({id:props.id})).data.data
  modelStore.modelIndex = 0;
  modelStore.topNav = {modelName:props.aiName,modelVersion:props.aiType}
  await router.push('/aichat?conversation_id='+props.id);
  modelStore.currClickId = props.id
}


</script>

<template>
  <div :class="classStyle? 'active': ''" class="card" @click="conversationClick">
    <img :src="url" alt="logo">
    <div class="rightSide">
      <div>
        <span  class="aiName">{{aiName}}</span>
        <span style="margin: 0 5px;color: #02a7f0">|</span>
        <span class="aiType">{{aiType}}</span>
        <span class="date">{{_data.date}}</span>
      </div>
      <div class="description">{{description}}</div>
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
        text-align: left;
      }
    }
  }
  .active {
    background:linear-gradient(to right, #f1f7fa,#CCEBFF);
    color: #02a7f0;
  }
</style>
