import {createRouter, createWebHistory} from 'vue-router'
import BasicInfo from '../views/BasicInfo.vue'
import WhiteBoard from '../views/WhiteBoard.vue'
import Content from '../views/Content.vue'

const routes = [
    {
        path: '/basic',
        name: 'basic',
        component: BasicInfo
    },
    {
        path: '/whiteBoard',
        name: 'home',
        component: WhiteBoard
    },
    {
        path: '/content',
        name: 'content',
        component: Content
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
