<script setup lang="ts">

import ChatBubble from "./ChatBubble.vue";
import {reactive, ref} from "vue";
import AiChatBubble from "./AiChatBubble.vue";

import { Chat } from '@kousum/semi-ui-vue';
import {uploadProps} from "element-plus";
// demo消息历史记录
const defaultMessage = [
  {
    role: 'system',
    id: '1',
    createAt: 1715676751919,
    content: "Hello, I'm your AI assistant.",
  },
  {
    role: 'user',
    id: '2',
    createAt: 1715676751919,
    content: "给一个 Semi Design 的 Button 组件的使用示例",
  },
  {
    role: 'assistant',
    id: '3',
    createAt: 1715676751919,
    content: "以下是一个 Semi 代码的使用示例：\n\`\`\`jsx \nimport React from 'react';\nimport { Button } from '@kousum/semi-ui-vue';\n\nconst MyComponent = () => {\n  return (\n    <Button>Click me</Button>\n );\n};\nexport default MyComponent;\n\`\`\`\n",
  }
];
// 角色设定
const roleInfo = {
  user: {
    name: 'User',
    avatar: 'https://lf3-static.bytednsdoc.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/docs-icon.png'
  },
  assistant: {
    name: 'Assistant',
    avatar: 'https://lf3-static.bytednsdoc.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/other/logo.png'
  },
  system: {
    name: 'System',
    avatar: 'https://lf3-static.bytednsdoc.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/other/logo.png'
  }
}

//生成 id
let id = 0;

function getId() {
  return `id-${id++}`
}
// 样式
const commonOuterStyle = {
  border: '1px solid var(--semi-color-border)',
  borderRadius: '16px',
  margin: '8px 16px',
  height: '550px',
}

// 交互

const message = ref(defaultMessage);
const mode = ref('bubble');
const align = ref('leftRight');

const onAlignChange = (e) => {
  align.value = (e.target.value);
};

const onModeChange = (e) => {
  mode.value = (e.target.value);
};

const onMessageSend = (content, attachment) => {
  const newAssistantMessage = {
    role: 'assistant',
    id: getId(),
    createAt: Date.now(),
    content: "这是一条 mock 回复信息",
  }
  setTimeout(() => {
    message.value = [...message.value, newAssistantMessage]
  }, 200);
};

const onChatsChange = (chats) => {
  message.value = chats;
};

const onMessageReset = (e) => {
  setTimeout(() => {
    const lastMessage = message.value[message.value.length - 1];
    const newLastMessage = {
      ...lastMessage,
      status: 'complete',
      content: 'This is a mock reset message.',
    }
    message.value = [...message.value.slice(0, -1), newLastMessage]
  }, 200);
}

</script>

<template>
<div id="frame">
  <Chat
      :chats="message"
      :role-config="roleInfo"
      :onChatsChange="onChatsChange"
      :onMessageSend="onMessageSend"
      :onMessageReset="onMessageReset"

  />
</div>
</template>

<style scoped>
#frame {
  width: 1000px;
  height: 500px;
  background-color: rgb(255, 255, 255);
}
#chat {
  position: relative;

}
</style>
