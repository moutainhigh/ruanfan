/*
Navicat MySQL Data Transfer

Source Server         : 公司服务器
Source Server Version : 50611
Source Host           : 4.16.0.180:3306
Source Database       : ruanfan

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2016-03-07 18:14:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for afflatus
-- ----------------------------
DROP TABLE IF EXISTS `afflatus`;
CREATE TABLE `afflatus` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '灵感图集id',
  `name` varchar(100) DEFAULT '' COMMENT '灵感图集名称',
  `userId` int(11) DEFAULT '0' COMMENT '发布者id',
  `type` int(1) DEFAULT '1' COMMENT '类型，1=单图，2=套图，默认为1',
  `styleId` int(11) DEFAULT '0' COMMENT '风格id',
  `areaId` int(11) DEFAULT '0' COMMENT '区域id',
  `coverId` int(11) DEFAULT '0' COMMENT '封面图id',
  `showNum` int(11) DEFAULT '0' COMMENT '浏览量',
  `shareNum` int(11) DEFAULT '0' COMMENT '分享数',
  `labels` varchar(1000) DEFAULT '' COMMENT '标签，中间用英文逗号隔开',
  `status` int(1) DEFAULT '0' COMMENT '状态，0=待审核，1=审核通过，2=审核不通过，默认为0',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灵感图集表';

-- ----------------------------
-- Records of afflatus
-- ----------------------------

-- ----------------------------
-- Table structure for areas
-- ----------------------------
DROP TABLE IF EXISTS `areas`;
CREATE TABLE `areas` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '区域id',
  `name` varchar(100) DEFAULT '' COMMENT '区域名称',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灵感图区域表';

-- ----------------------------
-- Records of areas
-- ----------------------------

-- ----------------------------
-- Table structure for attentions
-- ----------------------------
DROP TABLE IF EXISTS `attentions`;
CREATE TABLE `attentions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `userId` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `objectId` int(11) NOT NULL DEFAULT '0' COMMENT '关注目标id',
  `objectType` int(1) NOT NULL DEFAULT '0' COMMENT '关注目标类型，1=用户，2=设计师',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注表';

-- ----------------------------
-- Records of attentions
-- ----------------------------

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '广告id',
  `cover` varchar(200) DEFAULT '' COMMENT '图片路径',
  `type` int(1) DEFAULT '1' COMMENT '类型，1=商品，2=套餐商品，3=特价商品，默认为1',
  `sourceId` int(11) DEFAULT '0' COMMENT '目标id',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告表';

-- ----------------------------
-- Records of banner
-- ----------------------------

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '品牌id',
  `name` varchar(100) DEFAULT '' COMMENT '品牌名称',
  `cover` varchar(200) DEFAULT '' COMMENT '图标路径',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品牌表';

-- ----------------------------
-- Records of brand
-- ----------------------------

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` int(11) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '城市名称',
  `provinceId` int(11) NOT NULL COMMENT '所属省份id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市';

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('1', '北京市', '1');
INSERT INTO `city` VALUES ('2', '天津市', '2');
INSERT INTO `city` VALUES ('3', '上海市', '9');
INSERT INTO `city` VALUES ('4', '重庆市', '27');
INSERT INTO `city` VALUES ('5', '邯郸市', '3');
INSERT INTO `city` VALUES ('6', '石家庄市', '3');
INSERT INTO `city` VALUES ('7', '保定市', '3');
INSERT INTO `city` VALUES ('8', '张家口市', '3');
INSERT INTO `city` VALUES ('9', '承德市', '3');
INSERT INTO `city` VALUES ('10', '唐山市', '3');
INSERT INTO `city` VALUES ('11', '廊坊市', '3');
INSERT INTO `city` VALUES ('12', '沧州市', '3');
INSERT INTO `city` VALUES ('13', '衡水市', '3');
INSERT INTO `city` VALUES ('14', '邢台市', '3');
INSERT INTO `city` VALUES ('15', '秦皇岛市', '3');
INSERT INTO `city` VALUES ('16', '朔州市', '4');
INSERT INTO `city` VALUES ('17', '忻州市', '4');
INSERT INTO `city` VALUES ('18', '太原市', '4');
INSERT INTO `city` VALUES ('19', '大同市', '4');
INSERT INTO `city` VALUES ('20', '阳泉市', '4');
INSERT INTO `city` VALUES ('21', '晋中市', '4');
INSERT INTO `city` VALUES ('22', '长治市', '4');
INSERT INTO `city` VALUES ('23', '晋城市', '4');
INSERT INTO `city` VALUES ('24', '临汾市', '4');
INSERT INTO `city` VALUES ('25', '吕梁市', '4');
INSERT INTO `city` VALUES ('26', '运城市', '4');
INSERT INTO `city` VALUES ('27', '沈阳市', '6');
INSERT INTO `city` VALUES ('28', '铁岭市', '6');
INSERT INTO `city` VALUES ('29', '大连市', '6');
INSERT INTO `city` VALUES ('30', '鞍山市', '6');
INSERT INTO `city` VALUES ('31', '抚顺市', '6');
INSERT INTO `city` VALUES ('32', '本溪市', '6');
INSERT INTO `city` VALUES ('33', '丹东市', '6');
INSERT INTO `city` VALUES ('34', '锦州市', '6');
INSERT INTO `city` VALUES ('35', '营口市', '6');
INSERT INTO `city` VALUES ('36', '阜新市', '6');
INSERT INTO `city` VALUES ('37', '辽阳市', '6');
INSERT INTO `city` VALUES ('38', '朝阳市', '6');
INSERT INTO `city` VALUES ('39', '盘锦市', '6');
INSERT INTO `city` VALUES ('40', '葫芦岛市', '6');
INSERT INTO `city` VALUES ('41', '长春市', '7');
INSERT INTO `city` VALUES ('42', '吉林市', '7');
INSERT INTO `city` VALUES ('43', '延边朝鲜族自治州', '7');
INSERT INTO `city` VALUES ('44', '四平市', '7');
INSERT INTO `city` VALUES ('45', '通化市', '7');
INSERT INTO `city` VALUES ('46', '白城市', '7');
INSERT INTO `city` VALUES ('47', '辽源市', '7');
INSERT INTO `city` VALUES ('48', '松原市', '7');
INSERT INTO `city` VALUES ('49', '白山市', '7');
INSERT INTO `city` VALUES ('50', '哈尔滨市', '8');
INSERT INTO `city` VALUES ('51', '齐齐哈尔市', '8');
INSERT INTO `city` VALUES ('52', '鸡西市', '8');
INSERT INTO `city` VALUES ('53', '牡丹江市', '8');
INSERT INTO `city` VALUES ('54', '七台河市', '8');
INSERT INTO `city` VALUES ('55', '佳木斯市', '8');
INSERT INTO `city` VALUES ('56', '鹤岗市', '8');
INSERT INTO `city` VALUES ('57', '双鸭山市', '8');
INSERT INTO `city` VALUES ('58', '绥化市', '8');
INSERT INTO `city` VALUES ('59', '黑河市', '8');
INSERT INTO `city` VALUES ('60', '大兴安岭地区', '8');
INSERT INTO `city` VALUES ('61', '伊春市', '8');
INSERT INTO `city` VALUES ('62', '大庆市', '8');
INSERT INTO `city` VALUES ('63', '南京市', '10');
INSERT INTO `city` VALUES ('64', '无锡市', '10');
INSERT INTO `city` VALUES ('65', '镇江市', '10');
INSERT INTO `city` VALUES ('66', '苏州市', '10');
INSERT INTO `city` VALUES ('67', '南通市', '10');
INSERT INTO `city` VALUES ('68', '扬州市', '10');
INSERT INTO `city` VALUES ('69', '盐城市', '10');
INSERT INTO `city` VALUES ('70', '徐州市', '10');
INSERT INTO `city` VALUES ('71', '淮安市', '10');
INSERT INTO `city` VALUES ('72', '连云港市', '10');
INSERT INTO `city` VALUES ('73', '常州市', '10');
INSERT INTO `city` VALUES ('74', '泰州市', '10');
INSERT INTO `city` VALUES ('75', '宿迁市', '10');
INSERT INTO `city` VALUES ('76', '舟山市', '11');
INSERT INTO `city` VALUES ('77', '衢州市', '11');
INSERT INTO `city` VALUES ('78', '杭州市', '11');
INSERT INTO `city` VALUES ('79', '湖州市', '11');
INSERT INTO `city` VALUES ('80', '嘉兴市', '11');
INSERT INTO `city` VALUES ('81', '宁波市', '11');
INSERT INTO `city` VALUES ('82', '绍兴市', '11');
INSERT INTO `city` VALUES ('83', '温州市', '11');
INSERT INTO `city` VALUES ('84', '丽水市', '11');
INSERT INTO `city` VALUES ('85', '金华市', '11');
INSERT INTO `city` VALUES ('86', '台州市', '11');
INSERT INTO `city` VALUES ('87', '合肥市', '12');
INSERT INTO `city` VALUES ('88', '芜湖市', '12');
INSERT INTO `city` VALUES ('89', '蚌埠市', '12');
INSERT INTO `city` VALUES ('90', '淮南市', '12');
INSERT INTO `city` VALUES ('91', '马鞍山市', '12');
INSERT INTO `city` VALUES ('92', '淮北市', '12');
INSERT INTO `city` VALUES ('93', '铜陵市', '12');
INSERT INTO `city` VALUES ('94', '安庆市', '12');
INSERT INTO `city` VALUES ('95', '黄山市', '12');
INSERT INTO `city` VALUES ('96', '滁州市', '12');
INSERT INTO `city` VALUES ('97', '阜阳市', '12');
INSERT INTO `city` VALUES ('98', '宿州市', '12');
INSERT INTO `city` VALUES ('99', '巢湖市', '12');
INSERT INTO `city` VALUES ('100', '六安市', '12');
INSERT INTO `city` VALUES ('101', '亳州市', '12');
INSERT INTO `city` VALUES ('102', '池州市', '12');
INSERT INTO `city` VALUES ('103', '宣城市', '12');
INSERT INTO `city` VALUES ('104', '福州市', '13');
INSERT INTO `city` VALUES ('105', '厦门市', '13');
INSERT INTO `city` VALUES ('106', '宁德市', '13');
INSERT INTO `city` VALUES ('107', '莆田市', '13');
INSERT INTO `city` VALUES ('108', '泉州市', '13');
INSERT INTO `city` VALUES ('109', '漳州市', '13');
INSERT INTO `city` VALUES ('110', '龙岩市', '13');
INSERT INTO `city` VALUES ('111', '三明市', '13');
INSERT INTO `city` VALUES ('112', '南平市', '13');
INSERT INTO `city` VALUES ('113', '鹰潭市', '14');
INSERT INTO `city` VALUES ('114', '新余市', '14');
INSERT INTO `city` VALUES ('115', '南昌市', '14');
INSERT INTO `city` VALUES ('116', '九江市', '14');
INSERT INTO `city` VALUES ('117', '上饶市', '14');
INSERT INTO `city` VALUES ('118', '抚州市', '14');
INSERT INTO `city` VALUES ('119', '宜春市', '14');
INSERT INTO `city` VALUES ('120', '吉安市', '14');
INSERT INTO `city` VALUES ('121', '赣州市', '14');
INSERT INTO `city` VALUES ('122', '景德镇市', '14');
INSERT INTO `city` VALUES ('123', '萍乡市', '14');
INSERT INTO `city` VALUES ('124', '菏泽市', '15');
INSERT INTO `city` VALUES ('125', '济南市', '15');
INSERT INTO `city` VALUES ('126', '青岛市', '15');
INSERT INTO `city` VALUES ('127', '淄博市', '15');
INSERT INTO `city` VALUES ('128', '德州市', '15');
INSERT INTO `city` VALUES ('129', '烟台市', '15');
INSERT INTO `city` VALUES ('130', '潍坊市', '15');
INSERT INTO `city` VALUES ('131', '济宁市', '15');
INSERT INTO `city` VALUES ('132', '泰安市', '15');
INSERT INTO `city` VALUES ('133', '临沂市', '15');
INSERT INTO `city` VALUES ('134', '滨州市', '15');
INSERT INTO `city` VALUES ('135', '东营市', '15');
INSERT INTO `city` VALUES ('136', '威海市', '15');
INSERT INTO `city` VALUES ('137', '枣庄市', '15');
INSERT INTO `city` VALUES ('138', '日照市', '15');
INSERT INTO `city` VALUES ('139', '莱芜市', '15');
INSERT INTO `city` VALUES ('140', '聊城市', '15');
INSERT INTO `city` VALUES ('141', '商丘市', '16');
INSERT INTO `city` VALUES ('142', '郑州市', '16');
INSERT INTO `city` VALUES ('143', '安阳市', '16');
INSERT INTO `city` VALUES ('144', '新乡市', '16');
INSERT INTO `city` VALUES ('145', '许昌市', '16');
INSERT INTO `city` VALUES ('146', '平顶山市', '16');
INSERT INTO `city` VALUES ('147', '信阳市', '16');
INSERT INTO `city` VALUES ('148', '南阳市', '16');
INSERT INTO `city` VALUES ('149', '开封市', '16');
INSERT INTO `city` VALUES ('150', '洛阳市', '16');
INSERT INTO `city` VALUES ('151', '济源市', '16');
INSERT INTO `city` VALUES ('152', '焦作市', '16');
INSERT INTO `city` VALUES ('153', '鹤壁市', '16');
INSERT INTO `city` VALUES ('154', '濮阳市', '16');
INSERT INTO `city` VALUES ('155', '周口市', '16');
INSERT INTO `city` VALUES ('156', '漯河市', '16');
INSERT INTO `city` VALUES ('157', '驻马店市', '16');
INSERT INTO `city` VALUES ('158', '三门峡市', '16');
INSERT INTO `city` VALUES ('159', '武汉市', '17');
INSERT INTO `city` VALUES ('160', '襄樊市', '17');
INSERT INTO `city` VALUES ('161', '鄂州市', '17');
INSERT INTO `city` VALUES ('162', '孝感市', '17');
INSERT INTO `city` VALUES ('163', '黄冈市', '17');
INSERT INTO `city` VALUES ('164', '黄石市', '17');
INSERT INTO `city` VALUES ('165', '咸宁市', '17');
INSERT INTO `city` VALUES ('166', '荆州市', '17');
INSERT INTO `city` VALUES ('167', '宜昌市', '17');
INSERT INTO `city` VALUES ('168', '恩施土家族苗族自治州', '17');
INSERT INTO `city` VALUES ('169', '神农架林区', '17');
INSERT INTO `city` VALUES ('170', '十堰市', '17');
INSERT INTO `city` VALUES ('171', '随州市', '17');
INSERT INTO `city` VALUES ('172', '荆门市', '17');
INSERT INTO `city` VALUES ('173', '仙桃市', '17');
INSERT INTO `city` VALUES ('174', '天门市', '17');
INSERT INTO `city` VALUES ('175', '潜江市', '17');
INSERT INTO `city` VALUES ('176', '岳阳市', '18');
INSERT INTO `city` VALUES ('177', '长沙市', '18');
INSERT INTO `city` VALUES ('178', '湘潭市', '18');
INSERT INTO `city` VALUES ('179', '株洲市', '18');
INSERT INTO `city` VALUES ('180', '衡阳市', '18');
INSERT INTO `city` VALUES ('181', '郴州市', '18');
INSERT INTO `city` VALUES ('182', '常德市', '18');
INSERT INTO `city` VALUES ('183', '益阳市', '18');
INSERT INTO `city` VALUES ('184', '娄底市', '18');
INSERT INTO `city` VALUES ('185', '邵阳市', '18');
INSERT INTO `city` VALUES ('186', '湘西土家族苗族自治州', '18');
INSERT INTO `city` VALUES ('187', '张家界市', '18');
INSERT INTO `city` VALUES ('188', '怀化市', '18');
INSERT INTO `city` VALUES ('189', '永州市', '18');
INSERT INTO `city` VALUES ('190', '广州市', '19');
INSERT INTO `city` VALUES ('191', '汕尾市', '19');
INSERT INTO `city` VALUES ('192', '阳江市', '19');
INSERT INTO `city` VALUES ('193', '揭阳市', '19');
INSERT INTO `city` VALUES ('194', '茂名市', '19');
INSERT INTO `city` VALUES ('195', '惠州市', '19');
INSERT INTO `city` VALUES ('196', '江门市', '19');
INSERT INTO `city` VALUES ('197', '韶关市', '19');
INSERT INTO `city` VALUES ('198', '梅州市', '19');
INSERT INTO `city` VALUES ('199', '汕头市', '19');
INSERT INTO `city` VALUES ('200', '深圳市', '19');
INSERT INTO `city` VALUES ('201', '珠海市', '19');
INSERT INTO `city` VALUES ('202', '佛山市', '19');
INSERT INTO `city` VALUES ('203', '肇庆市', '19');
INSERT INTO `city` VALUES ('204', '湛江市', '19');
INSERT INTO `city` VALUES ('205', '中山市', '19');
INSERT INTO `city` VALUES ('206', '河源市', '19');
INSERT INTO `city` VALUES ('207', '清远市', '19');
INSERT INTO `city` VALUES ('208', '云浮市', '19');
INSERT INTO `city` VALUES ('209', '潮州市', '19');
INSERT INTO `city` VALUES ('210', '东莞市', '19');
INSERT INTO `city` VALUES ('211', '兰州市', '22');
INSERT INTO `city` VALUES ('212', '金昌市', '22');
INSERT INTO `city` VALUES ('213', '白银市', '22');
INSERT INTO `city` VALUES ('214', '天水市', '22');
INSERT INTO `city` VALUES ('215', '嘉峪关市', '22');
INSERT INTO `city` VALUES ('216', '武威市', '22');
INSERT INTO `city` VALUES ('217', '张掖市', '22');
INSERT INTO `city` VALUES ('218', '平凉市', '22');
INSERT INTO `city` VALUES ('219', '酒泉市', '22');
INSERT INTO `city` VALUES ('220', '庆阳市', '22');
INSERT INTO `city` VALUES ('221', '定西市', '22');
INSERT INTO `city` VALUES ('222', '陇南市', '22');
INSERT INTO `city` VALUES ('223', '临夏回族自治州', '22');
INSERT INTO `city` VALUES ('224', '甘南藏族自治州', '22');
INSERT INTO `city` VALUES ('225', '成都市', '28');
INSERT INTO `city` VALUES ('226', '攀枝花市', '28');
INSERT INTO `city` VALUES ('227', '自贡市', '28');
INSERT INTO `city` VALUES ('228', '绵阳市', '28');
INSERT INTO `city` VALUES ('229', '南充市', '28');
INSERT INTO `city` VALUES ('230', '达州市', '28');
INSERT INTO `city` VALUES ('231', '遂宁市', '28');
INSERT INTO `city` VALUES ('232', '广安市', '28');
INSERT INTO `city` VALUES ('233', '巴中市', '28');
INSERT INTO `city` VALUES ('234', '泸州市', '28');
INSERT INTO `city` VALUES ('235', '宜宾市', '28');
INSERT INTO `city` VALUES ('236', '资阳市', '28');
INSERT INTO `city` VALUES ('237', '内江市', '28');
INSERT INTO `city` VALUES ('238', '乐山市', '28');
INSERT INTO `city` VALUES ('239', '眉山市', '28');
INSERT INTO `city` VALUES ('240', '凉山彝族自治州', '28');
INSERT INTO `city` VALUES ('241', '雅安市', '28');
INSERT INTO `city` VALUES ('242', '甘孜藏族自治州', '28');
INSERT INTO `city` VALUES ('243', '阿坝藏族羌族自治州', '28');
INSERT INTO `city` VALUES ('244', '德阳市', '28');
INSERT INTO `city` VALUES ('245', '广元市', '28');
INSERT INTO `city` VALUES ('246', '贵阳市', '29');
INSERT INTO `city` VALUES ('247', '遵义市', '29');
INSERT INTO `city` VALUES ('248', '安顺市', '29');
INSERT INTO `city` VALUES ('249', '黔南布依族苗族自治州', '29');
INSERT INTO `city` VALUES ('250', '黔东南苗族侗族自治州', '29');
INSERT INTO `city` VALUES ('251', '铜仁地区', '29');
INSERT INTO `city` VALUES ('252', '毕节地区', '29');
INSERT INTO `city` VALUES ('253', '六盘水市', '29');
INSERT INTO `city` VALUES ('254', '黔西南布依族苗族自治州', '29');
INSERT INTO `city` VALUES ('255', '海口市', '20');
INSERT INTO `city` VALUES ('256', '三亚市', '20');
INSERT INTO `city` VALUES ('257', '五指山市', '20');
INSERT INTO `city` VALUES ('258', '琼海市', '20');
INSERT INTO `city` VALUES ('259', '儋州市', '20');
INSERT INTO `city` VALUES ('260', '文昌市', '20');
INSERT INTO `city` VALUES ('261', '万宁市', '20');
INSERT INTO `city` VALUES ('262', '东方市', '20');
INSERT INTO `city` VALUES ('263', '澄迈县', '20');
INSERT INTO `city` VALUES ('264', '定安县', '20');
INSERT INTO `city` VALUES ('265', '屯昌县', '20');
INSERT INTO `city` VALUES ('266', '临高县', '20');
INSERT INTO `city` VALUES ('267', '白沙黎族自治县', '20');
INSERT INTO `city` VALUES ('268', '昌江黎族自治县', '20');
INSERT INTO `city` VALUES ('269', '乐东黎族自治县', '20');
INSERT INTO `city` VALUES ('270', '陵水黎族自治县', '20');
INSERT INTO `city` VALUES ('271', '保亭黎族苗族自治县', '20');
INSERT INTO `city` VALUES ('272', '琼中黎族苗族自治县', '20');
INSERT INTO `city` VALUES ('273', '西双版纳傣族自治州', '30');
INSERT INTO `city` VALUES ('274', '德宏傣族景颇族自治州', '30');
INSERT INTO `city` VALUES ('275', '昭通市', '30');
INSERT INTO `city` VALUES ('276', '昆明市', '30');
INSERT INTO `city` VALUES ('277', '大理白族自治州', '30');
INSERT INTO `city` VALUES ('278', '红河哈尼族彝族自治州', '30');
INSERT INTO `city` VALUES ('279', '曲靖市', '30');
INSERT INTO `city` VALUES ('280', '保山市', '30');
INSERT INTO `city` VALUES ('281', '文山壮族苗族自治州', '30');
INSERT INTO `city` VALUES ('282', '玉溪市', '30');
INSERT INTO `city` VALUES ('283', '楚雄彝族自治州', '30');
INSERT INTO `city` VALUES ('284', '普洱市', '30');
INSERT INTO `city` VALUES ('285', '临沧市', '30');
INSERT INTO `city` VALUES ('286', '怒江傈傈族自治州', '30');
INSERT INTO `city` VALUES ('287', '迪庆藏族自治州', '30');
INSERT INTO `city` VALUES ('288', '丽江市', '30');
INSERT INTO `city` VALUES ('289', '海北藏族自治州', '25');
INSERT INTO `city` VALUES ('290', '西宁市', '25');
INSERT INTO `city` VALUES ('291', '海东地区', '25');
INSERT INTO `city` VALUES ('292', '黄南藏族自治州', '25');
INSERT INTO `city` VALUES ('293', '海南藏族自治州', '25');
INSERT INTO `city` VALUES ('294', '果洛藏族自治州', '25');
INSERT INTO `city` VALUES ('295', '玉树藏族自治州', '25');
INSERT INTO `city` VALUES ('296', '海西蒙古族藏族自治州', '25');
INSERT INTO `city` VALUES ('297', '西安市', '23');
INSERT INTO `city` VALUES ('298', '咸阳市', '23');
INSERT INTO `city` VALUES ('299', '延安市', '23');
INSERT INTO `city` VALUES ('300', '榆林市', '23');
INSERT INTO `city` VALUES ('301', '渭南市', '23');
INSERT INTO `city` VALUES ('302', '商洛市', '23');
INSERT INTO `city` VALUES ('303', '安康市', '23');
INSERT INTO `city` VALUES ('304', '汉中市', '23');
INSERT INTO `city` VALUES ('305', '宝鸡市', '23');
INSERT INTO `city` VALUES ('306', '铜川市', '23');
INSERT INTO `city` VALUES ('307', '防城港市', '21');
INSERT INTO `city` VALUES ('308', '南宁市', '21');
INSERT INTO `city` VALUES ('309', '崇左市', '21');
INSERT INTO `city` VALUES ('310', '来宾市', '21');
INSERT INTO `city` VALUES ('311', '柳州市', '21');
INSERT INTO `city` VALUES ('312', '桂林市', '21');
INSERT INTO `city` VALUES ('313', '梧州市', '21');
INSERT INTO `city` VALUES ('314', '贺州市', '21');
INSERT INTO `city` VALUES ('315', '贵港市', '21');
INSERT INTO `city` VALUES ('316', '玉林市', '21');
INSERT INTO `city` VALUES ('317', '百色市', '21');
INSERT INTO `city` VALUES ('318', '钦州市', '21');
INSERT INTO `city` VALUES ('319', '河池市', '21');
INSERT INTO `city` VALUES ('320', '北海市', '21');
INSERT INTO `city` VALUES ('321', '拉萨市', '31');
INSERT INTO `city` VALUES ('322', '日喀则地区', '31');
INSERT INTO `city` VALUES ('323', '山南地区', '31');
INSERT INTO `city` VALUES ('324', '林芝地区', '31');
INSERT INTO `city` VALUES ('325', '昌都地区', '31');
INSERT INTO `city` VALUES ('326', '那曲地区', '31');
INSERT INTO `city` VALUES ('327', '阿里地区', '31');
INSERT INTO `city` VALUES ('328', '银川市', '26');
INSERT INTO `city` VALUES ('329', '石嘴山市', '26');
INSERT INTO `city` VALUES ('330', '吴忠市', '26');
INSERT INTO `city` VALUES ('331', '固原市', '26');
INSERT INTO `city` VALUES ('332', '中卫市', '26');
INSERT INTO `city` VALUES ('333', '塔城地区', '24');
INSERT INTO `city` VALUES ('334', '哈密地区', '24');
INSERT INTO `city` VALUES ('335', '和田地区', '24');
INSERT INTO `city` VALUES ('336', '阿勒泰地区', '24');
INSERT INTO `city` VALUES ('337', '克孜勒苏柯尔克孜自治州', '24');
INSERT INTO `city` VALUES ('338', '博尔塔拉蒙古自治州', '24');
INSERT INTO `city` VALUES ('339', '克拉玛依市', '24');
INSERT INTO `city` VALUES ('340', '乌鲁木齐市', '24');
INSERT INTO `city` VALUES ('341', '石河子市', '24');
INSERT INTO `city` VALUES ('342', '昌吉回族自治州', '24');
INSERT INTO `city` VALUES ('343', '五家渠市', '24');
INSERT INTO `city` VALUES ('344', '吐鲁番地区', '24');
INSERT INTO `city` VALUES ('345', '巴音郭楞蒙古自治州', '24');
INSERT INTO `city` VALUES ('346', '阿克苏地区', '24');
INSERT INTO `city` VALUES ('347', '阿拉尔市', '24');
INSERT INTO `city` VALUES ('348', '喀什地区', '24');
INSERT INTO `city` VALUES ('349', '图木舒克市', '24');
INSERT INTO `city` VALUES ('350', '伊犁哈萨克自治州', '24');
INSERT INTO `city` VALUES ('351', '呼伦贝尔市', '5');
INSERT INTO `city` VALUES ('352', '呼和浩特市', '5');
INSERT INTO `city` VALUES ('353', '包头市', '5');
INSERT INTO `city` VALUES ('354', '乌海市', '5');
INSERT INTO `city` VALUES ('355', '乌兰察布市', '5');
INSERT INTO `city` VALUES ('356', '通辽市', '5');
INSERT INTO `city` VALUES ('357', '赤峰市', '5');
INSERT INTO `city` VALUES ('358', '鄂尔多斯市', '5');
INSERT INTO `city` VALUES ('359', '巴彦淖尔市', '5');
INSERT INTO `city` VALUES ('360', '锡林郭勒盟', '5');
INSERT INTO `city` VALUES ('361', '兴安盟', '5');
INSERT INTO `city` VALUES ('362', '阿拉善盟', '5');
INSERT INTO `city` VALUES ('363', '澳门特别行政区', '33');
INSERT INTO `city` VALUES ('364', '台北市', '32');
INSERT INTO `city` VALUES ('365', '高雄市', '32');
INSERT INTO `city` VALUES ('366', '台南市', '32');
INSERT INTO `city` VALUES ('367', '台中市', '32');
INSERT INTO `city` VALUES ('368', '金门县', '32');
INSERT INTO `city` VALUES ('369', '南投县', '32');
INSERT INTO `city` VALUES ('370', '基隆市', '32');
INSERT INTO `city` VALUES ('371', '新竹市', '32');
INSERT INTO `city` VALUES ('372', '嘉义县', '32');
INSERT INTO `city` VALUES ('373', '新北市', '32');
INSERT INTO `city` VALUES ('374', '宜兰县', '32');
INSERT INTO `city` VALUES ('375', '新竹县', '32');
INSERT INTO `city` VALUES ('376', '桃园县', '32');
INSERT INTO `city` VALUES ('377', '苗栗县', '32');
INSERT INTO `city` VALUES ('378', '彰化县', '32');
INSERT INTO `city` VALUES ('379', '嘉义县', '32');
INSERT INTO `city` VALUES ('380', '云林县', '32');
INSERT INTO `city` VALUES ('381', '屏东县', '32');
INSERT INTO `city` VALUES ('382', '台东县', '32');
INSERT INTO `city` VALUES ('383', '花莲县', '32');
INSERT INTO `city` VALUES ('384', '澎湖县', '32');
INSERT INTO `city` VALUES ('385', '连江县', '32');
INSERT INTO `city` VALUES ('386', '香港岛', '34');
INSERT INTO `city` VALUES ('387', '九龙', '34');
INSERT INTO `city` VALUES ('388', '新界', '34');

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `objectId` int(11) DEFAULT '0' COMMENT '收藏目标id',
  `objectType` int(11) DEFAULT '0' COMMENT '收藏目标类型，1=灵感集，2=设计作品',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收藏表';

-- ----------------------------
-- Records of collect
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `userId` int(11) DEFAULT '0' COMMENT '评论人id',
  `objectId` int(11) DEFAULT '0' COMMENT '评论对象id',
  `objectType` int(1) DEFAULT '0' COMMENT '评论对象类型，1=设计师，2=设计作品，3=灵感集',
  `content` varchar(500) DEFAULT '' COMMENT '评论内容',
  `createTime` datetime DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表';

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '范票id',
  `name` varchar(100) DEFAULT '' COMMENT '名称',
  `cover` varchar(200) DEFAULT '' COMMENT '封面图路径',
  `couponNum` varchar(100) DEFAULT '' COMMENT '券号',
  `money` varchar(50) DEFAULT '' COMMENT '金额',
  `type` int(1) DEFAULT '0' COMMENT '类型，0=无限制，1=满减，默认为0',
  `maxMoney` varchar(50) DEFAULT '' COMMENT '满减金额',
  `startDate` date DEFAULT NULL COMMENT '开始日期',
  `endDate` date DEFAULT NULL COMMENT '结束日期',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='范票表';

-- ----------------------------
-- Records of coupon
-- ----------------------------

-- ----------------------------
-- Table structure for designers
-- ----------------------------
DROP TABLE IF EXISTS `designers`;
CREATE TABLE `designers` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设计师id',
  `nickName` varchar(50) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(100) DEFAULT '' COMMENT '密码，MD5密文',
  `head` varchar(200) DEFAULT '' COMMENT '头像',
  `type` int(1) DEFAULT '1' COMMENT '类型，1=独立设计师，2=设计公司，默认为1',
  `proof` varchar(200) DEFAULT '' COMMENT '资质证明',
  `cityId` int(11) DEFAULT '0' COMMENT '所属城市id',
  `description` varchar(2000) DEFAULT '' COMMENT '设计师介绍',
  `isCheck` int(1) DEFAULT '0' COMMENT '审核状态，0=待审核，1=审核通过，2=审核不通过，默认为0',
  `status` int(1) DEFAULT '0' COMMENT '状态，0=启用，1=禁用（封禁），默认为0',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设计师表';

-- ----------------------------
-- Records of designers
-- ----------------------------

-- ----------------------------
-- Table structure for estate
-- ----------------------------
DROP TABLE IF EXISTS `estate`;
CREATE TABLE `estate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地产id',
  `name` varchar(100) DEFAULT '' COMMENT '地产名称',
  `cover` varchar(200) DEFAULT '' COMMENT '封面图路径',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地产表';

-- ----------------------------
-- Records of estate
-- ----------------------------

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '反馈id',
  `userId` int(11) DEFAULT NULL COMMENT '反馈用户id',
  `type` int(1) DEFAULT '0' COMMENT '类型，1=功能建议，2=性能问题，3=购买遇到问题，4=其他',
  `path` varchar(200) DEFAULT '' COMMENT '图片路径',
  `description` varchar(2000) DEFAULT '' COMMENT '意见详情',
  `createTime` datetime DEFAULT NULL COMMENT '反馈时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='反馈表';

-- ----------------------------
-- Records of feedback
-- ----------------------------

-- ----------------------------
-- Table structure for gams
-- ----------------------------
DROP TABLE IF EXISTS `gams`;
CREATE TABLE `gams` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `userId` int(11) DEFAULT '0' COMMENT '用户id',
  `objectId` int(11) DEFAULT '0' COMMENT '操作目标id',
  `objectType` int(1) DEFAULT '1' COMMENT '操作目标类型，1=日志，2=灵感集，3=设计师',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '操作，1=点赞，2=转发',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞转发表';

-- ----------------------------
-- Records of gams
-- ----------------------------

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `path` varchar(200) DEFAULT '' COMMENT '图片路径',
  `thuPath` varchar(200) DEFAULT '' COMMENT '缩略图路径',
  `width` varchar(50) DEFAULT '' COMMENT '图片宽度',
  `height` varchar(50) DEFAULT '' COMMENT '图片高度',
  `objectId` int(11) DEFAULT '0' COMMENT '图片所属对象id',
  `objectType` int(11) DEFAULT '0' COMMENT '图片所属对象类型，1=商品，2=商品套餐，3=秒杀图，4=灵感图，5=杂志详情图，6=楼盘，7=日志图，8=设计作品',
  `description` varchar(2000) DEFAULT '' COMMENT '图片描述',
  `demo` varchar(2000) DEFAULT '' COMMENT '备注',
  `url` varchar(200) DEFAULT '' COMMENT '链接URL',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片表';

-- ----------------------------
-- Records of image
-- ----------------------------

-- ----------------------------
-- Table structure for journal
-- ----------------------------
DROP TABLE IF EXISTS `journal`;
CREATE TABLE `journal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `userId` int(11) NOT NULL DEFAULT '0' COMMENT '发布人id',
  `content` varchar(500) DEFAULT '' COMMENT '日志内容',
  `forwardNum` int(11) DEFAULT '0' COMMENT '转发数',
  `createTime` datetime DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of journal
-- ----------------------------

-- ----------------------------
-- Table structure for magazine
-- ----------------------------
DROP TABLE IF EXISTS `magazine`;
CREATE TABLE `magazine` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '杂志id',
  `name` varchar(100) DEFAULT '' COMMENT '杂志名称',
  `cover` varchar(200) DEFAULT '' COMMENT '封面图路径',
  `month` int(2) DEFAULT '1' COMMENT '月份，默认为1，即一月',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='杂志表';

-- ----------------------------
-- Records of magazine
-- ----------------------------

-- ----------------------------
-- Table structure for merchants
-- ----------------------------
DROP TABLE IF EXISTS `merchants`;
CREATE TABLE `merchants` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商户id',
  `nickName` varchar(100) DEFAULT '' COMMENT '商户名称',
  `password` varchar(100) DEFAULT '' COMMENT '密码，MD5密文',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `url` varchar(200) DEFAULT '' COMMENT '链接',
  `head` varchar(200) DEFAULT '' COMMENT '头像',
  `license` varchar(200) DEFAULT '' COMMENT '营业执照路径',
  `labels` varchar(2000) DEFAULT '' COMMENT '标签，中间用英文逗号隔开',
  `type` int(1) DEFAULT '2' COMMENT '商家类型，1=品牌商家，2=独立商家，默认为2',
  `styleId` int(11) DEFAULT '0' COMMENT '风格id',
  `cityId` int(11) DEFAULT '0' COMMENT '所属城市id',
  `description` varchar(2000) DEFAULT '' COMMENT '商家介绍',
  `isCheck` int(1) DEFAULT '0' COMMENT '审核状态，0=待审核，1=审核通过，2=审核不通过，默认为0',
  `status` int(1) DEFAULT '0' COMMENT '状态，0=启用，1=禁用（封禁），默认为0',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户表';

-- ----------------------------
-- Records of merchants
-- ----------------------------

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `title` varchar(200) DEFAULT '' COMMENT '消息标题',
  `type` varchar(20) NOT NULL DEFAULT '' COMMENT '消息发送类型，0=全部，1=设计师，2=商户，3=用户',
  `description` text COMMENT '消息详情',
  `createTime` datetime DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for modules
-- ----------------------------
DROP TABLE IF EXISTS `modules`;
CREATE TABLE `modules` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '功能模块id',
  `name` varchar(100) DEFAULT '' COMMENT '功能模块名称',
  `parentId` int(11) DEFAULT '0' COMMENT '上级id，默认为0，即一级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='功能模块表';

-- ----------------------------
-- Records of modules
-- ----------------------------
INSERT INTO `modules` VALUES ('1', '用户管理', '0');
INSERT INTO `modules` VALUES ('2', '商品管理', '0');
INSERT INTO `modules` VALUES ('3', '灵感集管理', '0');
INSERT INTO `modules` VALUES ('4', '分类管理', '0');
INSERT INTO `modules` VALUES ('5', '站长工具', '0');
INSERT INTO `modules` VALUES ('6', '消息管理', '0');
INSERT INTO `modules` VALUES ('7', '日志管理', '0');
INSERT INTO `modules` VALUES ('8', '管理设计师', '1');
INSERT INTO `modules` VALUES ('9', '管理商户', '1');
INSERT INTO `modules` VALUES ('10', '管理会员', '1');
INSERT INTO `modules` VALUES ('11', '管理管理人员', '1');
INSERT INTO `modules` VALUES ('12', '管理商品单品', '2');
INSERT INTO `modules` VALUES ('13', '管理商品套餐', '2');
INSERT INTO `modules` VALUES ('14', '管理秒杀商品', '2');
INSERT INTO `modules` VALUES ('15', '管理订单', '2');
INSERT INTO `modules` VALUES ('16', '管理评价', '2');
INSERT INTO `modules` VALUES ('17', '管理灵感图集', '3');
INSERT INTO `modules` VALUES ('18', '管理虚拟体验', '3');
INSERT INTO `modules` VALUES ('19', '管理杂志', '3');
INSERT INTO `modules` VALUES ('20', '管理商品种类', '4');
INSERT INTO `modules` VALUES ('21', '管理品牌分类', '4');
INSERT INTO `modules` VALUES ('22', '管理风格分类', '4');
INSERT INTO `modules` VALUES ('23', '管理灵感图区域分类', '4');
INSERT INTO `modules` VALUES ('24', '管理虚拟体验分类', '4');
INSERT INTO `modules` VALUES ('25', '管理广告banner', '5');
INSERT INTO `modules` VALUES ('26', '管理范票', '5');
INSERT INTO `modules` VALUES ('27', '管理预约', '5');
INSERT INTO `modules` VALUES ('28', '管理反馈', '5');
INSERT INTO `modules` VALUES ('29', '管理收入', '5');
INSERT INTO `modules` VALUES ('30', '管理操作日志', '5');
INSERT INTO `modules` VALUES ('31', '管理消息', '6');
INSERT INTO `modules` VALUES ('32', '管理日志', '7');

-- ----------------------------
-- Table structure for notices
-- ----------------------------
DROP TABLE IF EXISTS `notices`;
CREATE TABLE `notices` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统通知id',
  `sourceId` int(11) DEFAULT '0' COMMENT '所属目标id',
  `sourceType` int(1) DEFAULT '0' COMMENT '所属目标类型，1=商户，2=设计师',
  `objectId` int(11) DEFAULT '0' COMMENT '关联目标id',
  `objectName` varchar(100) DEFAULT '' COMMENT '关联目标名称',
  `objectType` int(1) DEFAULT '0' COMMENT '关联目标类型，1=商品，2=灵感单图，3=灵感套图',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统通知表';

-- ----------------------------
-- Records of notices
-- ----------------------------

-- ----------------------------
-- Table structure for operatis
-- ----------------------------
DROP TABLE IF EXISTS `operatis`;
CREATE TABLE `operatis` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作id',
  `name` varchar(50) DEFAULT '' COMMENT '操作人名称',
  `roleName` varchar(50) DEFAULT '' COMMENT '操作人身份',
  `description` varchar(200) DEFAULT '' COMMENT '操作内容',
  `createTime` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- ----------------------------
-- Records of operatis
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `orderNum` varchar(50) DEFAULT '' COMMENT '订单流水号',
  `payType` int(1) DEFAULT '1' COMMENT '支付方式，1=支付宝，2=微信',
  `payTime` datetime DEFAULT NULL COMMENT '支付时间',
  `userId` int(11) DEFAULT '0' COMMENT '购买用户id',
  `merchantId` int(11) DEFAULT '0' COMMENT '商户id',
  `consignee` varchar(20) DEFAULT '' COMMENT '收货人',
  `mobile` varchar(20) DEFAULT '' COMMENT '收货电话',
  `address` varchar(200) DEFAULT '' COMMENT '收货地址',
  `price` varchar(50) DEFAULT '' COMMENT '金额',
  `realPrice` varchar(50) DEFAULT '' COMMENT '实付金额',
  `demo` varchar(2000) DEFAULT '' COMMENT '备注',
  `status` int(1) DEFAULT '0' COMMENT '状态，0=待付款，1=待发货，2=待确认，3=已完成，默认为0',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for ordersinfo
-- ----------------------------
DROP TABLE IF EXISTS `ordersinfo`;
CREATE TABLE `ordersinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单详情id',
  `orderId` int(11) DEFAULT '0' COMMENT '订单id',
  `merchantId` int(11) DEFAULT '0' COMMENT '商品所属商户id',
  `type` int(1) DEFAULT '1' COMMENT '类型，1=商品，2=秒杀',
  `productId` int(11) DEFAULT '0' COMMENT '商品id or 秒杀id',
  `productName` varchar(50) DEFAULT '' COMMENT '名称',
  `productPath` varchar(200) DEFAULT '' COMMENT '图片',
  `colors` varchar(500) DEFAULT '' COMMENT '颜色',
  `sizes` varchar(500) DEFAULT '' COMMENT '尺寸',
  `materials` varchar(500) DEFAULT '' COMMENT '材质',
  `price` varchar(50) DEFAULT '' COMMENT '商品价格（单个）',
  `count` int(11) DEFAULT '0' COMMENT '数量',
  `star` int(1) DEFAULT '0' COMMENT '星级',
  `comment` varchar(2000) DEFAULT '' COMMENT '评价内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';

-- ----------------------------
-- Records of ordersinfo
-- ----------------------------

-- ----------------------------
-- Table structure for packages
-- ----------------------------
DROP TABLE IF EXISTS `packages`;
CREATE TABLE `packages` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '套餐id',
  `name` varchar(100) DEFAULT '' COMMENT '套餐名称',
  `price` varchar(50) DEFAULT '' COMMENT '原价',
  `oldPrice` varchar(50) DEFAULT '—' COMMENT '现价',
  `brandId` int(11) DEFAULT NULL COMMENT '品牌id',
  `labels` varchar(1000) DEFAULT '' COMMENT '标签，中间用英文逗号隔开',
  `coverId` int(11) DEFAULT '0' COMMENT '封面图id',
  `description` text COMMENT '套餐描述',
  `count` int(11) DEFAULT '0' COMMENT '交易数量',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='套餐表';

-- ----------------------------
-- Records of packages
-- ----------------------------

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` varchar(100) DEFAULT '' COMMENT '商品名称',
  `price` varchar(20) DEFAULT '' COMMENT '现价',
  `oldPrice` varchar(20) DEFAULT '—' COMMENT '原价',
  `merchantId` int(11) DEFAULT '0' COMMENT '发布商户id',
  `coverId` int(11) DEFAULT '0' COMMENT '封面图id',
  `type` int(1) DEFAULT '1' COMMENT '分类，1=单品，2=艺术品，3=设计师品牌，默认为1',
  `brandId` int(11) DEFAULT '0' COMMENT '品牌id',
  `sortId` int(11) DEFAULT '0' COMMENT '产品种类id',
  `place` varchar(100) DEFAULT '' COMMENT '产地',
  `labels` varchar(500) DEFAULT '' COMMENT '标签，中间用英文逗号隔开',
  `colors` varchar(1000) DEFAULT '' COMMENT '颜色，中间用英文逗号隔开',
  `sizes` varchar(1000) DEFAULT '' COMMENT '尺寸，中间用英文逗号隔开',
  `materials` varchar(1000) DEFAULT '' COMMENT '材质，中间用英文逗号隔开',
  `isHot` int(1) DEFAULT '0' COMMENT '是否推荐，0=否，1=是，默认为0',
  `isCheck` int(1) DEFAULT '0' COMMENT '审核状态，0=待审核，1=审核通过，2=审核不通过，默认为0',
  `status` int(1) DEFAULT '0' COMMENT '状态，0=启用，1=禁用（封禁），默认为0',
  `description` text COMMENT '产品描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of products
-- ----------------------------

-- ----------------------------
-- Table structure for producttype
-- ----------------------------
DROP TABLE IF EXISTS `producttype`;
CREATE TABLE `producttype` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品分类id',
  `name` varchar(100) DEFAULT '' COMMENT '分类名称',
  `url` varchar(200) DEFAULT '' COMMENT '图标路径',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品分类表';

-- ----------------------------
-- Records of producttype
-- ----------------------------

-- ----------------------------
-- Table structure for propertys
-- ----------------------------
DROP TABLE IF EXISTS `propertys`;
CREATE TABLE `propertys` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '楼盘id',
  `name` varchar(100) DEFAULT '' COMMENT '楼盘名称',
  `cover` varchar(200) DEFAULT '' COMMENT '封面图路径',
  `address` varchar(500) DEFAULT '' COMMENT '楼盘地址',
  `labels` varchar(1000) DEFAULT '' COMMENT '标签，中间用英文逗号隔开',
  `parentId` int(11) DEFAULT '0' COMMENT '所属地产id',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='楼盘表';

-- ----------------------------
-- Records of propertys
-- ----------------------------

-- ----------------------------
-- Table structure for province
-- ----------------------------
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province` (
  `id` int(11) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '省份名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省份';

-- ----------------------------
-- Records of province
-- ----------------------------
INSERT INTO `province` VALUES ('1', '北京');
INSERT INTO `province` VALUES ('2', '天津');
INSERT INTO `province` VALUES ('3', '河北省');
INSERT INTO `province` VALUES ('4', '山西省');
INSERT INTO `province` VALUES ('5', '内蒙古自治区');
INSERT INTO `province` VALUES ('6', '辽宁省');
INSERT INTO `province` VALUES ('7', '吉林省');
INSERT INTO `province` VALUES ('8', '黑龙江省');
INSERT INTO `province` VALUES ('9', '上海市');
INSERT INTO `province` VALUES ('10', '江苏省');
INSERT INTO `province` VALUES ('11', '浙江省');
INSERT INTO `province` VALUES ('12', '安徽省');
INSERT INTO `province` VALUES ('13', '福建省');
INSERT INTO `province` VALUES ('14', '江西省');
INSERT INTO `province` VALUES ('15', '山东省');
INSERT INTO `province` VALUES ('16', '河南省');
INSERT INTO `province` VALUES ('17', '湖北省');
INSERT INTO `province` VALUES ('18', '湖南省');
INSERT INTO `province` VALUES ('19', '广东省');
INSERT INTO `province` VALUES ('20', '海南省');
INSERT INTO `province` VALUES ('21', '广西壮族自治区');
INSERT INTO `province` VALUES ('22', '甘肃省');
INSERT INTO `province` VALUES ('23', '陕西省');
INSERT INTO `province` VALUES ('24', '新疆维吾尔自治区');
INSERT INTO `province` VALUES ('25', '青海省');
INSERT INTO `province` VALUES ('26', '宁夏回族自治区');
INSERT INTO `province` VALUES ('27', '重庆市');
INSERT INTO `province` VALUES ('28', '四川省');
INSERT INTO `province` VALUES ('29', '贵州省');
INSERT INTO `province` VALUES ('30', '云南省');
INSERT INTO `province` VALUES ('31', '西藏自治区');
INSERT INTO `province` VALUES ('32', '台湾省');
INSERT INTO `province` VALUES ('33', '澳门特别行政区');
INSERT INTO `province` VALUES ('34', '香港特别行政区');

-- ----------------------------
-- Table structure for replys
-- ----------------------------
DROP TABLE IF EXISTS `replys`;
CREATE TABLE `replys` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '回复id',
  `commentId` int(11) DEFAULT NULL COMMENT '所属评论id',
  `userId` int(11) DEFAULT NULL COMMENT '回复人id',
  `content` varchar(500) DEFAULT '' COMMENT '回复内容',
  `createTime` datetime DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论回复表';

-- ----------------------------
-- Records of replys
-- ----------------------------

-- ----------------------------
-- Table structure for reserve
-- ----------------------------
DROP TABLE IF EXISTS `reserve`;
CREATE TABLE `reserve` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '预约id',
  `name` varchar(50) DEFAULT '' COMMENT '姓名',
  `mobile` varchar(20) DEFAULT '' COMMENT '预约电话',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `userId` int(11) DEFAULT '0' COMMENT '用户id',
  `designerId` int(11) DEFAULT '0' COMMENT '预约设计师id',
  `reseTime` datetime DEFAULT NULL COMMENT '预约时间',
  `styleId` int(11) DEFAULT '0' COMMENT '喜爱风格id',
  `address` varchar(500) DEFAULT '' COMMENT '地址',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `reseAddress` varchar(500) DEFAULT '' COMMENT '预约地址',
  `status` int(1) DEFAULT '0' COMMENT '状态，0=未处理，1=已处理，默认为0',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约表';

-- ----------------------------
-- Records of reserve
-- ----------------------------

-- ----------------------------
-- Table structure for rolemodules
-- ----------------------------
DROP TABLE IF EXISTS `rolemodules`;
CREATE TABLE `rolemodules` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `roleId` int(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  `moduleId` int(11) NOT NULL DEFAULT '0' COMMENT '功能模块id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of rolemodules
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(50) DEFAULT '' COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of roles
-- ----------------------------

-- ----------------------------
-- Table structure for shopcar
-- ----------------------------
DROP TABLE IF EXISTS `shopcar`;
CREATE TABLE `shopcar` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `userId` int(11) DEFAULT '0' COMMENT '用户id',
  `merchantId` int(11) DEFAULT '0' COMMENT '商户id',
  `type` int(1) DEFAULT '1' COMMENT '类型，1=商品，2=秒杀',
  `productId` int(11) DEFAULT '0' COMMENT '商品id',
  `cover` varchar(200) DEFAULT '' COMMENT '商品封面图路径',
  `name` varchar(100) DEFAULT '' COMMENT '商品名称',
  `colors` varchar(500) DEFAULT '' COMMENT '颜色',
  `sizes` varchar(500) DEFAULT '' COMMENT '尺寸',
  `materials` varchar(500) DEFAULT '' COMMENT '材质',
  `price` varchar(50) DEFAULT '' COMMENT '价格',
  `count` int(11) DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车表';

-- ----------------------------
-- Records of shopcar
-- ----------------------------

-- ----------------------------
-- Table structure for spikes
-- ----------------------------
DROP TABLE IF EXISTS `spikes`;
CREATE TABLE `spikes` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '秒杀id',
  `name` varchar(100) DEFAULT '' COMMENT '商品名称',
  `price` varchar(50) DEFAULT '' COMMENT '秒杀价',
  `oldPrice` varchar(50) DEFAULT '—' COMMENT '原价',
  `coverId` int(11) DEFAULT '0' COMMENT '封面图id',
  `startTime` datetime DEFAULT NULL COMMENT '开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '结束时间',
  `count` int(11) DEFAULT '0' COMMENT '交易数量',
  `labels` varchar(1000) DEFAULT '' COMMENT '标签，中间用英文逗号隔开',
  `colors` varchar(1000) DEFAULT '' COMMENT '颜色，中间用英文逗号隔开',
  `sizes` varchar(1000) DEFAULT '' COMMENT '尺寸，中间用英文逗号隔开',
  `materials` varchar(1000) DEFAULT '' COMMENT '材质，中间用英文逗号隔开',
  `description` text COMMENT '产品描述',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀表';

-- ----------------------------
-- Records of spikes
-- ----------------------------

-- ----------------------------
-- Table structure for styles
-- ----------------------------
DROP TABLE IF EXISTS `styles`;
CREATE TABLE `styles` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '风格id',
  `name` varchar(100) DEFAULT '' COMMENT '风格名称',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='风格表';

-- ----------------------------
-- Records of styles
-- ----------------------------

-- ----------------------------
-- Table structure for sysusers
-- ----------------------------
DROP TABLE IF EXISTS `sysusers`;
CREATE TABLE `sysusers` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理人员id',
  `account` varchar(100) DEFAULT '' COMMENT '账号',
  `password` varchar(100) DEFAULT '' COMMENT '密码，MD5密文',
  `roleId` int(11) DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理人员表';

-- ----------------------------
-- Records of sysusers
-- ----------------------------

-- ----------------------------
-- Table structure for usercoupon
-- ----------------------------
DROP TABLE IF EXISTS `usercoupon`;
CREATE TABLE `usercoupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `userId` int(11) DEFAULT '0' COMMENT '用户id',
  `couponId` int(11) DEFAULT '0' COMMENT '范票id',
  `status` int(1) DEFAULT '0' COMMENT '状态，0=未使用，1=已使用，默认为0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户范票中间表';

-- ----------------------------
-- Records of usercoupon
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机号（账号）',
  `nickName` varchar(50) DEFAULT '' COMMENT '昵称',
  `password` varchar(200) DEFAULT '' COMMENT '密码，MD5密文',
  `headPath` varchar(200) DEFAULT '' COMMENT '头像URL',
  `cityId` int(11) DEFAULT '0' COMMENT '所在城市id',
  `score` int(11) DEFAULT '0' COMMENT '积分，默认为0',
  `comName` varchar(100) DEFAULT '' COMMENT '所属小区名称',
  `comArea` varchar(100) DEFAULT '' COMMENT '所属小区面积',
  `type` int(1) DEFAULT '1' COMMENT '会员类型，1=马甲会员，2=普通会员，默认为1',
  `status` int(1) DEFAULT '0' COMMENT '状态，0=启用，1=禁用（封禁），默认为0',
  `createTime` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of users
-- ----------------------------

-- ----------------------------
-- Table structure for usersother
-- ----------------------------
DROP TABLE IF EXISTS `usersother`;
CREATE TABLE `usersother` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `userId` int(11) DEFAULT '0' COMMENT '关联用户id',
  `openId` varchar(200) DEFAULT '' COMMENT '唯一标识',
  `type` int(1) DEFAULT '0' COMMENT '平台类型，1=微信，2=QQ，3=新浪微博',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户第三方信息表';

-- ----------------------------
-- Records of usersother
-- ----------------------------

-- ----------------------------
-- Table structure for virtuals
-- ----------------------------
DROP TABLE IF EXISTS `virtuals`;
CREATE TABLE `virtuals` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'VR虚拟体验id',
  `name` varchar(100) DEFAULT '' COMMENT '名称',
  `styleId` int(11) DEFAULT '0' COMMENT '风格id',
  `typeId` int(11) DEFAULT '0' COMMENT '分类id',
  `labels` varchar(1000) DEFAULT '' COMMENT '标签，中间用英文逗号隔开',
  `cover` varchar(500) DEFAULT '' COMMENT '封面图地址',
  `url` varchar(500) DEFAULT '' COMMENT '链接地址',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟体验表';

-- ----------------------------
-- Records of virtuals
-- ----------------------------

-- ----------------------------
-- Table structure for vrtype
-- ----------------------------
DROP TABLE IF EXISTS `vrtype`;
CREATE TABLE `vrtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '虚拟体验分类id',
  `name` varchar(100) DEFAULT '' COMMENT '分类名称',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟体验分类表';

-- ----------------------------
-- Records of vrtype
-- ----------------------------

-- ----------------------------
-- Table structure for works
-- ----------------------------
DROP TABLE IF EXISTS `works`;
CREATE TABLE `works` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设计作品id',
  `designerId` int(11) DEFAULT '0' COMMENT '设计师id',
  `name` varchar(100) DEFAULT '' COMMENT '作品名称',
  `labels` varchar(1000) DEFAULT '' COMMENT '标签，中间用英文逗号隔开',
  `description` varchar(2000) DEFAULT '' COMMENT '作品描述',
  `coverId` int(11) DEFAULT '0' COMMENT '封面图id',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设计作品表';

-- ----------------------------
-- Records of works
-- ----------------------------
