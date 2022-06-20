<template>
  <div class="login-container">
    <el-form
      class="loginform"
      ref="form"
      :rules="rules"
      :model="form"
      label-width="80px"
    >
      <h2 class="login-title">管理系统</h2>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" show-password> </el-input>
      </el-form-item>
      <div class="denglu">
      <el-button type="primary" @click="submitForm('form')">登录</el-button>
      <el-button>取消</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import axios from '../axios/axios.js'
export default {
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      responseResult: "",
      msg: "",
      rules: {
        username: [
          {
            require: true,
            message: "用户名不能为空",
            trigger: "blur",
          },
          {
            min: 3,
            max: 16,
            message: "用户名3-16位",
            trigger: "blur",
          },
        ],
        password: [
          {
            require: true,
            message: "密码不能为空",
            trigger: "blur",
          },
          {
            min: 3,
            max: 16,
            message: "密码3-16位",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    onSubmit() {
      console.log("submit!");
    },
    submitForm(formName) {
      let vue = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log("验证成功");
          axios
            .post("http://localhost:8080/user/login", {
              userUsername: vue.form.username,
              userPassword: vue.form.password,
            })
            .then((successResponse) => {
              vue.responseResult = JSON.stringify(successResponse.data);
              vue.msg = JSON.stringify(successResponse.data.message);
              if (successResponse.data.code === 200) {
                vue.msg = "";
                localStorage.setItem("username", vue.form.username); //设置请求
                 var authorization = successResponse.headers["authorization"];//接收后端发送的响应头
                localStorage.setItem("authorization", authorization);
                  vue.$router.push('/ktvmain')
              }
            })
            .catch((falseResponse)=>{
              console.log("false:"+falseResponse)
            })
        } else {
          console.log("验证失败");
          return false;
        }
      });
    },
  },
};
</script>
<style>
.loginform {
  width: 350px;
  margin: 160px auto; /* 上下间距160px，左右自动居中*/
  background-color: rgb(255, 255, 255, 0.8); /* 透明背景色 */
  padding: 30px;
  border-radius: 20px; /* 圆角 */
  border: 1px;
  border-color: black;
}

.login-container {
  position: absolute;
  width: 100%;
  height: 100%;
  background: url("../assets/blackholo.jpg");
}
.login-title {
  color: #303133;
  text-align: center;
}
.denglu{
  text-align: center;
}
</style>
