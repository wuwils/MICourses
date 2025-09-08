import { defineStore } from 'pinia'

export const useChatStore = defineStore('chat', {
  state: () => ({
    messages: []
  }),
  actions: {
    addMessage(msg) {
      this.messages.push(msg)
    },
    setMessages(msgs) {
      this.messages = msgs
    },
    clearMessages() {
      this.messages = []
    }
  }
})
