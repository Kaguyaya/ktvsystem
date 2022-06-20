<template>

<div>
  <div  class="text item">
      <el-table
    :data="rechargelist"
    style="width: 100%;max-height:665px">
    <el-table-column
      prop="rechargeId"
      label="充值编号"
    >
    </el-table-column>
    <el-table-column
      prop="rechargeuserId"
      label="用户编号"
     >
    </el-table-column>
           <el-table-column
      prop="rechargeUsername"
      label="充值用户名">
    </el-table-column>
    <el-table-column
      prop="rechargeTime"
      label="充值时间">
    </el-table-column>
        <el-table-column
      prop="rechargeAmount"
      label="充值金额">
    </el-table-column>
  </el-table>
  </div>

  <div class="block">
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage4"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="100"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400">
    </el-pagination>
  </div>

</div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
        rechargelist:[
          {
      rechargeId: "",
      rechargeuserId: "",
      rechargeTime: "",
      rechargeAmount: "",
      rechargeUsername: ""
          }
        ]
				
    }
  },
     mounted() {
       let vue=this;
        axios.post("http://localhost:8080/recharge/listPage",{
            currentPage: 0,
            pageSize: 10,
            queryString: "",
            status: ""
        })
        .then((successResponse)=>{
            vue.rechargelist=successResponse.data.data.rows
        })
    },
    methods: {
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        let vue=this;
        console.log(val)
        axios.post("http://localhost:8080/recharge/listPage",{
          currentPage: val,
            pageSize: 10,
            queryString: "",
            status: ""
        }).then((successResponse)=>{
          vue.rechargelist=successResponse.data.data.rows
        })
        console.log(`当前页: ${val}`);
      }
    }
}
</script>

<style>
.rechangeinput{
    width: 200px;
}
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .el-pagination{
    height: 50px;
  }
</style>