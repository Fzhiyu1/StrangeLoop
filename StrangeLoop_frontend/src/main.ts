import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "./router";



const app=createApp(App)
//注册路由
app.use(router);
// 注册ElementPlus
app.use(ElementPlus);
// 注册axios


app.mount('#app');





