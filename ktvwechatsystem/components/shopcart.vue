<template>
	<view class="shopcart">
		<!-- @click="toggleList" -->
		<view class="cartBottom" >
			<view class="carIcon" @click="toggleList">
				<view class="iconBox" :class="getAllCount ? 'active' : '' ">
					<text class="allcount" v-if="getAllCount">{{getAllCount}}</text>
					<image src="/static/cart.png" mode="" class="img"></image>
				</view>
			</view>
			<view class="middle" @click="toggleList">
				<text class="price" :class="getAllCount ?　'active': ''">￥{{getAllPrice}}</text>
				<text class="deliveryPrice" style="font-size: 12px;">免配送费|支持自取</text>
			</view>
			<view class="BtnRight">
			<button :type="buyType" style="border-radius: 20rpx;" :disabled="buyDis"  @click="buyList">结算</button>
			</view>
		</view>
		<!-- 选择的商品 -->
		<view class="cartList" v-show="isShowList && getList.length">
			<scroll-view scroll-y style="max-height: 200px;">
				<view class="title">
					<text>购物车</text>
					<view class="clear" @click="delShopcart">
						清空
					</view>
				</view>
				<view class="list">
					<view class="list-text" v-for="(item,index) in getList" :key="index">
						<text style="flex:1">{{item.itemName}}</text>
						<text style="flex:1">￥{{item.itemPrice}}</text>
						<cartcontrol :food="item" @add="addCart" @dec="decreaseCart" @input="inputCart"></cartcontrol>
					</view>
				</view>
			</scroll-view>
		</view>
		<view class="listMask" v-show="isShowList && getList.length" @click="toggleList"></view>
	</view>
</template>

<script>
	import cartcontrol from '@/components/cartcontrol.vue'
	// import {mul} from '@/utils/lib'
	export default {
		props: {
			buyType:'default',
			buyDis:true,
			goods: {
				type: Array
			}
		},
		data() {
			return {
				isShowList: false,
			};
		},
		components: {
			cartcontrol
		},
		computed: {
			getList() {
				let result = [];
				this.goods.forEach((good) => {
					good.ktvItems.forEach((food) => {
						if (food.count) {
							result.push(food)
							// console.log('res', JSON.stringify(result))
						}
					})
				})
				return result
			},
			// 获得购物车所有商品数量
			getAllCount() {
				let result = 0;
				this.getList.forEach((food) => {
					result += food.count
				})
				return result
			},



			// 总价
			getAllPrice() {
				let result = 0;
				let result1 = 0;
				this.goods.forEach((good) => {
					good.ktvItems.forEach((food) => {
						result1 += this.accMul(food.count, food.itemPrice)
						result = result1.toFixed(2);
					})
				})
				return result
			}
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
		

			toggleList() {
				if (this.getList.length) {
					this.isShowList = !this.isShowList;
				}
			},
			buyList() {
				
				if (this.getList.length) {
					console.log("getAllPrice："+this.getAllPrice)
					console.log('buyList：'+JSON.stringify(this.getList))
					console.log("getallconunt"+this.getAllCount)
					uni.showToast({
						title:"总价格："+this.getAllPrice
					})
					const iteminfo={
						allPrice:this.getAllPrice,
						allCount:this.getAllCount,
						itemlist:this.getList,
						orderid:'233'
					}
					uni.navigateTo({
						url:'/pages/payment/payment?iteminfo='+encodeURIComponent(JSON.stringify(iteminfo))
					})
				}else{
					
				}
			},
			delShopcart() {
				this.$emit('delAll');
			},
			addCart: function(item) {
				this.$emit('add', item)
			},
			decreaseCart(item) {
				this.$emit('dec', item)
			},
			inputCart(item) {
				this.fcount = item.count
				if (item.count >= 999) {
					uni.showToast({
						title: "该宝贝不能购买更多了~"
					})
					return false;
				} else {
					this.$emit('input', item)
				}
			
			}

		},
	}
</script>

<style scoped>
	.list-text {
		display: flex;
		flex-direction: row;
	}

	.shopcart .cartBottom {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		height: 54px;
		z-index: 99;
		display: flex;
		background-color: #141d27;
	}

	.carIcon {
		flex: 1;
	}

	.iconBox {
		position: absolute;
		bottom: 22px;
		left: 18px;
		z-index: 101;
		background-color: rgb(70, 73, 75);
		border-radius: 50%;
		height: 48px;
		width: 48px;
		line-height: 55px;
		/* border: 6px solid #141d27; */
	}

	.iconBox .allcount {
		position: absolute;
		right: -6px;
		top: 0;
		display: inline-block;
		padding: 0 6px;
		font-size: 9px;
		line-height: 16px;
		font-weight: 400;
		border-radius: 10px;
		background-color: #f01414;
		color: #ffffff;
	}

	.img {
		font-size: 24px;
		line-height: 24px;
		width: 30px;
		height: 30px;
		padding-left: 6px;
		padding-top: 6px;
		color: #cccccc;
		border-radius: 50%;
	}

	.carIcon .active {
		background-color: #00a0dc;
	}

	.middle {
		display: flex;
		flex-direction: column;
		flex: 2;
		color: #ffffff;
	}

	.BtnRight {
		flex: 1;
		border-radius: 20rpx;
		margin: 10rpx;
		z-index:99999;
	}

	.cartList {
		position: fixed;
		bottom: 54px;
		left: 0;
		right: 0;
		z-index: 90;
	}

	.cartList .title,
	.cartList .list-text {
		display: flex;
		flex-direction: row;
	}

	.cartList .title {
		background: #F3F5F7;
		justify-content: space-between;
		padding: 4px 8px;
	}

	.cartList .list-text {
		padding: 10px 6px 10px 8px;
		text-align: center;
	}

	.list {
		background: #F8F8F8;
		padding-bottom: 10px;
	}


	/* 遮布 */
	.listMask {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 89;
		opacity: 0.5;
		background: #6a7076;
	}
</style>
