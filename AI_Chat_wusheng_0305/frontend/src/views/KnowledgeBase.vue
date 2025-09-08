<template>
  <div style="padding: 20px;">
    <el-card>
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="问题">
          <el-input v-model="searchForm.question" placeholder="请输入问题关键词"></el-input>
        </el-form-item>
        <el-form-item label="关键字">
          <el-input v-model="searchForm.keywords" placeholder="请输入关键字"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSearch">查询</el-button>
          <el-button @click="onReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-table :data="knowledgeData" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="question" label="问题"></el-table-column>
      <el-table-column prop="answer" label="解答"></el-table-column>
      <el-table-column prop="keywords" label="关键字" width="150"></el-table-column>
      <el-table-column prop="updateTime" label="更新时间" width="200">
        <template #default="scope">
          <span>{{ formatTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'

export default {
  name: 'KnowledgeBase',
  setup() {
    const searchForm = reactive({
      question: '',
      keywords: ''
    });
    const knowledgeData = ref([]);

    const fetchKnowledge = async () => {
      try {
        let url = '/api/knowledge';
        if (searchForm.question || searchForm.keywords) {
          url = '/api/knowledge/search';
        }
        const response = await axios.get(url, { params: searchForm });
        knowledgeData.value = response.data;
      } catch (error) {
        console.error('查询知识库失败', error);
      }
    };

    const onSearch = () => {
      fetchKnowledge();
    };

    const onReset = () => {
      searchForm.question = '';
      searchForm.keywords = '';
      fetchKnowledge();
    };

    onMounted(() => {
      fetchKnowledge();
    });

    const formatTime = (time) => {
      if (!time) return '';
      return time.toString().replace('T', ' ').substring(0, 19);
    };

    return {
      searchForm,
      knowledgeData,
      onSearch,
      onReset,
      formatTime
    }
  }
}
</script>

<style scoped>
.demo-form-inline .el-form-item {
  margin-right: 20px;
}
</style>
