CREATE DATABASE aisystem;

GRANT ALL PRIVILEGES oN aisystem.* To shawn@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

USE aisystem;


CREATE TABLE users (
    userId BIGINT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '加密后的密码',
    email VARCHAR(100) DEFAULT NULL COMMENT '用户邮箱',
    status TINYINT DEFAULT 1 COMMENT '账号状态：1 正常，0 禁用',
    createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


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






