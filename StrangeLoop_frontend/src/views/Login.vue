<script setup lang="ts">
import {loginUser,createUser} from "../api/login.ts";
import {onMounted, reactive, ref} from "vue";
import {ElMessage, FormRules} from "element-plus";
import {setToken} from "../utils/auth.ts";
import {useRequestStore} from "@/store/RequestStore.ts";
import router from "../router";

const USERNAME = import.meta.env.VITE_USERNAMES || "";
const PASSWORD = import.meta.env.VITE_PASSWORD || "";

const requestStore = useRequestStore();
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
      <div class="login-border login">
            <div >
              <img src="../assets/images/logo/logo.png" class="logo_big" v-if="!loginStatus">
              <img src="../assets/images/logo/logo.png" class="log_small" style="" v-if="loginStatus">
              <h2 >
                怪圈
              </h2>
            </div>
            <Transition name="login">
              <h1 v-if="!loginStatus">欢迎回来</h1>
            </Transition>
            <Transition name="create">
              <h1  v-if="loginStatus">注册账号</h1>
            </Transition>
            <div class="box">
              <div class=" loginInput">
                <span>用户名</span>
                  <el-form-item prop="account">
                    <input placeholder="请输入用户名" v-model="loginForm.account">
                  </el-form-item>
              </div>
              <div class=" loginInput">
                <span>密码</span>
                  <el-form-item prop="password">
                    <input placeholder="请输入密码" type="password" v-model="loginForm.password">
                  </el-form-item>
              </div>
              <div class=" loginInput" v-if="loginStatus">
                <span >请再次输入密码</span>
                  <el-form-item prop="passwordAgain" >
                    <input placeholder="请再次输入密码" type="password" v-model="loginForm.passwordAgain">
                  </el-form-item>
              </div>
              <div class="login-button " @click="startLogin" v-if="!loginStatus">
                登录
              </div>
              <div class="login-button " @click="startCreate" v-if="loginStatus">
                注册
              </div>
              <span class="downText" v-if="!loginStatus">还没有账号？
                <a @click="loginStatus=!loginStatus" >注册</a>
              </span>
              <span class="downText" v-if="loginStatus">我已经有账号了。
                <a @click="loginStatus=!loginStatus;resetForm();" >登录</a>
              </span>
            </div>
      </div>
  </el-form>
</div>
</template>

<style scoped>
#content{
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;

  .login {
    max-width: 40vw;
    min-width: 30vw;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #f1f7fe;
    border-radius: 30px;

    .box{
      width: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;
      padding-bottom: 50px;

      .loginInput {
        width: 80%;
        font-size: 20px;
        background-color: #fff;
        border: 1px solid #d9d9d9;
        border-radius: 15px;
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        padding: 0 0 0 2vw;
        min-height: 10vh;
        justify-content: space-between;
        margin-bottom: 3vh;

      }

      .login-button {
        width: 80%;
        height: 6vh;
        border-radius: 30px;
        background-color: #3f4583;
        font-size: 20px;
        line-height: 6vh;
        color: white;
        cursor: pointer;
        margin-bottom: 20px;
      }
    }

  }

  img {
    border-radius: 50%;
  }

  input {
    width: 100%;
    height: 5vh;
    outline: none;
    border: none;
    line-height: 5vh;
  }

}

.downText {
  font-size: 14px;
  position: relative;
  left: 30%;

  a{
    color: #02a7f0;
    text-decoration: underline;
    cursor: pointer;
  }
}




</style>
