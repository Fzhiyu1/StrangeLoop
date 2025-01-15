<script setup lang="ts">
  import leftNav from "./leftNav.vue"
  import topNav from "./topNav.vue"
</script>

<template>
  <div id="content">
    <leftNav id="leftNav"></leftNav>
    <div id="right">
      <topNav id="topNav"></topNav>
      <router-view id="views"></router-view>
    </div>
  </div>
</template>

<style scoped>
#content {
  position: absolute; /* 使用绝对定位 */
  top: 1%; /* 顶部边距 */
  left: 120px; /* 左侧固定边距 */
  right: 0.5%; /* 保证右边距固定 */
  bottom: 1%; /* 保证底部边距固定 */
  background-color: rgba(255, 255, 255, 0.82);
  border-radius: 30px;
  display: flex;
  width: calc(99.5% - 120px);
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.3);
  >* {
    border-radius: 30px;
  }
}

#leftNav {
  border-bottom-right-radius: 0;
}

#right {
  flex: 3;
  display: flex;
  flex-direction: column;

  #topNav {
    flex: 1;
  }

  #views {
    flex: 11;
    overflow: hidden;
  }

}
</style>
