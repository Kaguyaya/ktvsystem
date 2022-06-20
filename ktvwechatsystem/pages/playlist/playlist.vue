<template>
	<view>
		<uni-list v-for="(song,index) in songsinfo" :key='song'>
			<uni-list-item clickable  v-bind:title="song.songName" v-bind:rightText="song.songAuthor" ></uni-list-item>
		</uni-list>
		<text>{{message}}</text>
		<button type="primary" size="mini" class="nextplay" @click="playnext()">下一首</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				songsinfo:'',
				message:''
			};
			
		},
		methods:{
			
			playnext(){
				let vue=this;
				uni.request({
					method:'POST',
					url:'http://localhost:8080/song/nextPlay',
					success: (res) => {
						console.log(res);
						vue.getplaylist();
					}
				})
			},
			getplaylist(){
				let vue=this;
				uni.request({
					method:'GET',
					url:'http://localhost:8080/song/getPlayList',
					success: (res) => {
						console.log("获取歌曲",res)
						vue.songsinfo=res.data.data.reverse();
						if(vue.songsinfo.length==0){
							vue.message="暂无歌曲，请添加歌曲"
						}
						else{
							vue.message="";
						}
					}
				})
			}
		},
		mounted() {
			let vue=this;
			vue.getplaylist();
		}
	}
</script>

<style lang="scss">
.nextplay{
	position: absolute;
	right: 0;
	bottom: 0;
}
</style>
