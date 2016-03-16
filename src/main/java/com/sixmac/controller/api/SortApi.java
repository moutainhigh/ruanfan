package com.sixmac.controller.api;

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
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16 0016.
 */
@Controller
@RequestMapping(value = "api/sort")
public class SortApi {

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
     * 商品分类列表
     *
     * @param response
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/productTypeList")
    public void productTypeList(HttpServletResponse response,
                                Integer pageNum,
                                Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Producttype> page = producttypeService.find(pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "productNum");
        WebUtil.printApi(response, result);
    }

    /**
     * 品牌分类列表
     *
     * @param response
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/brandList")
    public void brandList(HttpServletResponse response,
                          Integer pageNum,
                          Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Brand> page = brandService.find(pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "productNum");
        WebUtil.printApi(response, result);
    }

    /**
     * 风格分类列表
     *
     * @param response
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/styleList")
    public void styleList(HttpServletResponse response,
                          Integer pageNum,
                          Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Styles> page = stylesService.find(pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "productNum");
        WebUtil.printApi(response, result);
    }

    /**
     * 灵感图区域列表
     *
     * @param response
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/afflatusAreaList")
    public void afflatusAreaList(HttpServletResponse response,
                                 Integer pageNum,
                                 Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Areas> page = areasService.find(pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "productNum");
        WebUtil.printApi(response, result);
    }

    /**
     * 虚拟体验分类列表
     *
     * @param response
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/virtualTypeList")
    public void virtualTypeList(HttpServletResponse response,
                                Integer pageNum,
                                Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Vrtype> page = vrtypeService.find(pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "productNum");
        WebUtil.printApi(response, result);
    }
}
