<template>
    <div style="padding: 20px;">
      <el-card>
        <el-form :inline="true" :model="searchForm" class="demo-form-inline">
          <el-form-item label="开始时间">
            <el-date-picker
              v-model="searchForm.startTime"
              type="datetime"
              placeholder="开始时间"
              format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间">
            <el-date-picker
              v-model="searchForm.endTime"
              type="datetime"
              placeholder="结束时间"
              format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSearch">查询</el-button>
            <el-button @click="onReset">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
      <el-table :data="conversationData" style="width: 100%; margin-top: 20px;">
        <el-table-column prop="conversationId" label="会话ID" width="100"></el-table-column>
        <el-table-column prop="question" label="提问"></el-table-column>
        <el-table-column prop="answer" label="回答"></el-table-column>
        <el-table-column prop="questionTime" label="提问时间" width="200"></el-table-column>
        <el-table-column prop="answerTime" label="回答时间" width="200"></el-table-column>
      </el-table>
    </div>
  </template>
  
  <script>
  import { ref, reactive, onMounted } from 'vue'
  import axios from 'axios'
  
  export default {
    name: 'ChatConversations',
    setup() {
      const searchForm = reactive({
        startTime: '',
        endTime: ''
      });
      const conversationData = ref([]);
  
      const fetchConversations = async () => {
        try {
          const response = await axios.get('/api/chat/history/conversations', {
            params: { 
              startTime: searchForm.startTime,
              endTime: searchForm.endTime
            }
          });
          conversationData.value = response.data;
        } catch (error) {
          console.error('查询对话记录失败', error);
        }
      };
  
      const onSearch = () => {
        fetchConversations();
      };
  
      const onReset = () => {
        searchForm.startTime = '';
        searchForm.endTime = '';
        fetchConversations();
      };
  
      onMounted(() => {
        fetchConversations();
      });
  
      return {
        searchForm,
        conversationData,
        onSearch,
        onReset
      }
    }
  }
  </script>
  
  <style scoped>
  .demo-form-inline .el-form-item {
    margin-right: 20px;
  }
  </style>
  