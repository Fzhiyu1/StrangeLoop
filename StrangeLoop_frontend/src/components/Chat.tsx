import { Chat } from '@kousum/semi-ui-vue';
import { defineComponent, ref } from 'vue';

const defaultMessage = [
    {
        role: 'assistant',
        id: '1',
        createAt: 1715676751919,
        content: "请求成功",
    },
    {
        id: 'loading',
        role: 'assistant',
        status: 'loading'
    },
    {
        role: 'assistant',
        id: 'error',
        content: '请求错误',
        status: 'error'
    }
];

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

const commonOuterStyle = {
    border: '1px solid var(--semi-color-border)',
    borderRadius: '16px',
    height: 400,
}

let id = 0;

function getId() {
    return `id-${id++}`
}

const uploadProps = { action: 'https://api.semi.design/upload' }


const Demo = defineComponent(() => {

    const message = ref(defaultMessage);

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

    const onChatsChange = (chats): void => {
        message.value = (chats);
    };

    return () => (
        <Chat
            style={commonOuterStyle}
            chats={message.value}

            roleConfig={roleInfo}
            onChatsChange={onChatsChange}
            onMessageSend={onMessageSend}
            uploadProps={uploadProps}

        />
    )
})


export default Demo
