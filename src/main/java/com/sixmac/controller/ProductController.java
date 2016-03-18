package com.sixmac.controller;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Products;
import com.sixmac.service.CityService;
import com.sixmac.service.ProductsService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
@Controller
@RequestMapping(value = "product")
public class ProductController extends CommonController {

    @Autowired
    private ProductsService productService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "商品列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String name,
                     String merchantName,
                     Integer isCheck,
                     Integer type,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Products> page = productService.page(name, merchantName, isCheck, type, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Products product = productService.getById(id);
            model.addAttribute("product", product);
        }

        return "新增商品";
    }

    /**
     * 启用 or 禁用商品
     *
     * @param designerId
     * @param status
     * @return
     */
    @RequestMapping("/changeStatus")
    @ResponseBody
    public Integer changeStatus(Integer designerId, Integer status) {
        try {
            Products product = productService.getById(designerId);
            product.setStatus(status);

            productService.update(product);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 推荐到MALL or 取消推荐
     *
     * @param productId
     * @param isHot
     * @return
     */
    @RequestMapping("/changeHot")
    @ResponseBody
    public Integer changeHot(Integer productId, Integer isHot) {
        try {
            Products product = productService.getById(productId);
            product.setIsHot(isHot);

            productService.update(product);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 审核商品
     *
     * @param productId
     * @param isCheck
     * @param reason
     * @return
     */
    @RequestMapping("/changeCheck")
    @ResponseBody
    public Integer changeCheck(Integer productId, Integer isCheck, String reason) {
        try {
            productService.changeCheck(productId, isCheck, reason);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除商品信息
     *
     * @param designerId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer designerId) {
        try {
            productService.deleteById(designerId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增商品信息
     *
     * @param request
     * @param id
     * @param mobile
     * @param password
     * @param type
     * @param nickName
     * @param cityId
     * @param content
     * @param multipartRequest
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(ServletRequest request,
                        Integer id,
                        String mobile,
                        String password,
                        Integer type,
                        String nickName,
                        Integer cityId,
                        String content,
                        MultipartRequest multipartRequest) {
        try {
            Products product = null;

            if (null == id) {
                product = new Products();
            } else {
                product = productService.getById(id);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
