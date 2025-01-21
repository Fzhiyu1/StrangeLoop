import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "./router";
import {createPinia} from "pinia";
import SvgIcon from "./components/SvgIcon.vue";
// 使用阿里云图标
import './assets/iconfont/iconfont.js'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//全局组件
import LoadingPage from "./views/LoadingPage.vue";
const pinia=createPinia()
// 注册ElementPlusIconsVue
const app=createApp(App)
// 注册全局组件
app.component('LoadingPage',LoadingPage)
// 全局变量控制全局组件显示
app.config.globalProperties.$loadingPageShow=false

//注册路由
app.use(router);
// 注册ElementPlus
app.use(ElementPlus);
// 注册图标
app.component('SvgIcon',SvgIcon)
// 注册chat
app.use(pinia)
// 注册ElementPlusIconsVue
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}


app.mount('#app');





