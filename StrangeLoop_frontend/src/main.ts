import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "./router";
import SvgIcon from "./components/SvgIcon.vue";
// 使用阿里云图标
import './assets/iconfont/iconfont.js'



const app=createApp(App)
//注册路由
app.use(router);
// 注册ElementPlus
app.use(ElementPlus);
// 注册图标
app.component('SvgIcon',SvgIcon)


app.mount('#app');





