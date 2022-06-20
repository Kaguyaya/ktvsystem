<template>
	<view>
		<uni-nav-bar height="300rpx" :fixed="false"  color="#333333" background-color="#FFFFFF"
			>
			<block slot="left">
				<view class="city">
					<view>
						<uni-badge class="uni-badge-left-margin" :text="messagevalue" absolute="rightTop" :offset="[-5, 10]" size="small">
						<text class="uni-nav-bar-text" @click="jumpto()">已点</text>
				</uni-badge>
					</view>
				</view>
			</block>
			<view class="input-view">
				<uni-icons class="input-uni-icon" type="search" size="18" color="#999" />
				<input confirm-type="search" class="nav-bar-input" type="text" :value="inputvalue" placeholder="输入搜索关键词"
					@confirm="confirm" />
			</view>
		</uni-nav-bar>
	
	<view class="container">
		<view class="recommend justify-center items-center flex10-clz">
			<text >推荐歌单</text>
		</view>

		<view v-for="songlists in songlist" :key='songlists'>
			<view class="flex flex-wrap diygw-col-24 justify-center items-center flex10-clz">
				<image src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01ee0b57a310120000012e7e538035.png&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1655432012&t=e2b6e89a8bf2544bd0e9319a864ed01f" class="response diygw-col-8 image5-clz" mode="widthFix"></image>
				<view class="flex flex-wrap diygw-col-16 flex-direction-column justify-between flex13-clz">
					<view class="diygw-col-24 text10-clz diygw-text-md"> {{songlists.songName.replace(/.(mp4)$/i,'').replace(/\[.*?\]/g, '')}} </view>
					<view class="diygw-col-24 text15-clz diygw-text-sm"> 热门 </view>
					<view class="flex flex-wrap diygw-col-24 justify-between items-center flex16-clz">
						<view class="diygw-form-item diygw-form-item-small diygw-form-item-small diygw-col-0 noborder">
							<view class="input" style="overflow-x: initial">
								<template v-for="item in rateCount"> <text class="diy-icon-starfill pointer" :data-index="item" :style="{ color: item <= rate ? 'rgba(245, 78, 6, 1)' : '#eee', fontSize: '12px' }" @tap="changeRate" /> </template>
								<input type="hidden" hidden name="rate" :value="rate" />
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				messagevalue:99,
				songtype:{},
				songlist:{},
				songlanguage:'',
				search: '',
				rateCount: 5,
				rate: 5,
				selectlanguage:'',
				selectlanguageindex:'',
				languageclass:'diygw-tag margin-xs radius ',
				selecttype:'',
				selecttypeindex:'',
				typeclass:'diygw-tag margin-xs radius '
			}
		},
				onShareAppMessage: function () {},

		methods: {

			SelectAllLanguage(){
				this.selectlanguage='';
			},
			SelectAllType(){
				this.selecttype='';
			},
			SelectLanguage(i){
				this.selectlanguage=i;
			},
			SelectType(i){
				this.selecttype=i;
			},
			getRecommend() {
				let vue = this;
				console.log(vue.$store.state.userid=='')
				if (vue.$store.state.userid == '') {
						uni.switchTab({
							url:'/pages/myinfo/myinfo'
						})
				} 
				else 
				{
					uni.request({
						method: 'POST',
						data: vue.$store.state.userid,
						url: "http://localhost:8080/UserSong/getRecommendList",
						success: (res) => {
							uni.request({
								method: 'POST',
								data: res.data.data,
								url: 'http://localhost:8080/song/getRecommendSongList',
								success: (successRes) => {
									vue.songlist=successRes.data.data;
									console.log(successRes.data.data);
								}
							})
						}
					})
				}
			},
						 init() {},
						changeRate(evt) {
							let rate = evt.currentTarget.dataset.index;
							if (this.rate == rate) {
								rate = rate - 1;
							}
							this.rate = rate;
						},
			getSongType(){
				let vue=this;
				uni.request({
					method:'POST',
					url:'http://localhost:8080/song/getSongType',
					success: (res) => {
						console.log(res)
						vue.songtype=res.data;
					}
				})
			},
			getSongLanguage(){
				let vue=this;
				uni.request({
					method:'POST',
					url:'http://localhost:8080/song/getSongLanguage',
					success: (res) => {
						vue.songlanguage=res.data;
					}
				})
			},
			onSocketError(){
				uni.onSocketError(function(res){
					console.log("WebSocket打开失败")
				})
			},
			onSocketMessage(){
				let vue=this;
				uni.onSocketMessage(function(res){
					let musiclist=JSON.parse(res.data)
					vue.messagevalue=musiclist.data.length;
					console.log(musiclist.data.length)
				})
			},
			jumpto(){
				uni.navigateTo({
					url:'/pages/playlist/playlist'
				})
			},
		},

		mounted() {
			let vue=this;
			this.init();
			uni.connectSocket({
				url:'ws://localhost:8080/return/websocket',
			})
			uni.onSocketOpen(function(res){
				console.log("WebSocket连接已打开");
			})
			
			this.onSocketError();
			this.onSocketMessage();
			this.getRecommend();
			this.getSongType();
			this.getSongLanguage();
			uni.request({
								method:'GET',
								url:'http://localhost:8080/song/getPlayList',
								success: (res) => {
									vue.messagevalue=res.data.data.length;
								}
							})
		}
	}
</script>

<style lang="scss" scoped>
		$nav-height: 30px;
		.recommend{
			width: 600rpx;
			width: 52;
			text-align: center;
			background-color: #000000;
			/* 推荐歌单 */
			font-size: 100rpx;
			font-weight: normal;
			color: #FFFFFF;
		}
	.uni-nav-bar-text {
		font-size: 12px;
	}
	
	.city {
		/* #ifndef APP-PLUS-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		justify-content: flex-start;
		// width: 160rpx;
		
		margin-left: 4px;
	}
	.uni-badge-left-margin {
		margin-left: 10px;
	}
	.input-view {
		/* #ifndef APP-PLUS-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		// width: 500rpx;
		flex: 1;
		background-color: #f8f8f8;
		height: $nav-height;
		border-radius: 15px;
		padding: 0 15px;
		flex-wrap: nowrap;
		margin: 7px 0;
		line-height: $nav-height;
	}
	
	.input-uni-icon {
		line-height: $nav-height;
	}
	
	.nav-bar-input {
		height: $nav-height;
		line-height: $nav-height;
		/* #ifdef APP-PLUS-NVUE */
		width: 370rpx;
		/* #endif */
		padding: 0 5px;
		font-size: 14px;
		background-color: #f8f8f8;
	}
	.search-clz {
		margin-left: 5px;
		width: calc(100% - 5px - 5px) !important;
		margin-top: 5px;
		margin-bottom: 5px;
		margin-right: 5px;
	}
	.flex2-clz {
		margin-left: 10px;
		z-index: 1000;
		width: calc(100% - 10px - 10px) !important;
		margin-top: 0px;
		margin-bottom: 0px;
		margin-right: 10px;
	}
	.flex-clz {
		margin-left: 10px;
		z-index: 1000;
		width: calc(100% - 10px - 10px) !important;
		margin-top: 0px;
		margin-bottom: 0px;
		margin-right: 10px;
	}
	.flex1-clz {
		margin-left: 10px;
		z-index: 1000;
		width: calc(100% - 10px - 10px) !important;
		margin-top: 0px;
		margin-bottom: 10px;
		margin-right: 10px;
	}
	.flex10-clz {
		margin-left: 8px;
		border-bottom-left-radius: 6px;
		box-shadow: 0px 1px 3px 3px rgba(111, 111, 111, 0.18);
		z-index: 1000;
		overflow: hidden;
		width: calc(100% - 8px - 8px) !important;
		border-top-left-radius: 6px;
		margin-top: 8px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 8px;
		margin-right: 8px;
	}
	.image5-clz {
		margin-left: 5px;
		border-bottom-left-radius: 6px;
		overflow: hidden;
		width: calc(33.3333333333% - 5px - 5px) !important;
		border-top-left-radius: 6px;
		margin-top: 5px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 5px;
		margin-right: 5px;
	}
	.flex13-clz {
		padding-top: 0px;
		z-index: 1000;
		padding-left: 5px;
		padding-bottom: 0px;
		padding-right: 5px;
	}
	.text10-clz {
		margin-left: 5px;
		width: calc(100% - 5px - 5px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 5px;
	}
	.text15-clz {
		margin-left: 5px;
		width: calc(100% - 5px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.flex16-clz {
		z-index: 1000;
	}
	.text17-clz {
		margin-left: 5px;
		margin-top: 5px;
		margin-bottom: 5px;
		margin-right: 5px;
	}
	.flex6-clz {
		margin-left: 8px;
		border-bottom-left-radius: 6px;
		box-shadow: 0px 1px 3px 3px rgba(111, 111, 111, 0.18);
		z-index: 1000;
		overflow: hidden;
		width: calc(100% - 8px - 8px) !important;
		border-top-left-radius: 6px;
		margin-top: 8px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 8px;
		margin-right: 8px;
	}
	.image3-clz {
		margin-left: 5px;
		border-bottom-left-radius: 6px;
		overflow: hidden;
		width: calc(33.3333333333% - 5px - 5px) !important;
		border-top-left-radius: 6px;
		margin-top: 5px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 5px;
		margin-right: 5px;
	}
	.flex9-clz {
		padding-top: 0px;
		z-index: 1000;
		padding-left: 5px;
		padding-bottom: 0px;
		padding-right: 5px;
	}
	.text9-clz {
		margin-left: 0px;
		width: calc(100% - 0px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.text11-clz {
		margin-left: 0px;
		width: calc(100% - 0px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.flex4-clz {
		margin-left: 8px;
		border-bottom-left-radius: 6px;
		box-shadow: 0px 1px 3px 3px rgba(111, 111, 111, 0.18);
		z-index: 1000;
		overflow: hidden;
		width: calc(100% - 8px - 8px) !important;
		border-top-left-radius: 6px;
		margin-top: 8px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 8px;
		margin-right: 8px;
	}
	.image2-clz {
		margin-left: 5px;
		border-bottom-left-radius: 6px;
		overflow: hidden;
		width: calc(33.3333333333% - 5px - 5px) !important;
		border-top-left-radius: 6px;
		margin-top: 5px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 5px;
		margin-right: 5px;
	}
	.flex7-clz {
		padding-top: 0px;
		z-index: 1000;
		padding-left: 5px;
		padding-bottom: 0px;
		padding-right: 5px;
	}
	.text6-clz {
		margin-left: 0px;
		width: calc(100% - 0px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.text8-clz {
		margin-left: 0px;
		width: calc(100% - 0px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.flex12-clz {
		margin-left: 8px;
		border-bottom-left-radius: 6px;
		box-shadow: 0px 1px 3px 3px rgba(111, 111, 111, 0.18);
		z-index: 1000;
		overflow: hidden;
		width: calc(100% - 8px - 8px) !important;
		border-top-left-radius: 6px;
		margin-top: 8px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 8px;
		margin-right: 8px;
	}
	.image6-clz {
		margin-left: 5px;
		border-bottom-left-radius: 6px;
		overflow: hidden;
		width: calc(33.3333333333% - 5px - 5px) !important;
		border-top-left-radius: 6px;
		margin-top: 5px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 5px;
		margin-right: 5px;
	}
	.flex15-clz {
		padding-top: 0px;
		z-index: 1000;
		padding-left: 5px;
		padding-bottom: 0px;
		padding-right: 5px;
	}
	.text18-clz {
		margin-left: 0px;
		width: calc(100% - 0px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.text20-clz {
		margin-left: 0px;
		width: calc(100% - 0px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.flex8-clz {
		margin-left: 8px;
		border-bottom-left-radius: 6px;
		box-shadow: 0px 1px 3px 3px rgba(111, 111, 111, 0.18);
		z-index: 1000;
		overflow: hidden;
		width: calc(100% - 8px - 8px) !important;
		border-top-left-radius: 6px;
		margin-top: 8px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 8px;
		margin-right: 8px;
	}
	.image4-clz {
		margin-left: 5px;
		border-bottom-left-radius: 6px;
		overflow: hidden;
		width: calc(33.3333333333% - 5px - 5px) !important;
		border-top-left-radius: 6px;
		margin-top: 5px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 5px;
		margin-right: 5px;
	}
	.flex11-clz {
		padding-top: 0px;
		z-index: 1000;
		padding-left: 5px;
		padding-bottom: 0px;
		padding-right: 5px;
	}
	.text12-clz {
		margin-left: 0px;
		width: calc(100% - 0px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.text14-clz {
		margin-left: 0px;
		width: calc(100% - 0px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.flex14-clz {
		margin-left: 8px;
		border-bottom-left-radius: 6px;
		box-shadow: 0px 1px 3px 3px rgba(111, 111, 111, 0.18);
		z-index: 1000;
		overflow: hidden;
		width: calc(100% - 8px - 8px) !important;
		border-top-left-radius: 6px;
		margin-top: 8px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 8px;
		margin-right: 8px;
	}
	.image7-clz {
		margin-left: 5px;
		border-bottom-left-radius: 6px;
		overflow: hidden;
		width: calc(33.3333333333% - 5px - 5px) !important;
		border-top-left-radius: 6px;
		margin-top: 5px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 5px;
		margin-right: 5px;
	}
	.flex17-clz {
		padding-top: 0px;
		z-index: 1000;
		padding-left: 5px;
		padding-bottom: 0px;
		padding-right: 5px;
	}
	.text21-clz {
		margin-left: 0px;
		width: calc(100% - 0px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.text23-clz {
		margin-left: 0px;
		width: calc(100% - 0px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.flex3-clz {
		margin-left: 8px;
		border-bottom-left-radius: 6px;
		box-shadow: 0px 1px 3px 3px rgba(111, 111, 111, 0.18);
		z-index: 1000;
		overflow: hidden;
		width: calc(100% - 8px - 8px) !important;
		border-top-left-radius: 6px;
		margin-top: 8px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 8px;
		margin-right: 8px;
	}
	.image1-clz {
		margin-left: 5px;
		border-bottom-left-radius: 6px;
		overflow: hidden;
		width: calc(33.3333333333% - 5px - 5px) !important;
		border-top-left-radius: 6px;
		margin-top: 5px;
		border-top-right-radius: 6px;
		border-bottom-right-radius: 6px;
		margin-bottom: 5px;
		margin-right: 5px;
	}
	.flex5-clz {
		padding-top: 0px;
		z-index: 1000;
		padding-left: 5px;
		padding-bottom: 0px;
		padding-right: 5px;
	}
	.text-clz {
		margin-left: 0px;
		width: calc(100% - 0px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.text2-clz {
		margin-left: 0px;
		width: calc(100% - 0px - 0px) !important;
		margin-top: 5px;
		margin-bottom: 0px;
		margin-right: 0px;
	}
	.container {
		padding-left: 0px;
		padding-right: 0px;
	}
</style>
