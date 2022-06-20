<template>
	<view class="content">
		<view :style="[MobileLeft]">
			<view v-show="!isNav">
				<view v-for="(item,index) in menuLists" :class="menuIndex==index ? 'menuListSelect' : 'menuList' " @click="selectMenu(index,item)">
					{{item.typeName}}
				</view>
			</view>
			<view v-show="isNav">
				<slot name="nav"></slot>
			</view>
			<view :style="[foot]"></view>
		</view>
		
		<view :style="[MobileRight]">
			<view v-show="!isSlot">
				<view class="goodsList" v-for="(it,index) in ktvgoods">
					<image :src="it.itemImg" :style="[ImgStyle]" mode="aspectFill"></image>
					<view class="goodsDesc">
						<text>{{it.itemName}}</text>
						<view class="hot">月销量{{it.itemConsume}}</view>
						<!-- 加减 -->
						<view class="food-btm">
							<text class="food-price">￥{{it.itemPrice}}</text>
						<cartcontrol :food="it" @add="addCart" @dec="decreaseCart" @input="inputCart">
						</cartcontrol>
						</view>
					</view>
				</view>
			</view>
			<view v-show="isSlot">
				<slot name="list"></slot>
			</view>
			<view :style="[foot]"></view>
		</view>
	<shopcart :goods="goods" @add="addCart" @dec="decreaseCart" @input="inputCart" @delAll="delAll"></shopcart>
	</view>
</template>

<script>
	import cartcontrol from '@/components/cartcontrol.vue'
	import shopcart from '@/components/shopcart.vue';
	import Vue from 'vue'
	export default {
		props:{
			menuLists:{
				type:[Array,Number,Object],
				default:[]
			},
			imgSize:{
				type:String,
				default:'120rpx'
			},
			bottomSize:{
				type:String,
				default:'0rpx'
			},
			isSlot:{
				type:Boolean,
				default:false
			},
			isNav:{
				type:Boolean,
				default:false
			}
		},
		data() {
			return {
				menuIndex: 0,
				goods:[],
				ktvgoods:[],
				height:'',
				title:'',
				menu:''
			}
		},
		components: {
			cartcontrol,
			shopcart,
		},
		created() {
			//设置手机的高度
			var me = this
			uni.getSystemInfo({
			    success: function (res) {
					me.height = res.windowHeight
			    }
			});
			uni.request({
				method:'POST',
				url:'http://localhost:8080/ktvItemType/getItemAndType',
				success: (res) => {
					me.menu=res.data.data
					console.log("me",res)
					if(me.menu == []){
						
					}
					me.goods=me.menu;
					me.ktvgoods = me.menu[0].ktvItems
				}
			})
		},
		mounted() {
			
			
		},
		computed:{
			// 右侧产品样式
			MobileRight(){
				let style = {}
				style.position = 'fixed'
				style.right = '0rpx'
				style.width = '520rpx'
				style.height = this.height +'px'
				style.right = "3rpx"
				style.backgroundColor = '#FFFFFF'
				style.overflow = 'auto'
				return style
			},
			//左侧菜单样式
			MobileLeft(){
				let style = {}
				style.position = 'fixed'
				style.left = '0rpx'
				style.width = '230rpx'
				style.height = this.height +'px'
				style.backgroundColor = '#f3f4f6'
				style.overflow = 'auto'
				return style
			},
			// 产品图片样式
			ImgStyle(){
				let style = {}
				style.width = this.imgSize
				style.height = this.imgSize
				style.marginTop = '10rpx'
				return style
			},
			//底部距离
			foot(){
				let style = {}
				style.width = '100%'
				style.float = 'left'
				style.height = this.bottomSize
				return style
			}
		},
		methods: {
			// 菜单选择
			selectMenu(index,data){
				// console.log("index",index)
				// console.log("data",data)
				// console.log(this.menu)
				this.menuIndex = index
				this.ktvgoods = this.menu[index].ktvItems
				this.title = this.menuLists[index].typeName

			},

				addCart: function(item) {
						// console.log('ev', JSON.stringify(item))
								
							console.log("itemcount",item)
							if (item.count >= 0) {
								item.count++
								this.goods.forEach((good) => {
									good.ktvItems.forEach((food) => {
										if (item.itemName == food.itemName)
											food.count = item.count
									})
								})
								console.log("goodstrue",this.goods)
								// console.log('c++', JSON.stringify(item))
							} else {
								this.goods.forEach((good) => {
									good.ktvItems.forEach((food) => {
										if (item.itemName == food.itemName)
											Vue.set(food, 'count', 1)
										// food.count = 1
										// console.log('add-shop', JSON.stringify(food))
									})
								})
								console.log("goodsfalse",this.goods)
							}
						},
						decreaseCart(item) {
							if (item.count) {
								item.count--
								this.goods.forEach((good) => {
									good.ktvItems.forEach((food) => {
										if (item.itemName == food.itemName)
											food.count = item.count
										// console.log('dec-shop', JSON.stringify(this.foods))
									})
								})
							}
						},
						inputCart: function(item) {
							if (item.count >= 0) {
								item.count++
								this.goods.forEach((good) => {
									good.ktvItems.forEach((food) => {
										if (item.itemName == food.itemName)
											food.count = item.count + -1
									})
								})
								// console.log('c++', JSON.stringify(item))
						
							} else {
								this.goods.forEach((good) => {
									good.ktvItems.forEach((food) => {
										if (item.itemName == food.itemName)
											Vue.set(food, 'count', 1)
										// food.count = 1
										// console.log('add-shop', JSON.stringify(food))
									})
								})
							}
						},
						// 清空购物车
						delAll() {
							this.goods.forEach((good) => {
								good.ktvItems.forEach((food) => {
									if (food.count) {
										food.count = 0
										// console.log(JSON.stringify(food));
									}
								})
							})
						}
			},
		}
</script>

<style lang="scss" scoped>
	.menuList{
		text-align: center;
		padding: 20rpx 10rpx;
		font-size: 30rpx;
	}
	.menuListSelect{
		text-align: center;
		background-color: #FFFFFF;
		padding: 30rpx 0rpx;
		font-size: 32rpx;
		font-weight: bold;
	}
	.goodsList{
		float: left;
		border-bottom: 1rpx solid #f3f4f6;
		padding: 10rpx;
		text{
			font-weight: bold;
			font-size: 32rpx;
		}
	}
	.title{
		font-weight: bold;
		font-size: 32rpx;
		padding: 10rpx;
	}
	.hot{
		color: red;
		margin-top: 10rpx;
		font-size: 26rpx;
	}
	.price{
		margin-top: 10rpx;
		font-size: 30rpx;
		font-weight: bold;
		color: #dd6161;
	}
	.type{
		margin-left: 180rpx;
		width: 105rpx;
		display:inline-block;
		margin-bottom: 10rpx;
		background-color: #dd6161;
		padding: 5rpx 10rpx;
		border-radius: 10rpx;
		color: #FFFFFF;
		text-align: center;
		font-size: 26rpx;
	}
	.goodsDesc{
		margin-left: 25rpx;
		float: right;
	}
	.food-btm,
	.content {
		display: flex;
		flex-direction: row;
	}
	.food-btm {
		justify-content: space-between;
	
	}
</style>
