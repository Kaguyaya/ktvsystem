<template>
  <div :key="tablekey">
    <el-table :data="form" style="width: 100%; height: auto">
      <el-table-column prop="typeId" label="消费编号"> </el-table-column>
      <el-table-column prop="typeName" label="商品名称"> </el-table-column>
      <el-table-column align="right" width="180">
        <!-- eslint-disable-next-line -->
        <template slot="header" slot-scope="scope">
          <el-button type="primary" @click="insertType()"> 添加类型 </el-button>
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="searchItemByPageTypeId(scope.row.typeId)"
            >相关商品</el-button
          >
          <el-button size="mini" type="danger" @click="deletetype(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
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

    <el-dialog title="商品" :visible.sync="dialogTableVisible">
      <el-table
        :data="itemdata"
        style="width: 100%; height: auto"
        :row-class-name="tableRowClassName"
      >
        <el-table-column prop="itemId" label="编号"> </el-table-column>
        <el-table-column prop="itemName" label="商品名"> </el-table-column>
        <el-table-column prop="itemPrice" label="商品价格"> </el-table-column>
        <el-table-column prop="itemCount" label="商品库存"> </el-table-column>
        <el-table-column prop="itemCreatetime" label="上架时间">
        </el-table-column>
        <el-table-column prop="itemEndtime" label="过期时间"> </el-table-column>
        <el-table-column align="right" width="180">
          <!-- eslint-disable-next-line -->
          <template slot="header" slot-scope="scope">
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入关键字搜索"
              @input="onInput()"
              width="120"
            />
          </template>
          <template slot-scope="scope">
            <el-button size="mini" @click="open(scope.$index, scope.row)">{{
              itemdata[scope.$index].itemState ? "下架" : "上架"
            }}</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
              >删除</el-button
            >
          </template>
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
          :total="400"
        >
        </el-pagination>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      tablekey: 0,
      form: {
        typeId: "",
        typeName: "",
      },
      itemdata: [{}],
      dialogTableVisible: false,
      queryString: "",
      nowcurrent:'',
      nowpage:''
    };
  },
  mounted() {
    this.oncreatemethods(0, 10);
  },
  methods: {
    handleDelete(index,row){
      let vue=this;
        console.log(index,row)
         this.$confirm("此操作将永久删除该商品, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios.post("http://localhost:8080/KtvItem/delItem",row).then((res)=>{
            console.log("删除成功",res);
            vue.selectByPage(vue.nowcurrent,vue.nowpage)
          })
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
        
    },
    oncreatemethods(current, pagesize) {
      let vue = this;
      axios
        .post("http://localhost:8080/KtvItem/searchItemByPage", {
          currentPage: current,
          pageSize: pagesize,
          queryString: "",
          status: "",
        })
        .then((res) => {
          vue.itemdata = res.data.data.rows;
          console.log(res);
        });

      axios.post("http://localhost:8080/ktvItemType/queryByPage",{
          currentPage: current,
          pageSize: pagesize,
          queryString: "",
          status: "",
        }).then((res) => {
        console.log("商品类型");
        this.form = res.data.data.rows;
      });
    },
    deletetypebyid(typeid) {
      let vue = this;
      axios
        .post("http://localhost:8080/ktvItemType/delOne", typeid)
        .then((res) => {
          console.log("删除成功", res);
          vue.oncreatemethods();
        });
    },
    deletetype(typeid) {
      this.$confirm("此操作将永久删除该类型, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.deletetypebyid(typeid);
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    selectByPage(current, pagesize) {
      let vue = this;
    vue.nowcurrent=current;
    vue.nowpage=pagesize;
      axios
        .post("http://localhost:8080/KtvItem/searchItemByPageTypeId", {
          currentPage: current,
          pageSize: pagesize,
          queryString: vue.queryString,
          status: "",
        })
        .then((res) => {
          vue.itemdata = res.data.data.rows;
        });
    },
    searchItemByPageTypeId(queryString) {
      let vue = this;
      vue.nowcurrent=0;
      vue.nowpage=10;
      vue.queryString = queryString;
      axios
        .post("http://localhost:8080/KtvItem/searchItemByPageTypeId", {
          currentPage: 0,
          pageSize: 10,
          queryString: queryString,
          status: "",
        })
        .then((res) => {
          vue.itemdata = res.data.data.rows;
          vue.dialogTableVisible = true;
        });
    },
    searchitembytype(typeId) {
      axios
        .post("http://localhost:8080/KtvItem/searchItemByTypeId", typeId)
        .then((res) => {
          console.log(res);
        });
    },
    tableRowClassName({ row }) {
      console.log(row);
      if (row.itemState == null) {
        return "warning-row";
      } else if (row.itemState == true) {
        return "success-row";
      } else if (row.itemState == false) {
        return "warning-row";
      }
    },
    open(index, row) {
      let vue = this;
      if (row.itemState == null) {
        row.itemState = true;
        axios
          .post("http://localhost:8080/KtvItem/altItem", row)
          .then((res) => {
            console.log(res);
          })
          .catch((error) => {
            console.log("error", error);
          });
        vue.$message({
          message: "上架成功",
          type: "success",
        });
      } else if (row.itemState == true) {
        row.itemState = false;
        axios.post("http://localhost:8080/KtvItem/altItem", row).then((res) => {
          console.log(res);
        });
        vue.$message({
          message: "已下架",
          type: "success",
        });
      } else if (row.itemState == false) {
        row.itemState = true;
        axios.post("http://localhost:8080/KtvItem/altItem", row).then((res) => {
          console.log(res);
        });
        vue.$message({
          message: "已上架",
          type: "success",
        });
      }
    },
    addnew(val) {
      let vue = this;
      axios
        .post("http://localhost:8080/ktvItemType/insertOne", { typeName: val })
        .then((res) => {
          console.log(res);
          vue.oncreatemethods();
        });
    },
    handleSizeChange1() {},
    handleCurrentChange1(val) {
      this.oncreatemethods(val, 10);
    },
        handleSizeChange2() {},
    handleCurrentChange2(val) {
      this.selectByPage(val, 10);
    },
    insertType() {
      this.$prompt("请输入类型名称", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputValidator: (value) => {
          // 点击按钮时，对文本框里面的值进行验证
          if (!value) {
            return "输入不能为空";
          } else if (value.length >= 10) {
            return "不能超过10个字";
          }
        },
        inputErrorMessage: "输入错误",
      })
        .then(({ value }) => {
          this.addnew(value);
          this.tablekey++;
          this.$message({
            type: "success",
            message: "创建的商品类型是: " + value,
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
    },
  },
};
</script>

<style></style>
