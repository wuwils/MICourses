# README.md

# AI 智能客服问答系统设计与实现

> 基于 Spring Boot 的 AI 智能客服系统，集成大模型 API，支持多轮对话、账号登录、问答历史、知识库查询，采用微服务架构，具备高并发处理能力。
> 

---

## 📁 项目目录结构

本仓库包含多个项目模块，按功能和开发阶段划分：

| 文件夹 | 类型 | 说明 |
| --- | --- | --- |
| `AI_Chat_wusheng_0304/` | 项目后端 | AI 客服系统后端（Day 1）：基础架构、用户认证、对话接口 |
| `AI_Chat_wusheng_0305/` | 项目后端 | AI 客服系统后端（Day 2）：知识库查询、Redis 缓存、RabbitMQ 消息队列、微服务通信 |
| `AI_Chat_wusheng_vue/` | 项目前端 | AI 客服系统前端：基于 Vue.js 实现多轮对话界面、用户交互 |
| `springboot-basic-app/` | 课堂练习 | Spring Boot 基础练习项目，用于学习核心组件 |
| `work_wusheng/` | 课堂练习与课后作业 | 课堂练习与课后作业代码（Git、Maven、Spring Boot 基础） |
| `work_wusheng_0303/` | SpringBoot作业 | SpringBoot作业，包含前后端实现 |

---

## 🚀 项目概述

本系统是一个 **AI 智能客服问答平台**，旨在通过大模型 API 实现自然语言交互，支持企业级用户进行智能问答服务。系统采用 **前后端分离 + 微服务架构**，具备高可用、高并发、可扩展的特性。

### 核心功能

- ✅ 用户账号注册与登录（JWT 认证）
- ✅ 多轮对话管理（会话上下文保持）
- ✅ 问答历史记录存储与查询
- ✅ 知识库检索（支持关键词匹配与语义搜索）
- ✅ Redis 缓存加速高频访问数据
- ✅ RabbitMQ 消息队列解耦核心模块（如日志、通知）
- ✅ 微服务间 RPC 通信（Feign）
- ✅ 压力测试报告验证系统性能（wrk）

---

## 🏗️ 技术栈

| 类别 | 技术 |
| --- | --- |
| **后端框架** | Spring Boot, Spring Security, Spring Data JPA |
| **前端框架** | Vue.js, Element UI, |
| **数据库** | MySQL（主数据）、Redis（缓存） |
| **消息队列** | RabbitMQ |
| **微服务通信** | Feign / RESTful API |
| **AI 集成** | 大模型 API（ChatGLM ） |
| **部署与运维** | Maven, Git |
| **测试工具** | Postman |

---

## 📂 模块说明

### 1. `AI_Chat_wusheng_0304/` - 后端基础模块

- 实现用户注册、登录、JWT 认证
- 对话接口设计，调用大模型 API
- 基础数据库设计（用户表、对话记录表）
- 使用 Spring Boot 构建 RESTful API

### 2. `AI_Chat_wusheng_0305/` - 后端进阶模块

- 引入 **Redis** 缓存热门问答、用户会话
- 使用 **RabbitMQ** 异步处理日志记录、消息通知
- 实现 **知识库查询** 功能（支持模糊匹配）
- 微服务拆分：用户服务、对话服务、知识库服务
- 通过 Feign 实现服务间通信
- 提供压测脚本与报告

### 3. `AI_Chat_wusheng_vue/` - 前端模块

- 基于 Vue.js 构建响应式用户界面
- 实现多轮对话气泡展示
- 用户登录/注册页面
- 问答历史查看、知识库浏览
- 调用后端 API 完成数据交互

### 4. `springboot-basic-app/`

- Spring Boot 基础练习项目
- 包含 CRUD 示例、拦截器、异常处理等
- 用于学习和测试 Spring Boot 核心功能

### 5. `work_wusheng/` & `work_wusheng_0303/`

- 课堂练习与课后作业代码
- Git 配置、SSH 密钥生成、远程推送
- Maven 项目构建、依赖管理
- 基础 Java 与 Spring Boot 练

---

---

## **🙌 作者**

**wusheng**

GitHub: https://github.com/wuwils

邮箱: 2646728524@qq.com