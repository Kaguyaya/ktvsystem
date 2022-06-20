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
		
						<view class="flex diygw-col-24 justify-between items-center flex-nowrap flex2-clz">
							<view class="flex diygw-col-24">
								<view :class="['diygw-tag margin-xs radius' ,{'bg-orange':selectlanguageindex===''}]" @click="SelectAllLanguage()">
									<text> 全部类型 </text>
								</view>
								<view v-for="(songlanguages,index) in songlanguage" :key='songlanguages'>
									<view :class="[languageclass,{'bg-orange':selectlanguageindex===index}]" @click="SelectLanguage(songlanguages,index)">
										<text> {{index.replace(/\[|]/g, '' )}} </text>
									</view>
								</view>
					
							</view>
							<view class="flex diygw-col-0">
								<view class="diygw-avatar md white radius">
									<text class="diy-icon-filter" />
								</view>
							</view>
						</view>
					
						<scroll-view scroll-x scroll-with-animation class="flex scroll-view diygw-col-24 justify-between items-center flex-nowrap flex1-clz">
							<view class="flex flex-nowrap">
								<view class="flex diygw-col-24">
									<view :class="['diygw-tag margin-xs radius' ,{'bg-orange':selecttypeindex===''}]" @click="SelectAllType()">
										<text> 全部分类 </text>
									</view>
									<view v-for="(songtypes,index) in songtype" :key='songtypes'>
										<view :class="[typeclass,{'bg-orange':selecttypeindex===index}]" @click="SelectType(songtypes,index)">
											<text> {{songtypes}} </text>
										</view>
									</view>
								</view>
							</view>
						</scroll-view>
						<view v-for="(songlists,index) in songlist" :key='songlists'>
							<view class="flex flex-wrap diygw-col-24 justify-center items-center flex10-clz" @click="playmusic(index)">
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
				inputvalue:'',
			songsinfo:{	
				name:'',
				id:''
			},
			messagevalue:99,
			pagedto:{
				currentPage:1,
				pageSize:10,
				queryString:''
			},
			total:'',
			searchsongsinfo:{
				name:null,
			},
			city: '北京',
			title: '下拉刷新 + 加载更多',
			data: [],
			loadMoreText: "加载中...",
			showLoadMore: false,
			max: 0,
			songtype:{},
			songlist:{},
			songlanguage:'',
			selectlanguage:'',
			selectlanguageindex:'',
			selecttypeindex:'',
			languageclass:'diygw-tag margin-xs radius ',
			selecttype:'',
			typeclass:'diygw-tag margin-xs radius '
			}
		},
		methods: {
		 init(){
				let vue = this;
				
				if (vue.$store.state.userid == '') {
						uni.reLaunch({
							url:'/pages/myinfo/myinfo'
						})
				}
				
				if(vue.$store.state.room==''){
					uni.navigateTo({
						url:'/pages/scans/scans'
					})
				}
				
				this.getSongType();
				this.getSongLanguage();
				vue.pagedto.pageSize=10;
				vue.querySongByPage();
			  uni.request({
					method:'GET',
					url:'http://localhost:8080/song/getPlayList',
					success: (res) => {
						vue.messagevalue=res.data.data.length;
					}
				})
			},
			showCity() {
				uni.showToast({
					title: '选择城市'
				})
			},
			confirm(event){
				let searchvalue=event.detail.value;
				let vue=this;
				
				const compare = (str1, str2) => {
				   var i;
				   for(i=0; i<=str1.length-1; i++)
				   {
				   if (str2.indexOf(str1.substr(i,1))!=-1)
				   {
				   return true;
				   }
				   else
				   if (i==str1.length-1){
					   return false;
				   }
				   }
				
				} 
				let searchinfo=[];
				vue.songsinfo.forEach((value,index)=>{
					
					if(compare(searchvalue,value.songName)){
						searchinfo.push(value);
					}
				});
				vue.songsinfo=searchinfo;
			},
			jumpto(){
				uni.navigateTo({
					url:'/pages/playlist/playlist'
				})
			},
			//搜索栏搜索功能
			search(res) {
				let vue=this;

				},
				input(res) {
					console.log('----input:', res)
				},
				clear(res) {
					uni.showToast({
						title: 'clear事件，清除值为：' + res.value,
						icon: 'none'
					})
				},
				cancel(res) {
					uni.showToast({
						title: '点击取消，输入值为：' + res.value,
						icon: 'none'
					})
				},
				playmusic(index){
					this.$store.commit("songsinfo",this.songsinfo[index]);
					let songinfo=this.$store.state.songsinfo;
					let vue=this;
					
					uni.request({
						method:'POST',
						url:'http://localhost:8080/song/insertPlayList',
						data:songinfo,
						success: (res) => {
							
							uni.request({
								method:'GET',
								url:'http://localhost:8080/song/getPlayList',
								success: (res) => {
									console.log("songlist",res);
								vue.messagevalue=res.data.data.length;	
								}
							})
						}
					})
				},
				querySongByPage(){
					let vue=this;
				vue.pagedto.queryString="{'language':'"+vue.selectlanguage+"','type':'"+vue.selecttype+"'}";
					uni.request({
						method:'POST',
						url:'http://localhost:8080/song/querySongByPage',
						data:vue.pagedto,
						success: (res) => {
							vue.songsinfo=res.data.data.rows;
							vue.songlist=res.data.data.rows;
							vue.total=res.data.data.total;
						}
					});
				},
				getSongType(){
					let vue=this;
					uni.request({
						method:'POST',
						url:'http://localhost:8080/song/getSongType',
						success: (res) => {
							
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
				SelectLanguage(language,index){
					this.selectlanguage=language;
					this.selectlanguageindex=index;
					let vue=this;
					vue.pagedto.queryString="{'language':'"+vue.selectlanguage+"','type':'"+vue.selecttype+"'}";
					uni.request({
						method:'POST',
						url:'http://localhost:8080/song/queryByPagelanguageandtype',
						data:vue.pagedto,
						success: (res) => {
							console.log(res)
							vue.songsinfo=res.data.data.rows;
							vue.songlist=res.data.data.rows;
							vue.total=res.data.data.total;
						}
					})
					
				},
				SelectType(type,index){
					this.selecttype=type;
					this.selecttypeindex=index;
					let vue=this;
					vue.pagedto.queryString="{'language':'"+vue.selectlanguage+"','type':'"+vue.selecttype+"'}";
					uni.request({
						method:'POST',
						url:'http://localhost:8080/song/queryByPagelanguageandtype',
						data:vue.pagedto,
						success: (res) => {
							console.log(res);
							vue.songsinfo=res.data.data.rows;
							vue.songlist=res.data.data.rows;
							vue.total=res.data.data.total;
						}
					})
				},
				SelectAllLanguage(){
					this.selectlanguageindex='';
					this.selectlanguage='';
					let vue=this;
					vue.pagedto.queryString="{'language':'"+vue.selectlanguage+"','type':'"+vue.selecttype+"'}";
					uni.request({
						method:'POST',
						url:'http://localhost:8080/song/queryByPagelanguageandtype',
						data:vue.pagedto,
						success: (res) => {
							vue.songsinfo=res.data.data.rows;
							vue.songlist=res.data.data.rows;
							vue.total=res.data.data.total;
						}
					})
				},
				SelectAllType(){
					this.selecttypeindex='';
					this.selecttype='';
					let vue=this;
					vue.pagedto.queryString="{'language':'"+vue.selectlanguage+"','type':'"+vue.selecttype+"'}";
					uni.request({
						method:'POST',
						url:'http://localhost:8080/song/queryByPagelanguageandtype',
						data:vue.pagedto,
						success: (res) => {
							vue.songsinfo=res.data.data.rows;
							vue.songlist=res.data.data.rows;
							vue.total=res.data.data.total;
						}
					})
				}
			},
			
			onBackPress() {
				// #ifdef APP-PLUS
				plus.key.hideSoftKeybord();
				// #endif
			},
			mounted() {
				this.init();
				uni.connectSocket({
					url:'ws://localhost:8080/return/websocket',
				})
				uni.onSocketOpen(function(res){
					console.log("WebSocket连接已打开");
				})
				this.onSocketError();
				this.onSocketMessage();
			},
	
			onShow() {
				let vue=this;
				vue.querySongByPage();
				uni.request({
					method:'GET',
					url:'http://localhost:8080/song/getPlayList',
					success: (res) => {
						vue.messagevalue=res.data.data.length;
					}
				})
			},
			onPullDownRefresh() {
				console.log('onPullDownRefresh');
				uni.stopPullDownRefresh();
			},
			onReachBottom() {
				let vue=this;
				let temp;
				console.log("onReachBottom");
				console.log("pagesize",vue.pagedto.pageSize)
				console.log("total",vue.total)
				if(vue.pagedto.pageSize>=vue.total){
					vue.loadMoreText="没有更多数据了"
					return;
				}
				this.showLoadMore = true;
				setTimeout(() => {
					temp=vue.total-vue.pagedto.pageSize;
					if(temp<=10){
						vue.pagedto.pageSize=vue.pagedto.pageSize+temp;
					}
					else{
						vue.pagedto.pageSize=vue.pagedto.pageSize+10;
					}
					vue.querySongByPage();
				}, 300);
			},
			onUnload() {
				this.max = 0,
				this.data = [],
				this.loadMoreText = "加载更多",
				this.showLoadMore = false;
			},
			onLoad() {
				console.log("onload")
				uni.stopPullDownRefresh();
			},
		}
</script>

<style lang="scss">
	$nav-height: 30px;

	/* #ifndef APP-NVUE */
	page {
		height: 120%;
	}

	/* #endif */
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

	.example-body {
		/* #ifndef APP-NVUE */
		display: block;
		/* #endif */
		padding: 0;
	}
	
	
	.container {
		overflow: hidden;
	}
	
	.custom-cover {
		flex: 1;
		flex-direction: row;
		position: relative;
	}
	
	.cover-content {
		position: absolute;
		bottom: 0;
		left: 0;
		right: 0;
		height: 40px;
		background-color: rgba($color: #000000, $alpha: 0.4);
		display: flex;
		flex-direction: row;
		align-items: center;
		padding-left: 15px;
		font-size: 14px;
		color: #fff;
	}
	
	.card-actions {
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		align-items: center;
		height: 45px;
		border-top: 1px #eee solid;
	}
	.card-actions-item {
		display: flex;
		flex-direction: row;
		align-items: center;
	}
	.card-actions-item-text {
		font-size: 12px;
		color: #666;
		margin-left: 5px;
	}
	.cover-image {
		flex: 1;
		height: 150px;
	}
	.no-border {
		border-width: 0;
	}
	.flex-pc {
		display: flex;
		justify-content: center;
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
