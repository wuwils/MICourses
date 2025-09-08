# 3月4日 技术方案文档

> 第二天：
> 
> 
> 一、调通大模型问答接口（已完成）
> 
> 二、完成问答历史数据表设计与功能实现（已完成）
> 
> 三、完成问答缓存功能设计（已完成）
> 
> 四、完成客服问答知识库读取功能实现（已完成）
> 
> 五、使用Java8新特性、缓存中间件等技术（已完成）
> 
> 六、完成调试、测试，各个功能正常（已完成）
> 
> 七、整理技术方案（已完成）
> 

# 项目环境

- Ubuntu 20.04.6 LTS
- JDK 1.8
- Maven 3.9.9
- MySQL 8.0.41
- NPM 10.9.2
- Node 22.14.0

# 项目文件结构（待优化）

```markdown

```

# 项目功能设计

## 使用 Vue3 + Element Plus 替换  Thymeleaf + Bootstrap

- 优点：解耦前后端，提升开发效率
    - **Thymeleaf + Bootstrap**：
    - **Vue 3 + Element Plus**：
- 执行以下命令，新建一个VUE3前端项目，并安装 Element Plus，用VSCode打开

```bash

npm init vue@latest project_wusheng_vue
cd project_wusheng_vue/
npm install element-plus --save
code .
```

- 前端项目目录结构

```markdown

project_wusheng_vue
├── public
│   └── index.html // 应用入口 HTML 文件
├── src
│   ├── assets // 静态资源（图片、样式等）
│   ├── components // 公共组件
│   ├── router // 路由配置
│   │   └── index.js
│   ├── views  // 页面视图
│   │   ├── Index.vue
│   │   ├── Login.vue
│   │   ├── Register.vue
│   │   └── Chat.vue
│   ├── App.vue // 根组件
│   └── main.js
├── package.json
└── vite.config.js
```

## 优化注册逻辑（确保昵称、密码、邮箱不为空）

- 修改 User 模型类，在 对应字段上添加校验注解 @NotBlank

```java

public class User {
    private Long userId;

    @NotBlank(message = "昵称不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    private String email;
```

- 修改 UserController 注册接口，添加 @Valid 注解，并通过 BindingResult 判断验证结果。如果有错误，则返回错误信息

```java

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
```

## 调用大语言模型 ChatGLM

- ChatGLM优势
    - **中文支持能力强**：ChatGLM 针对中文进行了专门优化，能够更好地处理中文语法、词汇和语境，尤其在中文问答和对话任务中表现优异
    - **完全开源**：ChatGLM 基于 Apache 2.0 协议开源，允许免费使用、修改和分发，适合学术研究和商业应用
    - **本地部署支持**：支持在消费级显卡上本地部署（如 INT4 量化版本仅需 6GB 显存），保障数据隐私和安全性
    - **量化技术**：支持 INT4 量化，显存需求低（5.1GB），推理速度快，适合资源受限的环境
    - **多轮对话优化**：支持长上下文（ChatGLM2 支持 32K，ChatGLM3 支持 128K），在多轮对话中表现稳定

### 调用 ChatGLM 的 API 接口

- 获取 url 和key
    - 访问 [https://bigmodel.cn/dev/api/normal-model/glm-4](https://bigmodel.cn/dev/api/normal-model/glm-4)，注册登录，参考官方的接口文档，设置url，并添加一个新的 API KEY 用于测试
    - 在application-dev.yml加入配置：

```yaml

chatglm:
  api:
    url: "https://open.bigmodel.cn/api/paas/v4/chat/completions"
    key: "021d2e70ada645dfa0e8562344d62ca4.QEmZKAJIk5vM9g4e"
```

- ChatRequest 类用于封装与聊天 API 进行交互时所需的请求数据
    - model: 对话模型名称
    - messages: 用户输入和系统回复
    - temperature: 值越大，生成的文本越随机
    - top_p: 核采样参数
    - do_sample: 是否启用采样模式
    - stream: 是否启用流式响应

```java

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private double temperature;
    private double top_p;
    private Boolean do_sample;
    private Boolean stream;
```

- ChatResponse 类用于封装从聊天 API 接收到的响应数据
    - object: 响应对象的类型或名称。
    - created: 响应的时间。
    - choices: 包含索引、消息和结束原因等信息

```java

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatResponse {
    private String id;
    private String object;
    private long created;
    private List<Choice> choices;
```

- ChatController 类用 Flux 实现异步、非阻塞的数据流处理，避免阻塞主线程

```java

@GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> stream(@RequestParam String prompt) {
    return chatService.chatStream(prompt);
}

```

- ChatServiceImpl 类接收用户输入的提示信息，并通过 HTTP POST 请求发送到指定的聊天服务，以获取流式响应数据
- 使用 WebClient 发起异步 POST 请求，并将响应转换为 Flux<String>，以便处理流式数据

```java

@Override
public Flux<String> chatStream(String prompt) {
    // 构造请求 payload
    ChatRequest.Message userMessage = new ChatRequest.Message();
    userMessage.setRole("user");
    userMessage.setContent(prompt);

    ChatRequest requestPayload = new ChatRequest();
    requestPayload.setModel("glm-4-plus"); // 官方推荐模型名称
    requestPayload.setMessages(Collections.singletonList(userMessage));
    requestPayload.setTemperature(0.95);
    requestPayload.setTop_p(0.70);
    requestPayload.setDo_sample(true);
    requestPayload.setStream(true);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + apiKey);

    // 使用 takeUntil() 检测到 "[DONE]" 时结束流
    return webClient.post()
            .uri(chatUrl)
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .bodyValue(requestPayload)
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(String.class)
            .takeUntil(line -> line.trim().equals("[DONE]"));
}

```

- 在浏览器中使用 Network Tab 查看请求和响应时出现 403 错误，发现是由于前端和后端运行在不同端口，跨域请求未正确配置而被拦截
- 在 SecurityConfig 中添加一个全局 CORS 配置，允许来自前端地址（ http://localhost:5173）的请求

```java

    // 配置全局CORS，允许前端的跨域请求
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
```

- 结果展示：

![聊天页面.png](%E8%81%8A%E5%A4%A9%E9%A1%B5%E9%9D%A2.png)

### 本地轻量化部署 ChatGLM

- 使用Conda创建Python环境chatglm，安装相关依赖

```bash

conda create -n chatglm python=3.8
conda activate chatglm
sudo apt install git-lfs  // 用于下载大模型文件
git lfs install
git clone https://huggingface.co/THUDM/chatglm2-6b
pip install torch==2.0.1 torchvision==0.15.2 torchaudio==2.0.2
pip install -r requirements.txt
```

- INT4 量化版本对显存需求更低（约 5.1GB），适合轻量化部署。直接访问 Hugging Face 下载比较困难，所以使用国内的镜像源下载模型
- ModelScope 是阿里云提供的模型托管平台，支持 ChatGLM 模型
- 安装 ModelScope并下载 ChatGLM2-6B-INT4 模型。下载完成后，模型会保存在 ~/.cache/modelscope/hub/ZhipuAI/chatglm2-6b-int4

```

pip install modelscope
python
from modelscope import snapshot_download
model_dir = snapshot_download('ZhipuAI/chatglm2-6b-int4')
```

- 本地大模型部署项目目录结构

```markdown

ChatGLM-6B/
├── models/
│   └── chatglm2-6b-int4/
│				├── config.json
│				├── configuration_chatglm.py
│				├── modeling_chatglm.py
│				├── pytorch_model.bin
│				├── quantization.py
│				├── tokenization_chatglm.py
│				└── tokenizer_config.json
│
├── cli_demo.py
├── web_demo.py
├── api.py
└── requirements.txt
```

- 修改模型加载代码 api.py，配置 model 路径和 host

```python

if __name__ == '__main__':
    tokenizer = AutoTokenizer.from_pretrained("models/chatglm2-6b-int4", trust_remote_code=True)
    model = AutoModel.from_pretrained("models/chatglm2-6b-int4", trust_remote_code=True).half().cuda()
    model.eval()
    uvicorn.run(app, host='127.0.0.1', port=8000, workers=1)
```

- API 服务会监听 [http://localhost:8000](http://localhost:8000/)，并提供 /chat 接口用于与模型对话

```markdown

INFO:     Started server process [379503]
INFO:     Waiting for application startup.
INFO:     Application startup complete.
INFO:     Uvicorn running on http://127.0.0.1:8000 
```

![本地部署ChatGLM.png](%E6%9C%AC%E5%9C%B0%E9%83%A8%E7%BD%B2ChatGLM.png)

### 问答历史数据表设计与功能实现

- 问答历史数据表设计

```sql

CREATE TABLE conversation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    start_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '会话开始时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE chat_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conversation_id INT NOT NULL COMMENT '会话ID',
    user_nickname VARCHAR(64) NOT NULL COMMENT '用户昵称',
    user_type VARCHAR(20) NOT NULL COMMENT '用户类型：user 或 assistant',
    time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
    content TEXT NOT NULL COMMENT '记录内容',
    CONSTRAINT fk_conversation FOREIGN KEY (conversation_id) REFERENCES conversation(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- queryHistory 方法用于从数据库中检索聊天记录，通过多个条件（会话 ID、用户昵称、时间范围）动态筛选聊天记录，并按时间倒序返回结果

```java

    @Select("<script>" +
            "SELECT id, conversation_id as conversationId, user_nickname as userNickname, user_type as userType, time, content " +
            "FROM chat_history " +
            "WHERE 1=1 " +
            "<if test='conversationId != null'>AND conversation_id = #{conversationId} </if>" +
            "<if test='userNickname != null and userNickname != \"\"'>AND user_nickname LIKE CONCAT('%', #{userNickname}, '%') </if>" +
            "<if test='startTime != null and startTime != \"\"'>AND time &gt;= #{startTime} </if>" +
            "<if test='endTime != null and endTime != \"\"'>AND time &lt;= #{endTime} </if>" +
            "ORDER BY time DESC" +
            "</script>")
    List<ChatHistory> queryHistory(@Param("conversationId") Integer conversationId,
                                   @Param("userNickname") String userNickname,
                                   @Param("startTime") String startTime,
                                   @Param("endTime") String endTime);
```

- 结果展示：

![问答历史页面.png](%E9%97%AE%E7%AD%94%E5%8E%86%E5%8F%B2%E9%A1%B5%E9%9D%A2.png)

### 问答缓存功能设计

![Redis缓存.png](Redis%E7%BC%93%E5%AD%98.png)

### 客服问答知识库读取功能实现