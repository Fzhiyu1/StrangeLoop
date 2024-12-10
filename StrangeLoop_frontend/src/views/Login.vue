<script setup lang="ts">
import {loginUser,createUser} from "../api/login.ts";
import {reactive, ref} from "vue";
import {ElMessage, FormRules} from "element-plus";
import {setToken} from "../utils/auth.ts";
//  表单实例
const formRef = ref(null);
// 表单验证
const rules = reactive<FormRules>({
  account:[
    {
      required: true,message:'姓名不能为空',trigger:"blur"
    },
    {
      min:3,max:10,message:'用户名长度在 5 到 10 个字符之间',trigger:"blur"
    }
  ],
  password: [
    { required: true, message: '密码不能为空', trigger: 'blur' },
    { min: 6, message: '密码长度不能小于 6 个字符', trigger: 'blur' },
  ],
})

// 登录表单
const loginForm = reactive({
  account: undefined,
  password: undefined,
});
const data = reactive({});
const startLogin = async () => {
  // 触发表单验证
  formRef.value?.validate((valid: boolean) => {
    if (valid) {
      // 调用登录接口
      loginUser({ data: loginForm }).then((res) => {
        if (res.data.code == 200) {
          ElMessage.success("登录成功");
          setToken(res.data.data);
        } else {
          ElMessage.error(res.data.message);
        }
      });
    } else {
      ElMessage.error("请重新输入！");
    }
  });
};

</script>

<template>
<div id="content">

  <el-form :rules="rules" :model="loginForm" ref="formRef">
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

                  <el-form-item prop="account">
                    <input placeholder="请输入账号" v-model="loginForm.account">
                  </el-form-item>

                </el-row>
              </div>

              <div class="login-border loginInput" style="margin-top: 20px">
                <span>密码</span>
                <el-row>

                  <el-form-item prop="password">
                    <input placeholder="请输入密码" type="password" v-model="loginForm.password">
                  </el-form-item>

                </el-row>
              </div>
              <div class="login-button login-border" style="margin-top: 20px" @click="startLogin">
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
  </el-form>
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
