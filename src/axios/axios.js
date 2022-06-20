import Vue from 'vue';
import axios from 'axios';
import router from '../router/router';


Vue.prototype.$axios = axios
axios.defaults.headers.common['Authorization'] = localStorage.getItem('authorization');
axios.defaults.headers.post['Content-Type'] = 'application/json';
axios.defaults.headers.put['Content-Type'] = 'application/json';
 
// 添加请求拦截器
axios.interceptors.request.use(function(config) {
    config.headers.Authorization = localStorage.getItem('authorization');
    
    console.info(config);
    return config;
}, function(error) {
    router.push("/login")
    return Promise.reject(error);
});
 
export default axios