<template>
    <div class="register-container">
      <el-card class="register-card">
        <h3>注册</h3>
        <el-form :model="registerForm" ref="registerFormRef" label-position="top">
          <el-form-item label="用户名">
            <el-input v-model="registerForm.userName" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="submitRegister">注册</el-button>
          </el-form-item>
        </el-form>
        <div style="margin-top: 1rem;">
          <el-button type="text" @click="goToLogin">返回登录</el-button>
        </div>
      </el-card>
      <el-dialog
        title="注册成功"
        :visible.sync="successDialogVisible"
        width="30%"
        :before-close="handleDialogClose"
      >
        <span>注册成功！将在 {{ countdown }} 秒后自动跳转到登录页面。</span>
        <template #footer>
          <el-button @click="redirectToLogin">返回登录</el-button>
        </template>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import { reactive, ref } from 'vue'
  import { useRouter } from 'vue-router'
  import { ElMessage } from 'element-plus'
  import axios from 'axios'
  
  export default {
    name: 'Register',
    setup() {
      const router = useRouter()
      const registerForm = reactive({
        userName: '',
        password: '',
        email: ''
      })
      const registerFormRef = ref(null)
      const successDialogVisible = ref(false)
      const countdown = ref(3)
      let timer = null
  
      const submitRegister = async () => {
        try {
          const response = await axios.post('/api/users/register', registerForm)
          // 注册成功后显示对话框
          successDialogVisible.value = true
          countdown.value = 3
          timer = setInterval(() => {
            countdown.value--
            if (countdown.value <= 0) {
              clearInterval(timer)
              redirectToLogin()
            }
          }, 1000)
        } catch (error) {
          ElMessage.error('注册失败：' + error.response.data)
        }
      }
  
      const redirectToLogin = () => {
        router.push('/login')
      }
  
      const goToLogin = () => {
        router.push('/login')
      }
  
      const handleDialogClose = () => {
        clearInterval(timer)
        redirectToLogin()
      }
  
      return {
        registerForm,
        registerFormRef,
        submitRegister,
        goToLogin,
        successDialogVisible,
        countdown,
        redirectToLogin,
        handleDialogClose
      }
    }
  }
  </script>
  
  <style scoped>
  .register-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }
  .register-card {
    width: 400px;
    padding: 2rem;
  }
  </style>
  