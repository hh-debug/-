import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css';
import Axios from "axios";

Axios.defaults.baseURL = process.env.VUE_APP_SERVER;

    createApp(App).use(store).use(router).use(Antd).mount('#app');

console.log('环境:',process.env.Node_ENV)
console.log('服务端:',process.env.VUE_APP_SERVER)


/**
 * axios拦截器
 */
Axios.interceptors.request.use(function (config) {
    console.log('请求参数：', config);
    return config;
}, error => {
    return Promise.reject(error);
});
Axios.interceptors.response.use(function (response) {
    console.log('返回结果：', response);
    return response;
}, error => {
    console.log('返回错误：', error);
    return Promise.reject(error);
});