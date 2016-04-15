package com.sixmac.controller.api;

import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
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

    @Autowired
    private SpikesService spikesService;

    /**
     * @api {post} /api/orders/list 订单列表
     * @apiName orders.list
     * @apiGroup orders
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数       <必传 />
     * @apiSuccess {Object} list 订单列表
     * @apiSuccess {Integer} list.id 订单id
     * @apiSuccess {String} list.orderNum 订单流水号
     * @apiSuccess {Integer} list.type 订单类型，1=商品订单，2=套餐订单，3=秒杀订单
     * @apiSuccess {Integer} list.payType 支付方式，1=支付宝，2=微信
     * @apiSuccess {String} list.payTime 支付时间
     * @apiSuccess {String} list.consignee 收货人
     * @apiSuccess {String} list.mobile 收货人电话
     * @apiSuccess {String} list.address 收货地址
     * @apiSuccess {String} list.price 订单金额
     * @apiSuccess {String} list.realPrice 实付金额
     * @apiSuccess {String} list.demo 备注
     * @apiSuccess {Integer} list.status 状态，0=待付款，1=待发货，2=待确认，3=待评价，4=已完成
     * @apiSuccess {String} list.createTime 下单时间
     * @apiSuccess {Object} list.orderInfoList 订单详情列表
     * @apiSuccess {Integer} list.orderInfoList.id 订单详情id
     * @apiSuccess {Integer} list.orderInfoList.type 类型，1=商品，2=秒杀，3=套餐
     * @apiSuccess {Integer} list.orderInfoList.productId 商品id or 秒杀id
     * @apiSuccess {String} list.orderInfoList.productName 名称
     * @apiSuccess {String} list.orderInfoList.productPath 图片
     * @apiSuccess {String} list.orderInfoList.colors 颜色
     * @apiSuccess {String} list.orderInfoList.sizes 尺寸
     * @apiSuccess {String} list.orderInfoList.materials 材质
     * @apiSuccess {String} list.orderInfoList.price 单价
     * @apiSuccess {Integer} list.orderInfoList.count 数量
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

        for (Orders order : page.getContent()) {
            // 根据订单id查询订单详情list
            order.setOrderInfoList(ordersinfoService.findListByOrderId(order.getId()));
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user", "merchant", "order", "product", "star", "comment");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/orders/confirmOrder 确认订单
     * @apiName orders.confirmOrder
     * @apiGroup orders
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {Integer} merchantId 商户id   当商品全部是同一家商户时，传入商户id；如果商品来自多家，则不传
     * @apiParam {Integer} couponId 优惠券id
     * @apiParam {Integer} type 订单类型，1=商品订单，2=套餐订单，3=秒杀订单       <必传 />
     * @apiParam {Integer} payType 支付方式，1=支付宝，2=微信       <必传 />
     * @apiParam {Integer} score 使用积分数
     * @apiParam {String} consignee 收货人       <必传 />
     * @apiParam {String} mobile 联系方式       <必传 />
     * @apiParam {String} address 地址       <必传 />
     * @apiParam {String} price 金额       <必传 />
     * @apiParam {String} realPrice 实付金额       <必传 />
     * @apiParam {String} demo 备注
     * @apiParam {Object} orderInfoList 订单详情（json格式字符串）       <必传 />
     * @apiParam {Integer} orderInfoList.shopCarId 购物车id
     * @apiParam {Integer} orderInfoList.merchantId 商品所属商户id，type为1时传入，type为2时不传
     * @apiParam {Integer} orderInfoList.type 类型，1=商品，2=秒杀       <必传 />
     * @apiParam {Integer} orderInfoList.id 商品id or 秒杀id       <必传 />
     * @apiParam {String} orderInfoList.name 名称       <必传 />
     * @apiParam {String} orderInfoList.path 图片路径       <必传 />
     * @apiParam {String} orderInfoList.colors 颜色       <必传 />
     * @apiParam {String} orderInfoList.sizes 尺寸       <必传 />
     * @apiParam {String} orderInfoList.materials 材质       <必传 />
     * @apiParam {String} orderInfoList.price 价格       <必传 />
     * @apiParam {Integer} orderInfoList.count 数量       <必传 />
     */
    @RequestMapping("confirmOrder")
    public void confirmOrder(HttpServletResponse response,
                             Integer userId,
                             Integer merchantId,
                             Integer couponId,
                             Integer type,
                             Integer payType,
                             Integer score,
                             String consignee,
                             String mobile,
                             String address,
                             String price,
                             String realPrice,
                             String demo,
                             String orderInfoList) {
        if (null == userId || null == type || null == consignee || null == mobile || null == address || null == payType || null == price || null == realPrice || null == orderInfoList) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Orders orders = new Orders();
        orders.setOrderNum(System.currentTimeMillis() + "");
        orders.setPayType(payType);
        orders.setUser(usersService.getById(userId));
        if (null != merchantId) {
            orders.setMerchant(merchantsService.getById(merchantId));
        }
        orders.setConsignee(consignee);
        orders.setType(type);
        orders.setMobile(mobile);
        orders.setAddress(address);
        orders.setPrice(price);
        orders.setRealPrice(realPrice);
        orders.setDemo(demo);
        orders.setStatus(0);
        orders.setCreateTime(new Date());

        // 增加订单信息
        ordersService.create(orders);

        // 增加订单详情信息
        JSONArray orderinfos = JSONArray.fromObject(orderInfoList);
        Map<String, Object> mapInfo = null;
        Ordersinfo ordersinfo = null;
        Products products = null;
        Spikes spikes = null;
        for (Object orderMap : orderinfos) {
            // 获取单个订单详情
            mapInfo = JsonUtil.jsontoMap(orderMap);
            ordersinfo = new Ordersinfo();
            ordersinfo.setOrder(orders);
            if (null != mapInfo.get("type") && !mapInfo.get("type").equals("") && mapInfo.get("type").toString().equals("1")) {
                // 当type为1时，表示传入的是商品，此时应该记录该商品所属商家信息
                // 当type为2时，表示传入的是秒杀，此时不记录商家信息
                ordersinfo.setMerchant(merchantsService.getById(Integer.parseInt(mapInfo.get("merchantId").toString())));
            }
            ordersinfo.setType(Integer.parseInt(mapInfo.get("type").toString()));

            if (null != mapInfo.get("type") && !mapInfo.get("type").equals("") && mapInfo.get("type").toString().equals("1")) {
                products = productsService.getById(Integer.parseInt(mapInfo.get("id").toString()));
            } else {
                spikes = spikesService.getById(Integer.parseInt(mapInfo.get("id").toString()));
            }

            ordersinfo.setProduct(products);
            ordersinfo.setProductName(mapInfo.get("name").toString());
            ordersinfo.setProductPath(mapInfo.get("path").toString());
            ordersinfo.setColors(mapInfo.get("colors").toString());
            ordersinfo.setSizes(mapInfo.get("sizes").toString());
            ordersinfo.setMaterials(mapInfo.get("materials").toString());
            ordersinfo.setPrice(mapInfo.get("price").toString());
            ordersinfo.setCount(Integer.parseInt(mapInfo.get("count").toString()));

            // 增加订单详情
            ordersinfoService.create(ordersinfo);

            if (null != mapInfo.get("type") && !mapInfo.get("type").equals("") && mapInfo.get("type").toString().equals("1")) {
                // 修改商品交易数量
                products.setCount(products.getCount() + ordersinfo.getCount());
                productsService.update(products);
            } else {
                // 修改秒杀交易数量
                spikes.setCount(spikes.getCount() + ordersinfo.getCount());
                spikesService.update(spikes);
            }

            // 判断是否传入了购物车id，如果传入，则表示该订单详情是从购物车中添加的，此时应该删除该购物车信息
            if (null != mapInfo.get("shopCarId") && !mapInfo.get("shopCarId").equals("")) {
                shopcarService.deleteById(Integer.parseInt(mapInfo.get("shopCarId").toString()));
            }
        }

        // 判断使用积分数，如果大于零，则表示使用了积分，此时应当减去对应用户的积分数
        if (null != score && score > 0) {
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
     * @api {post} /api/orders/updateOrders 修改订单状态
     * @apiName orders.updateOrders
     * @apiGroup orders
     * @apiParam {String} orderNum 订单流水号       <必传 />
     * @apiParam {Integer} status 订单状态， 1=付款，3=确认收货       <必传 />
     */
    @RequestMapping(value = "/updateOrders")
    public void updateOrders(HttpServletResponse response,
                             String orderNum,
                             Integer status) {
        if (null == orderNum || null == status) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        try {
            Orders orders = ordersService.iFindOneByOrderNum(orderNum);

            if (null == orders) {
                WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0022));
                return;
            }

            // 当status=1时，表示已经付款，此时应该更新当前订单状态和支付时间
            if (status == 1) {
                orders.setStatus(Constant.ORDERS_STATUS_001);
                orders.setPayTime(new Date());
            } else {
                orders.setStatus(Constant.ORDERS_STATUS_003);
            }

            ordersService.update(orders);

            WebUtil.printApi(response, new Result(true));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
        }
    }

    /*/**
     * @api {post} /api/orders/commentOrder 评价订单
     * @apiName orders.commentOrder
     * @apiGroup orders
     * @apiParam {Integer} ordersInfoId 订单详情id       <必传 />
     */
    /*@RequestMapping(value = "/commentOrder")
    public void commentOrder(HttpServletResponse response,
                             Integer ordersInfoId,
                             Integer star,
                             String content) {
        if (null == ordersInfoId || null == star || !StringUtils.isNotBlank(content)) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        try {
            Ordersinfo ordersinfo = ordersinfoService.getById(ordersInfoId);

            if (null == ordersinfo) {
                WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0022));
                return;
            }

            ordersinfo.setStar(star);
            ordersinfo.setComment(content);

            ordersinfoService.update(ordersinfo);

            WebUtil.printApi(response, new Result(true));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
        }
    }*/
}
