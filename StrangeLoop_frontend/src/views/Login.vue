<script setup lang="ts">
import {loginUser,createUser} from "../api/login.ts";
import {onMounted, reactive, ref} from "vue";
import {ElMessage, FormRules} from "element-plus";
import {setToken} from "../utils/auth.ts";
import router from "../router";

const USERNAME = import.meta.env.VITE_USERNAMES || "";
const PASSWORD = import.meta.env.VITE_PASSWORD || "";

let loginStatus = ref(false);
//  表单实例
const formRef = ref(null);
// 再次输入密码输入框表单验证
const validatePass2 = (rule:any, value:any, callback:any) => {
  if (value === '') {
    callback(new Error('密码不能为空'))
  } else if (value !== loginForm.password) {
    callback(new Error("两次输入的密码不一致"))
  } else {
    callback()
  }
}
// 表单验证
const rules = reactive<FormRules>({
  account: [
    {
      required: true, message: '用户名不能为空', trigger: "blur"
    },
    {
      min: 3, max: 10, message: '用户名长度在 5 到 10 个字符之间', trigger: "blur"
    }
  ],

  password: [
    {required: true, message: '密码不能为空', trigger: 'blur'},
    {min: 6, message: '密码长度不能小于 6 个字符', trigger: 'blur'},
  ],
  passwordAgain: [
    {required: true, message: '密码不能为空', trigger: 'blur'},
    { validator: validatePass2, trigger: 'blur' }
  ],
});
// 登录表单
let loginForm = reactive({
  account: USERNAME,
  passwordAgain: undefined,
  password: PASSWORD,
});
// 重置表单事件
const resetForm= ()=>{
  loginForm.account = undefined;
  loginForm.password = undefined;
  loginForm.passwordAgain = undefined;
}
const data = reactive({});
// 注册按钮事件
const startCreate =async ()=>{
  // 表单验证
  formRef.value?.validate((valid: boolean) => {
    if (valid) {
      // 调用创建用户API
      createUser({data:loginForm}).then(res => {
        if (res.data.code == 200) {
          ElMessage.success("创建成功");
          loginStatus.value = false;
          // 重置表单
          resetForm();
        } else {
          ElMessage.error(res.data.message);
          // 重置表单
          resetForm();
        }
      })
    }
  })
}
// 登录按钮事件
const startLogin = async () => {
  // 触发表单验证
  formRef.value?.validate((valid: boolean) => {
    if (valid) {
      // 调用登录接口
      loginUser({ data: loginForm }).then((res) => {
        if (res.data.code == 200) {
          ElMessage.success("登录成功");

          setToken({token:res.data.data});
          router.push("/");
          console.log(router)
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
    <el-col :span="14" :offset="10">
      <div class="login-border login">
        <el-row>
          <el-col :span="20" :offset="2">
            <div style="margin-top: 50px">
              <img src="../assets/images/logo/logo.png" class="logo_big" v-if="!loginStatus">
              <img src="../assets/images/logo/logo.png" class="log_small" style="" v-if="loginStatus">
              <h2 style="font-size: 30px">
                怪圈
              </h2>
            </div>
            <Transition name="login">
              <h1 style="margin-top: 70px" v-if="!loginStatus">欢迎回来</h1>
            </Transition>
            <Transition name="create">
              <h1 style="margin-top: 70px" v-if="loginStatus">注册账号</h1>
            </Transition>
            <div>
              <div class="login-border loginInput">
                <span>用户名</span>
                <el-row>
                  <el-form-item prop="account">
                    <input placeholder="请输入用户名" v-model="loginForm.account">
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
              <div class="login-border loginInput" style="margin-top: 20px" v-if="loginStatus">
                <span style="margin-left: 100px;font-size: 20px">请再次输入密码</span>
                <el-row>
                  <el-form-item prop="passwordAgain" >
                    <input placeholder="请再次输入密码" type="password" v-model="loginForm.passwordAgain">
                  </el-form-item>
                </el-row>
              </div>
              <div class="login-button login-border" style="margin-top: 20px" @click="startLogin" v-if="!loginStatus">
                登录
              </div>
              <div class="login-button login-border" style="margin-top: 20px" @click="startCreate" v-if="loginStatus">
                注册
              </div>
              <span class="downText" v-if="!loginStatus">还没有账号？
                <a @click="loginStatus=!loginStatus" style="cursor: pointer">注册</a>
              </span>
              <span class="downText" v-if="loginStatus">我已经有账号了。
                <a @click="loginStatus=!loginStatus;resetForm();" style="cursor: pointer">登录</a>
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
#content{
margin-top: 100px;
}
.login {
  width: 500px;
  height: 900px;
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
