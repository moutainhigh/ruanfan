package com.sixmac.controller.api;

import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Shopcar;
import com.sixmac.service.MerchantsService;
import com.sixmac.service.ProductsService;
import com.sixmac.service.ShopcarService;
import com.sixmac.service.UsersService;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/shopCar")
public class ShopCarApi {

    @Autowired
    private ShopcarService shopcarService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private MerchantsService merchantsService;

    @Autowired
    private ProductsService productsService;

    /**
     * @api {post} /api/shopCar/list 购物车列表
     * @apiName shopCar.list
     * @apiGroup shopCar
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数       <必传 />
     * @apiSuccess {Object} list 购物车列表
     * @apiSuccess {Integer} list.id 购物车id
     * @apiSuccess {String} list.cover 商品封面图
     * @apiSuccess {String} list.name 商品名称
     * @apiSuccess {String} list.colors 颜色
     * @apiSuccess {String} list.sizes 尺寸
     * @apiSuccess {String} list.materials 材质
     * @apiSuccess {String} list.price 价格
     * @apiSuccess {Integer} list.count 数量
     * @apiSuccess {Integer} list.merchantId 所属商户id
     * @apiSuccess {Integer} list.productId 商品id
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

        Page<Shopcar> page = shopcarService.iPage(userId, pageNum, pageSize);

        for (Shopcar shopcar : page.getContent()) {
            shopcar.setMerchantId(shopcar.getMerchant().getId());
            shopcar.setProductId(shopcar.getProduct().getId());
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user", "merchant", "product");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/shopCar/addShopCar 加入购物车
     * @apiName shopCar.addShopCar
     * @apiGroup shopCar
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {Integer} merchantId 所属商户id       <必传 />
     * @apiParam {Integer} productId 商品id       <必传 />
     * @apiParam {String} cover 商品封面图       <必传 />
     * @apiParam {String} name 商品名称       <必传 />
     * @apiParam {String} colors 颜色       <必传 />
     * @apiParam {String} sizes 尺寸       <必传 />
     * @apiParam {String} materials 材质       <必传 />
     * @apiParam {String} price 价格       <必传 />
     * @apiParam {Integer} count 数量       <必传 />
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
                           Integer count) {
        if (null == userId || null == merchantId || null == productId || null == cover || null == name || null == colors || null == sizes || null == materials || null == price || null == count) {
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
     * @api {post} /api/shopCar/deleteShopCar 删除购物车
     * @apiName shopCar.deleteShopCar
     * @apiGroup shopCar
     * @apiParam {Integer} shopCarId 购物车id       <必传 />
     */
    @RequestMapping("deleteShopCar")
    public void deleteShopCar(HttpServletResponse response,
                              Integer shopCarId) {
        if (null == shopCarId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Shopcar shopcar = shopcarService.getById(shopCarId);

        if (null == shopcar) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        shopcarService.deleteById(shopCarId);

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * @api {post} /api/shopCar/cleanShopCar 清空购物车
     * @apiName shopCar.cleanShopCar
     * @apiGroup shopCar
     * @apiParam {Integer} userId 用户id       <必传 />
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
}
