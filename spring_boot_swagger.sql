/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50528
Source Host           : 127.0.0.1:3306
Source Database       : spring_boot_swagger

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2020-07-09 18:35:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for system_swagger_info
-- ----------------------------
DROP TABLE IF EXISTS `system_swagger_info`;
CREATE TABLE `system_swagger_info` (
  `si_id` int(11) NOT NULL AUTO_INCREMENT,
  `si_title` varchar(50) DEFAULT NULL COMMENT '系统标题',
  `si_version` varchar(20) DEFAULT NULL COMMENT '系统版本',
  `si_description` varchar(200) DEFAULT NULL COMMENT '系统描述',
  `si_schemes` varchar(50) DEFAULT NULL COMMENT '请求模式',
  `si_serverhost` varchar(20) DEFAULT NULL COMMENT 'HostAddress',
  `si_serverport` smallint(10) DEFAULT NULL,
  `si_serverpath` varchar(100) DEFAULT NULL,
  `si_securityDefinitions` varchar(255) DEFAULT NULL COMMENT '全局变量',
  `si_contact_name` varchar(30) DEFAULT NULL,
  `si_contact_url` varchar(200) DEFAULT NULL,
  `si_contact_email` varchar(100) DEFAULT NULL,
  `si_createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`si_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='swagger 信息';

-- ----------------------------
-- Records of system_swagger_info
-- ----------------------------
INSERT INTO `system_swagger_info` VALUES ('1', 'spring-boot-api', '1.0', '聚合swagger测试平台1', '[\"http\",\"https\"]', '127.0.0.1', '8011', 'spring-boot-api', '{\"api_key\":{\"type\":\"apiKey\",\"name\":\"api_key\",\"in\":\"header\"}}', 'zhuqiaolun', 'https://github.com/zhuqiaolun/spring-boot-api', 'zhuqlchina@163.com', '2020-07-01 14:38:27');
INSERT INTO `system_swagger_info` VALUES ('2', 'spring-boot-api', '2.0', '聚合swagger测试平台2', '[\"http\",\"https\"]', '127.0.0.1', '8011', 'spring-boot-api', '{\"api_key\":{\"type\":\"apiKey\",\"name\":\"api_key\",\"in\":\"header\"}}', 'zhuqiaolun', 'https://github.com/zhuqiaolun/spring-boot-api', 'zhuqlchina@163.com', '2020-07-01 14:38:27');
INSERT INTO `system_swagger_info` VALUES ('3', 'spring-boot-api', '3.0', '聚合swagger测试平台3', '[\"http\",\"https\"]', '127.0.0.1', '8011', 'spring-boot-api', '{\"api_key\":{\"type\":\"apiKey\",\"name\":\"api_key\",\"in\":\"header\"}}', 'zhuqiaolun', 'https://github.com/zhuqiaolun/spring-boot-api', 'zhuqlchina@163.com', '2020-07-01 14:38:27');

-- ----------------------------
-- Table structure for system_swagger_tags
-- ----------------------------
DROP TABLE IF EXISTS `system_swagger_tags`;
CREATE TABLE `system_swagger_tags` (
  `st_id` int(11) NOT NULL AUTO_INCREMENT,
  `st_project` varchar(100) DEFAULT NULL COMMENT '项目名',
  `st_name` varchar(20) DEFAULT NULL COMMENT '标签名称',
  `st_description` varchar(50) DEFAULT NULL COMMENT '标签描述',
  `st_remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `st_createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`st_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='swagger 标签';

-- ----------------------------
-- Records of system_swagger_tags
-- ----------------------------
INSERT INTO `system_swagger_tags` VALUES ('1', '1', 'Test', '测试', '备注', '2020-07-01 13:43:51');
INSERT INTO `system_swagger_tags` VALUES ('2', '1', 'Auth', '认证', '备注', '2020-07-01 13:44:29');
INSERT INTO `system_swagger_tags` VALUES ('3', '2', 'User', '用户', '备注', '2020-07-01 13:44:03');
INSERT INTO `system_swagger_tags` VALUES ('5', '3', 'Weather', '天气', '备注', '2020-07-01 13:44:17');

-- ----------------------------
-- Table structure for system_swagger_url
-- ----------------------------
DROP TABLE IF EXISTS `system_swagger_url`;
CREATE TABLE `system_swagger_url` (
  `su_id` int(11) NOT NULL AUTO_INCREMENT,
  `su_project` varchar(100) DEFAULT NULL COMMENT '项目名',
  `su_tags` varchar(100) DEFAULT NULL COMMENT '标签',
  `su_url` varchar(255) DEFAULT NULL,
  `su_method` varchar(50) DEFAULT NULL COMMENT '请求类型',
  `su_summary` varchar(100) DEFAULT NULL COMMENT '标题',
  `su_description` varchar(100) DEFAULT NULL COMMENT '描述',
  `su_operationId` varchar(100) DEFAULT NULL COMMENT '标识（可写方法名称）',
  `su_consumes` varchar(255) DEFAULT '["application/json"]' COMMENT '请求参数类型',
  `su_produces` varchar(255) DEFAULT '["application/json"]' COMMENT '返回参数类型',
  `su_parameters` varchar(255) DEFAULT NULL COMMENT '参数',
  `su_responses` varchar(255) DEFAULT '{"200":{"description":"successful operation"}}' COMMENT '响应',
  `su_security` varchar(200) DEFAULT NULL COMMENT '全局api_key',
  `su_deprecated` tinyint(4) DEFAULT '0' COMMENT '是否过时',
  `su_createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`su_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='swagger 的URL 配置';

-- ----------------------------
-- Records of system_swagger_url
-- ----------------------------
INSERT INTO `system_swagger_url` VALUES ('1', '1', 'Test', 'test', 'get', '测试获取MD5值', '通过MessageDigest.getInstance(\"MD5\")执行返回结果', 'getMd5', '[\"application/json\"]', '[\"application/json\"]', null, '{\"200\":{\"description\":\"successful operation\"},\"400\":{\"description\":\"Invalid appId\"},\"404\":{\"description\":\"Page not found\"}}', '[{\"api_key\":[]}]', '0', '2020-07-01 10:16:32');
INSERT INTO `system_swagger_url` VALUES ('2', '1', 'Auth', 'auth/getToken', 'post', '获取token', '返回token以及时效', 'getToken', '[\"application/json\"]', '[\"application/json\"]', '[{\"name\":\"appId\",\"in\":\"query\",\"description\":\"The appId for login username\",\"required\":true,\"type\":\"string\"},{\"name\":\"appKey\",\"in\":\"query\",\"description\":\"The appKey for login password\",\"required\":true,\"type\":\"string\"}]', '{\"200\":{\"description\":\"successful operation\"}}', null, '0', '2020-07-01 13:38:04');
INSERT INTO `system_swagger_url` VALUES ('3', '2', 'User', 'user/getBaseUserInfo', 'post', '获取用户基础信息', '查询数据库，返回用户部分信息', 'getBaseUserInfo', '[\"application/json\"]', '[\"application/json\"]', '[{\"name\":\"token\",\"in\":\"header\",\"required\":true,\"type\":\"string\"}]', '{\"200\":{\"description\":\"successful operation\"}}', null, '0', '2020-07-01 10:50:06');
INSERT INTO `system_swagger_url` VALUES ('4', '2', 'User', 'user/getQueryUserInfo', 'post', '获取用户详细信息', '查询数据库，返回用户全部信息', 'getQueryUserInfo', '[\"application/json\"]', '[\"application/json\"]', '[{\"name\":\"token\",\"in\":\"header\",\"required\":true,\"type\":\"string\"}]', '{\"200\":{\"description\":\"successful operation\"}}', null, '0', '2020-07-01 10:55:51');
INSERT INTO `system_swagger_url` VALUES ('6', '3', 'Weather', 'weather/getToDayInfo', 'post', '获取上海浦东新区今日天气', '通过和风天气返回数据', 'getToDayInfo', '[\"application/json\"]', '[\"application/json\"]', null, '{\"200\":{\"description\":\"successful operation\"}}', '[{\"api_key\":[]}]', '0', '2020-07-01 11:02:20');
