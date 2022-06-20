<template>
	<section class="aui-flexView">
		<header class="aui-navBar aui-navBar-fixed">
			<view @click="back()" class="aui-navBar-item">
				<i class="icon icon-return"></i>
			</view>
			<div class="aui-center">
				<span class="aui-center-title">充值记录</span>
			</div>
		</header>
		<section class="aui-scrollView">
			<div class="aui-tab" data-ydui-tab>
				<div class="tab-panel">
							<block v-for="(menuList2,index3) in rechargeinfo" :key="index3">
								<div class="tab-item" @click="switchorder(index3)">
		
									<div class="aui-mail-product">
										<div class="aui-mail-product-item">
											<div class="aui-mail-product-item-hd">
												<img :src='"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.51yuansu.com%2Fpic3%2Fcover%2F02%2F44%2F88%2F59e526d9803b5_610.jpg&refer=http%3A%2F%2Fpic.51yuansu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656056558&t=f8b454be391570fcd0f168d4ca59b2d5"' alt="">
											</div>
											<div class="aui-mail-product-item-bd">
												<p>{{menuList2.rechargeAmount}}元</p>
											</div>
										</div>
									</div>
		<div class="aui-mail-payment">
			<p>
				{{menuList2.rechargeTime}}
			</p>
		</div>
								</div>
								<div :class="[index3+1==menuList.length ? 'hd':'divHeight']"></div>
							</block>
						</div>
				</div>
			</div>
		</section>
	</section>
</template>

<script>
	import Vue from 'vue'
	export default {
		data() {
			return {
				scrollLeft: 0,
				isClickChange: false,
				currentTab: 0,
				rechargeinfo:''
			}
		},
		onLoad() {
			this.init();
		},
		methods: {
			switchorder(){
				
			},
			swichMenu: async function(current) { //点击其中一个选项
				if (this.currentTab == current) {
					return false;
				} else {
					this.currentTab = current;
					this.setScrollLeft(current);
				}
			},
			swiperChange: async function(e) {
				let index = e.target.current;
				this.setScrollLeft(index);
				this.currentTab = index;
			},
			setScrollLeft: async function(tabIndex) {
				let leftWidthSum = 0;
				for (var i = 0; i <= tabIndex; i++) {
					let nowElement = await this.getWidth('tabNum' + i);
					leftWidthSum = leftWidthSum + nowElement.width;
				}
				let winWidth = uni.getSystemInfoSync().windowWidth;
				this.scrollLeft = leftWidthSum > winWidth ? (leftWidthSum - winWidth) : 0
			},
			getWidth: function(id) { //得到元素的宽高
				return new Promise((res, rej) => {
					uni.createSelectorQuery().select("#" + id).fields({
						size: true,
						scrollOffset: true
					}, (data) => {
						res(data);
					}).exec();
				})
			},
			back(){
					uni.navigateBack({
						delta:1,//返回层数，2则上上页
					})
			},
			getDateList: function(tabIndex) {
				// var entity = this.menuTabs[tabIndex].name;
				// this.menuLists[tabIndex].push(entity);
			},
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
				uni.request({
					method:'POST',
					url:'http://localhost:8080/recharge/queryByUserId',
					data:vue.$store.state.userid,
					success: (res) => {
						console.log(res)
						vue.rechargeinfo=res.data.data
					}
				})
			}
		}
	}
</script>

<style>
	@import '../../css/style.css';
</style>
