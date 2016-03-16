/*
Navicat MySQL Data Transfer

Source Server         : localhostDB
Source Server Version : 50527
Source Host           : 127.0.0.1:3306
Source Database       : jpa_demo

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2016-01-27 23:53:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图片编号',
  `path` varchar(500) NOT NULL DEFAULT '' COMMENT '图片路径',
  `thumbs` varchar(500) DEFAULT NULL COMMENT '图片尺寸数据',
  `attribute` varchar(50) DEFAULT NULL,
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1569 DEFAULT CHARSET=utf8 COMMENT='图片表';

-- ----------------------------
-- Records of image
-- ----------------------------
INSERT INTO `image` VALUES ('1429', 'images/2015/8/1439539842204.jpg', '{\"480x800\":\"images/2015/8/1439539842204_480x800.jpg\"}', '{\"width\":720,\"height\":1280}', '2015-08-14 16:10:43');
INSERT INTO `image` VALUES ('1430', 'images/2015/8/1439540354318.jpg', '{\"480x800\":\"images/2015/8/1439540354318_480x800.jpg\"}', '{\"width\":720,\"height\":1280}', '2015-08-14 16:19:15');
INSERT INTO `image` VALUES ('1431', 'images/2015/8/1439540892175.jpg', '{\"480x800\":\"images/2015/8/1439540892175_480x800.jpg\"}', '{\"width\":720,\"height\":1280}', '2015-08-14 16:28:13');
INSERT INTO `image` VALUES ('1432', 'images/2015/8/1439541595477.jpg', '{\"480x800\":\"images/2015/8/1439541595477_480x800.jpg\"}', '{\"width\":720,\"height\":1280}', '2015-08-14 16:39:56');
INSERT INTO `image` VALUES ('1433', 'images/2015/8/1439541917875.jpg', '{\"480x800\":\"images/2015/8/1439541917875_480x800.jpg\"}', '{\"width\":720,\"height\":1280}', '2015-08-14 16:45:18');
INSERT INTO `image` VALUES ('1434', 'images/2015/8/1439542460075.jpg', '{\"480x800\":\"images/2015/8/1439542460075_480x800.jpg\"}', '{\"width\":720,\"height\":1280}', '2015-08-14 16:54:20');
INSERT INTO `image` VALUES ('1435', 'images/2015/8/1439543512619.jpg', '{\"480x800\":\"images/2015/8/1439543512619_480x800.jpg\"}', '{\"width\":720,\"height\":1280}', '2015-08-14 17:11:53');
INSERT INTO `image` VALUES ('1436', 'images/2015/8/1439892293554_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-18 18:04:55');
INSERT INTO `image` VALUES ('1437', 'images/2015/8/1439892294903_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-18 18:04:55');
INSERT INTO `image` VALUES ('1438', 'images/2015/8/1440055637049_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-20 15:27:19');
INSERT INTO `image` VALUES ('1439', 'images/2015/8/1440055638694_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-20 15:27:19');
INSERT INTO `image` VALUES ('1440', 'images/2015/8/1440063341756_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-20 17:35:42');
INSERT INTO `image` VALUES ('1441', 'images/2015/8/1440063342931_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-20 17:35:43');
INSERT INTO `image` VALUES ('1442', 'images/2015/8/1440063473864_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-20 17:37:54');
INSERT INTO `image` VALUES ('1443', 'images/2015/8/1440063474081_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-20 17:37:54');
INSERT INTO `image` VALUES ('1444', 'images/2015/8/1440064463111_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-20 17:54:24');
INSERT INTO `image` VALUES ('1445', 'images/2015/8/1440064464060_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-20 17:54:24');
INSERT INTO `image` VALUES ('1446', 'images/2015/8/1440064603899_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-20 17:56:44');
INSERT INTO `image` VALUES ('1447', 'images/2015/8/1440064604111_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-20 17:56:44');
INSERT INTO `image` VALUES ('1448', 'images/2015/8/1440123376689_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 10:16:19');
INSERT INTO `image` VALUES ('1449', 'images/2015/8/1440123723791_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 10:22:04');
INSERT INTO `image` VALUES ('1450', 'images/2015/8/1440125622928_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 10:53:43');
INSERT INTO `image` VALUES ('1451', 'images/2015/8/1440125623220_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 10:53:44');
INSERT INTO `image` VALUES ('1452', 'images/2015/8/1440125790664_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 10:56:31');
INSERT INTO `image` VALUES ('1453', 'images/2015/8/1440125824783_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 10:57:05');
INSERT INTO `image` VALUES ('1454', 'images/2015/8/1440125825118_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 10:57:05');
INSERT INTO `image` VALUES ('1455', 'images/2015/8/1440125998851_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 10:59:59');
INSERT INTO `image` VALUES ('1456', 'images/2015/8/1440126560118_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:09:21');
INSERT INTO `image` VALUES ('1457', 'images/2015/8/1440126560617_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:09:21');
INSERT INTO `image` VALUES ('1458', 'images/2015/8/1440126590016_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:09:50');
INSERT INTO `image` VALUES ('1459', 'images/2015/8/1440127054717_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:17:36');
INSERT INTO `image` VALUES ('1460', 'images/2015/8/1440127145729_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:19:06');
INSERT INTO `image` VALUES ('1461', 'images/2015/8/1440127787160_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:29:49');
INSERT INTO `image` VALUES ('1462', 'images/2015/8/1440127788742_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:29:49');
INSERT INTO `image` VALUES ('1463', 'images/2015/8/1440127831648_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:30:32');
INSERT INTO `image` VALUES ('1464', 'images/2015/8/1440127832107_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:30:32');
INSERT INTO `image` VALUES ('1465', 'images/2015/8/1440128508270_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:41:49');
INSERT INTO `image` VALUES ('1466', 'images/2015/8/1440128508980_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:41:49');
INSERT INTO `image` VALUES ('1467', 'images/2015/8/1440128940700_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:49:01');
INSERT INTO `image` VALUES ('1468', 'images/2015/8/1440128941208_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:49:02');
INSERT INTO `image` VALUES ('1469', 'images/2015/8/1440129065518_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:51:06');
INSERT INTO `image` VALUES ('1470', 'images/2015/8/1440129065852_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:51:06');
INSERT INTO `image` VALUES ('1471', 'images/2015/8/1440129126005_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:52:06');
INSERT INTO `image` VALUES ('1472', 'images/2015/8/1440129131949_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:52:12');
INSERT INTO `image` VALUES ('1473', 'images/2015/8/1440129209894_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:53:30');
INSERT INTO `image` VALUES ('1474', 'images/2015/8/1440129217424_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:53:38');
INSERT INTO `image` VALUES ('1475', 'images/2015/8/1440129587232_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:59:48');
INSERT INTO `image` VALUES ('1476', 'images/2015/8/1440129587626_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 11:59:48');
INSERT INTO `image` VALUES ('1477', 'images/2015/8/1440129629126_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 12:00:29');
INSERT INTO `image` VALUES ('1478', 'images/2015/8/1440129629456_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 12:00:30');
INSERT INTO `image` VALUES ('1479', 'images/2015/8/1440129674734_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 12:01:15');
INSERT INTO `image` VALUES ('1480', 'images/2015/8/1440129675062_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 12:01:15');
INSERT INTO `image` VALUES ('1481', 'images/2015/8/1440129760155_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 12:02:40');
INSERT INTO `image` VALUES ('1482', 'images/2015/8/1440137019048_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:03:40');
INSERT INTO `image` VALUES ('1483', 'images/2015/8/1440137065621_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:04:26');
INSERT INTO `image` VALUES ('1484', 'images/2015/8/1440137142401_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:05:43');
INSERT INTO `image` VALUES ('1485', 'images/2015/8/1440137142623_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:05:43');
INSERT INTO `image` VALUES ('1486', 'images/2015/8/1440137283568_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:08:04');
INSERT INTO `image` VALUES ('1487', 'images/2015/8/1440138018439_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:20:19');
INSERT INTO `image` VALUES ('1488', 'images/2015/8/1440138019399_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:20:20');
INSERT INTO `image` VALUES ('1489', 'images/2015/8/1440138067885_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:21:08');
INSERT INTO `image` VALUES ('1490', 'images/2015/8/1440138068119_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:21:08');
INSERT INTO `image` VALUES ('1491', 'images/2015/8/1440138068307_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:21:09');
INSERT INTO `image` VALUES ('1492', 'images/2015/8/1440138068549_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:21:09');
INSERT INTO `image` VALUES ('1493', 'images/2015/8/1440138390255_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:26:30');
INSERT INTO `image` VALUES ('1494', 'images/2015/8/1440138871877_compact.jpg', null, '{\"width\":1024,\"height\":768}', '2015-08-21 14:34:33');
INSERT INTO `image` VALUES ('1496', 'images/2015/8/1440485482782.jpg', null, '{\"width\":662,\"height\":580}', '2015-08-25 14:51:23');
INSERT INTO `image` VALUES ('1497', 'images/2015/8/1440485483479_compact.jpg', null, '{\"width\":1280,\"height\":960}', '2015-08-25 14:51:25');
INSERT INTO `image` VALUES ('1498', 'images/2015/9/1441178293489.jpg', '{\"480x800\":\"images/2015/9/1441178293489_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-02 15:18:15');
INSERT INTO `image` VALUES ('1499', 'images/2015/9/1441178545034.jpg', '{\"480x800\":\"images/2015/9/1441178545034_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-02 15:22:25');
INSERT INTO `image` VALUES ('1500', 'images/2015/9/1441180928635.jpg', '{\"480x800\":\"images/2015/9/1441180928635_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-02 16:02:09');
INSERT INTO `image` VALUES ('1501', 'images/2015/9/1441180992528.jpg', '{\"480x800\":\"images/2015/9/1441180992528_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-02 16:03:13');
INSERT INTO `image` VALUES ('1502', 'images/2015/9/1441181518449.jpg', '{\"480x800\":\"images/2015/9/1441181518449_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-02 16:11:59');
INSERT INTO `image` VALUES ('1503', 'images/2015/9/1441181564642.jpg', '{\"480x800\":\"images/2015/9/1441181564642_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-02 16:12:45');
INSERT INTO `image` VALUES ('1504', 'images/2015/9/1441181709835.jpg', '{\"480x800\":\"images/2015/9/1441181709835_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-02 16:15:10');
INSERT INTO `image` VALUES ('1505', 'images/2015/9/1441181721775.jpg', '{\"480x800\":\"images/2015/9/1441181721775_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-02 16:15:22');
INSERT INTO `image` VALUES ('1506', 'images/2015/9/1441182062698.jpg', '{\"480x800\":\"images/2015/9/1441182062698_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-02 16:21:03');
INSERT INTO `image` VALUES ('1507', 'images/2015/9/1441619133161.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-07 17:45:34');
INSERT INTO `image` VALUES ('1508', 'images/2015/9/1441619133808.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-07 17:45:34');
INSERT INTO `image` VALUES ('1509', 'images/2015/9/1441619133876_compact.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-07 17:45:35');
INSERT INTO `image` VALUES ('1510', 'images/2015/9/1441619135232.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-07 17:45:35');
INSERT INTO `image` VALUES ('1511', 'images/2015/9/1441704373551.jpg', '{\"480x800\":\"images/2015/9/1441704373551_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-08 17:26:15');
INSERT INTO `image` VALUES ('1512', 'images/2015/9/1441704639067.jpg', '{\"480x800\":\"images/2015/9/1441704639067_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-08 17:30:40');
INSERT INTO `image` VALUES ('1513', 'images/2015/9/1441704650710.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-08 17:30:51');
INSERT INTO `image` VALUES ('1514', 'images/2015/9/1441704650793.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-08 17:30:51');
INSERT INTO `image` VALUES ('1515', 'images/2015/9/1441704650909.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-08 17:30:51');
INSERT INTO `image` VALUES ('1516', 'images/2015/9/1441793927994.jpg', '{\"480x800\":\"images/2015/9/1441793927994_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-09 18:18:49');
INSERT INTO `image` VALUES ('1517', 'images/2015/9/1441794295331.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:24:56');
INSERT INTO `image` VALUES ('1518', 'images/2015/9/1441794295586.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:24:56');
INSERT INTO `image` VALUES ('1519', 'images/2015/9/1441794524988.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:28:45');
INSERT INTO `image` VALUES ('1520', 'images/2015/9/1441794525051_compact.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:28:46');
INSERT INTO `image` VALUES ('1521', 'images/2015/9/1441794635597.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:30:36');
INSERT INTO `image` VALUES ('1522', 'images/2015/9/1441794635896.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:30:36');
INSERT INTO `image` VALUES ('1523', 'images/2015/9/1441794666920.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:31:07');
INSERT INTO `image` VALUES ('1524', 'images/2015/9/1441794666990.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:31:07');
INSERT INTO `image` VALUES ('1525', 'images/2015/9/1441794717168.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:31:57');
INSERT INTO `image` VALUES ('1526', 'images/2015/9/1441794717262.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:31:57');
INSERT INTO `image` VALUES ('1527', 'images/2015/9/1441794775875.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:32:56');
INSERT INTO `image` VALUES ('1528', 'images/2015/9/1441794775929.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:32:56');
INSERT INTO `image` VALUES ('1530', 'images/2015/9/1441794808419.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:33:28');
INSERT INTO `image` VALUES ('1531', 'images/2015/9/1441794917152.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:35:17');
INSERT INTO `image` VALUES ('1532', 'images/2015/9/1441794917218.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-09 18:35:17');
INSERT INTO `image` VALUES ('1533', 'images/2015/9/1441794929352.jpg', '{\"480x800\":\"images/2015/9/1441794929352_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-09 18:35:30');
INSERT INTO `image` VALUES ('1534', 'images/2015/9/1441794936533.jpg', '{\"480x800\":\"images/2015/9/1441794936533_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-09 18:35:37');
INSERT INTO `image` VALUES ('1535', 'images/2015/9/1441795053695.jpg', '{\"480x800\":\"images/2015/9/1441795053695_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-09 18:37:34');
INSERT INTO `image` VALUES ('1536', 'images/2015/9/1441795066134.jpg', '{\"480x800\":\"images/2015/9/1441795066134_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-09 18:37:46');
INSERT INTO `image` VALUES ('1537', 'images/2015/9/1441874721770.jpg', '{\"480x800\":\"images/2015/9/1441874721770_480x800.jpg\"}', '{\"width\":146,\"height\":220}', '2015-09-10 16:45:22');
INSERT INTO `image` VALUES ('1538', 'images/2015/9/1441874744043.jpg', null, '{\"width\":146,\"height\":220}', '2015-09-10 16:45:44');
INSERT INTO `image` VALUES ('1539', 'images/2015/9/1441881931909.jpg', '{\"480x800\":\"images/2015/9/1441881931909_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-10 18:45:33');
INSERT INTO `image` VALUES ('1540', 'images/2015/9/1441881966534.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-10 18:46:07');
INSERT INTO `image` VALUES ('1541', 'images/2015/9/1441881966677.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-10 18:46:07');
INSERT INTO `image` VALUES ('1542', 'images/2015/9/1441882106769.jpg', '{\"480x800\":\"images/2015/9/1441882106769_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-10 18:48:27');
INSERT INTO `image` VALUES ('1543', 'images/2015/9/1441882118682.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-10 18:48:39');
INSERT INTO `image` VALUES ('1544', 'images/2015/9/1441882118766.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-10 18:48:39');
INSERT INTO `image` VALUES ('1545', 'images/2015/9/1441882207373.jpg', '{\"480x800\":\"images/2015/9/1441882207373_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-10 18:50:08');
INSERT INTO `image` VALUES ('1546', 'images/2015/9/1441882222620.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-10 18:50:23');
INSERT INTO `image` VALUES ('1547', 'images/2015/9/1441882222716.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-10 18:50:23');
INSERT INTO `image` VALUES ('1548', 'images/2015/9/1441882245517.jpg', '{\"480x800\":\"images/2015/9/1441882245517_480x800.jpg\"}', '{\"width\":1440,\"height\":1280}', '2015-09-10 18:50:46');
INSERT INTO `image` VALUES ('1549', 'images/2015/9/1441882258385.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-10 18:50:58');
INSERT INTO `image` VALUES ('1550', 'images/2015/9/1441882258481.jpg', null, '{\"width\":1440,\"height\":1280}', '2015-09-10 18:50:59');
INSERT INTO `image` VALUES ('1551', 'images/2015/9/1441882891687.jpg', '{\"480x800\":\"images/2015/9/1441882891687_480x800.jpg\"}', '{\"width\":303,\"height\":359}', '2015-09-10 19:01:32');
INSERT INTO `image` VALUES ('1552', 'images/2015/9/1441882937302.jpg', '{\"480x800\":\"images/2015/9/1441882937302_480x800.jpg\"}', '{\"width\":303,\"height\":359}', '2015-09-10 19:02:17');
INSERT INTO `image` VALUES ('1553', 'images/2015/9/1443518603848.jpg', '{\"480x800\":\"images/2015/9/1443518603848_480x800.jpg\"}', '{\"width\":750,\"height\":750}', '2015-09-29 17:23:25');
INSERT INTO `image` VALUES ('1554', 'images/2015/10/1444890919860.jpg', '{\"480x800\":\"images/2015/10/1444890919860_480x800.jpg\"}', '{\"width\":750,\"height\":750}', '2015-10-15 14:35:21');
INSERT INTO `image` VALUES ('1555', 'images/2015/10/1444890969009.jpg', null, '{\"width\":750,\"height\":750}', '2015-10-15 14:36:09');
INSERT INTO `image` VALUES ('1556', 'images/2015/10/1444890969035.jpg', null, '{\"width\":750,\"height\":750}', '2015-10-15 14:36:09');
INSERT INTO `image` VALUES ('1557', 'images/2015/10/1444890969066.jpg', null, '{\"width\":800,\"height\":800}', '2015-10-15 14:36:09');
INSERT INTO `image` VALUES ('1558', 'images/2015/10/1444892662823.jpg', null, '{\"width\":750,\"height\":750}', '2015-10-15 15:04:23');
INSERT INTO `image` VALUES ('1559', 'images/2015/10/1444892663114.jpg', null, '{\"width\":750,\"height\":750}', '2015-10-15 15:04:23');
INSERT INTO `image` VALUES ('1560', 'images/2015/10/1444892663220.jpg', null, '{\"width\":800,\"height\":800}', '2015-10-15 15:04:23');
INSERT INTO `image` VALUES ('1561', 'images/2015/10/1444892667664.jpg', '{\"480x800\":\"images/2015/10/1444892667664_480x800.jpg\"}', '{\"width\":750,\"height\":750}', '2015-10-15 15:04:28');
INSERT INTO `image` VALUES ('1562', 'images/2015/10/1444892905223.jpg', '{\"480x800\":\"images/2015/10/1444892905223_480x800.jpg\"}', '{\"width\":750,\"height\":750}', '2015-10-15 15:08:25');
INSERT INTO `image` VALUES ('1563', 'images/2015/10/1444892931497.jpg', null, '{\"width\":350,\"height\":263}', '2015-10-15 15:08:52');
INSERT INTO `image` VALUES ('1564', 'images/2015/10/1444892931517.jpg', null, '{\"width\":266,\"height\":220}', '2015-10-15 15:08:52');
INSERT INTO `image` VALUES ('1565', 'images/2015/10/1444892931555.jpg', null, '{\"width\":750,\"height\":750}', '2015-10-15 15:08:52');
INSERT INTO `image` VALUES ('1566', 'images/2015/10/1445224183382.jpg', null, '{\"width\":610,\"height\":699}', '2015-10-19 11:09:44');
INSERT INTO `image` VALUES ('1567', 'images/2015/10/1445224184482.jpg', null, '{\"width\":610,\"height\":638}', '2015-10-19 11:09:45');
INSERT INTO `image` VALUES ('1568', 'images/2015/10/1445224184592.jpg', null, '{\"width\":610,\"height\":594}', '2015-10-19 11:09:45');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `type` varchar(255) NOT NULL COMMENT '类型(globle总后台，business商家后台)',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('1', 'admin', 'E10ADC3949BA59ABBE56E057F20F883E', 'GLOBLE', '2015-08-11 10:11:43', '2015-08-11 10:11:45');
INSERT INTO `member` VALUES ('4', 'wangbin', 'C33367701511B4F6020EC61DED352059', 'BUSINESS', '2015-08-12 10:14:13', '2015-08-12 10:14:13');
INSERT INTO `member` VALUES ('5', 'wangbin1', 'E10ADC3949BA59ABBE56E057F20F883E', 'BUSINESS', '2015-09-10 18:40:50', '2015-09-10 18:40:50');
INSERT INTO `member` VALUES ('6', 'wangbin2', 'E10ADC3949BA59ABBE56E057F20F883E', 'BUSINESS', '2015-09-10 18:43:38', '2015-09-10 18:43:38');
INSERT INTO `member` VALUES ('7', 'wangbin3', 'E10ADC3949BA59ABBE56E057F20F883E', 'BUSINESS', '2015-09-10 18:43:46', '2015-09-10 18:43:46');
INSERT INTO `member` VALUES ('8', 'wangbin4', 'E10ADC3949BA59ABBE56E057F20F883E', 'BUSINESS', '2015-09-10 18:43:55', '2015-09-10 18:43:55');
INSERT INTO `member` VALUES ('9', 'wangbin7', 'E10ADC3949BA59ABBE56E057F20F883E', 'BUSINESS', '2015-09-13 17:26:42', '2015-09-13 17:26:42');
INSERT INTO `member` VALUES ('13', 'wangb', 'E10ADC3949BA59ABBE56E057F20F883E', 'BUSINESS', '2015-10-14 14:23:11', '2015-10-14 14:23:11');
INSERT INTO `member` VALUES ('16', 'wangb', 'E10ADC3949BA59ABBE56E057F20F883E', 'BUSINESS', '2015-10-15 10:04:43', '2015-10-15 10:04:43');
INSERT INTO `member` VALUES ('17', 'wangb1', 'E10ADC3949BA59ABBE56E057F20F883E', 'BUSINESS', '2015-10-15 10:04:43', '2015-10-15 10:04:43');
INSERT INTO `member` VALUES ('18', 'wangb2', 'E10ADC3949BA59ABBE56E057F20F883E', 'BUSINESS', '2015-10-15 10:04:43', '2015-10-15 10:04:43');
INSERT INTO `member` VALUES ('19', 'wangb', 'E10ADC3949BA59ABBE56E057F20F883E', 'BUSINESS', '2015-10-15 14:14:40', '2015-10-15 14:14:40');
INSERT INTO `member` VALUES ('20', 'wangb1', 'E10ADC3949BA59ABBE56E057F20F883E', 'BUSINESS', '2015-10-15 14:14:41', '2015-10-15 14:14:41');
INSERT INTO `member` VALUES ('21', 'wangb2', 'E10ADC3949BA59ABBE56E057F20F883E', 'BUSINESS', '2015-10-15 14:14:41', '2015-10-15 14:14:41');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '商品编码',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `image_id` int(11) DEFAULT NULL,
  `market_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `price` decimal(10,2) DEFAULT NULL,
  `content` varchar(5000) DEFAULT NULL COMMENT '商品描述',
  `brand` varchar(255) DEFAULT NULL COMMENT '品牌',
  `is_added` bit(1) DEFAULT b'0',
  `product_area_id` int(11) DEFAULT NULL,
  `product_type_id` int(11) DEFAULT NULL,
  `sales_count` int(11) DEFAULT '0',
  `collect_count` int(11) DEFAULT '0',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `product_area_id` (`product_area_id`),
  KEY `product_type_id` (`product_type_id`),
  KEY `image_id` (`image_id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`product_area_id`) REFERENCES `product_area` (`id`),
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`product_type_id`) REFERENCES `product_type` (`id`),
  CONSTRAINT `product_ibfk_3` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('3', '2', '拉拉干红', '1553', '600.00', '100.00', '&ltp&gt啦啦啦简介啦啦啦简介啦啦啦简介啦啦啦简介啦啦啦简介&ltimg src=\"/files/upload/image/ueditor/20151014/1444819706841096505.jpg\" title=\"1444819706841096505.jpg\" alt=\"2522840_19_b.jpg\"/&gt&ltimg src=\"/files/upload/image/ueditor/20151014/1444819709957005643.jpg\" title=\"1444819709957005643.jpg\" alt=\"55cb1513N80969b2b.jpg!q80.jpg\"/&gt&lt/p&gt', '拉拉', '', '1', '2', null, null, null, null, '2015-09-07 16:08:35');
INSERT INTO `product` VALUES ('4', '1', '拉萨酒', '1512', '65.00', '120.00', '短短的', '啦啦啦', '', '3', '2', null, null, null, null, '2015-08-21 14:49:56');
INSERT INTO `product` VALUES ('5', 'fff', 'sdasd', '1512', '660.00', '150.00', '<p><img src=\"/winetast/ueditor/jsp/upload/image/20150825/1440486664657044091.jpg\" title=\"1440486664657044091.jpg\" alt=\"1423041680935.jpg\"/></p><p><br/></p><p>我我我</p>', 'asdasd', '', '1', '2', null, null, null, '2015-08-25 14:51:25', '2015-09-07 17:05:40');
INSERT INTO `product` VALUES ('6', 's66541', '拉拉干红', '1512', '60.00', '230.00', '&ltp&gt&ltbr/&gt&lt/p&gt&ltp&gt&ltimg src=\"/winetast/ueditor/jsp/upload/image/20151014/1444815067344025784.jpg\" title=\"1444815067344025784.jpg\" alt=\"548675ffNaef78887.jpg!q80.jpg\"/&gt&lt/p&gt', '拉菲', '', '1', '2', '0', '0', null, '2015-09-07 17:45:36', '2015-09-07 17:45:47');
INSERT INTO `product` VALUES ('7', 's665412', '拉拉干红', '1512', '60.00', '250.00', '&ltp&gt点点滴滴&ltbr/&gt&lt/p&gt', '拉菲', '', '1', '2', '0', '0', null, '2015-09-07 17:45:36', '2015-09-07 17:45:47');
INSERT INTO `product` VALUES ('8', 's665413', '拉拉干红', '1512', '60.00', '300.00', '&ltp&gt点点滴滴&ltbr/&gt&lt/p&gt', '拉菲', '', '1', '3', '0', '0', null, '2015-09-07 17:45:36', '2015-09-07 17:45:47');
INSERT INTO `product` VALUES ('9', 's665414', '拉拉干红', '1512', '60.00', '400.00', '&ltp&gt点点滴滴&ltbr/&gt&lt/p&gt', '拉菲', '', '1', '2', '0', '0', null, '2015-09-07 17:45:36', '2015-09-07 17:45:47');
INSERT INTO `product` VALUES ('10', 's665415', '拉拉干红', '1512', '60.00', '450.00', '&ltp&gt点点滴滴&ltbr/&gt&lt/p&gt', '拉菲', '', '1', '2', '0', '0', null, '2015-09-07 17:45:36', '2015-09-07 17:45:47');
INSERT INTO `product` VALUES ('11', 's665416', '拉拉干红', '1512', '60.00', '101.00', '&ltp&gt点点滴滴&ltbr/&gt&lt/p&gt', '拉菲', '', '1', '2', '0', '0', null, '2015-09-07 17:45:36', '2015-09-07 17:45:47');
INSERT INTO `product` VALUES ('12', 's665417', '拉拉干红', '1511', '60.00', '500.00', '&ltp&gt&ltimg src=\"/files/upload/image/ueditor/20151019/1445224156789088193.jpg\" style=\"\" title=\"1445224156789088193.jpg\"/&gt&lt/p&gt&ltp&gt&ltimg src=\"/files/upload/image/ueditor/20151019/1445224156805055637.jpg\" style=\"\" title=\"1445224156805055637.jpg\"/&gt&lt/p&gt&ltp&gt&ltimg src=\"/files/upload/image/ueditor/20151019/1445224156835067970.jpg\" style=\"\" title=\"1445224156835067970.jpg\"/&gt&lt/p&gt&ltp&gt&ltimg src=\"/files/upload/image/ueditor/20151019/1445224156860040988.jpg\" style=\"\" title=\"1445224156860040988.jpg\"/&gt&lt/p&gt&ltp&gt&ltimg src=\"/files/upload/image/ueditor/20151019/1445224156878092547.jpg\" style=\"\" title=\"1445224156878092547.jpg\"/&gt&lt/p&gt&ltp&gt&ltimg src=\"/files/upload/image/ueditor/20151019/1445224157013014528.jpg\" style=\"\" title=\"1445224157013014528.jpg\"/&gt&lt/p&gt&ltp&gt&ltimg src=\"/files/upload/image/ueditor/20151019/1445224157046074903.jpg\" style=\"\" title=\"1445224157046074903.jpg\"/&gt&lt/p&gt&ltp&gt&ltimg src=\"/files/upload/image/ueditor/20151019/1445224157062028350.jpg\" style=\"\" title=\"1445224157062028350.jpg\"/&gt&lt/p&gt&ltp&gt&ltimg src=\"/files/upload/image/ueditor/20151019/1445224157079018025.jpg\" style=\"\" title=\"1445224157079018025.jpg\"/&gt&lt/p&gt&ltp&gt&ltimg src=\"/files/upload/image/ueditor/20151019/1445224157139042714.jpg\" style=\"\" title=\"1445224157139042714.jpg\"/&gt&lt/p&gt&ltp&gt&ltimg src=\"/files/upload/image/ueditor/20151019/1445224157147002906.jpg\" style=\"\" title=\"1445224157147002906.jpg\"/&gt&lt/p&gt&ltp&gt&ltbr/&gt&lt/p&gt', '拉菲', '', '1', '2', '0', '0', null, '2015-09-07 17:45:36', '2015-09-07 17:45:47');
INSERT INTO `product` VALUES ('13', 's665418', '拉拉干红', '1513', '60.00', '700.00', '&ltp&gt点点滴滴&ltbr/&gt&lt/p&gt', '拉菲', '\0', '1', '2', '0', '0', null, '2015-09-07 17:45:36', '2015-09-07 17:45:47');

-- ----------------------------
-- Table structure for product_area
-- ----------------------------
DROP TABLE IF EXISTS `product_area`;
CREATE TABLE `product_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='产品产地';

-- ----------------------------
-- Records of product_area
-- ----------------------------
INSERT INTO `product_area` VALUES ('1', '德国', '2015-08-20 11:58:12', '2015-08-20 15:20:43');
INSERT INTO `product_area` VALUES ('3', '法国', '2015-08-20 14:10:59', '2015-08-20 15:20:29');

-- ----------------------------
-- Table structure for product_images
-- ----------------------------
DROP TABLE IF EXISTS `product_images`;
CREATE TABLE `product_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `image_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `image_id` (`image_id`),
  CONSTRAINT `product_images_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `product_images_ibfk_2` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_images
-- ----------------------------
INSERT INTO `product_images` VALUES ('48', '4', '1489');
INSERT INTO `product_images` VALUES ('49', '4', '1490');
INSERT INTO `product_images` VALUES ('50', '4', '1491');
INSERT INTO `product_images` VALUES ('51', '4', '1492');
INSERT INTO `product_images` VALUES ('53', '3', '1494');
INSERT INTO `product_images` VALUES ('55', '5', '1496');
INSERT INTO `product_images` VALUES ('56', '5', '1497');
INSERT INTO `product_images` VALUES ('57', '6', '1507');
INSERT INTO `product_images` VALUES ('58', '6', '1508');
INSERT INTO `product_images` VALUES ('59', '6', '1509');
INSERT INTO `product_images` VALUES ('60', '6', '1510');
INSERT INTO `product_images` VALUES ('67', '13', '1563');
INSERT INTO `product_images` VALUES ('68', '13', '1564');
INSERT INTO `product_images` VALUES ('69', '13', '1565');
INSERT INTO `product_images` VALUES ('70', '12', '1566');
INSERT INTO `product_images` VALUES ('71', '12', '1567');
INSERT INTO `product_images` VALUES ('72', '12', '1568');

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '商品类别',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='产品类别';

-- ----------------------------
-- Records of product_type
-- ----------------------------
INSERT INTO `product_type` VALUES ('2', '干红', '2015-08-20 14:29:41', '2015-08-20 15:21:04');
INSERT INTO `product_type` VALUES ('3', '葡萄酒', '2015-08-20 14:29:59', '2015-08-20 15:20:58');
INSERT INTO `product_type` VALUES ('4', 'll', '2016-01-27 23:51:30', '2016-01-27 23:51:36');
