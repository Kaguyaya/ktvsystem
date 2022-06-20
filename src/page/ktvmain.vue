<template>
<el-container>
  <el-aside  width="200px">
        <left>
        </left>
    </el-aside>
  <el-container>
    <el-header>
      <top>
      </top>
    </el-header>

    <el-main>
      <themain>
      </themain>
    </el-main>

  </el-container>
</el-container>
</template>

<script>
import Left from '../components/left.vue'
import Top from '../components/top.vue'
import themain from '../components/main.vue'
export default {
  components: {Left, Top,themain},
}
</script>

<style>

  .el-header, .el-footer {
    background-color: #a4b0be;
   
    text-align: center;
    line-height: 60px;
  }
    .el-aside {
    background-color: #FFFFFF;
   
    text-align: center;
    line-height: 1px;
  }
  
  .el-main {
    background-color: #a4b0be;
  
    text-align: center;
    line-height: 1px;
  }
</style>