/*
 Navicat Premium Data Transfer

 Source Server         : MySql
 Source Server Type    : MySQL
 Source Server Version : 80037 (8.0.37)
 Source Host           : localhost:3306
 Source Schema         : strange-loop

 Target Server Type    : MySQL
 Target Server Version : 80037 (8.0.37)
 File Encoding         : 65001

 Date: 24/02/2025 13:05:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for model_ai_online
-- ----------------------------
DROP TABLE IF EXISTS `model_ai_online`;
CREATE TABLE `model_ai_online`  (
  `ai_id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `ai_api` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT ' api接口',
  `ai_token` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '认证token',
  `ai_type` int NULL DEFAULT NULL COMMENT 'ai类型(llm:1,chat:2,image:3)',
  `ai_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ai名称',
  `timeout` int NULL DEFAULT NULL COMMENT '请求超时时间（默认30秒）',
  `ai_version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ai版本',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ai_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model_ai_online
-- ----------------------------
INSERT INTO `model_ai_online` VALUES (10, 'https://api.bianxie.ai/v1/chat/completions', 'sk-qxoWrNw4JDPobNAZxkIV11rXP8cFFTtZa4kUbYthQAhxdJJq', 1, 'gpt-3.5-turbo-0125', 5000, 'gpt-3.5-turbo-0125', 8, '2025-02-13 17:21:54', NULL, NULL);

-- ----------------------------
-- Table structure for model_conversation
-- ----------------------------
DROP TABLE IF EXISTS `model_conversation`;
CREATE TABLE `model_conversation`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int NULL DEFAULT NULL COMMENT '用户编号',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '会话标题',
  `model_info_id` int NULL DEFAULT NULL COMMENT '模型id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 608768032 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model_conversation
-- ----------------------------
INSERT INTO `model_conversation` VALUES (608768027, 8, '新会话', 16);
INSERT INTO `model_conversation` VALUES (608768029, 8, '新会话', 15);
INSERT INTO `model_conversation` VALUES (608768030, 8, '新会话', 16);
INSERT INTO `model_conversation` VALUES (608768031, 8, '新会话', 15);

-- ----------------------------
-- Table structure for model_file
-- ----------------------------
DROP TABLE IF EXISTS `model_file`;
CREATE TABLE `model_file`  (
  `model_file_id` int NOT NULL AUTO_INCREMENT COMMENT '角色设定id',
  `mirostat` int NULL DEFAULT NULL COMMENT 'mirostat算法设定（0：关闭,1:启用1.0版本，2：启用2.0版本）\r\n作用：控制模型生成文本的复杂性和困惑度（Perplexity）。\r\n实际用途：当需要控制生成文本的多样性和一致性时很有用，例如生成既不太随机也不太重复的内容。',
  `mirostat_eat` double NULL DEFAULT NULL COMMENT 'mirostat算法学习速率\r\n作用：控制 Mirostat 算法的学习速率，影响模型调整 perplexity 的速度。\r\n\n值类型：浮点数（默认：0.1）。\r\n\n影响：\n\r\n较低值（如 0.05）：调整缓慢，更稳定。\r\n\n较高值（如 0.2）：响应更快，但可能导致不稳定。\n\r\n实际用途：适用于需要快速响应用户输入上下文变化的场景。',
  `mirostat_tau` double NULL DEFAULT NULL COMMENT 'mirostat算法文本一致性\r\n作用：平衡生成文本的多样性和一致性。\n\r\n值类型：浮点数（默认：5.0）。\r\n\n影响：\n较低值（如 2.0）：输出更专注和连贯。\n\r\n较高值（如 8.0）：输出更多样化，但可能失去焦点。\r\n\n实际用途：在需要生成紧密上下文相关的内容（如代码）时，可以使用较低值。',
  `num_ctx` int NULL DEFAULT NULL COMMENT '上下文长度',
  `repeat_last_n` int NULL DEFAULT NULL COMMENT '重复生成长度\r\n作用：设置防止重复生成的回溯长度，模型会检查最近的 n 个 token 是否重复。\n\r\n值类型：整数（默认：64）。\n\r\n影响：\n较低值（如 32）：适合短对话。\n\r\n较高值（如 256）：更适合长文生成。\n\r\n0：禁用重复检测。\n\r\n-1：使用整个上下文窗口（num_ctx）。\n\r\n实际用途：减少生成文本中的重复现象，尤其是长文本。\n',
  `repeat_penalty` double NULL DEFAULT NULL COMMENT '重复token惩罚力度\r\n作用：设置对重复 token 的惩罚力度。\n\r\n值类型：浮点数（默认：1.1）。\r\n\n影响：\n\r\n较高值（如 1.5）：强力惩罚重复，文本更加多样。\r\n\n较低值（如 0.9）：惩罚较弱，可能更容易重复。\r\n\n实际用途：生成丰富多样的回答时适用。',
  `temperature` double NULL DEFAULT NULL COMMENT '生成随机性\r\n作用：控制模型生成的随机性。\n\r\n值类型：浮点数（默认：0.8）。\n\r\n影响：\n\r\n较低值（如 0.5）：输出更保守，适合严谨任务。\r\n\n较高值（如 1.2）：输出更随机，适合创意内容。\n\r\n实际用途：在需要生成逻辑性较强的内容时使用低温度，创意任务中使用高温度。',
  `seed` int NULL DEFAULT NULL COMMENT '随机数种子',
  `stop` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '停止触发序列',
  `tfs_z` double NULL DEFAULT NULL COMMENT '作用：通过 Tail Free Sampling 减少不常见 token 的影响。\n\r\n值类型：浮点数（默认：1）。\n\r\n影响：\r\n\n值为 1：禁用 Tail Free Sampling。\r\n\n值越高（如 2.0）：减少低概率 token 的权重，生成更稳定内容。\n\r\n实际用途：需要平衡罕见词输出时使用。\n',
  `num_predict` int NULL DEFAULT NULL COMMENT '作用：设置生成的最大 token 数。\n\r\n值类型：整数（默认：128）。\r\n\n影响：\n\r\n较低值（如 50）：适合短回答。\r\n\n较高值（如 512）：适合长文生成。\r\n\n-1：无限生成。\n-2：填满上下文窗口。\n\r\n实际用途：控制生成长度，避免过长生成。',
  `top_p` double NULL DEFAULT NULL COMMENT '作用：控制核采样的概率阈值，只选择累计概率达到 p 的候选 token。\r\n\n值类型：浮点数（默认：0.9）。\n\r\n影响：\r\n\n较低值（如 0.5）：更保守。\r\n\n较高值（如 0.95）：更多样化。\n\r\n实际用途：需要生成多样化内容时使用。',
  `min_p` double NULL DEFAULT NULL COMMENT '当top_p不满足需求时可使用',
  `system` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '模型的系统提示',
  PRIMARY KEY (`model_file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model_file
-- ----------------------------
INSERT INTO `model_file` VALUES (1, 2, 0.1, 5, 2568, 0, 1.1, 0.5, 1734674529, '停止', 1.5, 258, 0.6, 0.6, NULL);
INSERT INTO `model_file` VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `model_file` VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `model_file` VALUES (4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `model_file` VALUES (5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `model_file` VALUES (8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `model_file` VALUES (9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `model_file` VALUES (10, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `model_file` VALUES (11, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `model_file` VALUES (12, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `model_file` VALUES (13, NULL, 123, 12333, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `model_file` VALUES (14, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `model_file` VALUES (15, NULL, 123, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for model_file_egmessage
-- ----------------------------
DROP TABLE IF EXISTS `model_file_egmessage`;
CREATE TABLE `model_file_egmessage`  (
  `message_id` int NOT NULL AUTO_INCREMENT COMMENT '信息编号',
  `user_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '用户信息 ',
  `model_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '模型回复',
  `model_file_id` int NULL DEFAULT NULL COMMENT '模型角色设定详细id',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model_file_egmessage
-- ----------------------------
INSERT INTO `model_file_egmessage` VALUES (1, '打开销售管理页面', '好的我已经打开了页面#/mes/detail#', 1);
INSERT INTO `model_file_egmessage` VALUES (2, '什么是智造领航', '智造领航系统是.......12312321', 1);
INSERT INTO `model_file_egmessage` VALUES (3, '一条测试用例回复3', '一条测试用例回复23', 2);

-- ----------------------------
-- Table structure for model_info
-- ----------------------------
DROP TABLE IF EXISTS `model_info`;
CREATE TABLE `model_info`  (
  `model_id` int NOT NULL AUTO_INCREMENT COMMENT '模型编号',
  `model_version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型名称',
  `model_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `model_file_id` int NULL DEFAULT NULL COMMENT '角色设定id',
  `disable` int NULL DEFAULT NULL COMMENT '禁用状态（0：禁用，1开启）',
  `user_id` int NULL DEFAULT NULL COMMENT '归属用户id',
  `link_type` int NULL DEFAULT NULL COMMENT '链接类型（本地：0，在线：1）',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '模型描述',
  `aiol_id` int NULL DEFAULT NULL COMMENT '在线ai id',
  `localmodel_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '本地模型名称',
  PRIMARY KEY (`model_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model_info
-- ----------------------------
INSERT INTO `model_info` VALUES (1, 'GPT_3', '小智', 1, 0, 1, 0, '就这样asdasd', 3, NULL);
INSERT INTO `model_info` VALUES (2, 'qwen2.5:7b', 'XiaoZhi', 1, 1, 1, 0, '一个由qwen2.5:7b为基底模型的ai小智', 4, 'qwen2:7b');
INSERT INTO `model_info` VALUES (9, NULL, '小智', 8, 1, 8, 0, 'AI小智是智造领航系统中的智能助手。', NULL, 'qwen2.5:7b');
INSERT INTO `model_info` VALUES (10, NULL, '测试', 9, 1, 8, 0, '123', 10, '');
INSERT INTO `model_info` VALUES (11, NULL, '测试', 10, 1, 8, 1, '123', 10, 'qwen2.5:7b');
INSERT INTO `model_info` VALUES (12, NULL, '测试', 11, 1, 8, 0, '', 10, '');
INSERT INTO `model_info` VALUES (13, NULL, '千问模型基础', 12, 1, 8, 0, '用于测试系统', NULL, 'qwen2.5:7b');
INSERT INTO `model_info` VALUES (14, NULL, 'qwen测试', 13, 1, 8, 0, '阿斯顿', NULL, 'qwen2.5:7b');
INSERT INTO `model_info` VALUES (15, NULL, '查韦斯', 14, 1, 8, 0, '阿斯顿', NULL, 'qwen2.5:7b');
INSERT INTO `model_info` VALUES (16, NULL, '在线模型测试', 15, 1, 8, 1, '', 10, '');

-- ----------------------------
-- Table structure for model_message
-- ----------------------------
DROP TABLE IF EXISTS `model_message`;
CREATE TABLE `model_message`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发送人',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '消息',
  `conversation_id` int NULL DEFAULT NULL COMMENT '会话id',
  `created_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 182 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model_message
-- ----------------------------
INSERT INTO `model_message` VALUES (1, 'user', '测试数据', 1, '2024-12-11 19:03:04');
INSERT INTO `model_message` VALUES (3, 'user', '测试数据', 7, '2025-01-23 14:58:38');
INSERT INTO `model_message` VALUES (10, NULL, NULL, NULL, NULL);
INSERT INTO `model_message` VALUES (11, 'user', '测试数据12312312312', 7, '2025-01-16 14:58:51');
INSERT INTO `model_message` VALUES (12, 'user', '测试数据12312312312', 7, '2025-01-16 14:58:51');
INSERT INTO `model_message` VALUES (14, 'assistant', '', 7, '2025-02-12 16:55:51');
INSERT INTO `model_message` VALUES (15, 'user', '123', 7, '2025-02-12 16:55:51');
INSERT INTO `model_message` VALUES (16, 'assistant', '', 5, '2025-02-16 12:49:02');
INSERT INTO `model_message` VALUES (17, 'user', '1123', 5, '2025-02-16 12:49:02');
INSERT INTO `model_message` VALUES (18, 'user', '你好', 0, '2025-02-16 17:25:05');
INSERT INTO `model_message` VALUES (19, 'assistant', '你好！有什么问题或者需要帮助的吗？', 0, '2025-02-16 17:25:05');
INSERT INTO `model_message` VALUES (20, 'user', '你好', 7, '2025-02-16 17:25:13');
INSERT INTO `model_message` VALUES (21, 'assistant', '你好！有什么问题或者需要帮助的吗？', 7, '2025-02-16 17:25:13');
INSERT INTO `model_message` VALUES (22, 'assistant', '你好！有什么可以帮助你的吗？', 7, '2025-02-16 17:25:19');
INSERT INTO `model_message` VALUES (23, 'user', '你好', 7, '2025-02-16 17:25:19');
INSERT INTO `model_message` VALUES (24, 'assistant', '我是Qwen，由阿里云开发的人工智能助手。我可以回答问题、提供信息、聊天交流等。你有什么想知道的或想聊的话题吗？', 7, '2025-02-16 17:25:25');
INSERT INTO `model_message` VALUES (25, 'user', '你是谁', 7, '2025-02-16 17:25:25');
INSERT INTO `model_message` VALUES (26, 'assistant', '我是Qwen，由阿里云开发的大型语言模型。我在这里可以帮助你回答问题、提供信息或进行各种对话。你可以问我任何问题！', 7, '2025-02-16 17:25:35');
INSERT INTO `model_message` VALUES (27, 'user', '你是谁', 7, '2025-02-16 17:25:35');
INSERT INTO `model_message` VALUES (28, 'user', '你是谁', 7, '2025-02-16 17:25:40');
INSERT INTO `model_message` VALUES (29, 'assistant', '我是Qwen，一个由阿里巴巴云开发的语言模型，旨在帮助用户解答问题、提供信息和进行各种对话任务。有什么我可以帮助你的吗？', 7, '2025-02-16 17:25:40');
INSERT INTO `model_message` VALUES (30, 'assistant', '你好！有什么可以帮到你吗？', 7, '2025-02-16 17:27:20');
INSERT INTO `model_message` VALUES (31, 'user', '你好', 7, '2025-02-16 17:27:20');
INSERT INTO `model_message` VALUES (32, 'user', '写一篇500字做完', 7, '2025-02-16 17:27:31');
INSERT INTO `model_message` VALUES (33, 'assistant', '好的，我可以帮你写一篇500字的文章。不过，请告诉我你希望这篇文章的主题是什么？例如，科技、教育、生活感悟等。如果没有特定主题，我将为你创作一篇关于“时间管理”的文章。\n\n以下是一篇关于时间管理的500字文章：\n\n---\n\n**时间管理：让每一刻都充满价值**\n\n在快节奏的现代生活中，时间成为了最宝贵的资源之一。学会有效利用时间，不仅能够提高工作效率，还能帮助我们更好地平衡工作和生活，享受更加充实的生活。时间管理不仅仅是对时间的规划与安排，更是一种生活的艺术。\n\n首先，明确目标是时间管理的第一步。设定清晰、具体的目标可以帮助', 7, '2025-02-16 17:27:31');
INSERT INTO `model_message` VALUES (34, 'user', '你好', 7, '2025-02-16 17:27:39');
INSERT INTO `model_message` VALUES (35, 'assistant', '当然，我们可以聊聊天或者做一些任务。你想先聊些什么呢？比如你最近遇到的有趣的事情，或者是需要我帮助解答的问题。如果想要我写一篇500字的文章，请告诉我具体的主题或内容要求。', 7, '2025-02-16 17:27:39');
INSERT INTO `model_message` VALUES (36, 'user', '你好', 5, '2025-02-22 16:18:22');
INSERT INTO `model_message` VALUES (37, 'assistant', '你好！有什么可以帮助你的吗？', 5, '2025-02-22 16:18:22');
INSERT INTO `model_message` VALUES (38, 'user', '你是谁', 5, '2025-02-22 16:18:30');
INSERT INTO `model_message` VALUES (39, 'assistant', '我是Qwen，由阿里云开发的AI助手。我被设计用来提供信息、回答问题和协助各种任务。有什么我可以帮助你的吗？', 5, '2025-02-22 16:18:30');
INSERT INTO `model_message` VALUES (40, 'assistant', '你好！很高兴为你提供帮助。有什么问题或需要讨论的话题吗？', 608768016, '2025-02-22 16:19:30');
INSERT INTO `model_message` VALUES (41, 'user', '你好', 608768016, '2025-02-22 16:19:30');
INSERT INTO `model_message` VALUES (42, 'user', '你好', 608768016, '2025-02-22 16:19:51');
INSERT INTO `model_message` VALUES (43, 'assistant', '有什么可以帮助你的吗？无论是问题解答、信息查询，还是聊天交流，都可以随时告诉我。', 608768016, '2025-02-22 16:19:51');
INSERT INTO `model_message` VALUES (44, 'user', '你好', 608768015, '2025-02-22 16:19:54');
INSERT INTO `model_message` VALUES (45, 'assistant', '你好！有什么问题或者需要帮助的吗？', 608768015, '2025-02-22 16:19:54');
INSERT INTO `model_message` VALUES (46, 'assistant', '', 608768023, '2025-02-22 17:21:22');
INSERT INTO `model_message` VALUES (47, 'user', '你好', 608768023, '2025-02-22 17:21:22');
INSERT INTO `model_message` VALUES (48, 'user', '你好', 608768024, '2025-02-22 17:21:39');
INSERT INTO `model_message` VALUES (49, 'assistant', '', 608768024, '2025-02-22 17:21:39');
INSERT INTO `model_message` VALUES (50, 'assistant', '', 608768028, '2025-02-22 18:02:51');
INSERT INTO `model_message` VALUES (51, 'user', '你好', 608768028, '2025-02-22 18:02:51');
INSERT INTO `model_message` VALUES (52, 'assistant', '', 608768027, '2025-02-22 18:05:28');
INSERT INTO `model_message` VALUES (53, 'user', '你好', 608768027, '2025-02-22 18:05:28');
INSERT INTO `model_message` VALUES (54, 'assistant', '', 608768027, '2025-02-22 19:56:55');
INSERT INTO `model_message` VALUES (55, 'user', '你好', 608768027, '2025-02-22 19:56:55');
INSERT INTO `model_message` VALUES (56, 'user', '你好', 608768028, '2025-02-22 19:57:01');
INSERT INTO `model_message` VALUES (57, 'assistant', '', 608768028, '2025-02-22 19:57:01');
INSERT INTO `model_message` VALUES (58, 'user', '你好', 608768028, '2025-02-22 19:57:38');
INSERT INTO `model_message` VALUES (59, 'assistant', '', 608768028, '2025-02-22 19:57:38');
INSERT INTO `model_message` VALUES (60, 'assistant', '', 608768028, '2025-02-22 19:58:42');
INSERT INTO `model_message` VALUES (61, 'user', '你好', 608768028, '2025-02-22 19:58:42');
INSERT INTO `model_message` VALUES (62, 'assistant', '', 608768028, '2025-02-22 19:59:48');
INSERT INTO `model_message` VALUES (63, 'user', '你好', 608768028, '2025-02-22 19:59:48');
INSERT INTO `model_message` VALUES (64, 'assistant', '', 608768028, '2025-02-22 20:00:08');
INSERT INTO `model_message` VALUES (65, 'user', '你好', 608768028, '2025-02-22 20:00:08');
INSERT INTO `model_message` VALUES (66, 'assistant', '', 608768028, '2025-02-22 20:00:56');
INSERT INTO `model_message` VALUES (67, 'user', '你好', 608768028, '2025-02-22 20:00:56');
INSERT INTO `model_message` VALUES (68, 'assistant', '', 608768028, '2025-02-22 20:01:45');
INSERT INTO `model_message` VALUES (69, 'user', '你好', 608768028, '2025-02-22 20:01:45');
INSERT INTO `model_message` VALUES (70, 'user', '你好', 608768028, '2025-02-22 20:03:43');
INSERT INTO `model_message` VALUES (71, 'assistant', '你好！有什么可以帮你解答或帮忙的吗？', 608768028, '2025-02-22 20:03:43');
INSERT INTO `model_message` VALUES (76, 'user', '你好', 608768027, '2025-02-22 21:58:35');
INSERT INTO `model_message` VALUES (77, 'assistant', '', 608768027, '2025-02-22 21:58:35');
INSERT INTO `model_message` VALUES (78, 'assistant', '', 608768027, '2025-02-22 22:00:45');
INSERT INTO `model_message` VALUES (79, 'user', '你好', 608768027, '2025-02-22 22:00:45');
INSERT INTO `model_message` VALUES (88, 'user', '你好', 608768027, '2025-02-22 22:04:22');
INSERT INTO `model_message` VALUES (89, 'assistant', '', 608768027, '2025-02-22 22:04:22');
INSERT INTO `model_message` VALUES (90, 'assistant', '你好！有什么可以帮助你的吗？', 608768027, '2025-02-22 22:06:28');
INSERT INTO `model_message` VALUES (91, 'user', '你好', 608768027, '2025-02-22 22:06:28');
INSERT INTO `model_message` VALUES (94, 'assistant', '您好！有什么可以帮助您的吗？', 608768027, '2025-02-22 22:07:04');
INSERT INTO `model_message` VALUES (95, 'user', '你好', 608768027, '2025-02-22 22:07:04');
INSERT INTO `model_message` VALUES (96, 'assistant', '我是一个AI助手，可以回答您的问题和提供帮助。有什么可以帮助您的吗？', 608768027, '2025-02-22 22:07:12');
INSERT INTO `model_message` VALUES (97, 'user', '你是谁', 608768027, '2025-02-22 22:07:12');
INSERT INTO `model_message` VALUES (98, 'assistant', '', 608768027, '2025-02-22 22:11:41');
INSERT INTO `model_message` VALUES (99, 'user', '你好', 608768027, '2025-02-22 22:11:41');
INSERT INTO `model_message` VALUES (102, 'assistant', '', 608768027, '2025-02-22 22:12:15');
INSERT INTO `model_message` VALUES (103, 'user', '你是谁', 608768027, '2025-02-22 22:12:15');
INSERT INTO `model_message` VALUES (106, 'assistant', '你好，请问有什么我可以帮助您的吗？', 608768027, '2025-02-22 22:12:51');
INSERT INTO `model_message` VALUES (107, 'user', '你好', 608768027, '2025-02-22 22:12:51');
INSERT INTO `model_message` VALUES (110, 'assistant', '', 608768027, '2025-02-22 22:13:15');
INSERT INTO `model_message` VALUES (111, 'user', '你好', 608768027, '2025-02-22 22:13:15');
INSERT INTO `model_message` VALUES (116, 'user', '你好', 608768027, '2025-02-22 22:22:55');
INSERT INTO `model_message` VALUES (117, 'assistant', '你好，有什么可以帮助您的呢？', 608768027, '2025-02-22 22:22:55');
INSERT INTO `model_message` VALUES (118, 'assistant', '你好！有什么可以帮助您的吗？', 608768027, '2025-02-22 22:22:59');
INSERT INTO `model_message` VALUES (119, 'user', '你好', 608768027, '2025-02-22 22:22:59');
INSERT INTO `model_message` VALUES (124, 'user', '你好', 608768029, '2025-02-22 22:28:07');
INSERT INTO `model_message` VALUES (125, 'assistant', '你好！很高兴为你服务。有什么问题或需要帮助的吗？', 608768029, '2025-02-22 22:28:07');
INSERT INTO `model_message` VALUES (126, 'assistant', '我是Qwen，由阿里云开发的人工智能模型。我被设计用来提供信息、回答任务。如果你有任何疑问或需要帮助，请随时告诉我！', 608768029, '2025-02-22 22:28:18');
INSERT INTO `model_message` VALUES (130, 'user', '你是谁', 608768027, '2025-02-22 22:31:55');
INSERT INTO `model_message` VALUES (131, 'assistant', '我是Qwen，由阿里云。我的主要任务是帮助用户回答问题、提供信息和支持各种对话需求。有什么我可以帮你的吗？', 608768027, '2025-02-22 22:31:55');
INSERT INTO `model_message` VALUES (132, 'assistant', '我是Qwen，一个由阿里云开发的AI助手。我的任务是帮助用户信息和解决各种查询。有什么我可以帮忙的吗？', 608768027, '2025-02-22 22:32:19');
INSERT INTO `model_message` VALUES (133, 'user', '你是谁', 608768027, '2025-02-22 22:32:19');
INSERT INTO `model_message` VALUES (134, 'assistant', '我是Qwen，一个由阿里巴巴云开发的大型语言模型，旨在。有什么具体的问题或者任务可以帮助你的吗？', 608768027, '2025-02-22 22:34:03');
INSERT INTO `model_message` VALUES (135, 'user', '你是谁', 608768027, '2025-02-22 22:34:03');
INSERT INTO `model_message` VALUES (136, 'user', '你是谁', 608768027, '2025-02-22 22:36:01');
INSERT INTO `model_message` VALUES (137, 'assistant', '我是一个智能助手，可以回答您的问题和提供帮助。有什么可以帮助您的吗？', 608768027, '2025-02-22 22:36:01');
INSERT INTO `model_message` VALUES (138, 'user', '写一个500字论文', 608768029, '2025-02-22 22:36:23');
INSERT INTO `model_message` VALUES (139, 'assistant', '当然可以，但我需要更多具体的信息来帮助你撰写这篇论文。. **主题/题目**：你的论文是关于什么的？\n2. **研究目的或主题范围**：你想探讨的具体问题是什么？\n3. ****：这篇论文是为学术界、专业人士还是普通读者准备的？\n4. **主要观点或论点**：你希望在结论有哪些？\n5. **参考资料**：你已经参考了哪些资料或者数据？\n\n假设我们暂时没有具体的主题信息，我将为你提供一个通用的主题“人工智能在医疗', 608768029, '2025-02-22 22:36:23');
INSERT INTO `model_message` VALUES (140, 'user', '你好', 608768029, '2025-02-23 17:58:17');
INSERT INTO `model_message` VALUES (141, 'assistant', '你好你', 608768029, '2025-02-23 17:58:17');
INSERT INTO `model_message` VALUES (142, 'user', '你是谁', 608768029, '2025-02-23 17:58:29');
INSERT INTO `model_message` VALUES (143, 'assistant', '我是智能为', 608768029, '2025-02-23 17:58:29');
INSERT INTO `model_message` VALUES (144, 'user', '你为什么说话说一半', 608768029, '2025-02-23 17:58:44');
INSERT INTO `model_message` VALUES (145, 'assistant', '抱歉如果你帮助细节论文', 608768029, '2025-02-23 17:58:44');
INSERT INTO `model_message` VALUES (146, 'user', '你好', 608768027, '2025-02-23 18:00:15');
INSERT INTO `model_message` VALUES (147, 'assistant', '你好！感谢和您交流，有什么问题我可以帮助解答吗？', 608768027, '2025-02-23 18:00:15');
INSERT INTO `model_message` VALUES (148, 'user', '你是谁', 608768027, '2025-02-23 18:00:22');
INSERT INTO `model_message` VALUES (149, 'assistant', '我是一个虚拟智能助手，可以回答您的问题和提供帮助。有什么我可以帮助您的吗？', 608768027, '2025-02-23 18:00:22');
INSERT INTO `model_message` VALUES (150, 'user', '写一个关于ai的500字作为', 608768027, '2025-02-23 18:00:46');
INSERT INTO `model_message` VALUES (151, 'assistant', '题目：人工智能的应用和发展\n\n在当今社会，人工智能( Artificial Intelligence, AI) 已经成为了技术界的焦点。AI是以仿造人类智能为目标和研究对象的一门领域，涉及机器学习、深度学习、自然语言处理等多个研究领域，被广泛应用于各行各业。从智能手机助手到自动驾驶汽车，AI技术已经深入到我们的生活中，改变了我们的生活方式和工作方式。\n\n首先，AI在医疗健康领域的应用大有可为。通过分析大量的医学数据，AI可以帮助医生做出更快更准确的诊断，提高医疗质量，节省医疗资源。此外，AI还可以辅助手术、药物研发等方面，为医疗行业带来更多的创新。\n\n其次，AI在金融领域的应用也十分广泛。金融业是一个风险大、资金流动快的行业，AI技术的引入可以帮助金融机构更好地识别风险、预测市场走势、推荐投资组合等，从而提高金融决策的效率和准确性。\n\n另外，AI还在交通运输、教育、娱乐等领域有着广泛应用。自动驾驶技术的发展，不仅可以提高交通安全，还可以缓解交通拥堵问题；智能教育系统的应用，可以根据学生的学习特点制定个性化的教学计划，提高教学效果。\n\n随着互联网技术和计算能力的不断提升，人工智能技术将会越来越普及，为各行各业带来更多的便利和创新。但是，人工智能技术的发展也带来了一些问题和挑战，比如数据隐私、安全性等问题需要不断探索和解决。\n\n在未来，人工智能领域还将继续蓬勃发展，为社会带来更多的进步和发展。我们期待AI技术在未来的应用中，能够更好地服务人类，为人类的生活带来更多的便利和幸福。', 608768027, '2025-02-23 18:00:46');
INSERT INTO `model_message` VALUES (152, 'assistant', 'Hi assist information,', 608768029, '2025-02-23 18:01:02');
INSERT INTO `model_message` VALUES (153, 'user', 'hi', 608768029, '2025-02-23 18:01:02');
INSERT INTO `model_message` VALUES (154, 'user', '你好', 608768029, '2025-02-23 18:01:15');
INSERT INTO `model_message` VALUES (155, 'assistant', '你好你可以', 608768029, '2025-02-23 18:01:15');
INSERT INTO `model_message` VALUES (156, 'user', '你好', 0, '2025-02-23 18:09:44');
INSERT INTO `model_message` VALUES (157, 'assistant', '', 0, '2025-02-23 18:09:44');
INSERT INTO `model_message` VALUES (158, 'assistant', '帮忙', 0, '2025-02-23 18:10:08');
INSERT INTO `model_message` VALUES (159, 'user', '你好', 0, '2025-02-23 18:10:08');
INSERT INTO `model_message` VALUES (160, 'user', '你好', 608768029, '2025-02-23 18:13:12');
INSERT INTO `model_message` VALUES (161, 'assistant', '你好！有什么可以帮助你的吗？你可以告诉我你需要什么样的帮助，比如你是要撰写一篇关于某个主题的论文，还是有其他问题需要解答。', 608768029, '2025-02-23 18:13:12');
INSERT INTO `model_message` VALUES (162, 'user', '你好', 608768027, '2025-02-23 18:13:24');
INSERT INTO `model_message` VALUES (163, 'assistant', '你好！如果您有任何问题或需要帮助，请随时告诉我。我会尽力为您提供支持和解答。', 608768027, '2025-02-23 18:13:24');
INSERT INTO `model_message` VALUES (164, 'user', '写500字作为', 608768027, '2025-02-23 18:13:32');
INSERT INTO `model_message` VALUES (165, 'assistant', '很抱歉，我之前提供的关于AI的500字作为已经存在。您可以再次提出一个不同的问题或者主题，让我为您提供相关内容。如果有其他问题或者需要帮助的地方，请随时告诉我。感谢理解。', 608768027, '2025-02-23 18:13:32');
INSERT INTO `model_message` VALUES (166, 'user', '写个500字作文', 608768027, '2025-02-23 18:13:54');
INSERT INTO `model_message` VALUES (167, 'assistant', '好的，我来帮你写一个500字左右的作文。请先告诉我你希望这个作文的主题是什么？例如，你可以选择自然风光、个人经历、社会现象、科技发展等主题。如果没有特定主题，我可以为你提供一个通用主题。\n\n假设我们选择“家乡的变化”作为主题，以下是一个示例作文：\n\n---\n\n**家乡的变化**\n\n每个人都有属于自己的家乡，而我的家乡——那个充满记忆与故事的地方，在过去的几十年里经历了翻天覆地的变化。小时候的记忆中，家乡的街道狭窄且拥挤，两旁是低矮的小平房和简陋的商店。每到晚上，夜幕降临后整个村庄就陷入了一片寂静之中。\n\n随着社会经济的发展和技术的进步，我的家乡也逐渐发生了改变。首先，在基础设施方面，政府投资修建了多条宽阔的道路，不仅方便了人们的出行，还极大地改善了物流运输条件。此外，一座座高楼大厦拔地而起，取代了过去的平房，不仅提升了居民的居住质量，也使得城市面貌焕然一新。\n\n其次，在教育领域，家乡也取得了显著进步。新建了许多现代化学校，配备了先进的教学设施和丰富的图书资源。孩子们可以在这里接受更加优质的教育，未来的职业发展选择也更为广阔。\n\n再者，在医疗方面，随着卫生院和医院的不断扩建和完善，医疗服务水平得到了很大提升。无论是常见的疾病还是疑难杂症，居民们都能得到及时有效的治疗，生活质量有了显著提高。\n\n最后，家乡还引入了许多新兴产业项目，包括信息技术、新能源等行业。这些项目的落地不仅创造了大量就业机会，也促进了地方经济的增长与繁荣。夜幕降临时分外璀璨的灯火再次点亮了整个城市，让这里更加繁华和生机勃勃。\n\n通过以上几个方面的变化，我们可以看到家乡正以前所未有的速度向前发展着。虽然其中仍然存在一些问题需要解决，但无疑这些进步给居民带来了实实在在的好处，并且为未来的美好前景奠定了坚实的基础。\n\n---\n\n希望这个示例能够满足你的需求。如果有其他具体要求或想要调整的主题，请随时告诉我！', 608768027, '2025-02-23 18:13:54');
INSERT INTO `model_message` VALUES (168, 'user', '你好', 608768029, '2025-02-24 12:07:43');
INSERT INTO `model_message` VALUES (169, 'assistant', '你好！很高兴见到你。请问你需要什么帮助呢？无论是写作建议、信息查询，还是其他任何事情，都可以随时告诉我。', 608768029, '2025-02-24 12:07:43');
INSERT INTO `model_message` VALUES (170, 'user', '你是谁', 608768027, '2025-02-24 12:07:57');
INSERT INTO `model_message` VALUES (171, 'assistant', '我是一个智能助手，旨在回答您的问题和提供帮助。无论您需要信息、建议还是创作，我都乐意为您服务！请问有什么我可以帮助您的吗？', 608768027, '2025-02-24 12:07:57');
INSERT INTO `model_message` VALUES (172, 'assistant', '我是一个人工智能助手，旨在回答问题和提供信息。如果你有什么想了解的，尽管问我吧！', 608768030, '2025-02-24 12:08:25');
INSERT INTO `model_message` VALUES (173, 'user', '你是谁', 608768030, '2025-02-24 12:08:25');
INSERT INTO `model_message` VALUES (174, 'user', '你好吗', 608768031, '2025-02-24 12:08:39');
INSERT INTO `model_message` VALUES (175, 'assistant', '你好！我很好，谢谢。作为一个AI助手，我没有情感，但我随时准备帮助你，有什么问题或需要帮忙的吗？', 608768031, '2025-02-24 12:08:39');
INSERT INTO `model_message` VALUES (176, 'user', '你是qwen2.5', 608768031, '2025-02-24 12:08:48');
INSERT INTO `model_message` VALUES (177, 'assistant', '我是Qwen，由阿里云开发。虽然我没有具体版本号这样的信息，但我会尽全力帮助你解答问题、提供信息或进行交流。你可以告诉我你需要什么帮助吗？', 608768031, '2025-02-24 12:08:48');
INSERT INTO `model_message` VALUES (178, 'user', '你是谁开发的', 608768030, '2025-02-24 12:08:57');
INSERT INTO `model_message` VALUES (179, 'assistant', '我是由OpenAI开发的人工智能助手，旨在提供信息和回答问题。有什么我可以帮助你的吗？', 608768030, '2025-02-24 12:08:57');
INSERT INTO `model_message` VALUES (180, 'user', '写个500字的作文 关于ai', 608768031, '2025-02-24 12:31:43');
INSERT INTO `model_message` VALUES (181, 'assistant', '当然可以！以下是一篇关于人工智能（AI）的500字作文：\n\n标题：未来已来——探索人工智能的新篇章\n\n在当今社会，科学技术日新月异，其中最为引人注目的无疑是人工智能技术。它如同一把双刃剑，在为人类带来便利的同时，也引发了诸多思考和讨论。从自动驾驶汽车到智能家居，从医疗健康辅助诊断到教育个性化推荐，AI正逐渐渗透到我们生活的方方面面。\n\n首先，人工智能的发展极大地提高了生产力效率。在工业生产中，智能机器人可以24小时不间断地工作，大大提升了工作效率；在服务业，聊天机器人能够7×24小时提供客户服务，不仅节省了人力成本，还提高了服务质量。此外，在科研领域，AI通过大数据分析和深度学习技术，帮助科学家们加速新药研发、基因测序等工作进程。\n\n然而，随着人工智能的广泛应用，人们也开始担忧其可能带来的负面影响。比如隐私泄露问题，当我们的日常行为被机器学习算法捕捉并分析时，个人信息的安全性就面临着挑战；再者就是就业市场变化，自动化程度提高可能会导致某些传统岗位消失，从而引发失业潮。此外，AI系统在决策过程中存在偏见的风险也不容忽视。\n\n尽管如此，我们仍需保持乐观态度，积极面对这些挑战。政府和企业应当加强法律法规建设，确保技术的健康发展；同时也要注重培养跨学科人才，推动技术创新与伦理规范并行发展。比如建立完善的数据保护法规、提高算法透明度等措施将有助于缓解上述问题。\n\n总之，人工智能作为21世纪最具潜力的技术之一，正引领着新一轮科技革命和产业变革。面对机遇与挑战并存的局面，每个人都应当成为技术发展的参与者和受益者。让我们携手共进，在享受科技进步带来便利的同时，共同构建更加美好的未来社会。\n\n这篇作文概述了人工智能的积极影响以及可能带来的问题，并提出了相应的解决方案。希望对你有所帮助！如果需要更深入的内容或有其他要求，请随时告诉我。', 608768031, '2025-02-24 12:31:43');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `dict` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典名称',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `dict_id` int NULL DEFAULT NULL COMMENT '字典编号',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典值',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典值名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `account` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `disable` int NULL DEFAULT NULL COMMENT '是否禁用（1：开启，0：禁用）',
  `role` int NULL DEFAULT NULL COMMENT '角色(1:管理员，0:用户)',
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (8, 'admin', '2403809502@qq.com', NULL, 1, 1, '管理员', '$2a$10$12Z/hvaMQp0zH62jQppiSui9xurkDOIBH7Yw6t.mYcmhU8uSToo9e', '2024-11-24 16:15:23', NULL);
INSERT INTO `sys_user` VALUES (9, 'testUser01', NULL, NULL, NULL, NULL, NULL, '$2a$10$/WDUHbzh/RNAomWJ.OP10u8YM5f0QCUbtq8B0qLKm5Ekj3EtYaXNe', '2024-12-11 19:09:38', NULL);
INSERT INTO `sys_user` VALUES (10, 'testUser02', NULL, NULL, NULL, NULL, NULL, '$2a$10$1lya4oiBrBy7PtKSLmJlp.RDj92ZLQPUds/BEbXBkqnBq3ER6aCUy', '2024-12-11 19:11:36', NULL);
INSERT INTO `sys_user` VALUES (11, 'testUser03', NULL, NULL, NULL, NULL, NULL, '$2a$10$sURG0PeMdaSspR.l/45XfucFQxKroiqSsZYhiq9/rjsU2rROIGsVq', '2024-12-11 19:13:14', NULL);
INSERT INTO `sys_user` VALUES (12, 'testUser04', NULL, NULL, NULL, NULL, NULL, '$2a$10$J6mrCdE6RUBewM864JiLAOCHK.4Eu6T6wAl6o.xeX0SajlpYr7ExS', '2024-12-11 19:14:05', NULL);
INSERT INTO `sys_user` VALUES (13, 'testUser05', NULL, NULL, NULL, NULL, NULL, '$2a$10$avluQvSEksZT6e4wYHxlOujLPg5pjJETy6TvS2goOwjZlMZmpMTaO', '2024-12-11 19:15:40', NULL);
INSERT INTO `sys_user` VALUES (14, 'testUser06', NULL, NULL, NULL, NULL, NULL, '$2a$10$/deBjB1mFqHu0lkIPVZglOjOLEV1CTUxKnlxqWzrgihVNn8XQbAKi', '2024-12-11 19:17:40', NULL);
INSERT INTO `sys_user` VALUES (15, 'testUser07', NULL, NULL, NULL, NULL, NULL, '$2a$10$yo8IP2U1S0LtDiLyCeeKH.JGtgQgWp52rqGx75e4Vlw99NwF1mPq.', '2024-12-11 19:18:25', NULL);
INSERT INTO `sys_user` VALUES (16, 'testUser0', NULL, NULL, NULL, NULL, NULL, '$2a$10$lf4kjOy68D19OA6czTwk9uFGOpVyMXcD/j6y9jl56fXJuQXmcpfeu', '2025-01-10 14:21:46', NULL);

SET FOREIGN_KEY_CHECKS = 1;
