# 3月5日 技术方案文档

> 一、微服务架构设计，拆分账号模块、问答模块、问答历史模块、知识库模块
> 
> 
> 1、账号模块提供用户信息查询接口，问答历史模块通过id查询用户昵称
> 
> 2、问答模块查询问答历史模块获取当前会话上下文记录
> 
> 二、完成各模块之间RPC通信接口设计与通信
> 
> 三、完成微服务版本的登录、问答、问答历史、知识库功能
> 
> 四、完成Redis、消息队列等中间件的引入
> 
> 五、完成知识库更新、多轮会话管理的方案设计与实现
> 
> 六、完成调试、测试，各个功能正常
> 
> 七、对各个模块进行压测并输出压测报告
> 
> 1、输出物:①提交的代码 ②技术方案文档&压测报告 ③导出的数据库SQL ④ 项目运行的录屏
> 2、微服务使用maven的模块管理编译
> 
> 八、整理技术方案
> 

# 项目环境

- Ubuntu 20.04.6 LTS
- JDK 1.8
- Maven 3.9.9
- MySQL 8.0.41
- NPM 10.9.2
- Node 22.14.0

# 项目文件结构

```markdown

project_wusheng_0305
├── backend
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── xiaomi
│   │   │   │           └── wusheng
│   │   │   │               ├── Application.java
│   │   │   │               ├── config
│   │   │   │               │   └── SecurityConfig.java
│   │   │   │               ├── controller
│   │   │   │               │   ├── ChatController.java
│   │   │   │               │   ├── ChatHistoryController.java
│   │   │   │               │   ├── KnowledgeBaseController.java
│   │   │   │               │   └── UserController.java
│   │   │   │               ├── dao
│   │   │   │               │   ├── ChatHistoryMapper.java
│   │   │   │               │   ├── ConversationMapper.java
│   │   │   │               │   ├── KnowledgeBaseMapper.java
│   │   │   │               │   └── UserMapper.java
│   │   │   │               ├── dto
│   │   │   │               │   └── ChatRequestDTO.java
│   │   │   │               ├── model
│   │   │   │               │   ├── ChatHistory.java
│   │   │   │               │   ├── ChatRequest.java
│   │   │   │               │   ├── ChatResponse.java
│   │   │   │               │   ├── Conversation.java
│   │   │   │               │   ├── KnowledgeBase.java
│   │   │   │               │   └── User.java
│   │   │   │               └── service
│   │   │   │                   ├── ChatHistoryService.java
│   │   │   │                   ├── ChatService.java
│   │   │   │                   ├── ConversationService.java
│   │   │   │                   ├── impl
│   │   │   │                   │   ├── ChatHistoryServiceImpl.java
│   │   │   │                   │   ├── ChatServiceImpl.java
│   │   │   │                   │   ├── ConversationServiceImpl.java
│   │   │   │                   │   ├── KnowledgeBaseServiceImpl.java
│   │   │   │                   │   ├── RedisCacheServiceImpl.java
│   │   │   │                   │   └── UserServiceImpl.java
│   │   │   │                   ├── KnowledgeBaseService.java
│   │   │   │                   ├── RedisCacheService.java
│   │   │   │                   └── UserService.java
│   │   │   └── resources
│   │   │       ├── ai_system.sql
│   │   │       ├── application-dev.yml
│   │   │       └── application.yml
├── frontend

```

# Redis缓存功能优化

- 从 streamFlux 中的每一行数据中提取有效的内容部分，去除空字符串和 [DONE] 标记之后，将所有有效的部分累积成一个完整的答案，并保存到 Redis 缓存中

```java
        // 累积完整答案后保存到 Redis
        streamFlux
                .map(raw -> {
                    String line = raw.trim();
                    if (line.startsWith("data:")) {
                        line = line.substring(5).trim();
                    }
                    if (line.equals("[DONE]")) {
                        return "";
                    }
                    try {
                        JsonNode node = mapper.readTree(line);
                        JsonNode choices = node.get("choices");
                        if (choices != null && choices.isArray() && choices.size() > 0) {
                            JsonNode delta = choices.get(0).get("delta");
                            if (delta != null) {
                                JsonNode contentNode = delta.get("content");
                                if (contentNode != null) {
                                    return contentNode.asText();
                                }
                            }
                        }
                    } catch (Exception e) {
                        return "";
                    }
                    return "";
                })
                .filter(part -> !part.isEmpty())
                .reduce("", (acc, cur) -> acc + cur)
                .map(finalAnswer -> finalAnswer.replaceAll("\\[DONE\\]", "").trim()) // 去除可能存在的 [DONE]
                .publishOn(Schedulers.boundedElastic())
                .subscribe(finalAnswer -> {
                    // 保存经过处理的纯文本答案到 Redis
                    redisCacheService.cacheAnswer(prompt, finalAnswer);
                }, error -> {
                    System.err.println("缓存问答结果失败：" + error.getMessage());
                });
```

## 结果展示

- 运行 redis-server，问答过程中实现缓存功能，使用命令 redis-cli —raw GET “chat: ”即可查看缓存具体内容，重复提问直接输出

```bash
ubuntu@rongqi1:~$ redis-cli --raw GET "chat:你好"
你好👋！我是人工智能助手智谱清言（ChatGLM），很高兴见到你，欢迎问我任何问题。

ubuntu@rongqi1:~$ redis-cli --raw GET "chat:9+2="
9 + 2 = 11

ubuntu@rongqi1:~$ redis-cli --raw GET "chat:小米集团"
小米集团（Xiaomi Corporation）是一家总部位于中国北京的跨国科技公司，成立于2010年4月6日，由雷军等八位创始人共同创立。小米以研发、生产和销售智能手机、智能硬件及提供互联网服务为主营业务，是全球领先的智能手机制造商之一。

### 主要业务板块

1. **智能手机**：小米的智能手机以其高性价比著称，拥有多个产品线，包括小米系列、Redmi系列、黑鲨游戏手机等。

2. **智能硬件**：小米生态链涵盖了智能家居、穿戴设备、生活电器等多个领域，产品包括智能电视、手环、空气净化器等。

3. **互联网服务**：包括广告服务、游戏、金融科技等，通过硬件设备连接用户，提供增值服务。

```

![Redis优化.png](Redis%E4%BC%98%E5%8C%96.png)

# 页面及功能优化

- 设计知识库数据表，包括问题描述、对应答案、关键字，并插入一些测试数据。知识库可用来回答问题，也可以查看知识库并筛选

```sql
CREATE TABLE knowledge_base (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question TEXT NOT NULL COMMENT '问题描述',
    answer TEXT NOT NULL COMMENT '对应答案',
    keywords VARCHAR(255) DEFAULT NULL COMMENT '关键字',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO knowledge_base (question, answer, keywords)
VALUES 
('什么是人工智能？', '人工智能（AI）是指模拟、延伸和扩展人类智能的理论、方法、技术及应用系统。', '人工智能, AI'),
('机器学习的原理是什么？', '机器学习通过算法从数据中学习和提取规律，用以预测或决策。', '机器学习, 数据'),
('深度学习如何工作？', '深度学习利用多层神经网络对数据进行特征提取和建模，是机器学习的一个分支。', '深度学习, 神经网络'),
('什么是自然语言处理？', '自然语言处理（NLP）涉及计算机和人类语言之间的交互，处理理解和生成自然语言。', '自然语言处理, NLP'),
('知识库的作用是什么？', '知识库用于存储结构化或非结构化的知识信息，以便快速检索和回答相关问题。', '知识库, 数据库'),
('如何设计高效的搜索系统？', '高效的搜索系统通常需要结合索引、缓存、分布式架构和算法优化来提高查询效率。', '搜索, 系统设计'),
('数据库事务的ACID原则是什么？', 'ACID原则包括原子性、一致性、隔离性和持久性，是保证数据库事务正确执行的重要特性。', '数据库, ACID'),
('Redis在缓存中的作用是什么？', 'Redis是一种高性能的内存数据库，常用于缓存、消息队列等场景，可以极大提升数据访问速度。', 'Redis, 缓存'),
('如何确保数据安全？', '数据安全可以通过加密、备份、防火墙以及访问控制等多种手段来实现。', '数据安全, 加密, 备份'),
('云计算的主要特点有哪些？', '云计算具有资源共享、弹性扩展、按需分配和高可用性等特点，能够大幅降低IT成本。', '云计算, 高可用');
```

- 知识库页面展示：

![知识库查询.png](%E7%9F%A5%E8%AF%86%E5%BA%93%E6%9F%A5%E8%AF%A2.png)

- 通过 KnowledgeBaseService.matchKnowledge(prompt) 实现模糊匹配，返回最佳匹配答案

```java
    @Override
    public String matchKnowledge(String prompt) {
        // 模糊匹配：这里简单使用 question 和 keywords 两字段进行搜索，全部转换为小写比较
        List<KnowledgeBase> list = knowledgeBaseMapper.search(prompt, prompt);
        if (list != null && !list.isEmpty()) {
            // 返回第一个匹配记录的答案（也可改为更复杂的匹配策略）
            return list.get(0).getAnswer();
        }
        return "";
    }
```

- 可以选择使用大模型（ChatGLM或知识库回答问题）

```html
      <!-- 下拉框选择回答来源 -->
      <el-select v-model="answerSource" placeholder="选择回答来源" style="margin-right: 10px; width: 140px;">
        <el-option label="大模型回答" value="model"></el-option>
        <el-option label="知识库回答" value="knowledge"></el-option>
      </el-select>
      <el-button type="primary" @click="sendMessage">发送</el-button>
```

- 在知识库内对“什么是知识库”这个问题的解答是“知识库用于存储结构化或非结构化的知识信息，以便快速检索和回答相关问题”，与调用大模型得到回复不同

![选择模型问答.png](%E9%80%89%E6%8B%A9%E6%A8%A1%E5%9E%8B%E9%97%AE%E7%AD%94.png)

- 优化问答历史页面，只显示当前用户的聊天记录
- 测试时发现每次登录用户昵称都被自动设置为”anonymousUser”，调试发现所有用户都被记录为匿名用户。修改 UserController 的登录接口，创建认证令牌

```java
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest, HttpServletRequest request) {
        User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            // 创建认证令牌，这里假设没有额外的权限，使用空集合
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            // 设置认证信息到 SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 将 SecurityContext 存入 session
            request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }
```

- 在 ChatHistoryController 中获取用户的认证信息，从而筛选出该用户的聊天记录

```java
    @GetMapping
    public List<ChatHistory> queryHistory(
            @RequestParam(required = false) String conversationId,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        // 获取当前认证用户信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        String nickname = currentUser.getUserName();

        // 只查询当前用户的聊天历史
        return chatHistoryService.queryHistory(conversationId, nickname, startTime, endTime);
    }
```

- 前端使用 Pinia 将聊天记录存储在全局状态中，这样切换页面时chat页面的聊天记录不会被清除

```jsx
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

```

# 微服务架构设计

- 微服务架构，包括账号模块、问答模块、问答历史模块、知识库模块

```markdown
services
├── ChatHistoryModule
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── xiaomi
│   │   │   │           └── chatHistory
│   │   │   │               ├── ChatHistoryApplication.java
│   │   │   │               ├── config
│   │   │   │               │   └── SecurityConfig.java
│   │   │   │               ├── controller
│   │   │   │               │   └── ChatHistoryController.java
│   │   │   │               ├── dao
│   │   │   │               │   ├── ChatHistoryMapper.java
│   │   │   │               │   └── ConversationMapper.java
│   │   │   │               ├── model
│   │   │   │               │   ├── ChatHistory.java
│   │   │   │               │   └── Conversation.java
│   │   │   │               └── service
│   │   │   │                   ├── ChatHistoryService.java
│   │   │   │                   ├── ConversationService.java
│   │   │   │                   └── impl
│   │   │   │                       ├── ChatHistoryServiceImpl.java
│   │   │   │                       └── ConversationServiceImpl.java
│   │   │   └── resources
│   │   │       └── application.yml
│   │   └── test
│   │       └── java
│   └── target
│ 
├── ChatModule
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── xiaomi
│   │   │   │           └── chat
│   │   │   │               ├── ChatApplication.java
│   │   │   │               ├── config
│   │   │   │               │   └── SecurityConfig.java
│   │   │   │               ├── controller
│   │   │   │               │   └── ChatController.java
│   │   │   │               ├── dao
│   │   │   │               ├── dto
│   │   │   │               │   └── ChatRequestDTO.java
│   │   │   │               ├── model
│   │   │   │               │   ├── ChatRequest.java
│   │   │   │               │   └── ChatResponse.java
│   │   │   │               └── service
│   │   │   │                   ├── ChatService.java
│   │   │   │                   ├── impl
│   │   │   │                   │   ├── ChatServiceImpl.java
│   │   │   │                   │   └── RedisCacheServiceImpl.java
│   │   │   │                   └── RedisCacheService.java
│   │   │   └── resources
│   │   │       └── application.yml
│   │   └── test
│   │       └── java
│   └── target
│ 
├── KnowledgeBaseModule
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── xiaomi
│   │   │   │           └── knowledgeBase
│   │   │   │               ├── config
│   │   │   │               │   └── SecurityConfig.java
│   │   │   │               ├── controller
│   │   │   │               │   └── KnowledgeBaseController.java
│   │   │   │               ├── dao
│   │   │   │               │   └── KnowledgeBaseMapper.java
│   │   │   │               ├── KnowledgeBaseApplication.java
│   │   │   │               ├── model
│   │   │   │               │   └── KnowledgeBase.java
│   │   │   │               └── service
│   │   │   │                   ├── impl
│   │   │   │                   │   └── KnowledgeBaseServiceImpl.java
│   │   │   │                   └── KnowledgeBaseService.java
│   │   │   └── resources
│   │   │       └── application.yml
│   │   └── test
│   │       └── java
│   └── target
│ 
└── UserModule
    ├── pom.xml
    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   └── com
    │   │   │       └── xiaomi
    │   │   │           └── user
    │   │   │               ├── config
    │   │   │               │   └── SecurityConfig.java
    │   │   │               ├── controller
    │   │   │               │   └── UserController.java
    │   │   │               ├── dao
    │   │   │               │   └── UserMapper.java
    │   │   │               ├── model
    │   │   │               │   └── User.java
    │   │   │               ├── service
    │   │   │               │   ├── impl
    │   │   │               │   │   └── UserServiceImpl.java
    │   │   │               │   └── UserService.java
    │   │   │               └── UserApplication.java
    │   │   └── resources
    │   │       └── application.yml
    │   └── test
    │       └── java
    └── target
  

```

# RPC通信接口设计

- 在微服务架构中，各个模块之间需要通过 **RPC（Remote Procedure Call）** 进行通信，通常有两种常见的方式：
    1. **REST API（基于 HTTP）**
    2. **gRPC（基于 HTTP/2，适用于高性能通信）**
- 为每个服务定义一个接口，利用 Feign 进行远程调用，比如

```java
@FeignClient(name = "chat-history-service")
public interface ChatHistoryServiceClient {
    
    @PostMapping("/api/chat/history")
    List<ChatHistory> queryHistory(@RequestParam(required = false) String conversationId,
                                    @RequestParam(required = false) String userNickname,
                                    @RequestParam(required = false) String startTime,
                                    @RequestParam(required = false) String endTime);
}

```

# 压测

- 在 Linux 上安装 wrk

```bash
sudo apt-get update
sudo apt-get install -y build-essential libssl-dev git
git clone https://github.com/wg/wrk.git
cd wrk
make
sudo cp wrk /usr/local/bin/
```

- `t12`：表示使用 12 个线程来发送请求。
- `c400`：表示最多维持 400 个并发连接。
- `d30s`：表示测试持续 30 秒。

```bash
wrk -t12 -c400 -d30s http://localhost:9900/api/chat/ask
```

`wrk` 的输出将包含以下内容：

- **Requests/sec**：每秒请求数，衡量系统的吞吐量。
- **Latency**：请求的平均延迟，包括最小、最大和平均响应时间。
- **Connection failures**：连接失败的次数，表明系统的错误率。
- **Transfer rate**：每秒传输的字节数，表示带宽使用情况。