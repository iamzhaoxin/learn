import { createApp } from 'vue'
import App from './App.vue'
// Element-Plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "@/router";
import 'bootstrap';
import './assets/bootstrap-5.1.3-dist/css/bootstrap.css'

createApp(App)
    .use(ElementPlus)
    .use(router)
    .mount('#app')
