import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from "axios";
import VueAxios from "vue-axios"
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate);

const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.use(VueAxios, axios); // 挂载到全局
app.use(pinia)
app.mount('#app')
