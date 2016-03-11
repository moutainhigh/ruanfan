package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
import com.sixmac.service.*;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/mall")
public class MallApi extends CommonController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private SpikesService spikesService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private PackagesService packagesService;

    @Autowired
    private ImageService imageService;

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

        for (Spikes spikes : page.getContent()) {
            spikes.setCover(imageService.getById(spikes.getCoverId()).getPath());
        }

        Map<java.lang.String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "coverId");
        WebUtil.printApi(response, result);
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
            return;
        }

        spikes.setCover(imageService.getById(spikes.getCoverId()).getPath());

        Result obj = new Result(true).data(spikes);
        String result = JsonUtil.obj2ApiJson(obj, "coverId");
        WebUtil.printApi(response, result);
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

        for (Products products : page.getContent()) {
            products.setCover(imageService.getById(products.getCoverId()).getPath());
            products.setMerchantId(products.getMerchant().getId());
            products.setMerchantName(products.getMerchant().getNickName());
            products.setBrandId(products.getBrand().getId());
            products.setBrandName(products.getBrand().getName());
            products.setSortId(products.getSort().getId());
            products.setSortName(products.getSort().getName());
        }

        Map<java.lang.String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "merchant", "brand", "sort", "coverId", "isHot", "isCheck", "status");
        WebUtil.printApi(response, result);
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
            return;
        }

        products.setCover(imageService.getById(products.getCoverId()).getPath());
        products.setMerchantId(products.getMerchant().getId());
        products.setMerchantName(products.getMerchant().getNickName());
        products.setBrandId(products.getBrand().getId());
        products.setBrandName(products.getBrand().getName());
        products.setSortId(products.getSort().getId());
        products.setSortName(products.getSort().getName());

        Result obj = new Result(true).data(createMap("productInfo", products));
        String result = JsonUtil.obj2ApiJson(obj, "merchant", "brand", "sort", "coverId", "isHot", "isCheck", "status");
        WebUtil.printApi(response, result);
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

        // 获取该套餐对应的所有商品
        List<Packageproducts> list = null;
        List<Products> productList = new ArrayList<Products>();

        for (Packages packages : page.getContent()) {
            // 设置封面图路径和品牌信息
            packages.setCover(imageService.getById(packages.getCoverId()).getPath());
            packages.setBrandId(packages.getBrand().getId());
            packages.setBrandName(packages.getBrand().getName());

            // 获取商品列表
            list = packagesService.findListByPackageId(packages.getId());
            for (Packageproducts pack : list) {
                productList.add(pack.getProduct());
            }

            for (Products product : productList) {
                product.setCover(imageService.getById(product.getCoverId()).getPath());
                product.setMerchantId(product.getMerchant().getId());
                product.setMerchantName(product.getMerchant().getNickName());
                product.setBrandId(product.getBrand().getId());
                product.setBrandName(product.getBrand().getName());
                product.setSortId(product.getSort().getId());
                product.setSortName(product.getSort().getName());
            }

            packages.setProductsList(productList);

            productList = new ArrayList<Products>();
        }

        Map<java.lang.String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "merchant", "brand", "sort", "coverId", "isHot", "isCheck", "status");
        WebUtil.printApi(response, result);
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
            return;
        }

        // 设置封面图路径和品牌信息
        packages.setCover(imageService.getById(packages.getCoverId()).getPath());
        packages.setBrandId(packages.getBrand().getId());
        packages.setBrandName(packages.getBrand().getName());

        List<Products> productList = new ArrayList<Products>();

        // 获取商品列表
        List<Packageproducts> list = packagesService.findListByPackageId(packages.getId());
        for (Packageproducts pack : list) {
            productList.add(pack.getProduct());
        }

        for (Products product : productList) {
            product.setCover(imageService.getById(product.getCoverId()).getPath());
            product.setMerchantId(product.getMerchant().getId());
            product.setMerchantName(product.getMerchant().getNickName());
            product.setBrandId(product.getBrand().getId());
            product.setBrandName(product.getBrand().getName());
            product.setSortId(product.getSort().getId());
            product.setSortName(product.getSort().getName());
        }

        packages.setProductsList(productList);

        Result obj = new Result(true).data(createMap("packageInfo", packages));
        String result = JsonUtil.obj2ApiJson(obj, "merchant", "brand", "sort", "coverId", "isHot", "isCheck", "status");
        WebUtil.printApi(response, result);
    }
}
