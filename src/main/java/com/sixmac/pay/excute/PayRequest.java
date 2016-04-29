package com.sixmac.pay.excute;

import com.sixmac.pay.AccessTokenRequestHandler;
import com.sixmac.pay.ClientRequestHandler;
import com.sixmac.pay.PrepayIdRequestHandler;
import com.sixmac.pay.util.ConstantUtil;
import com.sixmac.pay.util.WXUtil;
import com.sixmac.utils.CommonUtils;
import org.json.JSONException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class PayRequest {

    public static Map<String, Object> pay(HttpServletRequest request, HttpServletResponse response) {

        response.resetBuffer();
        response.setHeader("ContentType", "text/xml");

        // 回调地址(正式)
        String notify_url = "http://123.59.155.131/ruanfan/api/pay/weChatCallBack";

        String out_trade_no = request.getAttribute("sn").toString();
        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
        ClientRequestHandler clientHandler = new ClientRequestHandler(request, response);

        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 预支付ID
        String prepayid = "";

        //token
        String token = AccessTokenRequestHandler.getAccessToken();
        if (!"".equals(token)) {
            prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
            prepayReqHandler.setParameter("body", "订单支付");
            prepayReqHandler.setParameter("device_info", ConstantUtil.DEVICE_INFO);
            prepayReqHandler.setParameter("fee_type", "CNY");
            prepayReqHandler.setParameter("mch_id", ConstantUtil.PARTNER);
            String noncestr = WXUtil.getNonceStr();
            prepayReqHandler.setParameter("nonce_str", noncestr);

            prepayReqHandler.setParameter("notify_url", notify_url);
            prepayReqHandler.setParameter("out_trade_no", out_trade_no);

            prepayReqHandler.setParameter("spbill_create_ip", CommonUtils.getRealAddress(request));
            prepayReqHandler.setParameter("total_fee","1");
            prepayReqHandler.setParameter("trade_type", ConstantUtil.TRADE_TYPE);


            String sign = prepayReqHandler.createMD5Sign();
            prepayReqHandler.setParameter("sign", sign);

            try {
                prepayid = prepayReqHandler.sendPrepay();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (null != prepayid && !"".equals(prepayid)) {
                clientHandler.setParameter("appid", ConstantUtil.APP_ID);
                // clientHandler.setParameter("appkey", ConstantUtil.APP_KEY);
                clientHandler.setParameter("noncestr", noncestr);
                clientHandler.setParameter("packages", "Sign=WXPay");
                clientHandler.setParameter("partnerid", ConstantUtil.PARTNER);
                clientHandler.setParameter("prepayid", prepayid);
                clientHandler.setParameter("timestamp", WXUtil.getTimeStamp());

                sign = clientHandler.createMD5Sign();
                clientHandler.setParameter("sign", sign);

                resultMap = clientHandler.getMap();
            }
        }

        return resultMap;
    }
}
