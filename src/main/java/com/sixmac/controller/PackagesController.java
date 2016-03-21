package com.sixmac.controller;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Image;
import com.sixmac.entity.Packageproducts;
import com.sixmac.entity.Packages;
import com.sixmac.service.*;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Controller
@RequestMapping(value = "packages")
public class PackagesController extends CommonController {

    @Autowired
    private PackagesService packagesService;

    @Autowired
    private PackageproductsService packageProductsService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductsService productService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "套餐列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String name,
                     Integer brandId,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Packages> page = packagesService.page(name, brandId, pageNum, length);

        for (Packages packages : page.getContent()) {
            // 获取商品套餐所包含的商品数量
            packages.setProductNum(packageProductsService.findListByPackageId(packages.getId()).size());
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        StringBuffer sb = new StringBuffer("");

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Packages packages = packagesService.getById(id);
            model.addAttribute("packages", packages);

            if (null != packages) {
                List<Packageproducts> packageproductsList = packageProductsService.findListByPackageId(id);
                for (Packageproducts packageProduct : packageproductsList) {
                    map = new HashMap<String, Object>();
                    map.put("id", packageProduct.getProduct().getCoverId());
                    map.put("path", imageService.getById(packageProduct.getProduct().getCoverId()).getPath());

                    list.add(map);

                    sb.append(packageProduct.getProduct().getCoverId() + ",");
                }
            }
        }

        model.addAttribute("imageIds", sb.toString());
        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "新增套餐";
    }

    /**
     * 删除套餐信息
     *
     * @param packagesId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer packagesId) {
        try {
            packagesService.deleteById(packagesId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增商品套餐信息
     *
     * @param id
     * @param name
     * @param price
     * @param oldPrice
     * @param coverId
     * @param labels
     * @param content
     * @param tempAddImageIds
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(Integer id,
                        String name,
                        String price,
                        String oldPrice,
                        Integer coverId,
                        Integer brandId,
                        String labels,
                        String content,
                        String tempAddImageIds) {
        try {
            String[] addImageIds = tempAddImageIds.split(",");
            Packages packages = null;

            if (null == id) {
                packages = new Packages();
            } else {
                packages = packagesService.getById(id);
            }

            packages.setName(name);
            packages.setPrice(price);
            packages.setOldPrice(oldPrice);
            packages.setBrand(brandService.getById(brandId));
            packages.setLabels(labels);
            packages.setCoverId(coverId);
            packages.setDescription(content);

            if (null == id) {
                packages.setCount(0);
                packages.setCreateTime(new Date());
                packagesService.create(packages);
            } else {
                packagesService.update(packages);
            }

            // 删除该商品套餐关联的所有商品信息
            packageProductsService.deleteByPackageId(packages.getId());

            // 保存新的商品关联信息
            Image image = null;
            Packageproducts packageProduct = null;
            for (String imageId : addImageIds) {
                if (null != imageId && !imageId.equals("")) {
                    packageProduct = new Packageproducts();
                    image = imageService.getById(Integer.parseInt(imageId));

                    packageProduct.setPackages(packages);
                    packageProduct.setProduct(productService.getById(image.getObjectId()));
                    packageProduct.setPath(image.getPath());

                    packageProductsService.create(packageProduct);
                }
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
