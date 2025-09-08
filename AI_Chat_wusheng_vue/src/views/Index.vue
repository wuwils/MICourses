<template>
    <el-container>
      <el-header>
        <el-menu mode="horizontal" :default-active="$route.path">
          <el-menu-item index="/">
            <router-link to="/">AI 客服系统</router-link>
          </el-menu-item>
          <el-menu-item index="/login">
            <router-link to="/login">登录</router-link>
          </el-menu-item>
          <el-menu-item index="/register">
            <router-link to="/register">注册</router-link>
          </el-menu-item>
        </el-menu>
      </el-header>
      <el-main>
        <div class="index-content">
          <el-card class="welcome-card">
            <h1>欢迎来到 AI 客服系统</h1>
            <p>请选择以下操作：</p>
            <el-button type="primary" @click="$router.push('/login')">登录</el-button>
            <el-button type="success" @click="$router.push('/register')">注册</el-button>
          </el-card>
        </div>
      </el-main>
    </el-container>
  </template>
  
  <script>
  export default {
    name: 'Index'
  }
  </script>
  
  <style scoped>
  .index-content {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 80vh;
  }
  .welcome-card {
    padding: 2rem;
    text-align: center;
  }
  </style>
  