<template>
	<view>
			<image class="ktv" mode="aspectFit" src="../../static/ktv.png" @click="scan()"></image>
		<view>
			<!-- 提示窗示例 -->
			<uni-popup ref="alertDialog" type="dialog">
				<uni-popup-dialog :type="msgType" cancelText="关闭" confirmText="同意" title="通知" content="您已激活此房间" @confirm="dialogConfirm"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				
			};
		},
		methods:{
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
									url:'../index/index'
								})
							},
			scan(){
				let vue=this;
				uni.scanCode({
						onlyFromCamera: true,
						success: function (res) {
							console.log('条码类型：' + res.scanType);
							console.log('条码内容：' + res.result);
							uni.request({
								method:'POST',
								url:'http://localhost:8080/room/searchRoomById',
								data:res.result,
								success: (res) => {
									vue.$store.commit("room",res.data.data)
									console.log(vue.$store.state.room)
									console.log(!res.data.data.roomIsopen)
									if(!res.data.data.roomIsopen){
										vue.$store.state.room=res.data.data;
										uni.navigateTo({
											url:'../openRooms/openRooms'
										})
									}else{
										vue.dialogToggle('success')
										
									}
									// this.$store.state.roomid=res.data.data
								}
							})
						}
				})
			}
		},
		mounted(){
			let vue = this;
			console.log(vue.$store.state.userid=='')
			if (vue.$store.state.userid == '') {
					uni.switchTab({
						url:'/pages/myinfo/myinfo'
					})
			}
		}
	}
</script>

<style lang="scss">
.ktv{
	width: 320px;
	height: 568px;
}
	@mixin flex {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
	}

	@mixin height {
		/* #ifndef APP-NVUE */
		height: 100%;
		/* #endif */
		/* #ifdef APP-NVUE */
		flex: 1;
		/* #endif */
	}

	.box {
		@include flex;
	}

	.button {
		@include flex;
		align-items: center;
		justify-content: center;
		flex: 1;
		height: 35px;
		margin: 0 5px;
		border-radius: 5px;
	}

	.example-body {
		background-color: #fff;
		padding: 10px 0;
	}

	.button-text {
		color: #fff;
		font-size: 12px;
	}

	.popup-content {
		@include flex;
		align-items: center;
		justify-content: center;
		padding: 15px;
		height: 50px;
		background-color: #fff;
	}

	.popup-height {
		@include height;
		width: 200px;
	}

	.text {
		font-size: 12px;
		color: #333;
	}

	.popup-success {
		color: #fff;
		background-color: #e1f3d8;
	}

	.popup-warn {
		color: #fff;
		background-color: #faecd8;
	}

	.popup-error {
		color: #fff;
		background-color: #fde2e2;
	}

	.popup-info {
		color: #fff;
		background-color: #f2f6fc;
	}

	.success-text {
		color: #09bb07;
	}

	.warn-text {
		color: #e6a23c;
	}

	.error-text {
		color: #f56c6c;
	}

	.info-text {
		color: #909399;
	}

	.dialog,
	.share {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
	}

	.dialog-box {
		padding: 10px;
	}

	.dialog .button,
	.share .button {
		/* #ifndef APP-NVUE */
		width: 100%;
		/* #endif */
		margin: 0;
		margin-top: 10px;
		padding: 3px 0;
		flex: 1;
	}

	.dialog-text {
		font-size: 14px;
		color: #333;
	}
</style>
