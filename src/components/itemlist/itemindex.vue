<template>
<div>
    <el-table
    :data=itemdata
    style="width: 100%;height:auto"
    :row-class-name="tableRowClassName"
    >
    <el-table-column
      prop="itemId"
      label="编号"
      >
    </el-table-column>
        <el-table-column
      prop="itemName"
      label="商品名"
      >
    </el-table-column>
    <el-table-column
      prop="itemPrice"
      label="商品价格"
      >
    </el-table-column>
    <el-table-column
      prop="itemCount"
      label="商品库存"
      >
    </el-table-column>
        <el-table-column
      prop="itemCreatetime"
      label="上架时间"
      >
    </el-table-column>
        <el-table-column
      prop="itemEndtime"
      label="过期时间"
      >
    </el-table-column>
     <el-table-column
      align="right"
      width="180"
      >
      <!-- eslint-disable-next-line -->
      <template slot="header" slot-scope="scope">
                <el-input
          v-model="search"
          size="mini"
          placeholder="输入关键字搜索"
          @input="onInput()"
          width="120"/>
      </template>
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="open(scope.$index,scope.row)"
          >{{itemdata[scope.$index].itemState?"下架":"上架"}}</el-button>
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
import axios from 'axios'
export default {
    data(){
        return{
            itemdata:[{

            }],
            search:'',
        }
    },
    methods:{ 
        tableRowClassName({row}) {
            console.log(row)
        if(row.itemState==null){
            return 'warning-row';
        }else if(row.itemState==true){
            return 'success-row'
        }else if(row.itemState==false){
            return 'warning-row'
        }
      },
        open(index,row){
                let vue=this;
                if(row.itemState==null){
                        row.itemState=true;
                        axios.post('http://localhost:8080/KtvItem/altItem',row).then((res)=>{
                            console.log(res);
                        }).catch((error)=>{
                            console.log('error',error)
                        })
                        vue.$message({
                            message:'上架成功',
                            type:'success'
                        })
                }else if(row.itemState==true){
                    row.itemState=false;
                    axios.post('http://localhost:8080/KtvItem/altItem',row).then((res)=>{
                        console.log(res)
                    })
                        vue.$message({
                            message:'已下架',
                            type:'success'
                        })
                }else if(row.itemState==false){
                    row.itemState=true;
                    axios.post('http://localhost:8080/KtvItem/altItem',row).then((res)=>{
                        console.log(res)
                    })
                    vue.$message({
                            message:'已上架',
                            type:'success'
                        })
                }
        },
        handleDelete(index,row){
                console.log(index,row)
        },
handleSizeChange(val){
    console.log("handlesizechange",val)
},
handleCurrentChange(val){
     let vue=this;
        axios.post("http://localhost:8080/KtvItem/searchItemByPage",{
  currentPage: val,
  pageSize: 10,
  queryString: "",
  status: ""
}).then((res)=>{
                    vue.itemdata=res.data.data.rows;
                    console.log(res);
            })
}
    },
    mounted(){
        let vue=this;
        axios.post("http://localhost:8080/KtvItem/searchItemByPage",{
  currentPage: 0,
  pageSize: 10,
  queryString: "",
  status: ""
}).then((res)=>{
                    vue.itemdata=res.data.data.rows;
                    console.log(res);
            })
    }
}
</script>

<style>
.el-table .success-row {
    background: #f0f9eb;
  }

    .el-table .warning-row {
    background: #FAECD8;
  }
</style>