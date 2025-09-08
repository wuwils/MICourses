<template>
    <div class="login-container">
      <el-card class="login-card">
        <h3>登录</h3>
        <el-form :model="loginForm" ref="loginFormRef" label-position="top">
          <el-form-item label="邮箱">
            <el-input v-model="loginForm.email" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitLogin">登录</el-button>
            <el-button type="text" @click="goToRegister">去注册</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </template>
  
  <script>
  import { reactive, ref } from 'vue'
  import { useRouter } from 'vue-router'
  import { ElMessage } from 'element-plus'
  import axios from 'axios'
  
  export default {
    name: 'Login',
    setup() {
      const router = useRouter()
      const loginForm = reactive({
        email: '',
        password: ''
      })
      const loginFormRef = ref(null)
      const submitLogin = async () => {
        try {
          const response = await axios.post('/api/users/login', loginForm)
          // 登录成功后，跳转到 /chat 页面
          router.push('/chat')
        } catch (error) {
          ElMessage.error('登录失败：' + error.response.data)
        }
      }
      const goToRegister = () => {
        router.push('/register')
      }
      return { loginForm, loginFormRef, submitLogin, goToRegister }
    }
  }
  </script>
  
  <style scoped>
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }
  .login-card {
    width: 400px;
    padding: 2rem;
  }
  </style>
  