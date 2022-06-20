import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex);

export default new Vuex.Store({
state:{
  istrue:true
},
getters:{},
mutations:{
  changecollapse(){
    if(this.state.istrue){
      this.state.istrue=false;
    }
    else{
      this.state.istrue=true;
    }
  }
},
actions:{},
modules:{}
})