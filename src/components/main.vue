<template>
<div>
    <router-view>
    </router-view>
</div>
</template>

<script>
export default {
    methods:{
        open2() {
        this.$notify({
          title: '提示',
          message: '您有一个新的订单，请进行配送',
          type: 'success',
          duration: 0
        });
      },    initWebSocket () {
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
        console.log(event);
        this.open2();
      },
      setOncloseMessage () {
        console.log('WebSocket连接关闭    状态码：' + this.websocket.readyState)
      },
            onbeforeunload (){
        this.closeWebSocket()
      },
      closeWebSocket () {
        this.websocket.close()
      },
    }
    ,
    mounted(){
              // WebSocket
      if ('WebSocket' in window) {
        this.websocket = new WebSocket('ws://localhost:8080/order/websocket')
        // alert('连接浏览器')
        this.initWebSocket()
      } else {
        alert('当前浏览器 不支持')
      }
    }
}
</script>

<style>

</style>