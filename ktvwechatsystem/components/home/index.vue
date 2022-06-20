<template>
	<view>
		<view class="search">
			<u-row>
				<u-col span="3">
					<view>
						<u-avatar
							src="/static/logo2.jpg"
							size="75"></u-avatar>
					</view>
				</u-col>
				<u-col span="6">
					<view>KTV阳光店</view>
				</u-col>
				<u-col span="3">
					<view>{{roomname}}</view>
				</u-col>
			</u-row>
		</view>
		<u-notice-bar :text="text" ></u-notice-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				roomname:'',
                list: [{
                    image: 'https://cdn.uviewui.com/uview/swiper/swiper2.png',
                    title: '昨夜星辰昨夜风，画楼西畔桂堂东',
                },{
                    image: 'https://cdn.uviewui.com/uview/swiper/swiper1.png',
                    title: '身无彩凤双飞翼，心有灵犀一点通'
                },{
                    image: 'https://cdn.uviewui.com/uview/swiper/swiper3.png',
                    title: '谁念西风独自凉，萧萧黄叶闭疏窗，沉思往事立残阳'
                }],
				text: '欢迎用户选择本店商品',
				jsCode:""
			}
		},
		methods: {
            click(item) {
                // console.log('item', item);
            },
			change(item){
				this.current = item.index
			}
		},
		mounted(){
		
			this.roomname=this.$store.state.room.roomName;
		console.log(this.roomname)
		}
	}
</script>

<style lang="scss">
	.top_bg{
		width: 100%;
		height: 150rpx;
		background-color: #dd6161;
	}
	.search{
		padding: 20rpx;
	}
</style>
