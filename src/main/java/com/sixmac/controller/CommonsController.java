package com.sixmac.controller;

import com.sixmac.entity.Areas;
import com.sixmac.entity.Image;
import com.sixmac.entity.Styles;
import com.sixmac.service.AreasService;
import com.sixmac.service.ImageService;
import com.sixmac.service.StylesService;
import com.sixmac.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.HashMap;
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
}
