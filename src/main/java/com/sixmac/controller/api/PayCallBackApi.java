package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Orders;
import com.sixmac.service.OrdersService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
@Controller
@RequestMapping(value = "api/pay")
public class PayCallBackApi extends CommonController {

    @Autowired
    private OrdersService ordersService;

    /**
     * @api {post} /api/pay/getPayInfo 拼接微信支付参数
     * @apiName pay.getPayInfo
     * @apiGroup pay
     * @apiParam {String} orderNum 订单流水号       <必传 />
     * @apiSuccess {Object} payInfo 微信支付信息
     * @apiSuccess {String} payInfo.appid 应用APPID
     * @apiSuccess {String} payInfo.packages 商户包信息
     * @apiSuccess {String} payInfo.prepayid 预支付交易会话标识
     * @apiSuccess {String} payInfo.noncestr 随机字符串
     * @apiSuccess {String} payInfo.partnerid 商户号
     * @apiSuccess {String} payInfo.timestamp 时间字符串
     * @apiSuccess {String} payInfo.sign 签名
     */
    @RequestMapping(value = "/getPayInfo")
    public void getPayInfo(HttpServletRequest request, HttpServletResponse response, String orderNum) {
        Map<String, Object> resultParam = ordersService.getPayInfo(request, response, orderNum);

        Result obj = new Result(true).data(createMap("payInfo", resultParam));
        String result = JsonUtil.obj2ApiJson(obj, "user", "merchant", "order", "product", "star", "comment");
        WebUtil.printApi(response, result);
    }

    // 支付宝回调
    @RequestMapping(value = "/aliPayCallBack")
    public void aliPayCallBack(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        try {
            // 获取回调参数
            Map<String, String> params = new HashMap<String, String>();
            System.out.println("------------------------");
            System.out.println("------------------------");
            System.out.println("------------------------");
            System.out.println("------------------------");
            System.out.println("AliPay callback SUCCESS!!");
            System.out.println("------------------------");
            System.out.println("------------------------");
            System.out.println("------------------------");
            System.out.println("------------------------");
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();

                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }

            // 判断是否支付成功
            String trade_status = params.get("trade_status"); // 支付状态
            String out_trade_no = params.get("out_trade_no"); // 订单号
            String total_fee = params.get("total_fee"); // 交易金额

            System.out.println("---------trade_status:" + trade_status);
            System.out.println("---------out_trade_no:" + out_trade_no);
            System.out.println("---------total_fee:" + total_fee);

            // 支付成功
            if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
                // 修改订单状态
                try {
                    changeOrderStatus(out_trade_no, response);

                    System.out.println("---------------------修改订单状态成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("交易成功");
                try {
                    result = "success";
                    response.getWriter().write(result);
                    response.flushBuffer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            WebUtil.printApi(response, new Result(false));
        }
    }

    // 微信回调
    @RequestMapping(value = "/weChatCallBack")
    public void weChatCallBack(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        try {
            System.out.println("------------------------");
            System.out.println("------------------------");
            System.out.println("------------------------");
            System.out.println("------------------------");
            System.out.println("WeChatPay callback SUCCESS!!");
            System.out.println("------------------------");
            System.out.println("------------------------");
            System.out.println("------------------------");
            System.out.println("------------------------");

            String line = null;
            StringBuffer buffer = new StringBuffer();
            // 通过request获取输入流
            BufferedReader reader = request.getReader();
            // 依次读取请求输入流中的数据
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            // 将从输入流中读取到的数据转化为字符串
            String xmlStr = buffer.toString();
            System.out.println("xmlStr = " + xmlStr);

            // 解析出明文xml标签的内容进行处理
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(xmlStr);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);

            Element root = document.getDocumentElement();
            // 支付状态
            NodeList nodelist1 = root.getElementsByTagName("return_code");
            String return_code = nodelist1.item(0).getTextContent();
            System.out.println("-----------------return_code：" + return_code);
            if (return_code.equals("SUCCESS")) {
                // 订单号
                NodeList nodelist2 = root.getElementsByTagName("out_trade_no");
                String out_trade_no = nodelist2.item(0).getTextContent();
                System.out.println("---------------out_trade_no：" + out_trade_no);
                // 总金额(分)
                NodeList nodelist3 = root.getElementsByTagName("total_fee");
                String total_fee = nodelist3.item(0).getTextContent();
                total_fee = Float.parseFloat(total_fee) / 100 + "";
                System.out.println("---------------total_fee：" + total_fee);
                // 修改订单状态
                changeOrderStatus(out_trade_no, response);

                System.out.println("---------------------修改订单状态成功");
            }

            result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            response.getWriter().write(result);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();

            WebUtil.printApi(response, new Result(false));
        }
    }

    /**
     * 改变订单状态
     *
     * @param orderNum
     * @return
     */
    private void changeOrderStatus(String orderNum, HttpServletResponse response) {
        try {
            Orders orders = ordersService.iFindOneByOrderNum(orderNum);
            orders.setStatus(Constant.ORDERS_STATUS_001);
            orders.setPayTime(new Date());

            ordersService.update(orders);

            WebUtil.printApi(response, new Result(true));
        } catch (Exception e) {
            e.printStackTrace();

            WebUtil.printApi(response, new Result(false));
        }
    }
}
