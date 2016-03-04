package com.sixmac.core;

/**
 * Created by wangbin on 2015/6/24.
 */
public interface Constant {
    String ENCODING = "UTF-8";

    int PAGE_DEF_SZIE = 20;

    String SESSION_MEMBER_GLOBLE = "session_globle_member";
    String SESSION_MEMBER_BUSINESS = "session_business_member";

    String SESSION_WEIXIN_USER = "session_weixin_user";

    String COOKIE_BUSINESS_OPTION = "cookie_business_option";

    String WEIXIN_STATE = "weixin_state";

    String EVENT_DEF_SUBSCRIBE_TEXT = "感谢关注!";

    String MSG_DEF_TEXT = "您输入的序列号不正确，请重新输入!";

    String SEND_TYPE_FLASH = "FLASHSEND";

    String EVENT_WINE_ONLINE_HOME = "WINE_ONLINE_HOME";

    String EVENT_WINE_ONLINE_HOTEL = "WINE_ONLINE_HOTEL";

    String MEMBER_TYPE_GLOBLE = "GLOBLE";

    String MEMBER_TYPE_BUSINESS = "BUSINESS";

    String WINE_CART_COOKIE = "wine_cart_cookie";

    //微信支付
    String PAYTYPE_WEIXIN = "WEIXIN_PAY";
    //货到付款
    String PAYTYPE_HOME ="HOME_PAY";
}
