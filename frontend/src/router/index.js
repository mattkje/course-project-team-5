import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/Home.vue'
import Explore from '../views/Explore.vue'

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/about',
        name: 'about',
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
    },
    {
        path: '/courses',
        name: 'courses',
        component: () => import(/* webpackChunkName: "about" */ '../views/Course.vue')
    },
    {
        path: '/explore',
        name: 'explore',
        component: Explore
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
