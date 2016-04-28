package com.sixmac.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/28 0028.
 */
public class WeChatUtil {

    private static String appId = "wx36b35372a5cf2af3";

    private static String appSecret = "5f3c697fe3c8917bd3bfde358a65d72c";

    private static String mchId = "1336888001";

    private static String key = "DuraGyqqd8inqERSwKslb10GAIwohsucdPRpeJgXhMg";

    // 生成支付签名
    private static String getSign(String nonceStr, String body) {
        StringBuffer sb = new StringBuffer("");
        sb.append("appid=" + appId);
        sb.append("&body=" + body);
        sb.append("&mch_id=" + mchId);
        sb.append("&nonce_str=" + nonceStr);

        String stringSignTemp = sb.toString() + "&key=" + key;

        return Md5Util.md5(stringSignTemp);
    }

    // 拼接支付参数
    public static Map<String, Object> getInfo(String body) {
        String nonceStr = System.currentTimeMillis() + "";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appid", appId);
        map.put("mch_id", mchId);
        map.put("body", body);
        map.put("nonce_str", nonceStr);
        map.put("sign", getSign(nonceStr, body));

        return map;
    }
}
