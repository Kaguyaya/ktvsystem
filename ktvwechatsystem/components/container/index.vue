<template>
	<view class="wrap">
		<view class="header">{{title}}</view>
		<view class="content">
			<slot></slot>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			title: {
				type: String,
				default: ''
			}
		},
		components: {},
		data() {
			return {}
		},
		onLoad() {

		},
		methods: {

		}
	}
</script>

<style lang="scss" scoped>
	.wrap {

		.header {
			padding: 20rpx 0 20rpx 20rpx;
			font-size: 24rpx;
			color: #909ca2;
			letter-spacing: 4rpx;
			background-color: #f7f7f7;
		}
		.content{
			padding: 20rpx 0;
		}
	}
</style>
