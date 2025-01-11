<script setup lang="ts">
import defaultAvatar from "@/assets/images/user/avatar.jpeg";
// import {useRouter} from "vue-router";

// const router = useRouter();
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
      classStyle:{
        type:Boolean
      }
    })
</script>

<template>
  <div :class="classStyle? 'active': ''" class="card">
    <img :src="url" alt="logo">
    <div class="rightSide">
      <div>
        <span  class="aiName">{{aiName}}</span>
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