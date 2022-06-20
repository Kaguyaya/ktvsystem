//引用Vuex
import Vue from 'vue'
import Vuex from 'vuex'
import api from '@/api/orderindex'
import addresses from '@/api/addresses'
Vue.use(Vuex)

//实例store对象
const store = new Vuex.Store({
	state: {
		songsinfo:'',
		room:'',
		wechatuser:'',
		userid:'',
		store: {},
		cart: [],
		orderType: 'takein',
		address: {},
		addresses: addresses,
		member: {},
		order: {}
	},
	getters: {
		isLogin: state => Object.keys(state.member).length > 0	//是否登录
	},
	mutations: {
		wechatuser(state,msg){
			state.wechatuser=msg;
		},
		room(state,msg){
			state.room=msg;
		},
		songsinfo(state,msg){
			state.songsinfo=msg;
		},
		userid(state,msg){
			state.userid=msg;
		},
		SET_ORDER_TYPE(state, type) {
			state.orderType = type
		},
		SET_MEMBER(state, member) {
			state.member = member
		},
		SET_ADDRESS(state, address) {
			state.address = address
		},
		SET_ADDRESSES(state, addresses) {
			state.addresses = addresses
		},
		SET_STORE(state, store) {
			state.store = store
		},
		SET_CART(state, cart) {
			state.cart = cart
		},
		REMOVE_CART(state) {
			state.cart = []
		},
		SET_ORDER(state, order) {
			state.order = order
		}
	},
	actions: {
		async getStore({commit}) {
			const store = await api('store')
			commit('SET_STORE', store)
		}
	}
})

//导出store对象
export default store

//export default const store或者export const store会报错
