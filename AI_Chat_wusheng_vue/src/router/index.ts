import { createRouter, createWebHistory } from 'vue-router'
import Index from '../views/Index.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Chat from '../views/Chat.vue'
import ChatHistory from '../views/ChatHistory.vue'
import KnowledgeBase from '../views/KnowledgeBase.vue'

const routes = [
  { path: '/', name: 'Index', component: Index },
  { path: '/login', name: 'Login', component: Login },
  { path: '/register', name: 'Register', component: Register },
  { path: '/chat', name: 'Chat', component: Chat },
  { path: '/chat-history', name: 'ChatHistory', component: ChatHistory },
  { path: '/knowledge', name: 'KnowledgeBase', component: KnowledgeBase }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
