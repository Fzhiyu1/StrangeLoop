<script setup lang="ts">
import {PropType, reactive, ref, watch} from "vue";
import {Chat} from '@kousum/semi-ui-vue';
import {SSEService} from "../utils/SSEService.ts";


// 组件参数
const props = defineProps({
  messages: {
    type: Array as PropType<Array<{
      role: string,
      id: string,
      createAt: bigint,
      content: string;
    }>>,
    default: () => [],
  },
  roleInfo: {
    type: {},

    default: {
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
  },
  // 请求地址
  url: {
    type: String,
    default: undefined,
  },
  // ai类型，0为本地、1为在线
  aiType: {
    type: Number,
    default: 0,

  },
  // 模型名称
  model: {
    type: String,
    default: undefined,
  }
})
// 获取长链接对象

// demo消息历史记录
const defaultMessage = props.messages;
//  自定义事件
const emit = defineEmits(['update:messages']);

//监听message
watch(() => props.messages, (newMessages) => {
  message.value = [...newMessages];
  console.log()
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
const intervalId = ref();
const message = ref(defaultMessage);
props.messages = message.value;
const mode = ref('bubble');
const align = ref('leftRight');
const onAlignChange = (e) => {
  align.value = (e.target.value);
};
// 弃用的方法
const onModeChange = (e) => {
  mode.value = (e.target.value);
};


// 收到信息
const onMessageSend = async (content, attachment) => {
  // 1. 用户发送的消息
  let sseService = new SSEService();
  //用户消息体
  const userMessage = {
    role: 'user',
    id: getId(),
    createAt: Date.now(),
    content: content,
  };
  // 将用户消息添加到数组中
  message.value = [...message.value, userMessage];
  // ai消息体
  let aiData = {
    model: props.model,
    messages: message.value,
  }
  const onUpdate = (message1) => {
    // 2. AI 的消息
    const aiMessage = {
      role: 'assistant',
      id: getId(),
      createAt: Date.now(),
      content: message1,
    };
    // 只更新最后一条消息
    message.value[props.messages.length]=aiMessage;
  }

  const onComplete = (finalMessage) => {
    console.log("收到信息结束", finalMessage);
    // 最后处理
  }

  // 判断ai类型是否为本地ai
  if (props.aiType === 0) {
    console.log(message.value);
    await sseService.send(props.url, aiData, onUpdate, onComplete);
  }
};
// 当信息发生改变
const onChatsChange = (chats) => {
  message.value = chats;
  emit('update:messages', chats); // 向父组件发出事件
  console.log(message.value);
};

</script>

<template>
  <div id="frame">
<!--    -->
    <Chat id="chat"
          :style="commonOuterStyle"
          :chats="message"
          :role-config="props.roleInfo"
          :onChatsChange="onChatsChange"
          :onMessageSend="onMessageSend"
    />
  </div>
</template>

<style scoped>

#frame {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  width: 100%;
  height: 100%;
  /* 如果父元素没有指定高度，可能需要确保 #frame 有足够的高度才能垂直居中 */
  min-height: 100vh; /* 确保最小高度为视口高度 */
}

#chat {
  margin: 8px 16px; /* 移动到这儿，以便更好地控制 */
  max-width: 1000px;
  width: 1000px;
  text-align: left;
}


</style>
