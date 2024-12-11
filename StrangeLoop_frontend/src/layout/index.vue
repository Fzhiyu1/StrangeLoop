<script setup lang="ts">

import Sidebar from "./components/sidebar.vue";
import Content from "./components/content.vue";
</script>

<template>
<el-container class="layout">
<sidebar/>
<content/>
</el-container>
</template>

<style scoped>
.layout{
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url("../assets/images/background.png");
  background-size: 100% 100%;


}
#app {
  width: 100%;
  height: 100%;
}
html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
}
</style>
