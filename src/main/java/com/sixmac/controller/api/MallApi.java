package com.sixmac.controller.api;

import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
import com.sixmac.service.*;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/mall")
public class MallApi {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private SpikesService spikesService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private PackagesService packagesService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private MerchantsService merchantsService;

    @Autowired
    private ShopcarService shopcarService;

    @Autowired
    private OrdersService ordersService;

    /**
     * 首页banner图列表
     *
     * @param response
     */
    @RequestMapping(value = "/bannerList")
    public void bannerList(HttpServletResponse response) {
        List<Banner> list = bannerService.findAll();

        WebUtil.printApi(response, new Result(true).data(list));
    }

    /**
     * 秒杀列表
     *
     * @param response
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/spikeList")
    public void spikeList(HttpServletResponse response,
                          Integer pageNum,
                          Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Spikes> page = spikesService.find(pageNum, pageSize);

        Map<java.lang.String, Object> dataMap = APIFactory.fitting(page);
        WebUtil.printApi(response, new Result(true).data(dataMap));
    }

    /**
     * 查看秒杀详情
     *
     * @param response
     * @param spikesId
     */
    @RequestMapping("spikeInfo")
    public void spikeInfo(HttpServletResponse response,
                          Integer spikesId) {
        if (null == spikesId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        Spikes spikes = spikesService.getById(spikesId);

        if (null == spikes) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, new Result(true).data(spikes));
    }

    /**
     * 商品列表
     *
     * @param response
     * @param type
     * @param name
     * @param brandId
     * @param sortId
     * @param isHot
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     Integer type,
                     String name,
                     Integer brandId,
                     Integer sortId,
                     Integer isHot,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Products> page = productsService.iPage(type, name, brandId, sortId, isHot, pageNum, pageSize);

        Map<java.lang.String, Object> dataMap = APIFactory.fitting(page);
        WebUtil.printApi(response, new Result(true).data(dataMap));
    }

    /**
     * 查看商品详情
     *
     * @param response
     * @param productId
     */
    @RequestMapping("info")
    public void info(HttpServletResponse response,
                     Integer productId) {
        if (null == productId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        Products products = productsService.getById(productId);

        if (null == products) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, new Result(true).data(products));
    }

    /**
     * 套餐列表
     *
     * @param response
     * @param brandId
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/packageList")
    public void packageList(HttpServletResponse response,
                            Integer brandId,
                            Integer pageNum,
                            Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Packages> page = packagesService.iPage(brandId, pageNum, pageSize);

        Map<java.lang.String, Object> dataMap = APIFactory.fitting(page);
        WebUtil.printApi(response, new Result(true).data(dataMap));
    }

    /**
     * 查看套餐详情
     *
     * @param response
     * @param packageId
     */
    @RequestMapping("packageInfo")
    public void packageInfo(HttpServletResponse response,
                            Integer packageId) {
        if (null == packageId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        Packages packages = packagesService.getById(packageId);

        if (null == packages) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, new Result(true).data(packages));
    }

    /**
     * 加入购物车
     *
     * @param response
     * @param userId
     * @param merchantId
     * @param productId
     * @param cover
     * @param name
     * @param colors
     * @param sizes
     * @param materials
     * @param price
     * @param count
     * @param packageId
     */
    @RequestMapping("addShopCar")
    public void addShopCar(HttpServletResponse response,
                           Integer userId,
                           Integer merchantId,
                           Integer productId,
                           String cover,
                           String name,
                           String colors,
                           String sizes,
                           String materials,
                           String price,
                           Integer count,
                           Integer packageId) {
        if (null == userId || null == merchantId || null == productId || null == cover || null == name || null == colors || null == sizes || null == materials || null == price || null == count || null == packageId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        Shopcar shopcar = new Shopcar();
        shopcar.setUser(usersService.getById(userId));
        shopcar.setMerchant(merchantsService.getById(merchantId));
        shopcar.setProduct(productsService.getById(productId));
        shopcar.setCover(cover);
        shopcar.setName(name);
        shopcar.setColors(colors);
        shopcar.setSizes(sizes);
        shopcar.setMaterials(materials);
        shopcar.setPrice(price);
        shopcar.setCount(count);

        shopcarService.create(shopcar);

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 删除购物车
     *
     * @param response
     * @param shopcarId
     */
    @RequestMapping("deleteShopCar")
    public void deleteShopCar(HttpServletResponse response,
                              Integer shopcarId) {
        if (null == shopcarId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Shopcar shopcar = shopcarService.getById(shopcarId);

        if (null == shopcar) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
        }

        shopcarService.deleteById(shopcarId);

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 清空购物车
     *
     * @param response
     * @param userId
     */
    @RequestMapping("cleanShopCar")
    public void cleanShopCar(HttpServletResponse response,
                             Integer userId) {
        if (null == userId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        shopcarService.iCleanAllByUserId(userId);

        WebUtil.printApi(response, new Result(true));
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
        if (null == userId || null == consignee || null == mobile || null == address || null == price || null == realPrice || null == orderinfoList) {
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

        ordersService.create(orders);

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
}
