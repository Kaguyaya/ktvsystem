<template>
<el-container class="videoroom">
  <el-aside class="left" width="200px">
  </el-aside>
  <el-main class="video">
  <video-player  class="video-player vjs-custom-skin"  v-if="roominfo.roomIsopen"
   ref="videoPlayer" 
   :playsinline="true" 
   :options="playerOptions"
   @ended="onPlayEnded($event)"
   @loadeddata="onloadeddata($event)"
   @play="onplayering()"
   @ready="playerReadied"
   @waiting="onPlayerWaiting($event)"
   @canplay="onPlayerCanplay($event)"
   >
  </video-player>
            <el-image v-if="!roominfo.roomIsopen"
            style="width: 150px; height: 150px"
            :src="fitUrl"
            :fit="fit"></el-image>
            <div class="box">
              <p class="animate" v-if="!roominfo.roomIsopen">
                当前房间：{{roominfo.roomName}}，房间已关闭，请扫码续费时长
              </p>
            </div>
  </el-main>
  <el-aside class="right" width="200px"></el-aside>
</el-container>
</template>

<script>
  import axios from 'axios'
export default {
  data() {
  return {
    myPlayer:'',
    roominfo:'',
    isopen:false,
    fit:'',
    fitUrl:'',
   playerOptions: {

   playbackRates: [0.5, 1.0, 1.5, 2.0], // 可选的播放速度
   autoplay: true, // 如果为true,浏览器准备好时开始回放。
   muted: false, // 默认情况下将会消除任何音频。
   loop: false, // 是否视频一结束就重新开始。
   preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
   language: 'zh-CN',
   aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
   fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
   sources: [{
    type: "video/mp4", // 类型
    src: '' // url地址
   }],
   poster: '', // 封面地址
   notSupportedMessage: '此视频暂无法播放，请稍后再试', // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
   controlBar: {
    timeDivider: true, // 当前时间和持续时间的分隔符
    durationDisplay: true, // 显示持续时间
    remainingTimeDisplay: false, // 是否显示剩余时间功能
    fullscreenToggle: true // 是否显示全屏按钮
   }
   }
  }
  },
    computed: {
    player() {
      return this.$refs.videoPlayer.player
    }
  },
   methods: {
      initWebSocket () {
        // 连接错误
        this.websocket.onerror = this.setErrorMessage
        // 连接成功
        this.websocket.onopen = this.setOnopenMessage
        // 收到消息的回调
        this.websocket.onmessage = this.setOnmessageMessage
        // 连接关闭的回调
        this.websocket.onclose = this.setOncloseMessage
        // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = this.onbeforeunload
      },
      setErrorMessage () {
        console.log('WebSocket连接发生错误   状态码：' + this.websocket.readyState)
      },
      setOnopenMessage () {
        console.log('WebSocket连接成功    状态码：' + this.websocket.readyState)
      },
      setOnmessageMessage (event) {
        // 根据服务器推送的消息做自己的业务处理
        let songlist= JSON.parse(event.data)
        let istrue='open'==songlist.data
        if(istrue){
          this.getRoomInfo();
        }
        else{
       this.playerOptions.sources[0].src=songlist.data.songUrl;
       console.log(songlist.data.songUrl)
        console.log('服务端返回：' + event.data)
        }
      },
      setOncloseMessage () {
        console.log('WebSocket连接关闭    状态码：' + this.websocket.readyState)
      },
      onbeforeunload () {
        this.closeWebSocket()
      },
      closeWebSocket () {
        this.websocket.close()
      },
      onPlayEnded(player){
        axios.post("http://localhost:8080/song/nextPlay").then((success)=>{
          console.log(success);
        })
        console.log("ended",player);
      },
         onPlayerWaiting (e) {
           console.log('wait',e)
    },
    onPlayerCanplay(play){ 
        this.player.play();
        console.log('canplay',play)
    },
    onplayering(){
       console.log(this.playerOptions.loop)
    },
      onloadeddata(player){
            console.log("loadeddata",player)
			console.log('videoPlayer', this.$refs.videoPlayer.player)
      },
      playerReadied (e) {
      console.log('e',e)
      this.myPlayer = e
      this.myPlayer.volume(0.1)
    },
    doplay(){
      this.myPlayer.play();
    },
     getQRcode(roomlist){
          let vue=this;
          let room;
          let successurl;
            room=roomlist
          axios({
            method:'post',
            url:"http://localhost:8080/room/getRoomQRcode",
            responseType:'blob',
            data:room
          }).then((success)=>{
            successurl= window.URL.createObjectURL(success.data)
            vue.fitUrl=successurl;
          }).catch((error)=>{
            console.log(error)
          })
},
    getRoomInfo(){
      let vue=this;
      axios.post("http://localhost:8080/room/getRoomlist").then((success)=>{
        success.data.data.forEach(element => {
          if(element.roomName=="房间1")
          {
            vue.roominfo=element
            console.log(vue.roominfo)
            if(!vue.roominfo.roomIsopen){
                  vue.getQRcode(element);
            }
          }
        });
      })
    },
},
mounted(){
      let vue=this;
      vue.getRoomInfo()
      // WebSocket
      if ('WebSocket' in window) {
        this.websocket = new WebSocket('ws://localhost:8080/push/websocket')
        // alert('连接浏览器')
        this.initWebSocket()
      } else {
        alert('当前浏览器 不支持')
      }
    },
    beforeDestroy () {
      this.onbeforeunload()
    }
}
</script>

<style scoped>
.videoroom{
    background: black;
    display: flex;
}

      .video-player {
        height: 800px;
        width: 100%;
        }
.left{
background-color: rgb(48, 46, 46);
align-items: stretch;
}
.right{
    background-color: rgb(48, 46, 46);
align-items: stretch;
}
.video{
    align-items: center;
    justify-content: center;
    min-height: 98vh;
    margin: 0;
    background-color: black;
}
       .box {
            width: 600px;
            margin: 0 auto;
            height: 100px;
            border: 0px solid #ff6700;
            overflow: hidden;
        }
 
        .animate {
            padding-left: 20px;
            font-size: 40px;
            color: #FFFFFF;
            display: inline-block;
            white-space: nowrap;
            animation: 10s wordsLoop linear infinite normal;
        }
        @keyframes wordsLoop {
            0% {
                transform: translateX(200px);
                -webkit-transform: translateX(200px);
            }
            100% {
                transform: translateX(-100%);
                -webkit-transform: translateX(-100%);
            }
        }
        @-webkit-keyframes wordsLoop {
            0% {
                transform: translateX(200px);
                -webkit-transform: translateX(200px);
            }
            100% {
                transform: translateX(-100%);
                -webkit-transform: translateX(-100%);
            }
        }
</style>