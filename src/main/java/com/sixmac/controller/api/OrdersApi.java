package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
import com.sixmac.service.*;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/orders")
public class OrdersApi extends CommonController {

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

    @Autowired
    private CouponService couponService;

    @Autowired
    private PackagesService packagesService;

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
     * @apiParam {String} couponIds 优惠券id（中间用英文逗号隔开）
     * @apiParam {Integer} type 订单类型，1=商品订单，2=套餐订单，3=秒杀订单       <必传 />
     * @apiParam {Integer} packageId 套餐id，当订单类型为套餐订单时传入
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
     * @apiParam {Integer} orderInfoList.merchantId 商品所属商户id，type为1时传入
     * @apiParam {Integer} orderInfoList.type 类型，1=商品，2=套餐，3=秒杀       <必传 />
     * @apiParam {Integer} orderInfoList.id 商品id or 秒杀id（如果是秒杀商品，此处传入秒杀id，否则传入商品id）      <必传 />
     * @apiParam {String} orderInfoList.name 名称       <必传 />
     * @apiParam {String} orderInfoList.path 图片路径       <必传 />
     * @apiParam {String} orderInfoList.colors 颜色       <必传 />
     * @apiParam {String} orderInfoList.sizes 尺寸       <必传 />
     * @apiParam {String} orderInfoList.materials 材质       <必传 />
     * @apiParam {String} orderInfoList.price 价格       <必传 />
     * @apiParam {Integer} orderInfoList.count 数量       <必传 />
     * @apiSuccess {Object} orderInfo 订单详情
     * @apiSuccess {Integer} orderInfo.id 订单id
     * @apiSuccess {String} orderInfo.orderNum 订单流水号
     */
    @RequestMapping("confirmOrder")
    public void confirmOrder(HttpServletResponse response,
                             Integer userId,
                             Integer packageId,
                             String couponIds,
                             Integer type,
                             Integer payType,
                             Integer score,
                             String consignee,
                             String mobile,
                             String address,
                             String demo,
                             String orderInfoList) {
        if (null == userId || null == type || null == consignee || null == mobile || null == address || null == payType || null == orderInfoList) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Orders orders = null;

        // 当订单类型为商品订单时，根据商家id拆分为不用的商品订单
        if (type == 1) {
            List<String> mapList = new ArrayList<String>();
            Ordersinfo ordersinfo = null;
            Products product = null;
            JSONArray orderinfos = JSONArray.fromObject(orderInfoList);
            Map<String, Object> mapInfo = null;
            for (Object orderMap : orderinfos) {
                // 获取单个订单详情
                mapInfo = JsonUtil.jsontoMap(orderMap);

                mapList.add(mapInfo.get("merchantId").toString());
            }

            // 获取优惠券总金额
            Double allCouponPrice = 0.0;
            if (StringUtils.isNotBlank(couponIds)) {
                couponIds += ",";
                String[] couIds = couponIds.split(",");
                for (String couponId : couIds) {
                    usersService.usedCoupon(userId, Integer.parseInt(couponId));
                    allCouponPrice += Double.parseDouble(couponService.getById(Integer.parseInt(couponId)).getMoney());
                }
            }

            // 获取积分兑换的总金额
            Double allScoreMoney = 0.0;

            if (null != score) {
                allScoreMoney = Double.parseDouble((score / Constant.scoreMoney) + "");
            }

            // 订单总额
            Double allPrice = 0.0;

            // 循环读取商户id集合，依此根据商户id生成对应的商家订单
            Users users = usersService.getById(userId);

            for (String merchantIdStr : mapList) {
                orders = new Orders();
                orders.setOrderNum(System.currentTimeMillis() + "");
                orders.setPayType(payType);
                orders.setUser(users);
                orders.setMerchant(merchantsService.getById(Integer.parseInt(merchantIdStr)));
                orders.setConsignee(consignee);
                orders.setType(type);
                orders.setMobile(mobile);
                orders.setAddress(address);
                orders.setDemo(demo);
                orders.setStatus(0);
                orders.setCreateTime(new Date());

                // 增加订单信息
                ordersService.create(orders);

                for (Object orderMap : orderinfos) {
                    // 获取单个订单详情
                    mapInfo = JsonUtil.jsontoMap(orderMap);
                    if (mapInfo.get("merchantId").toString().equals(merchantIdStr)) {
                        ordersinfo = new Ordersinfo();
                        ordersinfo.setOrder(orders);
                        ordersinfo.setMerchant(merchantsService.getById(Integer.parseInt(merchantIdStr)));
                        ordersinfo.setType(Integer.parseInt(mapInfo.get("type").toString()));
                        product = productsService.getById(Integer.parseInt(mapInfo.get("id").toString()));
                        ordersinfo.setProductId(product.getId());
                        ordersinfo.setProductName(mapInfo.get("name").toString());
                        ordersinfo.setProductPath(mapInfo.get("path").toString());
                        ordersinfo.setColors(mapInfo.get("colors").toString());
                        ordersinfo.setSizes(mapInfo.get("sizes").toString());
                        ordersinfo.setMaterials(mapInfo.get("materials").toString());
                        ordersinfo.setPrice(mapInfo.get("price").toString());
                        ordersinfo.setCount(Integer.parseInt(mapInfo.get("count").toString()));

                        // 增加订单详情
                        ordersinfoService.create(ordersinfo);

                        // 修改商品交易数量
                        product.setCount(product.getCount() + ordersinfo.getCount());
                        productsService.update(product);

                        // 判断是否传入了购物车id，如果传入，则表示该订单详情是从购物车中添加的，此时应该删除该购物车信息
                        if (null != mapInfo.get("shopCarId") && !mapInfo.get("shopCarId").equals("")) {
                            Shopcar shopcar = shopcarService.getById(Integer.parseInt(mapInfo.get("shopCarId").toString()));
                            if (null != shopcar) {
                                shopcarService.deleteById(shopcar.getId());
                            }
                        }

                        allPrice += Double.parseDouble(ordersinfo.getPrice()) * ordersinfo.getCount();
                    }
                }

                // 回写订单金额和实付金额
                orders.setPrice(allPrice.toString());
                orders.setRealPrice((allPrice - allCouponPrice / mapList.size() - allScoreMoney / mapList.size()) + "");
                ordersService.update(orders);

                // 用户将根据订单额获取等值的积分
                double doubleScore = Double.parseDouble(orders.getRealPrice());
                users.setScore(users.getScore() + (int) doubleScore);
                usersService.update(users);
            }
        } else {
            // 当订单类型为秒杀订单或套餐订单时，直接生成同一张订单
            Users tempUser = usersService.getById(userId);

            orders = new Orders();
            orders.setOrderNum(System.currentTimeMillis() + "");
            orders.setPayType(payType);
            orders.setUser(tempUser);
            orders.setConsignee(consignee);
            orders.setType(type);
            orders.setMobile(mobile);
            orders.setAddress(address);
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
            Double price = 0.0;

            for (Object orderMap : orderinfos) {
                // 获取单个订单详情
                mapInfo = JsonUtil.jsontoMap(orderMap);
                ordersinfo = new Ordersinfo();
                ordersinfo.setOrder(orders);
                ordersinfo.setType(Integer.parseInt(mapInfo.get("type").toString()));

                // type=2为套餐，type=3为秒杀
                if (mapInfo.get("type").toString().equals("2")) {
                    products = productsService.getById(Integer.parseInt(mapInfo.get("id").toString()));
                    ordersinfo.setProductId(products.getId());
                } else {
                    spikes = spikesService.getById(Integer.parseInt(mapInfo.get("id").toString()));
                    ordersinfo.setProductId(spikes.getId());
                }

                ordersinfo.setProductName(mapInfo.get("name").toString());
                ordersinfo.setProductPath(mapInfo.get("path").toString());
                ordersinfo.setColors(mapInfo.get("colors").toString());
                ordersinfo.setSizes(mapInfo.get("sizes").toString());
                ordersinfo.setMaterials(mapInfo.get("materials").toString());
                ordersinfo.setPrice(mapInfo.get("price").toString());
                ordersinfo.setCount(Integer.parseInt(mapInfo.get("count").toString()));

                // 增加订单详情
                ordersinfoService.create(ordersinfo);

                if (mapInfo.get("type").toString().equals("2")) {
                    // 修改套餐中商品的交易数量
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

                // 计算订单总金额和实付金额
                price += Double.parseDouble(ordersinfo.getPrice()) * ordersinfo.getCount();
            }

            orders.setPrice(price.toString());
            // 获取积分兑换的总金额
            Double allScoreMoney = 0.0;

            if (null != score) {
                allScoreMoney = Double.parseDouble((score / Constant.scoreMoney) + "");
            }
            // 获取减免金额
            Double reducePrice = 0.0;
            if (StringUtils.isNotBlank(couponIds)) {
                couponIds += ",";
                int[] couIds = JsonUtil.json2Obj(couponIds, int[].class);
                for (int couponId : couIds) {
                    usersService.usedCoupon(userId, couponId);
                    reducePrice += Double.parseDouble(couponService.getById(couponId).getMoney());
                }
            }
            orders.setRealPrice((price - reducePrice - allScoreMoney) + "");

            // 回写订单总金额和实付金额
            ordersService.update(orders);

            // 如果是套餐订单，修改套餐的交易数量
            if (type == 2) {
                Packages packages = packagesService.getById(packageId);

                if (null != packages) {
                    packages.setCount(packages.getCount() + 1);
                    packagesService.update(packages);
                }
            }

            // 用户将根据订单额获取等值的积分
            double doubleScore = Double.parseDouble(orders.getRealPrice());
            tempUser.setScore(tempUser.getScore() + (int) doubleScore);
            usersService.update(tempUser);
        }

        // 判断使用积分数，如果大于零，则表示使用了积分，此时应当减去对应用户的积分数
        if (null != score && score > 0) {
            Users users = usersService.getById(userId);
            users.setScore(users.getScore() - score);
            usersService.update(users);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orders.getId());
        map.put("orderNum", orders.getOrderNum());

        WebUtil.printApi(response, new Result(true).data(createMap("orderInfo", map)));
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
}
