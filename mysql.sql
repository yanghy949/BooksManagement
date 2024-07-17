/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : book_manager

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2022-08-02 17:18:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pic` varchar(64) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pages` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `publish` varchar(255) DEFAULT NULL,
  `publish_time` datetime(6) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `translate` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('27', '20210906/1630917735912.jpg', '埃文斯', '001', 'Java程序员修炼之道', '10', '100', '中国民族大学出版社', '2021-09-06 00:00:00.000000', '20', '是', 'JAVA编程');
INSERT INTO `book` VALUES ('28', '20210906/1630918638463.jpg', '昊斯特曼', '9787121361585', 'Java核心技术：卷Ⅰ基础知识（原书第8版）', '10', '120', '清华大学出版社', '2021-09-06 00:00:00.000000', '29', '否', 'java编程');

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `ret` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES ('20', '28', '2022-05-14 08:00:00.000000', '2022-05-15 08:00:00.000000', '11', '2022-05-15 08:00:00.000000', '1');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `birthday` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `identity` int(11) DEFAULT NULL,
  `is_admin` int(11) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('4', '北京', '20210906/1630924697826.png', '1997-02-02 00:00:00.000000', '844891239@163.com', '3', '1', '超级管理员', '123456', '4', '15518166234', 'admin');
INSERT INTO `users` VALUES ('11', '上海', '20210906/1630927332654.png', '1996-04-23 00:00:00.000000', '844897894@qq.com', '0', '0', '学生', '123456', '2', '15518166124', 'stu');
