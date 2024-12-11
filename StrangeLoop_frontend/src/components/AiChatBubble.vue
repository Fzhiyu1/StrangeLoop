<template>
  <div class="ai-message">
    <div class="avatar">
      <img :src="avatar" alt="Avatar" class="avatar-img" />
    </div>
    <div class="message-content" v-html="parsedMessage"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, defineProps } from 'vue';
import { marked } from 'marked';
import avatarUrl from "../assets/images/logo/logo.png"

// 接收父组件传入的参数
const props = defineProps({
  message: {
    type: String,
    required: true,
  },
  avatar: {
    type: String,
    default:avatarUrl,
    required: true,
  },
});


// 解析 Markdown 格式的消息
const parsedMessage = ref(marked(props.message));
</script>

<style scoped>
.ai-message {
  display: flex;
  text-align: left;
  align-items: flex-start; /* 让头像和内容垂直对齐 */
  width: 100%;
  padding: 10px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 10px;

  color: #333;
}

.avatar {
  margin-right: 10px; /* 头像和文本之间的间距 */
}

.avatar-img {
  width: 40px;
  height: 40px;
  border-radius: 50%; /* 圆形头像 */
  object-fit: cover; /* 保证头像显示不失真 */
}

.message-content {
  flex: 1; /* 确保消息内容占满剩余空间 */
  white-space: normal;
}

.ai-message a {
  color: #409eff;
}
</style>
