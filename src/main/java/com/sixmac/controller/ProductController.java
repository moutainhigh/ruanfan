package com.sixmac.controller;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Image;
import com.sixmac.entity.Products;
import com.sixmac.service.ImageService;
import com.sixmac.service.MerchantsService;
import com.sixmac.service.ProductsService;
import com.sixmac.service.ProducttypeService;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
@Controller
@RequestMapping(value = "product")
public class ProductController extends CommonController {

    @Autowired
    private ProductsService productService;

    @Autowired
    private ProducttypeService producttypeService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MerchantsService merchantsService;

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
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Products product = productService.getById(id);
            model.addAttribute("product", product);

            // 如果商品不为空，则查询对应的图片集合
            if (null != product) {
                List<Image> imageList = imageService.iFindList(product.getId(), Constant.IMAGE_PRODUCTS);
                for (Image image : imageList) {
                    map = new HashMap<String, Object>();
                    map.put("id", image.getId());
                    map.put("path", image.getPath());

                    list.add(map);
                }
            }
        }

        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "新增商品";
    }

    /**
     * 启用 or 禁用商品
     *
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping("/changeStatus")
    @ResponseBody
    public Integer changeStatus(Integer productId, Integer status) {
        try {
            Products product = productService.getById(productId);
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
     * @param productId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer productId) {
        try {
            productService.deleteById(productId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增商品信息
     * @param id
     * @param name
     * @param price
     * @param oldPrice
     * @param merchantId
     * @param coverId
     * @param type
     * @param sort
     * @param place
     * @param labels
     * @param colors
     * @param sizes
     * @param materials
     * @param content
     * @param tempAddImageIds
     * @param tempDelImageIds
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(Integer id,
                        String name,
                        String price,
                        String oldPrice,
                        Integer merchantId,
                        Integer coverId,
                        Integer type,
                        Integer sort,
                        String place,
                        String labels,
                        String colors,
                        String sizes,
                        String materials,
                        String content,
                        String tempAddImageIds,
                        String tempDelImageIds) {
        try {
            String[] addImageIds = tempAddImageIds.split(",");
            String[] delImageIds = tempDelImageIds.split(",");
            Products product = null;

            if (null == id) {
                product = new Products();
            } else {
                product = productService.getById(id);
            }

            product.setName(name);
            product.setPrice(price);
            product.setOldPrice(oldPrice);
            product.setMerchant(merchantsService.getById(merchantId));
            product.setCoverId(coverId);
            product.setType(type);
            product.setSort(producttypeService.getById(sort));
            product.setPlace(place);
            product.setLabels(labels);
            product.setColors(colors);
            product.setSizes(sizes);
            product.setMaterials(materials);
            product.setDescription(content);

            if (null == id) {
                product.setIsHot(Constant.RECOMMEND_STATUS_NO);
                product.setIsCheck(Constant.CHECK_STATUS_DEFAULT);
                product.setStatus(Constant.ADDED_STATUS_YES);
                product.setCreateTime(new Date());
                productService.create(product);
            } else {
                productService.update(product);
            }

            // 保存商品图片集合
            Image image = null;
            for (String imageId : addImageIds) {
                if (null != imageId && !imageId.equals("")) {
                    image = imageService.getById(Integer.parseInt(imageId));
                    image.setObjectId(product.getId());
                    image.setObjectType(Constant.IMAGE_PRODUCTS);

                    imageService.update(image);
                }
            }

            // 删除商品图片
            for (String imageId : delImageIds) {
                if (null != imageId && !imageId.equals("")) {
                    imageService.deleteById(Integer.parseInt(imageId));
                }
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
