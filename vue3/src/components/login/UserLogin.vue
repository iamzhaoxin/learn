<template>
  <div class="login-body">
    <div class="login-container" style="margin-top: 200px">
      <div class="head">
        <img class="logo" src="https://s.weituibao.com/1582958061265/mlogo.png" alt="logo"/>
        <div class="name">
          <div class="title">经费申报</div>
          <div class="tips">资产全生命周期信息化管理平台</div>
        </div>
      </div>

      <el-form label-position="top" :rules="rules" :model="ruleForm" ref="loginForm" class="login-form">
        <el-form-item label="账号" prop="username">
          <el-input type="text" v-model.trim="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model.trim="ruleForm.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="submitForm">立即登录</el-button>
          <el-checkbox v-model="autoLogin" @change="changeAutoLogin">下次自动登录</el-checkbox>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from '@/utils/axios'
import {reactive, ref, toRefs} from 'vue'
import {localGet, localRemove, localSet} from '@/utils'
import qs from 'qs';
import router from "@/router";
import {ElMessage} from "element-plus";

export default {
  name: 'UserLogin',
  setup() {
    const loginForm = ref(null)
    const state = reactive({
      ruleForm: {
        username: '',
        password: ''
      },
      autoLogin:false,
      rules: {
        username: [
          {required: 'true', message: '账户不能为空', trigger: 'blur'}
        ],
        password: [
          {required: 'true', message: '密码不能为空', trigger: 'blur'}
        ]
      }
    })
    const changeAutoLogin=()=>{
      console.log(this)
      !state.autoLogin
      if(state.autoLogin){
        let ms=new Date();
        localSet('autoLogin',ms)
      }else{
        localRemove('autoLogin')
      }
    }
    const submitForm = async () => {
      loginForm.value.validate((valid) => {
        if (valid) {
          axios
              .post('api/login', qs.stringify({
                username: state.ruleForm.username || '',
                password: state.ruleForm.password,
              }))
              .then(res => {
                console.log(res)
                if (res.code === 200) {
                  localSet('token', res.user)
                  console.log("login success")
                  router.push("/home")
                }
              })
              .catch((err) => {
                ElMessage.error(err.toString())
                console.log(err)
              })
        } else {
          console.log('error submit!!')
          return false;
        }
      })
    }
    const resetForm = () => {
      loginForm.value.resetFields();
    }
    return {
      ...toRefs(state),
      loginForm,
      changeAutoLogin,
      submitForm,
      resetForm
    }
  },
  mounted() {
    if(localGet('autoLogin')){
      this.autoLogin=true
    }
  }
}
</script>

<style scoped>
.login-body {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  background-color: #fff;
  /* background-image: linear-gradient(25deg, #077f7c, #3aa693, #5ecfaa, #7ffac2); */
}

.login-container {
  width: 420px;
  height: 500px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 21px 41px 0 rgba(0, 0, 0, 0.2);
}

.head {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 0 20px 0;
}

.head img {
  width: 100px;
  height: 100px;
  margin-right: 20px;
}

.head .title {
  font-size: 28px;
  color: #1BAEAE;
  font-weight: bold;
}

.head .tips {
  font-size: 12px;
  color: #999;
}

.login-form {
  width: 70%;
  margin: 0 auto;
}
</style>
<style>
.el-form--label-top .el-form-item__label {
  padding: 0;
}

.login-form .el-form-item {
  margin-bottom: 12px;
}
</style>