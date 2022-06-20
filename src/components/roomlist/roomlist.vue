<template>
<div class="roomlistmain">
<div class="buttontool">
    <el-button class="create" type="primary" @click="createlist">
        创建房间
    </el-button>
</div>
   <el-row>
  <el-col :span="4" v-for="(room, index) in (roomlist)"  :key="index" :offset="index > 0 ? 2 : 2">
    <el-card :body-style="{ padding: '0px' }">
            <el-image
            style="width: 150px; height: 150px"
            v-bind:src="fitUrlList[index]"
            :fit="fit"></el-image>
      <div style="padding: 14px;">
        <span>{{room.roomName}}</span>
        <div class="bottom clearfix">
            <el-button
          type="primary"
          icon="el-icon-download"
          size="mini"
          @click="createstarttime(room)"
          >设置时间</el-button>
        </div>
      </div>
    </el-card>
  </el-col>
</el-row>

    <el-dialog title="修改房间" :visible.sync="dialogOpenVisible">
  <el-form :model="alertform">
    <el-form-item label="房间编号" :label-width="formLabelWidth">
      <el-input disabled v-model="alertform.roomId" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="房间名" :label-width="formLabelWidth">
      <el-input disabled v-model="alertform.roomName" autocomplete="off"></el-input>
    </el-form-item>
        <el-form-item label="开始时间" :label-width="formLabelWidth">
  <el-time-picker
    v-model="alertform.roomOpentime"
    format = 'HH:mm:ss' value-format = 'yyyy-MM-dd HH:mm:ss'
    placeholder="任意时间点">
  </el-time-picker>
  <el-time-picker
    arrow-control
    format = 'HH:mm:ss' value-format = 'yyyy-MM-dd HH:mm:ss'
    v-model="alertform.roomEndtime"
    placeholder="任意时间点">
  </el-time-picker>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogOpenVisible = false">取 消</el-button>
    <el-button type="primary" @click="startRoom">确 定</el-button>
  </div>
</el-dialog>
<el-dialog title="创建房间" :visible.sync="dialogFormVisible">
  <el-form :model="form">
    <el-form-item label="房间编号" :label-width="formLabelWidth">
      <el-input disabled v-model="form.id" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="房间名" :label-width="formLabelWidth">
      <el-input v-model="form.name" autocomplete="off"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="sendlist">确 定</el-button>
  </div>
</el-dialog>


</div>

</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
        dialogFormVisible: false,
        form: {
            id:'',
            name:''
        },
        alertform:{

        },
        fileUrl:"",
        fitUrl:"",
        dialogOpenVisible:false,
        fitUrlList:[],
        image:"",
        fit:"",
        dialogVisible:false,
        roomlist:"",
        formLabelWidth: '120px'
    };
  },
  mounted(){
      this.getroomlist();
  },
    methods:{
      getroomlist(){
          let vue=this;
           axios.post("http://localhost:8080/room/getRoomlist").then((success)=>{
                vue.getQRcode(success.data.data)
                vue.roomlist=success.data.data;
                console.log("roomlist")
                console.log(vue.roomlist)
          }).catch((error)=>{
            console.log(error)
          });
      },
       async getQRcode(roomlist){
          let vue=this;
          let room;
          let successurl;
            for(let i=0;i<roomlist.length;i++){
            room=roomlist[i]
         await axios({
            method:'post',
            url:"http://localhost:8080/room/getRoomQRcode",
            responseType:'blob',
            data:room
          }).then((success)=>{
            successurl= window.URL.createObjectURL(success.data)
            vue.fitUrlList[i]=successurl
            vue.$set(room,'qrcode',successurl)
           vue.fitUrlList[i]=successurl
          }).catch((error)=>{
            console.log(error)
          })
          }
        },
        createlist(){
          this.dialogFormVisible=true;
          let vue=this;
            axios.post("http://localhost:8080/room/getRandomId").then((success)=>{
                console.log("success")
                console.log(success)
                vue.form.id=success.data.data
            })
        },
        sendlist(){
          let vue=this;
            axios.post("http://localhost:8080/room/createRoom",{
              roomName:vue.form.name,
              roomId:vue.form.id
            }).then((success)=>{
                console.log(success)
            })
            this.dialogFormVisible=false;
        },
        createstarttime(room){
          this.dialogOpenVisible=true;
          let vue=this;
          vue.alertform=room;
        },
        getindex(index){
          console.log("index")
          console.log(index)
        },
        startRoom(){
          let vue=this;
          console.log(vue.alertform);
          axios.post("http://localhost:8080/room/startRoom",vue.alertform).then((successreques)=>{
                console.log(successreques);
          });
          vue.dialogOpenVisible=false;
        },
          handleClose(){
          this.dialogVisible=false;
        },
        setRoomUsering(){

        }
    }
}
</script>

<style>

.buttontool{
    text-align: right;
}
.roomlistmain{
    background-color: #FFFFFF;
}
  .time {
    font-size: 13px;
    color: #999;
  }
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 25%;
    
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
</style>