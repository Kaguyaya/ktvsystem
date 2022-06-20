import App from './App'
import store from './store'
import api from './api/orderindex'
import util from './common/util'
import uView from '@/uni_modules/uview-ui'
// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false

App.mpType = 'app'
Vue.prototype.$api = api
Vue.prototype.$store = store
Vue.prototype.$util = util
const app = new Vue({
    ...App,store
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
	  store,
    app 
  }
}
// #endif 