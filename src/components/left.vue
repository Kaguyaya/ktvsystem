<template>
  <div>
    <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>用户列表</span>
        </template>
        <el-menu-item-group>
          <router-link to="/userinfo">
            <el-menu-item index="1-1">用户资料</el-menu-item>
          </router-link>
          <router-link to="/userrechange">
            <el-menu-item index="1-2">用户充值</el-menu-item>
          </router-link>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span>歌曲列表</span>
        </template>
        <el-menu-item-group>
          <router-link to="/songlist">
            <el-menu-item index="2-1">歌曲列表</el-menu-item>
          </router-link>
          <router-link to="/uploadsong">
            <el-menu-item index="2-2">上传歌曲</el-menu-item>
          </router-link>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title">
          <i class="el-icon-document"></i>
          <span>房间列表</span>
        </template>
        <el-menu-item-group>
          <router-link to="/roomlist">
            <el-menu-item index="3-1">房间总览</el-menu-item>
          </router-link>
          <router-link to="/testroom">
<el-menu-item index="3-3">
            播放测试
          </el-menu-item>
          </router-link>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>商品列表</span>
        </template>
        <el-menu-item-group>
          <router-link to="/itemadd">
          <el-menu-item index="4-1">添加商品</el-menu-item>
          </router-link>
          <router-link to="/itemindex">
          <el-menu-item index="4-2">商品目录</el-menu-item>
          </router-link>
          <router-link to="/itemconsume">
            <el-menu-item index="4-3">消费记录</el-menu-item>
          </router-link>
          <router-link to="itemtype">
          <el-menu-item index="4-4">
            商品种类
          </el-menu-item>
          </router-link>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style>
.el-menu-vertical-demo {
  height: 800px;
}
</style>
