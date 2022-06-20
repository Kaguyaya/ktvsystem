<template>
	<view>
		<view class="content" v-if="!islogin">
			<view>
				<uni-icons type="weixin" size="120" color="#04BE02"></uni-icons>
			</view>
			<view class="text-area">
				<button  class="bottom" type="primary"  @tap="wxLogin">授权登录</button>
			</view>
		</view>
		<view v-else>
			<view class="user">
				<!-- 头部 -->
				<view class="user-wrap">
					<view class="setting iconfont icon31shezhi"></view>
					<view class="info">
						<image class="avatar" mode="aspectFill" :src="userInfo.avatarUrl"></image>
						<view class="nickname">{{ userInfo.nickName }}</view>
					</view>
				</view>
			
				<!-- 订单状态 -->
<!-- 				<view class="order-status">
					<view class="status-wrap"> -->
						<!-- 单元格 -->
<!-- 						<view class="cell">
							<view class="cell-left">
								<image class="cell-icon" src="/static/images/user/icon-order.png" mode="aspectFill"></image>
								<view class="cell-text">全部订单</view>
							</view>
							<view class="iconfont iconmore1"></view>
						</view> -->
			
						<!-- 订单状态 -->
	<!-- 					<view class="status-list">
							<view class="status-item" hover-class="btn-hover" v-for="(item, index) in orderStatusList" :key="index">
								<view class="item-icon" :class="item.icon"></view>
								<view class="item-text">{{ item.name }}</view>
							</view>
						</view>
					</view>
				</view> -->
			
				<!-- 滑动导航 -->
				<view style="border-radius: 20rpx; overflow: hidden; margin: 0 20rpx;">
					<com-nav :list="list" :col="2"></com-nav>
				</view>
			
				<!-- 用户功能 -->
				<view class="com-item">
					<view class="com-wrap">
						<view class="cell" v-for="(item, index) in userList" :key="index">
							<view class="cell-left" @click="userlist(item.path)">
								<image class="cell-icon" :src="item.icon" mode="aspectFill"></image>
								<view class="cell-text">{{ item.title }}</view>
							</view>
							<view class="iconfont iconmore1"></view>
						</view>
					</view>
				</view>
			
	
			</view>
		</view>
	</view>

</template>

<script>
import comNav from './components/com-nav.vue'
	export default {
		components: {
			comNav
		},
		data() {
			return {
				title: '微信授权登录',
				host:'' ,//配置为自己的额服务器即可,
				loginModel:{},
				code:'',
				islogin:false,
				userInfo: {
						headPicUrl: '/static/images/user/avatar.jpg',
						nickName: '史蒂芬.林'
					},
					orderStatusList: [
						{ name: '待付款', icon: 'iconfont icon31daifukuan', status: 10 },
						{ name: '待发货', icon: 'iconfont icon31daifahuo', status: 30 },
						{ name: '待收货', icon: 'iconfont icon31daishouhuo', status: 40 },
						{ name: '待评价', icon: 'iconfont icon31daipingjia', status: 50 },
						{ name: '已完成', icon: 'iconfont icon31yiguanzhudianpu', status: 55 }
					],
					currentIndex: 0,
					list: [
						{
							icon: '/static/images/user/class-01.png',
							text: '个人信息'
						},
						{
							icon: '/static/images/user/class-02.png',
							text: '我的订单'
						}
					],
					userList: [
						{
							title: '点歌历史',
							icon: '/static/images/user/icon-address.png',
							path: 'address-list'
						},
						{
							title: '充值记录',
							icon: '/static/images/user/icon-collect.png',
							path: '/pages/rechangelist/rechangelist'
						},
						{
							title: '充值入口',
							icon: '/static/images/user/icon-foot.png',
							path: '/pages/rechange/rechange'
						}
					],
			}
		},
		onLoad() {
			let that=this;
			if(that.$store.state.userid == '')
			{
				that.islogin=false;
			}
			else{
				that.islogin=true;
				that.userInfo=that.$store.state.wechatuser
			}
			uni.login({
				success: (res) => {
					if(res.code){
						that.code=res.code;
						console.log('code',res.code);
					}
					else{
						console.log('登录失败'+res.errMsg)
					}
				}
			})
		}
		,
		methods: {
			test(){
				console.log("test")
			},
			userlist(pages){
				uni.navigateTo({
					url:pages
				})
			},
			wxLogin() {
			let vue=this;
				// 获取code 小程序专有，用户登录凭证。
				uni.getUserProfile({
					desc: "获取用户基本资料",
					lang: 'zh_CN',
					success: (user) => {
						//获取成功基本资料后开启登录，因为基本资料首先要授权
						uni.login({
							provider: 'weixin',
							success: function(code_res) {
								vue.userInfo=user.userInfo;
								console.log(user);
								if (code_res.errMsg == "login:ok" ) {
									let code = code_res.code;
									console.log("666")
									uni.request({
										method:'POST',
										url:'http://localhost:8080/user/getSessionAndOpen',
										data:{code:vue.code,encryptedData:user.encryptedData,iv:user.iv},
										success: (res) => {
											console.log(res);
											vue.$store.commit("userid",res.data.data.userId)
											vue.$store.commit("wechatuser",vue.userInfo)
											vue.islogin=true;
											uni.request({
												method:'POST',
												url:'http://localhost:8080/user/updatelogintime',
												data:vue.$store.state.userid,
												success: (res) => {
													console.log("updatelogin",res);
												}
											})
											console.log(vue.islogin)
										},
										fail: (res) => {
											console.log(res)
										}
									})
									// uni.request({
									// 	url: this.host+"/weixin/tocode/code/" + code,
									// 	success: (session_res) => {
									// 		uni.request({
									// 			url: this.host+"/weixin/deuser",
									// 			method: 'POST',
									// 			data: {
									// 				'code': code,
									// 				'iv': user.iv,
									// 				'en_data': user.encryptedData,
									// 			},
									// 			success: (user_res) => {
									// 				if (user_res.data.code == 0) {
									// 					try{
									// 						//存储到本地缓存
									// 						uni.setStorageSync('token',user_res.data.result.token);
									// 						uni.showModal({
									// 							title:"登录成功",
									// 							showCancel:false
									// 						})
									// 					}catch{
									// 						uni.showModal({
									// 							title:"登录失败",
									// 							showCancel:false
									// 						})
									// 					}
														
									// 				} else {
									// 					uni.showModal({
									// 						title:"服务器异常",
									// 						showCancel:false
									// 					})
									// 				}
									// 			},
									// 		})
									// 	},
									// });
								}
							}
						});
					},
					fail: (res) => {
						uni.showModal({
							title:"用户拒绝授权",
							showCancel:false
						})
					}
				});
			}
		}
	};

</script>

<style scoped lang="scss">
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}
	.text-area {
		display: flex;
		justify-content: center;
	}
	
	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
	
	.btn-hover {
		background: #f2f2f2 !important;
	}
	.user {
		.user-wrap {
			display: flex;
			justify-content: center;
			align-items: center;
			height: 50vw;
			padding: 30rpx;
			z-index: 9;
			border-radius: 0 0 20% 20%;
			background: url('https://handsel.oss-cn-shenzhen.aliyuncs.com/1588938371592.jpg') no-repeat;
			background-size: cover;
			.setting {
				color: #fff;
				position: absolute;
				top: 60rpx;
				left: 60rpx;
				font-size: 50rpx;
			}
			.info {
				position: absolute;
				text-align: center;
				.avatar {
					width: 150rpx;
					height: 150rpx;
					border-radius: 50%;
				}
				.nickname {
					color: #fff;
					font-size: 28rpx;
				}
			}
		}
		.order-status {
			padding: 0 20rpx;
			margin-top: -10vw;
			.status-wrap {
				border-radius: 25rpx;
				overflow: hidden;
				.status-list {
					display: flex;
					justify-content: space-evenly;
					align-items: center;
					background: #fff;
					padding-top: 30rpx;
					padding-bottom: 30rpx;
					.status-item {
						flex: 1;
						display: flex;
						flex-direction: column;
						justify-content: center;
						align-items: center;
						.item-icon {
							line-height: 1;
							font-size: 65rpx;
							color: #bbb;
						}
						.item-text {
							font-size: 28rpx;
							color: #666;
							margin-top: 5rpx;
						}
					}
				}
			}
		}
		.com-item {
			padding-left: 20rpx;
			padding-right: 20rpx;
			margin-top: 20rpx;
			.com-wrap {
				border-radius: 25rpx;
				overflow: hidden;
			}
		}
		.cell {
			height: 80rpx;
			padding-left: 20rpx;
			padding-right: 20rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			background: #fff;
			border-bottom: 1px solid #f8f8f8;
			&:active {
				background: #f2f2f2;
			}
			&:last-child {
				border-bottom: none !important;
			}
			.cell-left {
				display: flex;
				align-items: center;
				.cell-icon {
					width: 50rpx;
					height: 50rpx;
				}
				.cell-text {
					color: #666;
					font-size: 28rpx;
					margin-left: 20rpx;
				}
			}
			.iconfont {
				font-size: 40rpx;
				color: #999;
			}
		}
	}
</style>

