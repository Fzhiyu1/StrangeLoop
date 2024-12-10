<script setup lang="ts">

</script>

<template>
<div id="content">
  <el-row>
    <el-col :span="18" :offset="3">
      <div class="login-border login">
        <el-row>
          <el-col :span="20" :offset="2">
            <div style="margin-top: 50px">
              <img src="../assets/images/logo/logo.png" class="logo_big">
              <h2 style="font-size: 30px">
                怪圈
              </h2>
            </div>
            <h1 style="margin-top: 70px">欢迎回来</h1>
            <div>
              <div class="login-border loginInput">
                <span>邮箱</span>
                <el-row>
                  <input placeholder="请输入电子邮箱">
                </el-row>
              </div>

              <div class="login-border loginInput" style="margin-top: 20px">
                <span>密码</span>
                <el-row>
                  <input placeholder="请输入密码" type="password">
                </el-row>
              </div>
              <div class="login-button login-border" style="margin-top: 20px">
                登录
              </div>
              <span class="downText">还没有账号？
                <a href="#">注册</a>
              </span>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-col>
  </el-row>
</div>
</template>

<style scoped>
.login {
  width: 500px;
  height: 1000px;
  background-color: #eff5fc;

}
.loginInput {
  width: 420px;
  height: 120px;
  background-color:white ;

}
.loginInput span {
  font-size: 30px;
  position: relative;
  left: -150px;
  top:10px
}
.loginInput input {
  border: none;
  background-color: #ffffff;
  width: 400px;
  height: 50px;
  margin-top: 10px;
  margin-left: 10px;
  font-size: 20px;
}

.loginInput input:focus{
  border: 0px;
  background-color: white;
}
.downText{
  position: absolute;
  left: 300px;
  margin-top: 20px;
}
.downText a{
  text-decoration: underline;
}




</style>
