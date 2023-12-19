import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)
const routes = [
    {
        path: '/',
        name: 'top',
        component: () => import( '../views/homePage/topView.vue'),
        redirect: 'mall',
        children: [
            {
                path: 'mall',  //商城主页
                name: 'mall',
                component: () => import('@/views/homePage/mallView.vue'),
            },
            {
                path: 'rl',
                name: 'rl',
                component: () => import('../views/register_login/rlView.vue'),
                redirect: '/rl/login',
                children: [
                    {
                        path: 'register',
                        component: () => import('../views/register_login/registerView.vue')
                    },
                    {
                        path: 'login',
                        component: () => import(/* webpackChunkName: "about" */ '../views/register_login/loginView.vue')
                    }
                ]
            },
            {
                path: 'userSpace',
                name: 'userSpace',
                component: () => import('@/views/userSpace/SpaceView.vue'),
                redirect: '/userSpace/info',
                children: [
                    {
                        path: 'info',
                        component: () => import('@/views/userSpace/InfoView.vue')
                    },
                    {
                        path: 'order',
                        component: () => import ('@/views/userSpace/orderView.vue')
                    },
                    // 消费者的购物车界面
                    {
                        path: 'cart',
                        component: () => import( '@/views/userSpace/consumer/cartView.vue')
                    },
                    // 商家商品目录的管理
                    {
                        path: 'catalogue',
                        component: () => import( '@/views/userSpace/manager/catalogueView.vue')
                    },
                    // 商家销售数据的统计
                    {
                        path: 'statistics',
                        component: () => import( '@/views/userSpace/manager/statisticsView.vue')
                    },
                ]
            },
            {
                path: 'shopping',  //商品展示
                name: 'shopping',
                component: () => import('@/views/shopping/searchView.vue')
            },
        ]
    }
]

const router = new VueRouter({
    routes
})

export default router
