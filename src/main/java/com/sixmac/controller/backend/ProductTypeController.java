package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Producttype;
import com.sixmac.service.ProducttypeService;
import com.sixmac.utils.ImageUtil;
import com.sixmac.utils.QiNiuUploadImgUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/15 0015.
 */
@Controller
@RequestMapping(value = "backend/productType")
public class ProductTypeController extends CommonController {

    @Autowired
    private ProducttypeService producttypeService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "backend/商品种类";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Producttype> page = producttypeService.find(pageNum, length);

        // 循环查找每个商品分类的关联商品数量
        for (Producttype proType : page.getContent()) {
            proType.setProductNum(producttypeService.findProductListByProductTypeId(proType.getId()).size());
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    /**
     * 删除商品种类
     *
     * @param productTypeId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer productTypeId) {
        try {
            producttypeService.deleteById(productTypeId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增商品种类
     *
     * @param request
     * @param id
     * @param name
     * @param multipartRequest
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(ServletRequest request, Integer id, String name, MultipartRequest multipartRequest) {
        try {
            Producttype producttype = null;

            if (null != id) {
                producttype = producttypeService.getById(id);
            } else {
                producttype = new Producttype();
            }

            producttype.setName(name);
            producttype.setUpdateTime(new Date());

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                String url = QiNiuUploadImgUtil.upload(multipartFile);
                producttype.setUrl(url);
            }

            if (null != id) {
                producttypeService.update(producttype);
            } else {
                producttypeService.create(producttype);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
