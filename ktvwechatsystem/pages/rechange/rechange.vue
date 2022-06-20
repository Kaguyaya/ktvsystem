<!--本页面由uniapp切片工具生成，uni-app切片-可视化设计工具(一套代码编译到7个平台iOS、Android、H5、小程序)，软件下载地址：http://www.ymznkf.com/new_view_669.htm -->
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
		

		<view class="loading">{{loadingText}}</view>
		<view class="ymBbottom"></view>
		
		<view>
			<!-- 提示窗示例 -->
			<uni-popup ref="alertDialog" type="dialog">
				<uni-popup-dialog :type="msgType" cancelText="关闭" confirmText="同意" title="通知" content="充值成功,返回个人信息" @confirm="dialogConfirm"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				money:'',
				userinfo:''
			}
		},
		methods: {
			money_input(event){
				this.money=event.target.value;
				
			},
			payType_change(){
				
			},
			btSubmit_21_click(){
					let vue=this;
					uni.request({
						method:'POST',
						url:'http://localhost:8080/user/setUserMoney',
						data:{
							ktvuser:vue.userinfo,
							money:vue.money
						},
						success: (res) => {
							console.log(res);
						}
					})
					uni.request({
						method:'POST',
						url:'http://localhost:8080/recharge/rechargeRecord',
						data:{
							ktvuser:vue.userinfo,
							money:vue.money
						},
						success: (res) => {
							console.log(res);
						}
					})
					vue.dialogToggle('success')
			},
			init(){
				let vue=this;
				uni.request({
					method:'POST',
					url:'http://localhost:8080/user/getUserById',
					data:this.$store.state.userid,
					success: (res) => {
						vue.userinfo=res.data.data
					}
				})
			},
			dialogClose() {
						console.log('点击关闭')
					},
			dialogToggle(type) {
				this.msgType = type
				this.$refs.alertDialog.open()
				
			},
			dialogConfirm() {
							console.log('点击确认')
							this.messageText = `点击确认了 ${this.msgType} 窗口`
						
							uni.switchTab({
								url:'/pages/myinfo/myinfo'
							})
						},
		},
		mounted() {
			this.init();
		}
	}
</script>

<style lang="scss" scoped>
   @import url("/animate.min.css");
   @import './voucher_center_.scss'
</style>