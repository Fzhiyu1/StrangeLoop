<script setup lang="ts">
import {computed, onBeforeUnmount, PropType, ref, watch} from "vue";
import {Chat} from '@kousum/semi-ui-vue';
import {SSEService} from "../utils/SSEService.ts";
import {useModelStore} from "@/store/ModelStore.ts";
import {addMessage} from "@/api/message.ts";
import {getTime} from "@/utils/time.ts";
import {deleteMessage} from "../api/message.ts";
import {onBeforeRouteLeave} from "vue-router";
import {getConversation} from "../api/conversation.ts";

const modelStore = useModelStore();

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
  // token
  token: {
    type: String,
    default: null,
  },

  // 模型名称
  model: {
    type: String,
    default: undefined,
  }
})
// 获取长链接对象
// chat实例
const chatRef = ref(null);
const bottom = ref(null);
// demo消息历史记录
const defaultMessage = props.messages;
//  自定义事件
const emit = defineEmits(['update:messages']);

//监听message
watch(() => props.messages, (newMessages) => {
  message.value = [...newMessages];
})
// 停止按钮



//生成 id
let id = 0;

function getId() {
  return `id-${id++}`
}

// 样式
const commonOuterStyle = {
  margin: '8px 16px',
  height: 'calc(100% - 40px)',
  maxWidth:'calc(100% - 32px)',
  overflowY: 'auto'
}
// 自动滚动
const scrollToBottomWithAnimation = () => {
  if (chatRef.value) {
    chatRef.value.scrollToBottom(true);

  }
};


// 交互
const message = ref(defaultMessage);

//下列代码试图改变单向数据流，请勿解除注释，否则将会发出readonly警告。修复于2025/2/26 hyh
// props.messages = message.value;

const mode = ref('bubble');
const align = ref('leftRight');
const onAlignChange = (e) => {
  align.value = (e.target.value);
};
// 弃用的方法
const onModeChange = (e) => {
  mode.value = (e.target.value);
};

let sseService = new SSEService();
// 收到信息
const onMessageSend = async (content, attachment) => {
  const convId = modelStore.currConversation.id;
  // 1. 用户发送的消息
  //用户消息体
  const userMessage = {
    role: 'user',
    id: getId(),
    createAt: Date.now(),
    content: content,
  };
  // 将用户消息添加到数组中
  message.value = [...message.value, userMessage];
  const aiMessageInit = {
    role: 'assistant',
    id: getId(),
    createAt: Date.now(),
    content:"",
    status: 'loading'
  };
  console.log(aiMessageInit.id)
  // 只更新最后一条消息
  message.value[props.messages.length] = aiMessageInit;
  // ai消息体
  let aiData = {
    model: props.model,
    messages: message.value,
    stream:true
  }
  //初始化消息体


  const onUpdate = (message1:string) => {
    // AI 的消息 注意status控制停止按钮的显示 status==incomplete 为加载中 status==complete 为停止加载
    const aiMessage = {
      role: 'assistant',
      id: getId(),
      createAt: Date.now(),
      content: message1,
      status: 'incomplete',
    };
    // 只更新最后一条消息
    if(convId === modelStore.currConversation.id){
      message.value[props.messages.length] = aiMessage;
      scrollToBottomWithAnimation();
    }
  }

  const onComplete = (finalMessage:string) => {
    let maxId = parseInt(message.value[props.messages.length-1].id)
    // AI 的消息 注意status控制停止按钮的显示 status==incomplete 为加载中 status==complete 为停止加载
    const aiMessage = {
      role: 'assistant',
      id: getId(),
      createAt: Date.now(),
      content: finalMessage,
      status: 'complete',
    };
    // 只更新最后一条消息
    if(convId === modelStore.currConversation.id){
      message.value[props.messages.length] = aiMessage;
    }
    // 最后处理
    // 存储用户消息和AI消息

    //不可能出现用户没有发消息，AI自动发消息
    addMessage({data:{
        "role":"user",
        "content":content,
        "conversationId":convId,
        "createdTime":getTime()
      }}).then((res)=>{
      message.value[message.value.length-2].id = res.data.data;
        const str = "<think>\n" +
            "\n" +
            "</think>";
        const str1 = "<think>"
        if(finalMessage.includes(str1)){
          finalMessage = finalMessage.replace(str, "");
          finalMessage = finalMessage.replace(str1, "");
        }
      addMessage({data:{
          "role":"assistant",
          "content":finalMessage,
          "conversationId":convId,
          "createdTime":getTime()
        }}).then(res2=>message.value[message.value.length-1].id = res2.data.data)
    })

  }

  // 判断ai类型是否为本地ai
  if (props.aiType === 0) {
    console.log(message.value);
    console.log(aiData)
    await sseService.sendLocal(props.url, aiData, onUpdate, onComplete);
  }else if(props.aiType === 1) {
    sseService.setToken(props.token);
    await sseService.sendOnline(props.url, aiData, onUpdate, onComplete);
  }


};
// 当信息发生改变
const onChatsChange = (chats) => {
  message.value = chats;
  emit('update:messages', chats); // 向父组件发出事件
  console.log(message.value);
};
// 删除消息时触发
const deleteOne=async (message)=>{
    deleteMessage({id: message.id});
}

const onStopGenerator = () => {
  sseService.stop();
}


</script>

<template>
  <div id="frame">
    <Chat id="chat"
          :style="commonOuterStyle"
          :show-stop-generate="true"
          ref="chatRef"
          :chats="message"
          :on-message-delete="deleteOne"
          :role-config="props.roleInfo"
          :onChatsChange="onChatsChange"
          :onMessageSend="onMessageSend"
          :onStopGenerator="onStopGenerator"
          mode="userBubble"
    >
      <!--        <template #bottomSlot>-->
      <!--          <div style="text-align: center; padding: 10px;">-->
      <!--            这是一个自定义的底部插槽内容！-->
      <!--          </div>-->
      <!--        </template>-->
    </Chat>
  </div>

</template>

<style scoped>

#frame {
  overflow: hidden;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  width: 100%;
  height: 100%;
  /* 如果父元素没有指定高度，可能需要确保 #frame 有足够的高度才能垂直居中 */
  min-height: 100vh; /* 确保最小高度为视口高度 */
}

#chat {
  text-align: left;
  ::-webkit-scrollbar {
    width: 3px;
  }
  ::-webkit-scrollbar-thumb {
    background-color: #ccc;
    border-radius: 4px;
  }
  ::-webkit-scrollbar-track {
    background-color: #f1f1f1;
    border-radius: 4px;
  }
}


</style>
