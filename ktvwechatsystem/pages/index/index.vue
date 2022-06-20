<template>
	<view>
		<view>{{room}}</view>
		<container title='基础式'>
			<ls-swiper :list="base_lsit" imgKey="imgUrl" :loop="true" :dots='true' :autoplay='true' height='150' @clickItem="clickItem()" />
		</container>	
		<view style="text-align: center;" @click="openroom()">
			<image src="../../static/diange1.png" mode="widthFix" ></image>
		</view>
		<view style="text-align: center;" @click="shop()">
			<image src="../../static/shop.png" mode="widthFix"></image>
		</view>
	<view style="text-align: center;" @click="recommend()">
		<image src="../../static/recommend.png" mode="widthFix"></image>
	</view>
	</view>


</template>

<script>
	import api from '../../api/index.js'
	import container from '../../components/container/index.vue'
	import LsSwiper from '../../components/ls-swiper/index.vue'
	export default {
		components: {
			container,
			LsSwiper
		},
		data() {
			return {
				base_lsit: [],
				room:''
			}
		},
		onLoad() {
			this.getBase()
		},
		methods: {
			openroom(){
				uni.navigateTo({
					url:'../scans/scans'
				})
				console.log('scan')
			},
			getBase() {
				api.base(100).then(res => {
					this.base_lsit = res
				})
			},
			clickItem(data) {
				console.log(data)
			},
			back() {
				uni.redirectTo({
					url: '/pages/index/index',
				})
			},
			shop(){
				uni.switchTab({
					url:'/pages/orderItem/orderItem'
				})
			},
			recommend(){
				uni.redirectTo({
					url:'/pages/recommend/recommend'
				})
			},
			scan(){
				let vue=this;
				uni.scanCode({
					onlyFromCamera:true,
					success:function(res){
						console.log('条码类型:'+res.scanType);
						console.log('条码内容:'+res.result);
							vue.room=res.result;
							uni.request({
								method:"POST",
								url:"http://localhost:8080/room/searchRoomById",
								data:res.result,
								success: (successreponse) => {
									vue.room=successreponse.data.data.roomName
									console.log(successreponse)
								},
								fail: (failresponse) => {
									console.log(failresponse)
								}
							})
					}
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
	.header {
		margin-top: 40rpx;
		display: flex;
		align-items: center;
		height: 80rpx;
		padding-left: 20rpx;
		margin-bottom: 30rpx;
	
		.back {
			width: 30rpx;
			height: 30rpx;
		}
	}
</style>
