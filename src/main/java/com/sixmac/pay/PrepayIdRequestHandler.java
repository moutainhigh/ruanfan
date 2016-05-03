package com.sixmac.pay;

import com.sixmac.pay.client.TenpayHttpClient;
import com.sixmac.pay.util.ConstantUtil;
import com.sixmac.pay.util.MD5Util;
import com.sixmac.pay.util.Sha1Util;
import com.sixmac.pay.util.XMLUtil;
import com.sixmac.utils.CommonUtils;
import org.json.JSONException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class PrepayIdRequestHandler extends RequestHandler {

    public PrepayIdRequestHandler(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public Map<String, Object> getMap() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            resultMap.put(k, v);
        }
        return resultMap;
    }

    /**
     * ����ǩ��SHA1
     *
     * @return
     * @throws Exception
     */
    public String createSHA1Sign() {
        StringBuffer sb = new StringBuffer();
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        String appsign = Sha1Util.getSha1(params);
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "sha1 sb:" + params);
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "app sign:" + appsign);
        return appsign;
    }

    /**
     * ����ǩ��SHA1
     *
     * @return
     * @throws Exception
     */
    public String createMD5Sign() {
        StringBuffer sb = new StringBuffer();
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        sb.append("key=").append(ConstantUtil.API_KEY);

        String appsign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
        System.out.println("appsign:" + appsign);
        return appsign;
    }

    // �ύԤ֧��
    public String sendPrepay() throws JSONException {
        String prepayid = "";
//		StringBuffer sb = new StringBuffer("{");
//		Set es = super.getAllParameters().entrySet();
//		Iterator it = es.iterator();
//		while (it.hasNext()) {
//			Map.Entry entry = (Map.Entry) it.next();
//			String k = (String) entry.getKey();
//			String v = (String) entry.getValue();
//			if (null != v && !"".equals(v) && !"appkey".equals(k)) {
//				sb.append("\"" + k + "\":\"" + v + "\",");
//			}
//		}
//		String params = sb.substring(0, sb.lastIndexOf(","));
//		params += "}";

        String params = CommonUtils.mapToXml(super.getAllParameters());
        String requestUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "requestUrl:" + requestUrl);
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setReqContent(requestUrl);
        String resContent = "";
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" + params);
        if (httpClient.callHttpPost(requestUrl, params)) {
            resContent = httpClient.getResContent();
            try {
                Map<String, Object> resultMap = XMLUtil.doXMLParse(resContent);

                for (Map.Entry<String, Object> map : resultMap.entrySet()) {
                    System.out.println(map.getKey() + ":" + map.getValue());
                }
                prepayid = resultMap.get("prepay_id").toString();
                System.out.println("prepayid================:" + prepayid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return prepayid;
    }

    // �ж�access_token�Ƿ�ʧЧ
    public String sendAccessToken() {
        String accesstoken = "";
        StringBuffer sb = new StringBuffer("{");
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"appkey".equals(k)) {
                sb.append("\"" + k + "\":\"" + v + "\",");
            }
        }
        String params = sb.substring(0, sb.lastIndexOf(","));
        params += "}";

        String requestUrl = super.getGateUrl();
        // this.setDebugInfo(this.getDebugInfo() + "\r\n" + "requestUrl:"
        // + requestUrl);
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setReqContent(requestUrl);
        String resContent = "";
        // this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" +
        // params);
        if (httpClient.callHttpPost(requestUrl, params)) {
            resContent = httpClient.getResContent();
            if (2 == resContent.indexOf(ConstantUtil.ERRORCODE)) {
                accesstoken = resContent.substring(11, 16);// ��ȡ��Ӧ��errcode��ֵ
            }
        }
        return accesstoken;
    }
}
