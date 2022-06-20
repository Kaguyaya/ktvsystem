<template>
 <div class='demo'>
  <video-player class="video-player vjs-custom-skin"
   ref="videoPlayer"
   :playsinline="true"
   :options="playerOptions">
  </video-player>
 </div>
 </template>


<script>
  import axios from 'axios'
export default {
  data() {
  return {

   playerOptions: {
   playbackRates: [0.5, 1.0, 1.5, 2.0], // 可选的播放速度
   autoplay: false, // 如果为true,浏览器准备好时开始回放。
   muted: false, // 默认情况下将会消除任何音频。
   loop: false, // 是否视频一结束就重新开始。
   preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
   language: 'zh-CN',
   aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
   fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
   sources: [{
    type: "video/mp4", // 类型
    src: require("../../assets/小沈阳-大海-国语-531703.mp4") // url地址
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
        console.log(event)
       let songlist= JSON.parse(event.data)
       this.playerOptions.sources[0].src=songlist.songUrl;
       console.log(songlist)
        console.log('服务端返回：' + event.data)
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
      onloadeddata(player){
            console.log("loadeddata",player)
      }
}
  ,
mounted () {
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
    },
 }

</script>

<style>

</style>