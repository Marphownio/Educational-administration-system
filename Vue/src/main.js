import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import tokenmanage from "@/utils/Tokenmanage";
import ALERTMSG from "@/assets/js/alert";

import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/index.css'
import Papa from 'papaparse'
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

const app=createApp(App)
app.config.globalProperties.$axios=axios;
app.config.globalProperties.Papa = Papa;
app.use(tokenmanage).use(ALERTMSG).use(store).use(router).use(ElementPlus).mount('#app')
