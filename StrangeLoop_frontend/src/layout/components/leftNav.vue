<script setup lang="ts">
import {reactive, ref} from "vue";
import ListComp from "@/components/ListComp.vue";
interface Module{
  topBtnName:string
  searchPlaceholder:string
  listComp:any
  bottomBtnName:string
}

const isShow = ref<boolean>(false)
const module = reactive<Module[]>([
    {
      topBtnName:"新增模型",
      searchPlaceholder:"搜索历史记录",
      listComp:null,
      bottomBtnName:"管理对话记录"
    }

])

</script>

<template>
<div class="container">
  <div class="top">
      <div class="box">
          <div class="addConv" @click="isShow = false" :style="isShow?'width:60px':''">
            <svg  class="svg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024"><path fill="currentColor" d="M480 480V128a32 32 0 0 1 64 0v352h352a32 32 0 1 1 0 64H544v352a32 32 0 1 1-64 0V544H128a32 32 0 0 1 0-64z"></path></svg>
            <span v-show="!isShow">新增对话</span>
          </div>
           <div class="search" :class="!isShow?'hoverClass':''" @click="isShow = true" :style="isShow?'width:calc(100% - 60px);outline: 1px solid #d7d7d7;':''">
             <svg :style="isShow?'border:none':''"  class="svg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024"><path fill="currentColor" d="m795.904 750.72 124.992 124.928a32 32 0 0 1-45.248 45.248L750.656 795.904a416 416 0 1 1 45.248-45.248zM480 832a352 352 0 1 0 0-704 352 352 0 0 0 0 704"></path></svg>
              <input v-show="isShow"  type="text" placeholder="搜索历史记录">
           </div>
      </div>
  </div>

  <hr color="#d7d7d7">

  <div class="middle">
    <ListComp v-for="i in 3" :key="i"></ListComp>
  </div>

  <hr color="#d7d7d7">
  <div class="management">
    <div class="managerBtn">
      <svg class="svg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024"><path fill="currentColor" d="M389.44 768a96.064 96.064 0 0 1 181.12 0H896v64H570.56a96.064 96.064 0 0 1-181.12 0H128v-64zm192-288a96.064 96.064 0 0 1 181.12 0H896v64H762.56a96.064 96.064 0 0 1-181.12 0H128v-64zm-320-288a96.064 96.064 0 0 1 181.12 0H896v64H442.56a96.064 96.064 0 0 1-181.12 0H128v-64z"></path></svg>      <span>管理对话记录</span>
    </div>
  </div>
</div>
</template>

<style scoped>
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
              background-color: #4152b4;
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