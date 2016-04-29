package com.sixmac.pay.util;

public class ConstantUtil {
	
	public static String APP_ID = "wx36b35372a5cf2af3";// 应用APPID
	public static String APP_SECRET = "5f3c697fe3c8917bd3bfde358a65d72c";// �ͻ�
	
	public static String APP_KEY = "";
	public static String PARTNER = "1336888001";// 微信支付商户号

	public static String API_KEY = "DuraGyqqd8inqERSwKslb10GAIwohsuc";
	public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";
	public static String GRANT_TYPE = "client_credential";//�����̶�ֵ 
	public static String EXPIRE_ERRCODE = "42001";//access_tokenʧЧ�����󷵻ص�errcode
	public static String FAIL_ERRCODE = "40001";//�ظ���ȡ������һ�λ�ȡ��access_tokenʧЧ,���ش�����
	public static String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//��ȡԤ֧��id�Ľӿ�url
	public static String ACCESS_TOKEN = "access_token";//access_token����ֵ
	public static String ERRORCODE = "errcode";//�����ж�access_token�Ƿ�ʧЧ��ֵ
	public static String SIGN_METHOD = "sha1";//ǩ���㷨����ֵ
	
	public static String DEVICE_INFO = "WEB"; // �ն��豸��(�ŵ�Ż������豸ID)��ע�⣺PC��ҳ���ں���֧���봫"WEB"
	public static String TRADE_TYPE = "APP"; // ��������  ȡֵ���£�JSAPI��NATIVE��APP��WAP,��ϸ˵�������涨
	
	public static String packageValue = "bank_type=WX&body=%B2%E2%CA%D4&fee_type=1&input_charset=GBK&notify_url=http%3A%2F%2F127.0.0.1%3A8180%2Ftenpay_api_b2c%2FpayNotifyUrl.jsp&out_trade_no=2051571832&partner=1900000109&sign=10DA99BCB3F63EF23E4981B331B0A3EF&spbill_create_ip=127.0.0.1&time_expire=20131222091010&total_fee=1";
	public static String traceid = "testtraceid001";//�����û�id
}
