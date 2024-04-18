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
        path: '/community',
        name: 'community',
        component: () => import(/* webpackChunkName: "about" */ '../views/Community.vue')
    },
    {
        path: '/pro',
        name: 'pro',
        component: () => import(/* webpackChunkName: "about" */ '../views/LearniversePro.vue')
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
        path: '/community/post',
        name: 'community post',
        component: () => import(/* webpackChunkName: "about" */ '../views/Post.vue')
    },
    {
        path: '/community/create',
        name: 'community create',
        component: () => import(/* webpackChunkName: "about" */ '../views/CreatePost.vue')
    },
    {
        path: '/explore',
        name: 'explore',
        component: Explore
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('@/components/Register.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/components/Login.vue')
    },
    {
        path: '/profile',
        name: 'Profile',
        component: () => import('@/views/Profile.vue')
    },
    {
        path: '/no-access',
        name: 'NoAccess',
        component: () => import('@/components/NoAccess.vue')
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
