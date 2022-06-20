<template>
<div class="uploadbg">
  <el-form ref="form" :model="form" label-width="100px">
    <el-row :gutter="100">
      <el-col :span="12">
  <el-form-item label="歌曲名称">
    <el-input v-model="form.songName"></el-input>
  </el-form-item>
  </el-col>
  <el-col :span="12">
    <el-form-item label="歌曲作者">
 <el-input v-model="form.songAuthor"></el-input>
  </el-form-item>
  </el-col>
    </el-row>
<el-row :gutter="100">
  <el-col :span="12">
    <el-form-item label="上传日期">
    <el-date-picker
    disabled
      v-model="date"
      type="date"
      placeholder="选择日期">
    </el-date-picker>
  </el-form-item>
  </el-col>
  <el-col :span="12">
    <el-form-item label="歌曲专辑">
 <el-input v-model="form.songAlbum"></el-input>
  </el-form-item>
  </el-col>
  </el-row>
<el-row :gutter="100">
  <el-col :span="12">
    <el-form-item label="歌曲类型">
  <el-select v-model="form.songType" placeholder="请选择">
    <el-option
      v-for="typeitem,typeindex in typeOptions"
      :key="typeindex"
      :label="typeitem"
      :value="typeitem">
    </el-option>
  </el-select>
  </el-form-item>
  </el-col>
  <el-col :span="12">
    <el-form-item label="歌曲语言">
   <el-select v-model="form.songLanguage" placeholder="请选择" @change="searchSelect(form.songLanguage)" >
    <el-option
      v-for="languageitem,languageindex in languageOptions"
      :key="languageindex"
      :label="languageindex"
      :value="languageitem"
      >
    </el-option>
  </el-select>
  </el-form-item>
  </el-col>
  </el-row>

  <el-row :gutter="100">
  <el-col :span="12">
    <el-form-item label="演唱者">
<el-input v-model="form.songSinger"></el-input>
  </el-form-item>
  </el-col>
  <el-col :span="12">
    <el-form-item label="歌词作者">
 <el-input v-model="form.songLyricist"></el-input>
  </el-form-item>
  </el-col>
  </el-row>
  <el-form-item>
    <el-upload
  class="c-upload"
    ref="upload"
    :data="{language:language}"
    action="http://localhost:8080/song/upload"
    accept=".mp4,.mkv"
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
    >选取文件</el-button>
    <div
        slot="tip"
        class="el-upload__tip"
    >只能上传mp4文件，且不超过一个</div>
</el-upload>
</el-form-item>
  <el-form-item class="form-button">
    <el-button type="primary" @click="onSubmit">立即上传</el-button>
    <el-button>取消</el-button>
  </el-form-item>

</el-form>


</div>

</template>

<script>
import axios from 'axios';
export default  {
    data() {
      return {
          form: {
          songId:"",
         songName:"",
         songUrl:"",
         songLength:"",
         songAuthor:"",
         songAlbum:"",
         songType:"",
         songLanguage:"",
         songSinger:"",
         songLyricist:"",
         songCreatetime:"",
         songUpdatetime:""
        },
        date:"",
        languageOptions: "",
        typeOptions: "",
        typevalue: '',
        languagevalue:'',
        fileList: [],
        language:""
      }
    },
    mounted(){
      let vue=this;
      this.date=new Date().toLocaleString();
      axios.post("http://localhost:8080/song/getSongType").then((successResponse)=>{
        vue.typeOptions=successResponse.data;
      })
      axios.post("http://localhost:8080/song/getSongLanguage").then((successResponse)=>{
        console.log(successResponse)
        vue.languageOptions=successResponse.data;
      })
    },
    methods: {
      searchSelect(value){
      console.log(value)
      this.language=value
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
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file) {
        return this.$confirm(`确定移除 ${ file.name }?`);
      },
      beforeUpload(file) {
     var testmsg=file.name.substring(file.name.lastIndexOf('.')+1)
     const extension = testmsg === 'mp4'
     const extension2 = testmsg === 'mkv'
     // const isLt2M = file.size / 1024 / 1024 < 10
     if(!extension && !extension2) {
         this.$message({
             message: '上传文件只能是mp4或者mkv格式!',
             type: 'warning'
         });
     }
     // 也可以在这里增加文件大小限制
     // if(!isLt2M) {
     //     this.$message({
     //         message: '上传文件大小不能超过 10MB!',
     //         type: 'warning'
     //     });
     // }
     // return (extension || extension2) && isLt2M
     return extension || extension2
 },
 submitUpload() {
        this.$refs.upload.submit();
      },
uploadSuccess(successResponse){
  console.log("success");
  console.log(successResponse);
  this.fileList.pop();
},
      onSubmit() {
       let vue=this;
        // console.log('submit!');
        console.log(this.$refs);
        let returnvalue=this.$refs.upload.submit();
        console.log(returnvalue)
        axios.post("http://localhost:8080/song/setSongInfo",vue.form).then((successResponse)=>{
            console.log(successResponse)
        })
      }
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
</style>