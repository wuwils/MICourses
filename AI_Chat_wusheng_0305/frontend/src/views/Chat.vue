<template>
  <el-container style="height: 100vh; display: flex; flex-direction: column;">
    <el-header style="background: #409EFF; color: #fff; padding: 0 20px; display: flex; align-items: center; height: 60px;">
      <div style="font-size: 20px; flex: 1;">AI 客服系统 - 问答交流</div>
      <el-button type="text" @click="goToHistory" style="color: #fff;">聊天记录</el-button>
      <el-button type="text" @click="goToKnowledge" style="color: #fff;">知识库</el-button>
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
      <!-- 下拉框选择回答来源 -->
      <el-select v-model="answerSource" placeholder="选择回答来源" style="margin-right: 10px; width: 140px;">
        <el-option label="大模型回答" value="model"></el-option>
        <el-option label="知识库回答" value="knowledge"></el-option>
      </el-select>
      <el-button type="primary" @click="sendMessage">发送</el-button>
    </div>
  </el-container>
</template>

<script>
import { ref, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from 'axios'
import { useChatStore } from '../stores/chatStore'

export default {
  name: 'Chat',
  setup() {
    const router = useRouter()
    const chatStore = useChatStore()  // 使用全局状态管理
    const inputMessage = ref('')
    // 使用全局 store 中的 messages 数组
    const messages = chatStore.messages
    const messageContainer = ref(null)
    // 默认使用大模型回答
    const answerSource = ref('model')

    const sendMessage = async () => {
      const prompt = inputMessage.value.trim()
      if (!prompt) {
        ElMessage.warning('请输入问题内容')
        return
      }
      // 将用户提问保存到全局 store
      chatStore.addMessage({ role: 'user', content: prompt })
      inputMessage.value = ''
      
      if (answerSource.value === 'model') {
        // 调用大模型流式接口
        try {
          const response = await fetch('/api/chat/stream?prompt=' + encodeURIComponent(prompt), {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
          })
          if (!response.body) throw new Error('无响应体')
          const reader = response.body.getReader()
          const decoder = new TextDecoder('utf-8')
          let buffer = ''
          while (true) {
            const { done, value } = await reader.read()
            if (done) break
            buffer += decoder.decode(value, { stream: true })
            const parts = buffer.split('\n\n')
            buffer = parts.pop()
            parts.forEach(part => {
              const lines = part.split('\n')
              lines.forEach(line => {
                const jsonData = processSSELine(line)
                if (jsonData && jsonData.choices && jsonData.choices.length > 0) {
                  const delta = jsonData.choices[0].delta
                  if (delta && delta.content) {
                    // 更新最新的 bot 消息
                    if (messages.length > 0 && messages[messages.length - 1].role === 'bot') {
                      messages[messages.length - 1].content += delta.content
                    } else {
                      chatStore.addMessage({ role: 'bot', content: delta.content })
                    }
                  }
                }
              })
            })
          }
        } catch (error) {
          console.error(error)
          ElMessage.error('问答接口调用失败')
          chatStore.addMessage({ role: 'bot', content: '对不起，暂时无法回答您的问题' })
        } finally {
          nextTick(() => {
            if (messageContainer.value) {
              messageContainer.value.scrollTop = messageContainer.value.scrollHeight
            }
          })
        }
      } else if (answerSource.value === 'knowledge') {
        // 调用知识库匹配接口
        try {
          const response = await axios.get('/api/chat/match', { params: { prompt } })
          if (response.data && response.data.trim() !== '') {
            chatStore.addMessage({ role: 'bot', content: response.data })
          } else {
            ElMessage.info('没有匹配的知识库答案')
          }
        } catch (error) {
          console.error(error)
          ElMessage.error('知识库匹配失败')
          chatStore.addMessage({ role: 'bot', content: '对不起，暂时无法提供知识库答案' })
        } finally {
          nextTick(() => {
            if (messageContainer.value) {
              messageContainer.value.scrollTop = messageContainer.value.scrollHeight
            }
          })
        }
      }
    }

    const goToHistory = () => {
      router.push('/chat-history')
      conversations
    }

    const goToKnowledge = () => {
      router.push('/knowledge')
    }

    const logout = () => {
      router.push('/')
    }

    const processSSELine = (line) => {
      if (!line.trim() || line.trim() === '[DONE]') return null
      if (line.startsWith('data:')) {
        line = line.slice(5).trim()
      }
      try {
        return JSON.parse(line)
      } catch (e) {
        // 如果解析失败，则返回纯文本数据封装为对象格式
        return { choices: [{ delta: { content: line } }] }
      }
    }

    return {
      inputMessage,
      messages,
      sendMessage,
      messageContainer,
      goToHistory,
      goToKnowledge,
      logout,
      answerSource
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
