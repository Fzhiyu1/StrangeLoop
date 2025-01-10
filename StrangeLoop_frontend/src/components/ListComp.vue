<script setup lang="ts">
import defaultAvatar from "@/assets/images/user/avatar.jpeg";
defineProps(
    {
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
      date: {
        type: String,
        default: "2022年3月15日"
      },
      description: {
        type: String,
        default: "AI小智是一只可爱的小机器人，她的名字叫做小智。她的主人是一位叫做小明的程序员，她的目标是成为一名优秀的机器学习工程师。"
      }
    })
</script>

<template>
  <div class="card">
    <img :src="url" alt="logo">
    <div class="rightSide">
      <div>
        <span class="aiName">{{aiName}}</span>
        <span class="aiType">{{aiType}}</span>
        <span class="date">{{date}}</span>
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
        text-wrap: nowrap;
        width: 90%;
        height: 1rem;
        font-size: 12px;
        color: #666;
      }
    }

  }
</style>