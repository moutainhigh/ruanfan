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
    Integer GAM_JOURNAL = 1;  // 日志
    Integer GAM_AFFLATUS = 2; // 灵感集

    // 收藏目标类型
    Integer COLLECT_AFFLATUS = 1;  // 灵感集
}
