<template>
<div>
<el-table
    ref="multipleTable"
    :data="tableData"
    tooltip-effect="dark"
    style="width: auto"
    @selection-change="handleSelectionChange">
    <el-table-column
      type="selection"
      width="55">
    </el-table-column>
    <el-table-column
      prop="songName"
      label="歌曲名称"
      width="auto">
    </el-table-column>
    <el-table-column
      prop="songAuthor"
      label="歌曲作者"
      width="120">
    </el-table-column>
        <el-table-column
      prop="songAlbum"
      label="歌曲专辑"
      width="120">
    </el-table-column>
        <el-table-column
      prop="songType"
      label="歌曲类型"
      width="120">
    </el-table-column>
        <el-table-column
      prop="songLanguage"
      label="歌曲语言"
      width="120">
    </el-table-column>
        <el-table-column
      prop="songSinger"
      label="演唱者"
      width="120">
    </el-table-column>
            <el-table-column
      prop="songLyricist"
      label="歌词作者"
      width="120">
    </el-table-column>
        <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="open(scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
    <div class="block">
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="1"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="100"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400">
    </el-pagination>
  </div>
  <el-dialog title="编辑歌曲" :visible.sync="dialogFormVisible">
  <el-form :model="form">
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
   <el-select v-model="form.songLanguage" placeholder="请选择">
    <el-option
      v-for="languageitem,languageindex in languageOptions"
      :key="languageindex"
      :label="languageitem"
      :value="languageitem">
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
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
  </div>
</el-dialog>
</div>
</template>

<script>
  import axios from 'axios'
export default {
      data() {
        return {
          tableData: [],
        multipleSelection: [],
        dialogFormVisible: false,
         form: {
         songName:"",
         songUrl:"",
         songLength:"",
         songAuthor:"",
         songAlbum:"",
         songType:"",
         songLanguage:"",
         songSinger:"",
         songLyricist:"",
        },
         date:"",
        languageOptions: "",
        typeOptions: "",
        typevalue: '',
        languagevalue:'',
        formLabelWidth: '120px',
        fileList: [],
        currentPages:0
        }
      },
          methods: {
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
            handleEdit(index, row) {
              console.log(index, row);
              this.dialogFormVisible=true
      },
        open(row) {
          let vue=this;
          console.log(row)
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.post("http://localhost:8080/song/deleteSong",
            row
          ).then((successResponse)=>{
            console.log(successResponse)
               axios.post("http://localhost:8080/song/querySongByPage",{
            currentPage: vue.currentPages,
            pageSize: 10,
            queryString: "",
            status: ""
        })
        .then((successResponse)=>{
            vue.tableData=successResponse.data.data.rows
        })
          })
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
            handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        this.currentPages=val;
       let vue=this;
        axios.post("http://localhost:8080/song/querySongByPage",{
          currentPage: val,
            pageSize: 10,
            queryString: "",
            status: ""
        }).then((successResponse)=>{
          vue.tableData=successResponse.data.data.rows
        })
        console.log(`当前页: ${val}`);
      },
       handleChange(file){
          let vue=this;
          let url= URL.createObjectURL(file.raw)
          var audioElement=new Audio(url)
          audioElement.addEventListener('loadedmetadata',function(){
            let playTime=audioElement.duration;
            vue.form.songLength=playTime;
          })
          let name=file.name
          let index=name.indexOf('.')
          let res=name.substring(0,index)
          vue.form.songName=res;
          console.log(vue.form)
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
       onSubmit() {
        let vue=this;
        // console.log('submit!');
        // this.$refs.upload.submit();
        axios.post("http://localhost:8080/song/setSongUInfo",vue.form).then((successResponse)=>{
            console.log(successResponse)
        })
      },
      uploadSuccess(successResponse){
  console.log(successResponse)
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
    },
    mounted(){
      this.date=new Date().toLocaleString();
      let vue=this;
        axios.post("http://localhost:8080/song/querySongByPage",{
            currentPage: 0,
            pageSize: 10,
            queryString: "",
            status: ""
        })
        .then((successResponse)=>{
            vue.tableData=successResponse.data.data.rows
        })
      axios.post("http://localhost:8080/song/getSongType").then((successResponse)=>{
        vue.typeOptions=successResponse.data;
      })
      axios.post("http://localhost:8080/song/getSongLanguage").then((successResponse)=>{
        vue.languageOptions=successResponse.data;
      })

    }
}
</script>

<style>

</style>