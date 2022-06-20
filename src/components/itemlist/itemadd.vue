<template>
<div class="uploadbg">
<el-form ref="form" :model="form" label-width="80px">
    <el-row :gutter="100">
    <el-col :span='12'>
          <el-form-item label="商品名称">
    <el-input v-model="form.itemName"></el-input>
  </el-form-item>
    </el-col>
        <el-col :span='12'>
          <el-form-item label="商品单价">
    <el-input v-model="form.itemPrice"></el-input>
  </el-form-item>
    </el-col>
            <el-col :span='12'>
          <el-form-item label="商品类型">
        <el-select v-model="form.itemTypeid" placeholder="请选择商品类型" >
          <div v-for="type in itemtype" :key="type.typeId">
      <el-option :label="type.typeName" :value=type.typeId></el-option>
          </div>
    </el-select>
  </el-form-item>
    </el-col>
    </el-row>
    <el-row :gutter="100">
        <el-col :span='12'>
          <el-form-item label="商品库存">
    <el-input v-model="form.itemCount"></el-input>
  </el-form-item>
    </el-col>
  <el-col :span="12">
    <el-form-item label="过期时间">
    <el-date-picker
      v-model="form.itemEndtime"
      value-format="yyyy-MM-dd HH:mm:ss"
      type="datetime"
      placeholder="选择日期"
      >
    </el-date-picker>
  </el-form-item>
  </el-col>
  <el-col>
  <el-form-item>
    <el-upload
  class="c-upload"
    ref="upload"
    :data="{itemName:itemid}"
    action="http://localhost:8080/KtvItem/uploadImg"
    accept=".jpg,.png"
    :before-upload="beforeUpload"
    :auto-upload="false"
    :file-list="fileList"
    :on-success="uploadSuccess"
    :on-progress="handleupload"
    :on-change="handleChange"
    :on-preview="handlePreview"
    >
    <el-button
        slot="trigger"
        size="small"
        type="primary"
    >选取图片</el-button>
    <div
        slot="tip"
        class="el-upload__tip"
    >只能上传png或jpg文件，且不超过一个</div>
</el-upload>
</el-form-item>
  </el-col>
    </el-row>
  <el-form-item>
    <el-button type="primary" @click="additem">创建</el-button>
    <el-button>取消</el-button>
  </el-form-item>
</el-form>
</div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
      return {
        form: {
          itemName:'',
          itemPrice:'',
          itemCount:'',
          itemEndtime:'',
          itemTypeid:'',
          imageUrl: ''
        },
        fileList:[],
        itemtype:'',
        itemid:''
      }
    },
    methods: {
            beforeUpload(file) {
     var testmsg=file.name.substring(file.name.lastIndexOf('.')+1)
     const extension = testmsg === 'png'
     const extension2 = testmsg === 'jpg'
     // const isLt2M = file.size / 1024 / 1024 < 10
     if(!extension && !extension2) {
         this.$message({
             message: '上传文件只能是mp4或者mkv格式!',
             type: 'warning'
         });
     }
     return extension || extension2
 },
      handlePreview(file) {
        console.log(file);
      },
            handleChange(file){
          let vue=this;
          let url= URL.createObjectURL(file.raw)
          var audioElement=new Audio(url)
          audioElement.addEventListener('loadedmetadata',function(){
            let playTime=audioElement.duration;
            vue.form.songLength=playTime;
          })
          vue.form.songName=file.name;
          console.log(vue.form)
      },
      handleupload(file){
          console.log(file.name);
      },
      uploadSuccess(successResponse){
  console.log("success");
  console.log(successResponse);
  this.fileList.pop();
},

     async additem(){
          let vue=this;
         await axios.post("http://localhost:8080/KtvItem/addItem",{
              itemName:vue.form.itemName,
              itemPrice:vue.form.itemPrice,
              itemCount:vue.form.itemCount,
              itemTypeid:vue.form.itemTypeid,
              itemEndtime:vue.form.itemEndtime,
              itemConsume:0
          }).then((res)=>{
          this.itemid=res.data.data.itemId;
          })
        console.log(this.itemid);
                let returnvalue=this.$refs.upload.submit();
        console.log(returnvalue)
      },
      handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isPNG=file.type==='image/png';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG&&!isPNG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return (isJPG&&isPNG)&& isLt2M;
      }
    },
    mounted(){
      let vue=this;
      axios.post("http://localhost:8080/ktvItemType/searchAll").then((res)=>{
        console.log(res);
        vue.itemtype=res.data.data;
      })
    }
}
</script>

<style>
.uploadbg{
    background-color: #FFFFFF;
    border-radius: 30px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    padding: 20px;
    width: 800px;
    margin-left: 25%;
}
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>