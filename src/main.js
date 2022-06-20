import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import router from './router/router';
import axios from 'axios'
import VueAxios from 'vue-axios'
import store from './store/index'
import VideoPlayer from 'vue-video-player'
import 'vue-video-player/src/custom-theme.css'
import 'video.js/dist/video-js.css'
Vue.use(VideoPlayer)
Vue.use(store)
Vue.use(VueAxios,axios);
Vue.use(ElementUI);
new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')

// axios.interceptors.request.use(function (config)
// {
//   let token=localStorage.getItem('authorization');
//   if (token){
//     config.headers['Authorization']=token;
//   }
//   return config;
// }
// )
// axios.interceptors.response.use(response=>{console.log(response)},error=>{
//   if(error.response.status===401){
//       alert("登录状态失效，即将返回登录界面");
//       Vue.$router.push("/login")
//   }
//   return Promise.resolve(error.response)
//   })