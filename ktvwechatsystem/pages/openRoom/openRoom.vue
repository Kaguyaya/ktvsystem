<template>
	<view class="YmContent" :animation="animationData">
			<view class="voucher_center__1">
				<text decode="true" class="voucher_center__47">充值金额</text>
				<view class="voucher_center__48">
					<text decode="true" class="voucher_center__49">￥</text>
					<input :value="money" placeholder="0" @input="money_input" class="money" />
		
				</view>

				<button v-on:click="btSubmit_21_click()" class="btSubmit">确认支付
				</button>

			</view>
		
		<view>
			<uni-popup ref="popup" background-color="#fff" @change="change">
				<view class="popup-content" :class="{ 'popup-height': type === 'left' || type === 'right' }"><text
						class="text">支付成功，正在跳转</text></view>
			</uni-popup>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				type:'',
				time:1,
				money:5,
				room:''
			}
		},
		onLoad() {
			this.room=this.$store.state.room;
		},
		methods:{
			recharge(){
				let mytime=new Date();
				let vue=this;
				mytime.setHours(mytime.getHours()+this.time)
				this.room.roomOpentime=this.transformTimestamp(new Date());
				this.room.roomEndtime=this.transformTimestamp(mytime);
				uni.request({
					method:'POST',
					url:'http://localhost:8080/room/startRoom',
					data:vue.room,
					success: (res) => {
						console.log(res);

						vue.toggle('bottom')
					}
				})
			},
			toggle(type) {
				this.type = type
				// open 方法传入参数 等同在 uni-popup 组件上绑定 type属性
				this.$refs.popup.open(type)
				setTimeout(()=>{
					uni.switchTab({
						url:'../index/index'
					})
				},3000)

			},
			transformTimestamp(timestamp){//时间对象转字符串
			      let a = new Date(timestamp).getTime();
			      const date = new Date(a);
			      const Y = date.getFullYear() + '-';
			      const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
			      const D = (date.getDate() < 10 ? '0'+date.getDate() : date.getDate()) + ' ';
			      const h = (date.getHours() < 10 ? '0'+date.getHours() : date.getHours()) + ':';
			      const m = (date.getMinutes() <10 ? '0'+date.getMinutes() : date.getMinutes()) + ':' ;
			      const s = date.getSeconds(); // 秒
			      const dateString = Y + M + D + h + m + s;
			      // console.log('dateString', dateString); // > dateString 2021-07-06 14:23
			      return dateString;
			    },
			add(){
				if(this.time>=24){
					return
				}
				else{
					this.time++
					this.money=this.time*5
				}
			},
			min(){
				if(this.time<=1){
					return
				}
				else{
					this.time--
					this.money=this. time*5
				}
			},
			appLoginWx(){
			                // #ifdef MP-WEIXIN
			                    uni.getProvider({
			                      service: 'oauth',
			                      success: function (res) {
			                        if (~res.provider.indexOf('weixin')) {
			                            uni.login({
			                                provider: 'weixin',
			                                success: (res2) => {
			                                    
			                                    uni.getUserInfo({
			                                        provider: 'weixin',
			                                        success: (info) => {//这里请求接口
			                                            console.log(res2);
			                                            console.log(info);
			                                            
			                                        },
			                                        fail: () => {
			                                            uni.showToast({title:"微信登录授权失败",icon:"none"});
			                                        }
			                                    })
			                            
			                                },
			                                fail: () => {
			                                    uni.showToast({title:"微信登录授权失败",icon:"none"});
			                                }
			                            })
			                            
			                        }else{
			                            uni.showToast({
			                                title: '请先安装微信或升级版本',
			                                icon:"none"
			                            });
			                        }
			                      }
			                    });
			                    //#endif
								            uni.getSetting({
								             success(res) {
								                console.log("授权：",res);
								               if (!res.authSetting['scope.userInfo']) {
								                    //这里调用授权
								                    console.log("当前未授权");
								               } else {
								                    //用户已经授权过了
								                    console.log("当前已授权");
								               }
								             }
								           })
			            }
		},
		mounted() {
			this.appLoginWx();
		}
	}
</script>

<style lang="scss">
	@import url("/animate.min.css");
	@import './voucher_center_.scss';
.mins{
	width: 200rpx;
	height: 160rpx;
	background-color: #f4f5f7;
	text-align: center;
	line-height: 100rpx;
	font-size: 60px;
	font-weight: bold;
}
.text{
	text-align: center;
	font-size: 80rpx;
}
.money{
	text-align: center;
	font-weight: bold;
	font-size: 100rpx;
}
.uni-flex{
	width: auto;
	height: auto;
	margin: 20rpx;
}
</style>
