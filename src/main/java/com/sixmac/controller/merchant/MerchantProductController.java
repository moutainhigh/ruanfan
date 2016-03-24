package com.sixmac.controller.merchant;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Image;
import com.sixmac.entity.Merchants;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/3/24 0024.
 */
@Controller
@RequestMapping(value = "merchant/product")
public class MerchantProductController extends CommonController {

    @Autowired
    private MerchantsService merchantsService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ProducttypeService producttypeService;

    @Autowired
    private ImageService imageService;

    @RequestMapping("index")
    public String index(ModelMap model, HttpServletRequest request) {
        Merchants merchants = MerchantIndexController.getMerchant(request, model, merchantsService);
        if (merchants.getIsCheck() != Constant.CHECK_STATUS_SUCCESS) {
            return "merchant/个人资料待审核";
        }

        return "merchant/商品列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String name,
                     Integer isCheck,
                     Integer type,
                     Integer draw,
                     Integer start,
                     Integer length,
                     ModelMap model) {
        Merchants merchants = MerchantIndexController.getMerchant(request, model, merchantsService);

        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Products> page = productsService.page(merchants.getId(), name, isCheck, type, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Products products = productsService.getById(id);
            model.addAttribute("product", products);

            // 如果商品不为空，则查询对应的图片集合
            if (null != products) {
                List<Image> imageList = imageService.iFindList(products.getId(), Constant.IMAGE_PRODUCTS);
                for (Image image : imageList) {
                    map = new HashMap<String, Object>();
                    map.put("id", image.getId());
                    map.put("path", image.getPath());

                    list.add(map);
                }
            }
        }

        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "merchant/新增商品";
    }

    /**
     * 新增商品信息
     *
     * @param id
     * @param name
     * @param price
     * @param oldPrice
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
    public Integer save(HttpServletRequest request,
                        ModelMap model,
                        Integer id,
                        String name,
                        String price,
                        String oldPrice,
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
            Merchants merchants = MerchantIndexController.getMerchant(request, model, merchantsService);

            String[] addImageIds = tempAddImageIds.split(",");
            String[] delImageIds = tempDelImageIds.split(",");
            Products product = null;

            if (null == id) {
                product = new Products();
            } else {
                product = productsService.getById(id);
            }

            product.setName(name);
            product.setPrice(price);
            product.setOldPrice(oldPrice);
            product.setMerchant(merchants);
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
                productsService.create(product);
            } else {
                productsService.update(product);
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

    /**
     * 删除商品
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Integer delete(Integer productId) {
        try {
            productsService.deleteById(productId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 上下架商品
     *
     * @param productId
     * @param isAdd
     * @return
     */
    @RequestMapping(value = "/changeAdd")
    @ResponseBody
    public Integer changeAdd(Integer productId, Integer isAdd) {
        try {
            Products products = productsService.getById(productId);
            products.setIsAdd(isAdd);

            productsService.update(products);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 商品提交审核
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/subToCheck")
    @ResponseBody
    public Integer subToCheck(Integer productId) {
        try {
            Products products = productsService.getById(productId);
            products.setStatus(Constant.CHECK_STATUS_DEFAULT);

            productsService.update(products);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
