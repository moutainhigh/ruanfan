package com.sixmac.controller.api;

import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Banner;
import com.sixmac.entity.Packages;
import com.sixmac.entity.Products;
import com.sixmac.entity.Spikes;
import com.sixmac.service.BannerService;
import com.sixmac.service.PackagesService;
import com.sixmac.service.ProductsService;
import com.sixmac.service.SpikesService;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
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
}
