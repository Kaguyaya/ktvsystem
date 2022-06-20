<template>
<div>
  <div>
    <el-table
      :key="tablekey"
      :data="form"
      style="width: 100%; height: auto"
    >
      <el-table-column prop="itemOrderId" label="消费编号"> </el-table-column>
      <el-table-column label="商品详情">
              <template slot-scope="scope">
          <el-button type="text" size="small" @click="itemconsumelist(scope.row.itemOrderId)">{{ scope.row.consumename}}</el-button>
          </template>
         </el-table-column>
      <el-table-column prop="roomname" label="房间号"> </el-table-column>
      <el-table-column prop="createtime" label="商品编号"> </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange1"
        @current-change="handleCurrentChange1"
        :current-page="1"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="100"
        layout="total, sizes, prev, pager, next, jumper"
        :total="400"
      >
      </el-pagination>
    </div>
  </div>
    <el-dialog title="消费商品" :visible.sync="dialogTableVisible">
    <el-table
    :data=consumeformlist
    style="width: 100%;height:auto"
    >
    <el-table-column
      prop="consumeId"
      label="消费id"
      >
    </el-table-column>
        <el-table-column
      prop="itemName"
      label="消费商品"
      >
    </el-table-column>
    <el-table-column
      prop="consumePrice"
      label="消费价格"
      >
    </el-table-column>
    <el-table-column
      prop="consumeCount"
      label="消费数量"
      >
    </el-table-column>
        <el-table-column
      prop="allprice"
      label="消费总价"
      >
    </el-table-column>
  </el-table>
  <div class="block">
    <el-pagination
      @size-change="handleSizeChange2"
      @current-change="handleCurrentChange2"
      :current-page="1"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="100"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400">
    </el-pagination>
  </div>
</el-dialog>
</div>
</template>

<script>
import axios from "axios";
import Vue from 'vue';
export default {
  data() {
    return {
      selectid:'',
      consumeformlist:'',
      tablekey:0,
      form: {
      },
      consumelist: [],
      dialogTableVisible: false,
    };
  },
  methods: {
    handleSizeChange1(val) {
console.log(val)
    },
    handleCurrentChange1(val) {
      this.searchorder(val,10)
    },
    handleSizeChange2(val) {
console.log(val)
    },
    handleCurrentChange2(current) {
      this.itemconsumelistByPage(current,10)
    },
accMul(arg1,arg2) {
				let m = 0,
					s1 = '',
					s2 = '';
				if (arg1 && arg1 != null)
					s1 = arg1.toString();
				if (arg2 && arg2 != null)
					s2 = arg2.toString();
				try {
					m += s1.split('.')[1].length
				} catch (e) {
          console.log(e)
        }
				try {
					m += s2.split('.')[1].length
				} catch (e) {
          console.log(e)
        }
				return Number(s1.replace('.', '')) * Number(s2.replace('.', '')) / Math.pow(10, m);
			},

          itemconsumelistByPage(currentpage,pagesize){
      let vue=this;
        axios.post("http://localhost:8080/Itemconsume/searchItemconsumeByPageTypeId",
            {
            currentPage: currentpage,
            pageSize: pagesize,
            queryString:vue.selectid,
            status: "",
          }).then((res)=>{
            vue.consumeformlist=res.data.data.rows;
              res.data.data.rows.forEach((list)=>{
                Vue.set(list,"allprice",vue.accMul(list.consumeCount,list.consumePrice))
              })
        })
    },
    itemconsumelist(itemorderid){
      let vue=this;
      vue.selectid=itemorderid;
        axios.post("http://localhost:8080/Itemconsume/searchByIdList",itemorderid).then((res)=>{
         vue.consumeformlist=res.data.data;
         vue.consumeformlist.forEach((list)=>{
              Vue.set(list,"allprice",vue.accMul(list.consumeCount,list.consumePrice))
         })
          console.log(res);
          this.dialogTableVisible=true;
        })
    },
    
   async searchorder(currentpage,pagesize) {
     let vue=this;

     await axios
        .post(
          "http://localhost:8080/KtvItemOrder/searchItemconsumeByPageTypeId",
          {
            currentPage: currentpage,
            pageSize: pagesize,
            queryString: "",
            status: "",
          }
        )
        .then((res) => {
          vue.form=res.data.data.rows;
         
          vue.form.forEach((orders)=>{
                vue.searchconsume(orders)
          });
          vue.form.forEach((orders)=>{
              vue.searchroomname(orders);
          });
        });
    },
    searchroomname(roomid){
      console.log("roomid",roomid)
        axios.post("http://localhost:8080/room/searchRoomById",
        roomid.roomId
        ).then((res)=>{
            Vue.set(roomid,"roomname",res.data.data.roomName)
          console.log(res);
        })
    },
    searchconsume(order) {
      let consumename="";
      axios
        .post(
          "http://localhost:8080/Itemconsume/searchByIdList",
          order.itemOrderId
        )
        .then((res) => {
          res.data.data.forEach((consume)=>{
            consumename=consumename+consume.itemName+"x"+consume.consumeCount+","
          })
          if(consumename.length>10){
           consumename=consumename.substr(0,10)+"..."
          }
          Vue.set(order,"consumename",consumename);
          this.tablekey++;
          console.log("consume", consumename);
           console.log("order", consumename);
        });
    },
  },
  mounted() {
    this.searchorder(0,10)
  },
};
</script>

<style></style>
