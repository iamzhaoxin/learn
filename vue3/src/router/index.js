import {createRouter, createWebHashHistory} from 'vue-router'
import {localGet} from "@/utils";

const router=createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            redirect: 'login'
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../components/login/UserLogin')
        },
        {
            path: '/home',
            name: 'home',
            component: () => import('../components/home/HomePage')
        }
    ],
})

// 未登录则跳转到登录页
router.beforeEach((to, from, next) => {
    let token = localGet(`token`)
    if(token&&token.userId){
        // 如果已登录，跳过登陆页
        if(to.path==='/login'){
            next({path:'/home'})
        }
        next();
    }
    next({path:'/login'})
})

export default router