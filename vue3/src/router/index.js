import {createRouter, createWebHashHistory} from 'vue-router'
import {localGet} from "@/utils";

const router = createRouter({
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
        },
        {
            path: '/budget/increase',
            name: 'increaseBudget',
            component: () => import('../components/budget/BudgetPage')
        },
        {
            path: '/user/manager',
            name: 'userManager',
            component: () => import('../components/user/UserPage')
        }
    ],
})

// 未登录则跳转到登录页
router.beforeEach((to, from, next) => {
    // console.log(from.path)
    // console.log(to.path)
    let token = localGet(`token`)
    if (token === null || token.userId === null) {
        if (to.path === '/login') {
            next();
        } else {
            next({path: '/login'})
        }
    }
    if (token && token.userId) {
        // 如果已登录，跳过登陆页
        if (to.path === '/login') {
            next({path: '/home'})
        }
        next();
    }
})

export default router