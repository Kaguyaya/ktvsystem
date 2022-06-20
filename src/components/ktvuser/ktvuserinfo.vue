<template>
<div> 
    <el-table
    :data="tableData.filter(data => !search || data.userNickname.toLowerCase().includes(search.toLowerCase()))"
    style="width: 100%;height:auto"
    >
    
    <el-table-column
      prop="userId"
      label="编号"
      width="180">
    </el-table-column>
        <el-table-column
      prop="userUsername"
      label="用户名"
      width="180">
    </el-table-column>
    <el-table-column
      prop="userNickname"
      label="姓名"
      width="180">
    </el-table-column>
    <el-table-column
      prop="userBalance"
      label="余额">
    </el-table-column>
        <el-table-column
      prop="userBirthday"
      label="生日">
    </el-table-column>
        <el-table-column
      prop="userPlaytime"
      label="在线时长">
    </el-table-column>
            <el-table-column
      prop="userRegistertime"
      label="注册时间">
    </el-table-column>
            <el-table-column
      prop="userUpdatetime"
      label="最后一次登录">
    </el-table-column>
     <el-table-column
      align="right">
      <!-- eslint-disable-next-line -->
      <template slot="header" slot-scope="scope">
                <el-input
          v-model="search"
          size="mini"
          placeholder="输入关键字搜索"
          @input="onInput()"
          width="120"          />
      </template>
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="open(scope.$index,scope.row)">充值</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
  </div>
</template>

<script>
import axios from 'axios';
export default {
   data() {
      return {
        tableData: [{
            userBalance:'',
            userBirthday:'',
            userId:'',
            userNickname:'',
            userPassword:'',
            userPlaytime:'',
            userRegistertime:'',
            userUpdatetime:'',
            userUsername:''
           }
        ],
        rechargetable:[]
        ,
        search:''
      }
    },
    mounted(){

      this.init();
    },
        methods: {
      handlechonzhi(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      },
      init(){
          let vue=this;
          vue.axios.post('http://localhost:8080/user/queryByPage',
            {
  "currentPage": 0,
  "pageSize": 10,
  "queryString": "",
  "status": ""
}).then((res)=>{
  vue.tableData=res.data.data.rows;
})
      },
      onInput(){
            console.log(this.search);
          this.$forceUpdate()
        },
         open(index,row) {
           console.log(row.userId);
           let vue=this;
           let ktvuser;
           axios.get("http://localhost:8080/user/getUserById",{
              params: {
                  userId: row.userId
                }
           }).then((successResponse)=>{
             console.log(successResponse);
             ktvuser=successResponse;
           })
        this.$prompt('请输入充值金额', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern:/^\d+$|^\d+[.]?\d+$/,
          inputErrorMessage: '请输入数字'
        }).then(({ value }) => {
          console.log("data:");
          console.log(ktvuser.data.data);
          axios.post("http://localhost:8080/recharge/rechargeRecord",
          {
            ktvuser:ktvuser.data.data,
            money:value
          }
          ).then((successResponse)=>{
             axios.post("http://localhost:8080/user/setUserMoney",{
              ktvuser:ktvuser.data.data,
              money:value
            }).then((successresponse)=>{
              console.log("setusermoney")
              console.log(successresponse.data.data);
              vue.tableData[index]=successresponse.data.data;
              console.log(vue.tableData[index]);
              vue.tableData.splice(1,0);
            }).catch((errormessage)=>{
              console.log("error")
              console.log(errormessage);
            })

            console.log(successResponse);
          }).catch((error)=>{
            console.log(error);
          })
          this.$message({
            type: 'success',
            message: '您的充值金额是: ' + value
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });
        });
      },
            handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        let vue=this;
          vue.axios.post('http://localhost:8080/user/queryByPage',
            {
  "currentPage": val,
  "pageSize": 10,
  "queryString": "",
  "status": ""
}).then((res)=>{
  vue.tableData=res.data.data.rows;
})
        console.log(`当前页: ${val}`);
      }
    },
}
</script>

<style>

</style>