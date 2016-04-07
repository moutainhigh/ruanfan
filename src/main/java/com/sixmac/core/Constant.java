package com.sixmac.core;

/**
 * Created by wangbin on 2015/6/24.
 */
public interface Constant {

    // 首页查询天数
    Integer days = 7;

    // 云片网appkey
    String YUNPIAN_APPKEY = "0003c8a4216ac79cdd02327ff99b96ae";

    // 云片网select

    String ENCODING = "UTF-8";

    int PAGE_DEF_SZIE = 20;

    String MEMBER_TYPE_ADMIN = "ADMIN";

    String MEMBER_TYPE_MERCHANT = "MERCHANT";

    String MEMBER_TYPE_DESIGNER = "DESIGNER";

    // 当前登录人id
    String CURRENT_USER_ID = "session_member_id";

    // 当前登录人姓名
    String CURRENT_USER_NAME = "session_member_name";

    // 当前登录人类型
    String CURRENT_USER_TYPE = "session_member_type";

    // 管理后台登录人类型
    Integer MASTER_TYPE_ADMIN = 1;
    Integer MASTER_TYPE_MERCHANT = 2;
    Integer MASTER_TYPE_DESIGNER = 3;

    // 新建用户默认头像
    String DEFAULT_HEAD_PATH = "static/images/default.png";

    // 删除标记
    Integer IS_CUT_YES = 1;
    Integer IS_CUT_NO = 0;

    // 点赞状态
    Integer GAM_LOVE_YES = 0;  // 已赞
    Integer GAM_LOVE_NO = 1;   // 未赞

    // 关注状态
    Integer ATTENTION_STATUS_YES = 0;  // 已关注
    Integer ATTENTION_STATUS_NO = 1;   // 未关注

    // 评论状态
    Integer COMMENT_STATUS_YES = 0;  // 已评论
    Integer COMMENT_STATUS_NO = 1;   // 未评论

    // 排序方式
    Integer SORT_TYPE_ASC = 1;   // 正序
    Integer SORT_TYPE_DESC = 2;  // 倒序

    // 点赞or转发类型
    Integer GAM_LOVE = 1;  // 点赞
    Integer GAM_FORWARD = 2;  // 转发

    // 点赞or转发目标类型
    Integer GAM_JOURNAL = 1;    // 日志
    Integer GAM_AFFLATUS = 2;   // 灵感集
    Integer GAM_DESIGNERS = 3;  // 设计师

    // 收藏目标类型
    Integer COLLECT_AFFLATUS = 1;  // 灵感集
    Integer COLLECT_WORKS = 2;     // 设计作品

    // 评论目标类型
    Integer COMMENT_DESIGNERS = 1;  // 设计师
    Integer COMMENT_WORKS = 2;      // 设计作品
    Integer COMMENT_AFFLATUS = 3;   // 灵感集
    Integer COMMENT_JOURNAL = 4;    // 日志

    // 关注目标类型
    Integer ATTENTION_USERS = 1;      // 用户
    Integer ATTENTION_DESIGNERS = 2;  // 设计师

    // 图片所属对象类型
    Integer IMAGE_PRODUCTS = 1;   // 商品
    Integer IMAGE_PACKAGES = 2;   // 商品套餐
    Integer IMAGE_SPIKES = 3;     // 秒杀
    Integer IMAGE_AFFLATUS = 4;   // 灵感图
    Integer IMAGE_MAGAZINE = 5;   // 杂志详情图
    Integer IMAGE_PROPERTYS = 6;  // 楼盘
    Integer IMAGE_JOURNAL = 7;    // 日志图
    Integer IMAGE_WORKS = 8;      // 设计作品

    // 优惠券使用状态
    Integer COUPON_STATUS_YES = 1;   // 已使用
    Integer COUPON_STATUS_NO = 0;    // 未使用

    // 订单状态
    Integer ORDERS_STATUS_000 = 0;   // 待付款
    Integer ORDERS_STATUS_001 = 1;   // 待发货
    Integer ORDERS_STATUS_002 = 2;   // 待确认
    Integer ORDERS_STATUS_003 = 3;   // 待评价
    Integer ORDERS_STATUS_004 = 4;   // 已完成

    // 标签所属目标类型
    Integer LABEL_AFFLATUS = 1;   // 灵感集

    // 审核状态
    Integer CHECK_STATUS_DEFAULT = 0;  // 待审核
    Integer CHECK_STATUS_SUCCESS = 1;  // 审核通过
    Integer CHECK_STATUS_FAIL = 2;     // 审核不通过

    // 用户封禁状态
    Integer BANNED_STATUS_YES = 0;  // 启用
    Integer BANNED_STATUS_NO = 1;   // 禁用（封禁）

    // 系统消息类型
    String MESSAGE_ALL = "0";         // 全部
    String MESSAGE_DESIGNERS = "1";   // 设计师
    String MESSAGE_MERCHANTS = "2";   // 商户
    String MESSAGE_USERS = "3";       // 用户

    // 设计师类型
    Integer DESIGNER_TYPE_ONE = 1;    // 独立设计师
    Integer DESIGNER_TYPE_TWO = 2;    // 设计公司

    // 商家类型
    Integer MERCHANT_TYPE_ONE = 1;    // 品牌商家
    Integer MERCHANT_TYPE_TWO = 2;    // 独立商家

    // 个人消息类型
    Integer MESSAGE_PLUS_DESIGNERS = 1;   // 设计师
    Integer MESSAGE_PLUS_MERCHANTS = 2;   // 商户
    Integer MESSAGE_PLUS_USERS = 3;       // 用户

    // 是否推荐
    Integer RECOMMEND_STATUS_YES = 1;
    Integer RECOMMEND_STATUS_NO = 0;

    // 是否上架
    Integer ADDED_STATUS_YES = 0;
    Integer ADDED_STATUS_NO = 1;

    // 消息类型
    String MESSAGE_STATUS_ALL = "全部";
    String MESSAGE_STATUS_DESIGNER = "设计师";
    String MESSAGE_STATUS_MERCHANT = "商户";
    String MESSAGE_STATUS_USER = "用户";

    // 商品类型
    Integer PRODUCT_TYPE_ONE = 1;   // 单品
    Integer PRODUCT_TYPE_TWO = 2;   // 艺术品
    Integer PRODUCT_TYPE_THREE = 3; // 设计师品牌

}
