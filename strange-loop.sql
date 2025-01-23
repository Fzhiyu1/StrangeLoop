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

 Date: 23/01/2025 15:01:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for model_ai_online
-- ----------------------------
DROP TABLE IF EXISTS `model_ai_online`;
CREATE TABLE `model_ai_online`  (
  `ai_id` int NOT NULL COMMENT '编号',
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model_ai_online
-- ----------------------------

-- ----------------------------
-- Table structure for model_conversation
-- ----------------------------
DROP TABLE IF EXISTS `model_conversation`;
CREATE TABLE `model_conversation`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int NULL DEFAULT NULL COMMENT '用户编号',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '会话标题',
  `ai_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ai名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 608768008 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model_conversation
-- ----------------------------
INSERT INTO `model_conversation` VALUES (1, 1, '测试数据22212121', 'qwen2.5');
INSERT INTO `model_conversation` VALUES (4, 1, '测试数据4', 'XiaoZhi');
INSERT INTO `model_conversation` VALUES (5, 1, '测试数据222', 'qwen2.5');
INSERT INTO `model_conversation` VALUES (6, 1, '测试数据22212121', 'qwen2.5');
INSERT INTO `model_conversation` VALUES (7, 1, '修改后的数据', 'qwen2.5');
INSERT INTO `model_conversation` VALUES (8, 1, '测试数据22212121121212312', 'qwen2.5');
INSERT INTO `model_conversation` VALUES (608768006, 1, '测试数据22212121121212312', 'qwen2.5');
INSERT INTO `model_conversation` VALUES (608768007, 1, '测试数据22212121121212312', 'qwen2.5');

-- ----------------------------
-- Table structure for model_file
-- ----------------------------
DROP TABLE IF EXISTS `model_file`;
CREATE TABLE `model_file`  (
  `model_file_id` int NOT NULL COMMENT '角色设定id',
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
  PRIMARY KEY (`model_file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model_file
-- ----------------------------

-- ----------------------------
-- Table structure for model_file_egmessage
-- ----------------------------
DROP TABLE IF EXISTS `model_file_egmessage`;
CREATE TABLE `model_file_egmessage`  (
  `message_id` int NOT NULL COMMENT '信息编号',
  `user_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '用户信息 ',
  `model_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '模型回复',
  `model_file_id` int NULL DEFAULT NULL COMMENT '模型角色设定详细id',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model_file_egmessage
-- ----------------------------

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
  `ai_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AI模型名称',
  `link_type` int NULL DEFAULT NULL COMMENT '链接类型（本地：0，在线：1）',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '模型描述',
  PRIMARY KEY (`model_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model_info
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model_message
-- ----------------------------
INSERT INTO `model_message` VALUES (1, '1', '测试数据', 1, '2024-12-11 19:03:04');
INSERT INTO `model_message` VALUES (2, '1', '测试数据123123', 1, '2025-01-10 14:35:59');
INSERT INTO `model_message` VALUES (3, '1', '测试数据', 7, '2025-01-23 14:58:38');
INSERT INTO `model_message` VALUES (4, '1', '测试数据2', 7, '2025-01-16 14:58:51');

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
