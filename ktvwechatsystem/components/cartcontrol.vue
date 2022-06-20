<template>
	<view style="display: flex;flex-direction: row;height: 27px;">
		<view class="food-control">
			<view class="cont" style="margin-top: 2px;" @click="decreaseCart(food)" v-show="food.count>0">
				<image src="/static/des.png" mode="" style="width: 20px;height: 20px;"></image>
			</view>
			<!-- <text style="padding: 0 4px;" v-show="food.count>0">{{food.count}}</text> -->
			<input type="number" maxlength="3"
					style="margin: 0 2px;width: 36px;padding: 0 1px;border: 1px solid #C8C7CC;border-radius: 3px;" v-show="food.count>0"
					v-model="food.count"  @input="inputCart(food)"/>
		</view>
		<view style="flex: 1;margin-top: 2px; " class="cont" @click="addCart(food)">
			<image src="/static/add2.png" style="width: 20px;height: 20px;color: #CCCCCC;" mode=""></image>
		</view>
	</view>
</template>

<script>
	// import Vue from 'vue'
	export default {
		props: {
			food: {
				type: Object,
			}
		},
		data() {
			return {
			};
		},
		methods: {
			addCart: function(item) {
				this.$emit('add',item)
			},
			decreaseCart(item) {
				this.$emit('dec',item)
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
	.food-control {
		display: flex;
		flex: 1;
		justify-content: space-around;
	}

	.cont {
		width: 22px;
		height: 22px;
		box-sizing: border-box;
		border-radius: 50%;
		text-align: center;
	}
</style>
