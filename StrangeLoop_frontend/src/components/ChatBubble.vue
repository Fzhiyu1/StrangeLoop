<template>
  <div class="chat-container">
    <div :class="['chat-bubble', bubbleClass]" v-html="message">

    </div>
  </div>
</template>

<script setup>
import {defineProps, computed} from 'vue';

const props = defineProps({
  message: {
    type: String,
    required: true
  },
  isUser: {
    type: Boolean,
    default: false
  }
});

const bubbleClass = computed(() => ({
  'user-bubble': props.isUser,
  'bot-bubble': !props.isUser
}));
</script>

<style scoped>
.chat-container {
  position: relative; /* 确保子元素的绝对定位相对于父容器 */
  width: 100%;
  height: 100%;
}

.chat-bubble {
  position: absolute;
  text-align: left;
  right: 20px; /* 固定距离右侧 20px */
  max-width: 60%;
  padding: 10px;
  border-radius: 10px;
  margin: 5px;
  display: inline-block;
}

.user-bubble {
  background-color: #d1e7dd;
  align-self: flex-end;
}

.bot-bubble {
  background-color: #f8d7da;
  align-self: flex-start;
}
</style>
