/*
 Navicat Premium Data Transfer

 Source Server         : hugai
 Source Server Type    : MySQL
 Source Server Version : 80037
 Source Host           : localhost:3306
 Source Schema         : ts_bdth

 Target Server Type    : MySQL
 Target Server Version : 80037
 File Encoding         : 65001

 Date: 06/06/2024 16:25:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for balance_records
-- ----------------------------
DROP TABLE IF EXISTS `balance_records`;
CREATE TABLE `balance_records`  (
  `balance_records_id` int NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int NOT NULL,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `model_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `prompt_tokens` int NULL DEFAULT NULL,
  `completion_tokens` int NULL DEFAULT NULL,
  `balance` decimal(16, 9) NOT NULL,
  PRIMARY KEY (`balance_records_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 548 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_recharge
-- ----------------------------
DROP TABLE IF EXISTS `product_recharge`;
CREATE TABLE `product_recharge`  (
  `product_recharge_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `recharge_quota` decimal(16, 9) NOT NULL,
  `price` decimal(16, 9) NOT NULL,
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_recharge_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_dall_key
-- ----------------------------
DROP TABLE IF EXISTS `ts_dall_key`;
CREATE TABLE `ts_dall_key`  (
  `dall_key_id` int NOT NULL AUTO_INCREMENT,
  `open_ai_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`dall_key_id`) USING BTREE,
  INDEX `ts_dall_key_open_ai_key_index`(`open_ai_key` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_dialogue_drawing
-- ----------------------------
DROP TABLE IF EXISTS `ts_dialogue_drawing`;
CREATE TABLE `ts_dialogue_drawing`  (
  `dialogue_drawing_id` int NOT NULL AUTO_INCREMENT,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`dialogue_drawing_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_drawing_prompt
-- ----------------------------
DROP TABLE IF EXISTS `ts_drawing_prompt`;
CREATE TABLE `ts_drawing_prompt`  (
  `drawing_prompt_id` bigint NOT NULL AUTO_INCREMENT,
  `prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` enum('SD','DALL') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`drawing_prompt_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_exception
-- ----------------------------
DROP TABLE IF EXISTS `ts_exception`;
CREATE TABLE `ts_exception`  (
  `exception_id` int NOT NULL AUTO_INCREMENT,
  `server_name` enum('CHAT','AUTH','DRAWING') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `level` enum('LOW','MIDDLE','HEIGHT') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'LOW',
  `cause` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`exception_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 836787 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_generate_drawing
-- ----------------------------
DROP TABLE IF EXISTS `ts_generate_drawing`;
CREATE TABLE `ts_generate_drawing`  (
  `generate_drawing_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` bigint NOT NULL,
  `status` enum('DISUSE','PENDING','PROCESSING','SUCCEED') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'PENDING',
  `url` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` enum('SD','DALL','MJ') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'SD',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`generate_drawing_id`) USING BTREE,
  INDEX `ts_generate_drawing_status_index`(`status` ASC) USING BTREE,
  INDEX `ts_generate_drawing_type_index`(`type` ASC) USING BTREE,
  INDEX `ts_generate_drawing_user_id_index`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_gpt_key
-- ----------------------------
DROP TABLE IF EXISTS `ts_gpt_key`;
CREATE TABLE `ts_gpt_key`  (
  `gpt_key_id` int NOT NULL AUTO_INCREMENT,
  `open_ai_key` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_senior_model` tinyint(1) NOT NULL DEFAULT 0,
  `created_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`gpt_key_id`) USING BTREE,
  INDEX `ts_gpt_key_is_senior_model_index`(`is_senior_model` ASC) USING BTREE,
  INDEX `ts_gpt_key_open_ai_key_index`(`open_ai_key` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_gpt_model
-- ----------------------------
DROP TABLE IF EXISTS `ts_gpt_model`;
CREATE TABLE `ts_gpt_model`  (
  `gpt_model_id` int NOT NULL AUTO_INCREMENT,
  `model_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_senior_model` tinyint(1) NOT NULL DEFAULT 0,
  `top_p` double NOT NULL,
  `max_tokens` int NOT NULL,
  `temperature` double NOT NULL,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `prompt_price` decimal(16, 9) NULL DEFAULT 0.000000000,
  `completion_price` decimal(16, 9) NULL DEFAULT 0.000000000,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`gpt_model_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_micro_app
-- ----------------------------
DROP TABLE IF EXISTS `ts_micro_app`;
CREATE TABLE `ts_micro_app`  (
  `micro_app_id` bigint NOT NULL AUTO_INCREMENT,
  `micro_category_id` bigint NOT NULL,
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `introduce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `chinese_issue` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `english_issue` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `chinese_answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `english_answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`micro_app_id`) USING BTREE,
  INDEX `ts_micro_app_micro_category_id_index`(`micro_category_id` ASC) USING BTREE,
  INDEX `ts_micro_app_title_index`(`title` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_micro_category
-- ----------------------------
DROP TABLE IF EXISTS `ts_micro_category`;
CREATE TABLE `ts_micro_category`  (
  `micro_category_id` bigint NOT NULL AUTO_INCREMENT,
  `el_icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`micro_category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_orders
-- ----------------------------
DROP TABLE IF EXISTS `ts_orders`;
CREATE TABLE `ts_orders`  (
  `orders_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` bigint NOT NULL,
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` decimal(16, 9) NOT NULL,
  `days` bigint NULL DEFAULT 0,
  `status` enum('SUCCEED','WAIT','CANCEL') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'WAIT',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `quota` decimal(16, 9) NULL DEFAULT 0.000000000,
  `trade_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '交易标识',
  PRIMARY KEY (`orders_id`) USING BTREE,
  INDEX `ts_orders_status_index`(`status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_product_card
-- ----------------------------
DROP TABLE IF EXISTS `ts_product_card`;
CREATE TABLE `ts_product_card`  (
  `product_card_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `days` int NOT NULL,
  `price` decimal(16, 9) NOT NULL,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_card_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_redeem_code
-- ----------------------------
DROP TABLE IF EXISTS `ts_redeem_code`;
CREATE TABLE `ts_redeem_code`  (
  `redeem_code_id` bigint NOT NULL AUTO_INCREMENT,
  `redeem_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `currency` bigint NOT NULL,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`redeem_code_id`) USING BTREE,
  INDEX `ts_redeem_code_redeem_code_index`(`redeem_code` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_user
-- ----------------------------
DROP TABLE IF EXISTS `ts_user`;
CREATE TABLE `ts_user`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `open_id` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_sign_in` int NOT NULL DEFAULT 0,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` enum('USER','ADMIN') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'USER',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expiration_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `nick_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account_balance` decimal(16, 9) NULL DEFAULT 0.000000000,
  `phone_number` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `ts_user_email_index`(`email` ASC) USING BTREE,
  INDEX `ts_user_open_id_index`(`open_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
