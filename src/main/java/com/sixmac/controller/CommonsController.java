package com.sixmac.controller;

import com.sixmac.entity.*;
import com.sixmac.service.*;
import com.sixmac.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/9 0009.
 */
@Controller
@RequestMapping(value = "common")
public class CommonsController {

    @Autowired
    private StylesService stylesService;

    @Autowired
    private AreasService areasService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private DesignersService designersService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private VrtypeService vrtypeService;

    /**
     * 设计师列表
     *
     * @return
     */
    @RequestMapping("/designerList")
    @ResponseBody
    public List<Designers> designerList() {
        return designersService.findAll();
    }

    /**
     * 风格列表
     *
     * @return
     */
    @RequestMapping("/styleList")
    @ResponseBody
    public List<Styles> styleList() {
        return stylesService.findAll();
    }

    /**
     * 区域列表
     *
     * @return
     */
    @RequestMapping("/areaList")
    @ResponseBody
    public List<Areas> areaList() {
        return areasService.findAll();
    }

    /**
     * 虚拟体验分类列表
     *
     * @return
     */
    @RequestMapping("/vrtypeList")
    @ResponseBody
    public List<Vrtype> typeList() {
        return vrtypeService.findAll();
    }

    /**
     * 商品列表
     *
     * @return
     */
    @RequestMapping("/productList")
    @ResponseBody
    public List<Products> productList() {
        return productsService.findAll();
    }

    /**
     * 上传缓存图片
     *
     * @return
     */
    @RequestMapping("/addTempImage")
    @ResponseBody
    public Image addTempImage(ServletRequest request, MultipartRequest multipartRequest) {
        Image image = new Image();

        try {
            MultipartFile multipartFile = multipartRequest.getFile("tempImage");

            // 验证图片格式
            String originalFileName = multipartFile.getOriginalFilename().toLowerCase();
            String fileType = originalFileName.substring(originalFileName.indexOf("."));

            List<String> list = new ArrayList<String>();
            list.add(".jpg");
            list.add(".gif");
            list.add(".jpeg");
            list.add(".png");
            list.add(".bmp");

            if (!list.contains(fileType)) {
                return image;
            }

            Map<String, Object> map = ImageUtil.saveImage(request, multipartFile, false);

            image.setPath(map.get("imgURL").toString());
            image.setWidth(map.get("imgWidth").toString());
            image.setHeight(map.get("imgHeight").toString());
            image.setCreateTime(new Date());

            imageService.create(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * 根据产品id获取产品信息
     *
     * @param productId
     * @return
     */
    @RequestMapping("/getProductInfo")
    @ResponseBody
    public Products getProductInfo(Integer productId) {
        try {
            Products products = productsService.getById(productId);

            return products;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据标签id删除标签
     *
     * @param labelId
     * @return
     */
    @RequestMapping("/deleteLabel")
    @ResponseBody
    public Integer deleteLabel(String labelId) {
        try {
            Label label = labelService.searchByLabelId(labelId);
            labelService.deleteById(label.getId());
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 根据标签id查询标签最后停留的位置
     *
     * @param objectId
     * @param labelId
     * @return
     */
    @RequestMapping("/queryLastPosition")
    @ResponseBody
    public Label queryLastPosition(Integer objectId, String labelId) {
        try {
            Label label = labelService.findLastPosition(objectId, labelId);

            return label;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 保存标签信息
     *
     * @param objectId
     * @param labelId
     * @param name
     * @param objectType
     * @param height
     * @param width
     * @param leftPoint
     * @param topPoint
     * @param productId
     * @return
     */
    @RequestMapping("/saveLabelInfo")
    @ResponseBody
    public Integer saveLabelInfo(Integer objectId,
                                 String labelId,
                                 String name,
                                 Integer objectType,
                                 String height,
                                 String width,
                                 Double leftPoint,
                                 Double topPoint,
                                 Integer productId) {
        try {
            Label label = new Label();
            Label tempLabel = null;
            if (null != labelId && !labelId.equals("")) {
                tempLabel = labelService.findLastPosition(objectId, labelId);
                if (null != tempLabel) {
                    label = tempLabel;
                }
            }

            label.setName(name);
            label.setObjectId(objectId);
            label.setObjectType(objectType);
            label.setHeight(height);
            label.setWidth(width);
            label.setProduct(productsService.getById(productId));
            label.setLabelId(labelId);
            label.setLeftPoint(leftPoint);
            label.setTopPoint(topPoint);

            if (null == tempLabel) {
                labelService.create(label);
            } else {
                labelService.update(label);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
