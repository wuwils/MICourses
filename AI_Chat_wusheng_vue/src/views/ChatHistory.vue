<template>
  <div style="padding: 20px;">
    <el-card>
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="会话ID">
          <el-input v-model="searchForm.conversationId" placeholder="会话ID"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="searchForm.userNickname" placeholder="用户昵称"></el-input>
        </el-form-item>
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
    <el-table :data="historyData" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="conversationId" label="会话ID" width="150"></el-table-column>
      <el-table-column prop="userNickname" label="用户昵称" width="150"></el-table-column>
      <el-table-column prop="userType" label="用户类型" width="100">
        <template #default="scope">
          <span>{{ scope.row.userType === 'user' ? '用户' : '智能客服' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="time" label="时间" width="200"></el-table-column>
      <el-table-column prop="content" label="记录"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'

export default {
  name: 'ChatHistory',
  setup() {
    const searchForm = reactive({
      conversationId: '',
      userNickname: '',
      startTime: '',
      endTime: ''
    });
    const historyData = ref([]);

    const fetchHistory = async () => {
      try {
        const response = await axios.get('/api/chat/history', {
          params: { 
            conversationId: searchForm.conversationId,
            userNickname: searchForm.userNickname,
            startTime: searchForm.startTime,
            endTime: searchForm.endTime
          }
        });
        historyData.value = response.data;
      } catch (error) {
        console.error('查询历史记录失败', error);
      }
    };

    const onSearch = () => {
      fetchHistory();
    };

    const onReset = () => {
      searchForm.conversationId = '';
      searchForm.userNickname = '';
      searchForm.startTime = '';
      searchForm.endTime = '';
      fetchHistory();
    };

    // 默认加载所有历史记录
    onMounted(() => {
      fetchHistory();
    });

    return {
      searchForm,
      historyData,
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
