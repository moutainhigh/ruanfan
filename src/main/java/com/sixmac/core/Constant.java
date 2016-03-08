package com.sixmac.core;

/**
 * Created by wangbin on 2015/6/24.
 */
public interface Constant {
    String ENCODING = "UTF-8";

    int PAGE_DEF_SZIE = 20;

    String SESSION_MEMBER_GLOBLE = "session_globle_member";
    String SESSION_MEMBER_BUSINESS = "session_business_member";

    String EVENT_WINE_ONLINE_HOME = "WINE_ONLINE_HOME";

    String MEMBER_TYPE_GLOBLE = "GLOBLE";

    String MEMBER_TYPE_BUSINESS = "BUSINESS";

    // 排序方式
    Integer SORT_TYPE_ASC = 1;   // 正序
    Integer SORT_TYPE_DESC = 2;  // 倒序

    // 点赞or转发类型
    Integer GAM_LOVE = 1;  // 点赞
    Integer GAM_MOVE = 2;  // 转发

    // 点赞or转发目标类型
    Integer GAM_JOURNAL = 1;    // 日志
    Integer GAM_AFFLATUS = 2;   // 灵感集
    Integer GAM_DESIGNERS = 3;  // 设计师

    // 收藏目标类型
    Integer COLLECT_AFFLATUS = 1;  // 灵感集

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
}
