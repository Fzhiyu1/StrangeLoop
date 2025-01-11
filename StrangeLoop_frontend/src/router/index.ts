
import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router';
import Login from "../views/Login.vue";
import Layout from "../layout/index.vue"
import {getToken} from "../utils/auth.ts";
import Aichat from "../views/AiChat/index.vue";
const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'Login',
        component: Login,
    },
    {
        path: '/',
        name: 'Home',
        component: Layout,
        redirect: '/aichat',
        children:[
            {
                path:'aichat',
                name: 'AiChat',
                component:Aichat
            },
            {
                path:'modelManager',
                name: 'modelManager',
                component:()=>import('@/views/ModelManager/index.vue')
            },
            {
                path:'aiToolbox',
                name: 'aiToolbox',
                component:()=>import('@/views/AiToolbox/index.vue')
            },
            {
                path:'chooseAi',
                name: 'chooseAi',
                component:()=>import('@/views/AiChat/ChooseAi.vue')
            },
            {
                path:'chooseModel',
                name: 'chooseModel',
                component:()=>import('@/views/ModelManager/ChooseModel.vue')
            }
        ]

    }



];

const router = createRouter({
    history: createWebHistory(), // HTML5 模式
    routes,
});

export default router;
router.beforeEach((to, from, next) => {
    //判断是否有token
    const isAuthenticated: boolean = !!getToken();
    // console.log(getToken());
    from
    if (to.path !== '/login' && !isAuthenticated) {
        //未登录则跳转到登录页面
        next('/login');
    } else {
        //已登录则继续在该页面
        next();
    }
});
