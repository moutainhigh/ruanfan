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
     * 购物车列表
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
        if (null == pageNum || null == pageSize) {
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
            return;
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
}
