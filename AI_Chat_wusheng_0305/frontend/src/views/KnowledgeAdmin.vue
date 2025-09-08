<template>
    <div style="padding: 20px;">
      <el-card>
        <h3>知识库管理</h3>
        <el-form :model="form" label-width="80px">
          <el-form-item label="问题">
            <el-input v-model="form.question" placeholder="请输入问题"></el-input>
          </el-form-item>
          <el-form-item label="解答">
            <el-input v-model="form.answer" placeholder="请输入解答"></el-input>
          </el-form-item>
          <el-form-item label="关键字">
            <el-input v-model="form.keywords" placeholder="请输入关键字"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">提交</el-button>
            <el-button @click="onReset">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
      <el-table :data="knowledgeData" style="width: 100%; margin-top: 20px;">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="question" label="问题"></el-table-column>
        <el-table-column prop="answer" label="解答"></el-table-column>
        <el-table-column prop="keywords" label="关键字" width="150"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="200"></el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button size="mini" @click="onEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="onDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </template>
  
  <script>
  import { ref, reactive, onMounted } from 'vue'
  import axios from 'axios'
  import { ElMessage } from 'element-plus'
  
  export default {
    name: 'KnowledgeAdmin',
    setup() {
      const form = reactive({
        id: null,
        question: '',
        answer: '',
        keywords: ''
      });
      const knowledgeData = ref([]);
  
      const fetchKnowledge = async () => {
        try {
          const response = await axios.get('/api/knowledge');
          knowledgeData.value = response.data;
        } catch (error) {
          console.error('查询知识库失败', error);
        }
      };
  
      const onSubmit = async () => {
        if (form.id) {
          // 修改
          try {
            await axios.put(`/api/admin/knowledge/${form.id}`, form);
            ElMessage.success("更新成功");
            onReset();
            fetchKnowledge();
          } catch (error) {
            ElMessage.error("更新失败");
          }
        } else {
          // 添加
          try {
            await axios.post('/api/admin/knowledge', form);
            ElMessage.success("添加成功");
            onReset();
            fetchKnowledge();
          } catch (error) {
            ElMessage.error("添加失败");
          }
        }
      };
  
      const onReset = () => {
        form.id = null;
        form.question = '';
        form.answer = '';
        form.keywords = '';
      };
  
      const onEdit = (row) => {
        form.id = row.id;
        form.question = row.question;
        form.answer = row.answer;
        form.keywords = row.keywords;
      };
  
      const onDelete = async (id) => {
        try {
          await axios.delete(`/api/admin/knowledge/${id}`);
          ElMessage.success("删除成功");
          fetchKnowledge();
        } catch (error) {
          ElMessage.error("删除失败");
        }
      };
  
      onMounted(() => {
        fetchKnowledge();
      });
  
      return {
        form,
        knowledgeData,
        onSubmit,
        onReset,
        onEdit,
        onDelete
      }
    }
  }
  </script>
  
  <style scoped>
  .demo-form-inline .el-form-item {
    margin-right: 20px;
  }
  </style>
  