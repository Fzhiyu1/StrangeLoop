<script setup lang="ts">
import {useRequestStore} from "@/store/RequestStore.ts";
const requestStore = useRequestStore();
import LoadingPage from "@/views/LoadingPage.vue";
</script>

<template>
  <loading-page style="position:absolute;z-index: 99999" v-show="requestStore.isLoading"></loading-page>
  <div>
    <router-view></router-view>
  </div>

</template>

<style scoped>

</style>
