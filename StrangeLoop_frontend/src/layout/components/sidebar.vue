<script setup lang="ts">
import SvgIcon from "../../components/SvgIcon.vue";
import {ref,reactive} from "vue";
import {Router, useRouter} from "vue-router";
import {useModelStore} from "@/store/ModelStore.ts";
import {listModelInfo} from "@/api/manage.ts";

const modelStore = useModelStore()
const router:Router = useRouter()
const activeMenu = ref<number>(0)
const hoverColor = reactive<string[] | null[]>([null, null, null])

const conf = {
  menuColor:{//菜单颜色配置
    hoverColor: '#009fff',//鼠标移入文本和图标的颜色
    leavedColor: '#ffffff',//鼠标移出图标的颜色，
    activeStyle :{//选中样式配置
      backgroundColor: "rgba(255,255,255,0.5)",
      borderRadius: "50%",
      color: "#009fff"
    }
  }
}
const gotoPage =async (index:number,path:string) => {
  activeMenu.value=index
  modelStore.modelIndex = index
  if(path === '/modelManager' && index === 1){
    modelStore.modelList =(await listModelInfo({})).data.data
  }
   router.push(path)
}
</script>

<template>
  <div class="sidebar">
    <div id="sidebarUp">
      <div id="logoBlock">
        <img style="border-radius: 50%" src="../../assets/images/logo/logo.png" class="log_small">
        <br/>
        <span>怪圈</span>
      </div>

<!--      改颜色见上conf配置项-->
      <div id="logoMenu">
        <div @mouseover="hoverColor[0] = conf.menuColor.hoverColor" @mouseleave="hoverColor[0]=null" class="logoAndText" @click="gotoPage(0,'/aichat')" >
          <svg-icon :style="activeMenu===0?conf.menuColor.activeStyle:''" :hover-color="hoverColor[0]" icon-name="icon-duihua" class="icon" :color="activeMenu===0? conf.menuColor.hoverColor:conf.menuColor.leavedColor"></svg-icon>
          <br/>
          <span>对话</span>
        </div>
        <div @mouseover="hoverColor[1] = conf.menuColor.hoverColor" @mouseleave="hoverColor[1]=null"  class="logoAndText"  @click="gotoPage(1,'/modelManager')">
          <svg-icon :style="activeMenu===1?conf.menuColor.activeStyle:''" :hover-color="hoverColor[1]" icon-name="icon-damoxing" class="icon" :color="activeMenu===1? conf.menuColor.hoverColor:conf.menuColor.leavedColor"></svg-icon>
          <br/>
          <span>模型管理</span>
        </div>
        <div  @mouseover="hoverColor[2] = conf.menuColor.hoverColor" @mouseleave="hoverColor[2]=null"  class="logoAndText"  @click="gotoPage(2,'/aiToolbox')">
          <svg-icon :style="activeMenu===2?conf.menuColor.activeStyle:''" :hover-color="hoverColor[2]" icon-name="icon-AIgongjuxiang" class="icon" :color="activeMenu===2? conf.menuColor.hoverColor:conf.menuColor.leavedColor"></svg-icon>
          <br/>
          <span>AI工具箱</span>
        </div>
      </div>

    </div>
    <div id="sidebarDown">
      <svg-icon icon-name="icon-a-accountcircle" class="icon" style="width: 50px;height: 50px"></svg-icon>
    </div>
  </div>
</template>

<style scoped>
#sidebarDown {
  position: absolute;
  bottom: 30px;
  cursor: pointer;
  width: 120px;
}

#logoBlock {
  margin-top: 20px;
  span {
   color: #fff;
 }
}

#logoMenu {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.logoAndText {
  margin-top: 50px;
  width: 80px;
  height: 70px;
  color: white;
}

.logoAndText:hover{
  cursor: pointer;
}

.icon {
  color: white;
  width: 40px;
  height: 40px;
  cursor: pointer;
  padding: 10px;
  margin-bottom: 10px;
}

.sidebar {
  width: 120px;
  height: 100%;
}

#logoBlock {
  font-size: 20px;

}
</style>
