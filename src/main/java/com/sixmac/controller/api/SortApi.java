package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
import com.sixmac.service.*;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16 0016.
 */
@Controller
@RequestMapping(value = "api/sort")
public class SortApi extends CommonController {

    @Autowired
    private ProducttypeService producttypeService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private StylesService stylesService;

    @Autowired
    private AreasService areasService;

    @Autowired
    private VrtypeService vrtypeService;

    /**
     * @api {post} /api/sort/productTypeList 商品分类列表
     * @apiName sort.productTypeList
     * @apiGroup sort
     * @apiSuccess {Object} list 商品分类列表
     * @apiSuccess {Integer} list.id 分类id
     * @apiSuccess {String} list.name 分类名称
     * @apiSuccess {String} list.url 分类图标路径
     * @apiSuccess {String} list.updateTime 更新时间
     */
    @RequestMapping(value = "/productTypeList")
    public void productTypeList(HttpServletResponse response) {
        List<Producttype> list = producttypeService.findAll();

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj, "productNum");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/sort/brandList 品牌分类列表
     * @apiName sort.brandList
     * @apiGroup sort
     * @apiSuccess {Object} list 品牌分类列表
     * @apiSuccess {Integer} list.id 品牌id
     * @apiSuccess {String} list.name 品牌名称
     * @apiSuccess {String} list.cover 品牌图标路径
     * @apiSuccess {String} list.updateTime 更新时间
     */
    @RequestMapping(value = "/brandList")
    public void brandList(HttpServletResponse response) {
        List<Brand> list = brandService.findAll();

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj, "productNum");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/sort/styleList 风格分类列表
     * @apiName sort.styleList
     * @apiGroup sort
     * @apiSuccess {Object} list 风格分类列表
     * @apiSuccess {Integer} list.id 风格id
     * @apiSuccess {String} list.name 风格名称
     * @apiSuccess {String} list.backImg 风格背景图
     * @apiSuccess {String} list.updateTime 更新时间
     */
    @RequestMapping(value = "/styleList")
    public void styleList(HttpServletResponse response) {
        List<Styles> list = stylesService.findAll();

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj, "productNum");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/sort/afflatusAreaList 灵感图区域列表
     * @apiName sort.afflatusAreaList
     * @apiGroup sort
     * @apiSuccess {Object} list 灵感图区域列表
     * @apiSuccess {Integer} list.id 区域id
     * @apiSuccess {String} list.name 区域名称
     * @apiSuccess {String} list.updateTime 更新时间
     */
    @RequestMapping(value = "/afflatusAreaList")
    public void afflatusAreaList(HttpServletResponse response) {
        List<Areas> list = areasService.findAll();

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj, "productNum");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/sort/virtualTypeList 虚拟体验分类列表
     * @apiName sort.virtualTypeList
     * @apiGroup sort
     * @apiSuccess {Object} list 虚拟体验分类列表
     * @apiSuccess {Integer} list.id 分类id
     * @apiSuccess {String} list.name 分类名称
     * @apiSuccess {String} list.updateTime 更新时间
     */
    @RequestMapping(value = "/virtualTypeList")
    public void virtualTypeList(HttpServletResponse response) {
        List<Vrtype> list = vrtypeService.findAll();

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj, "productNum");
        WebUtil.printApi(response, result);
    }
}
