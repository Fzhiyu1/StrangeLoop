
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import Login from "../views/Login.vue";




const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'Login',
        component: Login,
    },
    // 其他页面路由...
];

const router = createRouter({
    history: createWebHistory(), // HTML5 模式
    routes,
});

export default router;
router.beforeEach((to, from, next) => {
    //判断是否有token
    const isAuthenticated: boolean = !!localStorage.getItem('token');

    if (to.path !== '/login' && !isAuthenticated) {
        //未登录则跳转到登录页面
        next('/login');
    } else {
        //已登录则继续在该页面
        next();
    }
});
