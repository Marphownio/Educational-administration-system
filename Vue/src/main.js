import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'


import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/index.css'
import Papa from 'papaparse'
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

const app=createApp(App)
app.config.globalProperties.$axios=axios;
app.use(store).use(router).use(ElementPlus).use(Papa).mount('#app')
