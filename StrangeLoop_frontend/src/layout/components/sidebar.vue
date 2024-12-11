<script setup lang="ts">
import SvgIcon from "../../components/SvgIcon.vue";
</script>

<template>
  <div class="sidebar">
    <div id="sidebarUp">
      <div id="logoBlock">
        <img src="../../assets/images/logo/logo.png" class="log_small">
        <br/>
        <span>怪圈</span>
      </div>

      <div id="logoMenu">
        <div class="logoAndText">
          <svg-icon icon-name="icon-duihua" class="icon" color="#efefef" hover-color="white"></svg-icon>
          <br/>
          <span>对话</span>
        </div>
        <div class="logoAndText">
          <svg-icon icon-name="icon-damoxing" class="icon" color="#efefef" hover-color="white"></svg-icon>
          <br/>
          <span>模型管理</span>
        </div>
        <div class="logoAndText">
          <svg-icon icon-name="icon-AIgongjuxiang" class="icon" color="#efefef" hover-color="white"></svg-icon>
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
  width: 80px;
}

#logoBlock {
  margin-top: 50px;

}

#logoMenu {
  margin-top: 70px;

}

.logoAndText {
  margin-top: 50px;
  width: 80px;
  height: 70px;
}

.icon {
  color: white;
  width: 40px;
  height: 40px;
  cursor: pointer;
}

.sidebar {
  width: 80px;
  height: 100%;
}

#logoBlock {
  font-size: 20px;

}

span {
  color: white;
}

</style>
