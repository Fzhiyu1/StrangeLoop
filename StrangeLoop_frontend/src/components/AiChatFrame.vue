<script setup lang="ts">

import ChatBubble from "./ChatBubble.vue";
import {PropType, reactive, ref, watch} from "vue";
import AiChatBubble from "./AiChatBubble.vue";

import {Chat} from '@kousum/semi-ui-vue';
import {uploadProps} from "element-plus";

// 组件参数
 const props =defineProps({
   messages:{
     type: Array as PropType<Array<{
       role: string,
       id: string,
       createAt: bigint,
       content: string;
     }>>,
     default: () => [],

   },
   roleInfo:{
     type:Array as PropType<Array<{
       name:string,
       avatar:string
     }>>,
     default:{
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
   }
 })
// demo消息历史记录
const defaultMessage = props.messages;
const emit = defineEmits(['update:messages']);
 //监听message
watch(()=>props.messages,(newMessages) =>{
  message.value = [...newMessages];

})

//生成 id
let id = 0;

function getId() {
  return `id-${id++}`
}

// 样式
const commonOuterStyle = {
  margin: '8px 16px',
  height: '90%',
  width: '90%',

}

// 交互

const message = ref(defaultMessage);
props.messages=message.value;
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
  emit('update:messages', chats); // 向父组件发出事件
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
    <Chat id="chat"
          :style="commonOuterStyle"
        :chats="message"
        :role-config="props.roleInfo"
        :onChatsChange="onChatsChange"
        :onMessageSend="onMessageSend"
        :onMessageReset="onMessageReset"
    />
  </div>
</template>

<style scoped>

#frame {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center;     /* 垂直居中 */
  width: 100%;
  height: 100%;
  /* 如果父元素没有指定高度，可能需要确保 #frame 有足够的高度才能垂直居中 */
  min-height: 100vh;       /* 确保最小高度为视口高度 */
}

#chat {
  margin: 8px 16px; /* 移动到这儿，以便更好地控制 */
  max-width: 1000px;
  width: 1000px;
}

#semi-chat-inputBox{
  width: 500px;
}

</style>
