<template>
	<view class="container">
		<view class="ui-all">
			<view class="avatar" >
				<view  class="imgAvatar">
					<view class="iavatar" :style="'background: url('+avater+') no-repeat center/cover #eeeeee;'"></view>
				</view>

			</view>
			<view class="ui-list">
				<text>昵称</text>
				<input type="text" :placeholder="value" :value="userinfo.userNickname" @input="bindnickName" placeholder-class="place" />
			</view>

			<view class="ui-list">
				<text>注册时间</text>
						<text>{{userinfo.userRegistertime}}</text>
			</view>

			<view class="ui-list right">
				<text>生日</text>
					<picker mode="date" :value="userinfo.userBirthday" @change="bindDateChange">
							<view class="picker">
								{{userinfo.userBirthday}}
							</view>
						</picker>
						</view>
			<view class="ui-list ">
				<text>余额</text>
				<text>{{userinfo.userBalance}}</text>
			</view>
			<button class="save" @tap="back()">保 存 修 改</button>
		</view>
		
	</view>
</template>

<script>
	export default {
	
		data() {
			return {
				avater:'',
				userinfo:'',
				sex:["男","女","未知"]
			}

		},
		methods: {
			bindDateChange(e) {
				console.log(e)
				this.userinfo.userBirthday=e.detail.value
				uni.request({
					method:'POST',
					url:'http://localhost:8080/user/updateuser',
					data:this.userinfo,
					success: (res) => {
						console.log(res);
						this.userinfo=res.data.data;
					}
				})
			},
			back(){
					uni.navigateBack({
						delta:1,//返回层数，2则上上页
					})
			},
			init(){
				uni.request({
					method:'POST',
					url:'http://localhost:8080/user/getUserById',
					data:this.$store.state.userid,
					success: (res) => {
						console.log(res)
						console.log(res.data.data.userRegistertime)
						if(res.data.data.userBirthday==null){
							res.data.data.uszerBirthday="未设置"
						}
						this.userinfo=res.data.data;
					}
				})
			},
		},
	
		mounted() {
			this.init();
			this.avater=this.$store.state.wechatuser.avatarUrl
		}

	}
</script>

<style lang="less">
	.container {
		display: block;
	}

	.ui-all {
		padding: 20rpx 40rpx;

		.avatar {
			width: 100%;
			text-align: left;
			padding: 20rpx 0;
			border-bottom: solid 1px #f2f2f2;
			position: relative;

			.imgAvatar {
				width: 140rpx;
				height: 140rpx;
				border-radius: 50%;
				display: inline-block;
				vertical-align: middle;
				overflow: hidden;

				.iavatar {
					width: 100%;
					height: 100%;
					display: block;
				}
			}

			text {
				display: inline-block;
				vertical-align: middle;
				color: #8e8e93;
				font-size: 28rpx;
				margin-left: 40rpx;
			}

			&:after {
				content: ' ';
				width: 20rpx;
				height: 20rpx;
				border-top: solid 1px #030303;
				border-right: solid 1px #030303;
				transform: rotate(45deg);
				-ms-transform: rotate(45deg);
				/* IE 9 */
				-moz-transform: rotate(45deg);
				/* Firefox */
				-webkit-transform: rotate(45deg);
				/* Safari 和 Chrome */
				-o-transform: rotate(45deg);
				position: absolute;
				top: 85rpx;
				right: 0;
			}
		}

		.ui-list {
			width: 100%;
			text-align: left;
			padding: 20rpx 0;
			border-bottom: solid 1px #f2f2f2;
			position: relative;

			text {
				color: #4a4a4a;
				font-size: 28rpx;
				display: inline-block;
				vertical-align: middle;
				min-width: 150rpx;
			}

			input {
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
			}
			button{
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
				background: none;
				margin: 0;
				padding: 0;
				&::after{
					display: none;
				}
			}
			picker {
				width: 90%;
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
				position: absolute;
				top: 30rpx;
				left: 150rpx;
			}

			textarea {
				color: #030303;
				font-size: 30rpx;
				vertical-align: middle;
				height: 150rpx;
				width: 100%;
				margin-top: 50rpx;
			}

			.place {
				color: #999999;
				font-size: 28rpx;
			}
		}

		.right:after {
			content: ' ';
			width: 20rpx;
			height: 20rpx;
			border-top: solid 1px #030303;
			border-right: solid 1px #030303;
			transform: rotate(45deg);
			-ms-transform: rotate(45deg);
			/* IE 9 */
			-moz-transform: rotate(45deg);
			/* Firefox */
			-webkit-transform: rotate(45deg);
			/* Safari 和 Chrome */
			-o-transform: rotate(45deg);
			position: absolute;
			top: 40rpx;
			right: 0;
		}

		.save {
			background: #030303;
			border: none;
			color: #ffffff;
			margin-top: 40rpx;
			font-size: 28rpx;
		}
	}
</style>
