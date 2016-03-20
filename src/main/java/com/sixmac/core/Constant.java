package com.sixmac.core;

/**
 * Created by wangbin on 2015/6/24.
 */
public interface Constant {

    // 云片网appkey
    String YUNPIAN_APPKEY = "sdfsdfsdfsdfsdfsdfd";

    // 云片网select

    String ENCODING = "UTF-8";

    int PAGE_DEF_SZIE = 20;

    String SESSION_MEMBER_GLOBLE = "session_globle_member";
    String SESSION_MEMBER_BUSINESS = "session_business_member";

    String EVENT_WINE_ONLINE_HOME = "WINE_ONLINE_HOME";

    String MEMBER_TYPE_GLOBLE = "GLOBLE";

    String MEMBER_TYPE_BUSINESS = "BUSINESS";

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
}
