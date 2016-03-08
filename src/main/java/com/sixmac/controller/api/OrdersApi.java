package com.sixmac.controller.api;

import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Orders;
import com.sixmac.entity.Ordersinfo;
import com.sixmac.entity.Users;
import com.sixmac.service.*;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/orders")
public class OrdersApi {

    @Autowired
    private UsersService usersService;

    @Autowired
    private MerchantsService merchantsService;

    @Autowired
    private ShopcarService shopcarService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersinfoService ordersinfoService;

    @Autowired
    private ProductsService productsService;

    /**
     * 订单列表
     *
     * @param response
     * @param userId
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     Integer userId,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == userId || null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Orders> page = ordersService.iPage(userId, pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);
        WebUtil.printApi(response, new Result(true).data(dataMap));
    }

    /**
     * 确认订单
     *
     * @param response
     */
    @RequestMapping("confirmOrder")
    public void confirmOrder(HttpServletResponse response,
                             Integer userId,
                             Integer merchantId,
                             Integer couponId,
                             Integer payType,
                             Integer score,
                             String consignee,
                             String mobile,
                             String address,
                             String price,
                             String realPrice,
                             String demo,
                             String orderinfoList) {
        if (null == userId || null == consignee || null == mobile || null == address || null == payType || null == price || null == realPrice || null == orderinfoList) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Orders orders = new Orders();
        orders.setOrderNum(System.currentTimeMillis() + "");
        orders.setPayType(payType);
        orders.setUser(usersService.getById(userId));
        orders.setMerchant(merchantsService.getById(merchantId));
        orders.setConsignee(consignee);
        orders.setMobile(mobile);
        orders.setAddress(address);
        orders.setPrice(price);
        orders.setRealPrice(realPrice);
        orders.setDemo(demo);
        orders.setCreateTime(new Date());

        // 增加订单信息
        ordersService.create(orders);

        // 增加订单详情信息
        JSONArray orderinfos = JSONArray.fromObject(orderinfoList);
        Map<String, Object> mapInfo = null;
        Ordersinfo ordersinfo = null;
        for (Object orderMap : orderinfos) {
            // 获取单个订单详情
            mapInfo = JsonUtil.jsontoMap(orderMap);
            ordersinfo = new Ordersinfo();
            ordersinfo.setOrder(orders);
            if (null != mapInfo.get("type") && !mapInfo.get("type").equals("") && mapInfo.get("type").equals("1")) {
                // 当type为1时，表示传入的是商品，此时应该记录该商品所属商家信息
                // 当type为0时，表示传入的是秒杀，此时不记录商家信息
                ordersinfo.setMerchant(merchantsService.getById(Integer.parseInt(mapInfo.get("merchantId").toString())));
            }
            ordersinfo.setType(Integer.parseInt(mapInfo.get("type").toString()));
            ordersinfo.setProduct(productsService.getById(Integer.parseInt(mapInfo.get("id").toString())));
            ordersinfo.setProductName(mapInfo.get("name").toString());
            ordersinfo.setProductPath(mapInfo.get("path").toString());
            ordersinfo.setColors(mapInfo.get("colors").toString());
            ordersinfo.setSizes(mapInfo.get("sizes").toString());
            ordersinfo.setMaterials(mapInfo.get("materials").toString());
            ordersinfo.setPrice(mapInfo.get("price").toString());
            ordersinfo.setCount(Integer.parseInt(mapInfo.get("count").toString()));

            // 增加订单详情
            ordersinfoService.create(ordersinfo);

            // 判断是否传入了购物车id，如果传入，则表示该订单详情是从购物车中添加的，此时应该删除该购物车信息
            if (null != mapInfo.get("shopcarId") && !mapInfo.get("shopcarId").equals("")) {
                shopcarService.deleteById(Integer.parseInt(mapInfo.get("shopcarId").toString()));
            }
        }

        // 判断使用积分数，如果大于零，则表示使用了积分，此时应当减去对应用户的积分数
        if (score > 0) {
            Users users = usersService.getById(userId);
            users.setScore(users.getScore() - score);
            usersService.update(users);
        }

        // 判断couponId是否为空或为0，如果都不满足条件，则代表此次订单使用了优惠券，此时应当将对应用户的优惠券标记为已使用状态
        if (null != couponId && couponId != 0) {
            usersService.usedCoupon(userId, couponId);
        }

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 修改订单状态
     *
     * @param response
     * @param orderNum
     * @param status
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/updateOrders")
    public void updateOrders(HttpServletResponse response,
                             String orderNum,
                             Integer status,
                             Integer pageNum,
                             Integer pageSize) {
        if (null == orderNum || null == status || null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Orders orders = ordersService.iFindOneByOrderNum(orderNum);

        // 当status=1时，表示已经付款，此时应该更新当前订单状态和支付时间
        if (status == 1) {
            orders.setStatus(Constant.ORDERS_STATUS_001);
            orders.setPayTime(new Date());
        } else {
            orders.setStatus(Constant.ORDERS_STATUS_003);
        }

        ordersService.update(orders);

        WebUtil.printApi(response, new Result(true));
    }
}
