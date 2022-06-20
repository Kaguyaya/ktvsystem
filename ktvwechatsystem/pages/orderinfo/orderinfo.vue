<template>
	<view class='box' v-if="reload">
		<view class="sun">
			<image src="../../static/sun.png" alt="" class='sunbody'>
				<image src="../../static/eye.png" alt="" class='eye'>
		</view>
		<text class="tip">生成中...</text>
	</view>
	<view class="r_detail" v-else>
		<view class='tag-e'>
			<view class="goods " v-for="order in orderlist">
				<view>
					<image :src="order.itemImg"></image>
				</view>
				<view class='goods_02'>
					<view class='goods_title'>{{order.itemName}} x{{order.consumeCount}}</view>
					<view class='good_p'>
						<view class="good_price">¥{{order.consumePrice}}</view>
					</view>
				</view>
			</view>
		</view>
		<view class="total">{{order_info.pay_statement==0?'待支付':'支付'}}：¥ {{orderinfo.allPrice}}</view>
		<view class="mess">
			<view class="mess_01">订单信息</view>
			<view class="mess_02">
				<view class="mess_02_1">
					<view class="mess_02_l">订单编号:</view>
					<view class="mess_02_r">{{orderinfo.itemOrderId}}</view>
				</view>
				<view class="mess_02_1">
					<view class="mess_02_l">{{order_info.pay_statement==0?'创建时间':'支付时间'}}: </view>
					<view class="mess_02_r">{{orderinfo.createtime}}</view>
				</view>
				<view class="mess_02_1">
					<view class="mess_02_l">订单状态: </view>
					<view class="mess_02_r">{{state[order_info.state]}}</view>
				</view>
			</view>
			<view class="mt-3">
				<u-row>
					<u-col :span="4">
						<view style="text-align: center;">
							<u-button type="error" size="medium" @click="gotoindex()">关闭</u-button>
						</view>
					</u-col>
				</u-row>
			</view>
		</view>
		<view class="KB" style="height: 80px;"></view>
		<u-toast ref="uToast" />
		<u-modal v-model="tuiShow" :show-title="false" :content="tuiContent" :show-cancel-button="true">
		</u-modal>
	</view>
</template>

<script>
	import Vue from 'vue'
	export default {
		data() {
			return {
				ktvroomid: '',
				allPrice: '',
				orderlist: [],
				order: '',
				orderinfo: '',
				reload: "true",
				state: ["正常状态", "退款中", "已退款", "已驳回"],
				tuiShow: false,
				tuiContent: '确认退款该订单吗？',
				order_info: {
					name: "思维开阔基础java课",
					price: "5.01",
					url: require('@/tupian/touxiang.jpg'),
					order_id: "2255",
					order_num: "D323906381817708",
					update_time: "2021-03-23 17:10:38",
					state: "0"
				}
			};
		},
		created() {
			
		},
		onLoad(items) {
			let vue = this;
			vue.iteminfo = JSON.parse(decodeURIComponent(items.iteminfo))
			vue.orderinfo=vue.iteminfo;
				vue.init();
			setInterval(() => {
				vue.reload = false;
			}, 1000)
		},
		onUnload() {
			console.log("页面销毁")
		},
		methods: {
			// 解决浮点数 运算出现多位小数
			accMul(arg1, arg2) {
				let m = 0,
					s1 = '',
					s2 = '';
				if (arg1 && arg1 != null)
					s1 = arg1.toString();
				if (arg2 && arg2 != null)
					s2 = arg2.toString();
				// console.log('m1',s2.replace('.',''))
				try {
					m += s1.split('.')[1].length
				} catch (e) {}
				try {
					m += s2.split('.')[1].length
				} catch (e) {}

				return Number(s1.replace('.', '')) * Number(s2.replace('.', '')) / Math.pow(10, m);
			},
			init(){
				let vue=this;
				console.log("iteminfo",vue.iteminfo)
				uni.request({
					method:'POST',
					url:'http://localhost:8080/Itemconsume/searchByIdList',
					data:vue.iteminfo.itemOrderId,
					success: (res) => {
						console.log(res);
						vue.orderlist=res.data.data
						res.data.data.forEach((item)=>{
							uni.request({
								method:'POST',
								url:'http://localhost:8080/KtvItem/searchItemsById',
								data:item.itemId,
								success: (res) => {
									Vue.set(item,'itemImg',res.data.data.itemImg)
								}
							})
						})
					}
				})
			},
			guid() {
				return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
					var r = Math.random() * 16 | 0,
						v = c == 'x' ? r : (r & 0x3 | 0x8);
					return v.toString(16);
				});
			},
			gotoindex() {
				uni.switchTab({
					url: '/pages/index/index'
				})
				console.log("runrunrun")
			},
			createorder() {
				let vue = this;

				uni.request({
					method: 'POST',
					data: vue.ktvroomid,
					url: 'http://localhost:8080/KtvItemOrder/insert',
					success: (res) => {
						vue.goods.consumeOrderId = res.data.data.itemOrderId;
					}
				})
			},
			itemconsume() {
				let vue = this;
				uni.request({
					//记录
					method: 'POST',
					url:'http://localhost:8080/Itemconsume/insertById',
					data: vue.goods,
					success: (res) => {
						vue.orderinfo = res.data.data;
						//商品
						uni.request({
							method: "POST",
							url: 'http://localhost:8080/KtvItem/searchItemsById',
							data: res.data.data.itemId,
							success: (response) => {
								console.log("商品", response)
								Vue.set(res.data.data, "itemImg", response.data.data.itemImg)
								vue.orderlist.push(res.data.data);
								console.log("res", res.data.data)
							}
						})
					}
				})

			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #f4c042;
		height: 100%;
	}

	.box {
		width: 306rpx;
		height: 113rpx;
		margin: 300rpx auto 0;
		background: url(../../static/1.png) no-repeat left bottom;
		background-size: contain;
		position: relative;
	}

	.sun {
		width: 136rpx;
		height: 136rpx;
		position: absolute;
		left: 0;
		right: 0;
		margin: 0 auto;
		z-index: -1;
		animation: move 2.7s infinite linear;
	}

	.sun .eye {
		position: absolute;
		top: 28%;
		left: 33%;
		width: 22rpx;
		height: 6rpx;
		animation: eye 2.7s infinite linear;
	}

	.sun .sunbody {
		width: 136rpx;
		height: 136rpx;
		animation: scroll 2.7s linear infinite;
	}

	.box .tip {
		text-align: center;
		padding: 0 100rpx;
		font-family: '苹方';
		font-size: 25rpx;
		color: #7a5600;
		animation: opp 2.7s infinite linear;
	}

	@keyframes move {
		0% {
			transform: translateY(0);
		}

		10% {
			transform: translateY(0);
		}

		40% {
			transform: translateY(-55rpx);
		}

		70% {
			transform: translateY(-55rpx);
		}

		100% {
			transform: translateY(0);
		}
	}

	@keyframes scroll {
		0% {
			transform: rotate(0);
		}

		40% {
			transform: rotate(0);
		}

		50% {
			transform: rotate(45deg);
		}

		60% {
			transform: rotate(45deg);
		}

		100% {
			transform: rotate(45deg);
		}
	}

	@keyframes eye {
		0% {
			transform: translateX(0rpx);
		}

		50% {
			transform: translateX(0rpx) scale(1, 1);
		}

		55% {
			transform: translateX(0rpx) scale(1, 0.1);
		}

		60% {
			transform: translateX(0rpx) scale(1);
		}

		70% {
			transform: translateX(26rpx);
		}

		100% {
			transform: translateX(26rpx);
		}
	}

	@keyframes opp {
		0% {
			opacity: 1;
		}

		40% {
			opacity: 1;
		}

		60% {
			opacity: 0;
		}

		100% {
			opacity: 1;
		}
	}

	.r_detail {
		background-color: #F5F5F5;
		height: 100vh;

		.head {
			display: flex;
			justify-content: space-between;
			background-color: #F85043;
			padding: 10px 20px 0;

			.head_l {
				padding: 20px;
				color: #FDF1F0;

				.head_l_01 {
					font-size: 16px;
					padding-bottom: 6px;
				}

				.head_l_02 {
					font-size: 13px;
				}
			}

			.head_r img {
				height: 100px;
				width: 120px;
			}
		}

		.address {
			display: flex;
			padding: 20px 15px;
			;
			background-color: #fff;
			margin-bottom: 10px;

			.address_l {
				padding-right: 20px;
				display: flex;
				flex-direction: column;
				justify-content: center;

				img {
					width: 20px;
					height: 20px;
				}
			}

			.address_r {
				flex-grow: 1;

				.address_r_01 {
					display: flex;
					justify-content: space-between;
					font-size: 15px;
					font-weight: 600;
					padding-bottom: 8px;
				}

				.address_r_02 {
					line-height: 20px;
				}
			}
		}

		.tag-e {
			background-color: #fff;
			margin-bottom: 10px;

			.goods {
				display: flex;
				font-size: 14px;
				width: 100%;
				background-color: #ffffff;
				padding: 10px;
				box-sizing: border-box;
				border-bottom: 1px solid #EEF0EF;

				image {
					width: 160rpx;
					height: 160rpx;
				}

				.goods_02 {
					box-sizing: border-box;
					display: flex;
					flex-direction: column;
					height: 160rpx;
					flex-grow: 1;
					justify-content: space-between;
					padding-top: 10rpx;
					padding-left: 25px;
				}

				.goods_title {
					max-height: 40px;
					overflow: hidden;
					line-height: 20px;
					font-weight: 600;
					font-size: 15px;
				}

				.goods_des {
					color: #979797;
				}

				.good_p {
					display: flex;
					justify-content: space-between;

					.good_price {
						color: #F04E42;
						font-weight: 600;
					}
				}

			}
		}

		.total {
			background-color: #fff;
			padding: 12px 15px;
			text-align: right;
			margin-bottom: 10px;
			font-size: 15px;
		}

		.mess {
			background-color: #fff;
			padding: 10px;
			margin-bottom: 10px;

			.mess_01 {
				border-left: 2px solid #FD5153;
				padding-left: 10px;
				margin-bottom: 10px;
				font-size: 15px;
				font-weight: 600;
			}

			.mess_02 {
				border-top: 1px solid #F8F8F8;
				padding: 10px;

				.mess_02_1 {
					display: flex;
					line-height: 25px;

					.mess_02_l {
						width: 100px;
					}
				}
			}
		}

		.foot {
			position: fixed;
			bottom: 0px;
			left: 0px;
			z-index: 99;
			border-top: 1px solid #E4E4E4;
			width: 100%;
			background-color: #fff;
			justify-content: flex-end;
			padding: 10px 15px;
			display: flex;
		}

	}
</style>
