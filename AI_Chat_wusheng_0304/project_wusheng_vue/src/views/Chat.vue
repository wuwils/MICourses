<template>
    <el-container style="height: 100vh; display: flex; flex-direction: column;">
      <el-header style="background: #409EFF; color: #fff; padding: 0 20px; display: flex; align-items: center; height: 60px;">
        <div style="font-size: 20px; flex: 1;">AI 客服系统 - 问答交流</div>
        <el-button type="text" @click="goToHistory" style="color: #fff;">聊天记录</el-button>
        <el-button type="text" @click="logout" style="color: #fff;">注销</el-button>
      </el-header>
      <el-main style="flex: 1; padding: 20px; overflow-y: auto;" ref="messageContainer">
        <div
          v-for="(msg, index) in messages"
          :key="index"
          :class="msg.role === 'user' ? 'user-message' : 'bot-message'"
        >
          <el-card shadow="hover" style="margin: 10px 0;">
            <div>
              <strong>{{ msg.role === 'user' ? '我' : '客服' }}：</strong>
              <span>{{ msg.content }}</span>
            </div>
          </el-card>
        </div>
      </el-main>
      <div style="padding: 10px 20px; background: #fff; border-top: 1px solid #ebeef5; display: flex; align-items: center;">
        <el-input
          v-model="inputMessage"
          placeholder="请输入您的问题"
          @keyup.enter.native="sendMessage"
          clearable
          style="flex: 1; margin-right: 10px;"
        ></el-input>
        <el-button type="primary" @click="sendMessage">发送</el-button>
      </div>
    </el-container>
  </template>
  
  <script>
  import { ref, reactive, nextTick } from 'vue'
  import { useRouter } from 'vue-router'
  import { ElMessage } from 'element-plus'
  
  export default {
    name: 'Chat',
    setup() {
      const router = useRouter()
      const inputMessage = ref('')
      const messages = reactive([])
  
      const messageContainer = ref(null)
  
      const sendMessage = async () => {
        const prompt = inputMessage.value.trim()
        if (!prompt) {
          ElMessage.warning('请输入问题内容')
          return
        }
        messages.push({ role: 'user', content: prompt })
        inputMessage.value = ''
        try {
          const response = await fetch('/api/chat/stream?prompt=' + encodeURIComponent(prompt), {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
          });
          if (!response.body) throw new Error('无响应体');
          const reader = response.body.getReader();
          const decoder = new TextDecoder('utf-8');
          let buffer = '';
          while (true) {
            const { done, value } = await reader.read();
            if (done) break;
            buffer += decoder.decode(value, { stream: true });
            const parts = buffer.split('\n\n');
            buffer = parts.pop();
            parts.forEach(part => {
              const lines = part.split('\n');
              lines.forEach(line => {
                const jsonData = processSSELine(line);
                if (jsonData && jsonData.choices && jsonData.choices.length > 0) {
                  const delta = jsonData.choices[0].delta;
                  if (delta && delta.content) {
                    if (messages.length > 0 && messages[messages.length - 1].role === 'bot') {
                      messages[messages.length - 1].content += delta.content;
                    } else {
                      messages.push({ role: 'bot', content: delta.content });
                    }
                  }
                }
              });
            });
          }
        } catch (error) {
          console.error(error);
          ElMessage.error('问答接口调用失败');
          messages.push({ role: 'bot', content: '对不起，暂时无法回答您的问题' });
        } finally {
          nextTick(() => {
            if (messageContainer.value) {
              messageContainer.value.scrollTop = messageContainer.value.scrollHeight;
            }
          });
        }
      };
  
      const goToHistory = () => {
        router.push('/chat-history');
      };
  
      const logout = () => {
        // 注销逻辑（调用后端注销接口或直接跳转）
        router.push('/');
      };
  
      // 处理 SSE 数据行
      const processSSELine = (line) => {
        if (!line.trim() || line.trim() === '[DONE]') return null;
        if (line.startsWith('data:')) {
          line = line.slice(5).trim();
        }
        try {
          return JSON.parse(line);
        } catch (e) {
          console.error("解析 SSE 行失败：", e, line);
          return null;
        }
      };
  
      return {
        inputMessage,
        messages,
        sendMessage,
        messageContainer,
        goToHistory,
        logout
      }
    }
  }
  </script>
  
  <style scoped>
  .user-message {
    text-align: right;
  }
  .bot-message {
    text-align: left;
  }
  .el-card {
    display: inline-block;
    max-width: 70%;
  }
  </style>
  