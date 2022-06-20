<template>
	<view>
		<view v-if="value==0">
			<index></index>
			<menuList :menuLists="lists" 
				imgSize="150rpx"
				bottomSize="200rpx"
				@listenEvent="myEvent" 
				v-if="hackReset"
				@listenMenu="myMenu">
			</menuList>

		</view>
	</view>
</template>

<script>
	import index from "@/components/home/index.vue"
	import menuList from "@/components/sehochen-left-menu/menuList.vue"
	export default {
		components:{
			index,
			menuList
		},
		data() {
			return {
				value: 0,
				lists:'',
				hackReset:''
					}
		},
		onShow() {
			this.hackReset = false;
			  this.$nextTick(() => {
			       this.hackReset = true;
			  })
		},
		methods:{
			change(name) {
				this.value = name
				console.log(name)
			},
			init(){
				let vue = this;
				console.log(vue.$store.state.userid=='')
				if (vue.$store.state.userid == '') {
						uni.reLaunch({
							url:'/pages/myinfo/myinfo'
						})
				}
				
				if(vue.$store.state.room==''){
					uni.redirectTo({
						url:'/pages/scans/scans'
					})
				}
				uni.request({
					method:'POST',
					url:'http://localhost:8080/ktvItemType/getItemAndType',
					success: (res) => {
						console.log(res)
						vue.lists=res.data.data
						console.log(vue.lists)
					}
				})
			}
		},
		mounted(){
			this.init();
		}
	}
</script>

<style lang="scss">

</style>
