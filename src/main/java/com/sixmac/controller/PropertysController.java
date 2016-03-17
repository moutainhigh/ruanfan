package com.sixmac.controller;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Image;
import com.sixmac.entity.Propertys;
import com.sixmac.entity.Vrtype;
import com.sixmac.service.ImageService;
import com.sixmac.service.PropertysService;
import com.sixmac.service.VrtypeService;
import com.sixmac.utils.ImageUtil;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
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
import java.util.*;

/**
 * Created by Administrator on 2016/3/16 0016.
 */
@Controller
@RequestMapping(value = "propertys")
public class PropertysController extends CommonController {

    @Autowired
    private PropertysService propertysService;

    @Autowired
    private ImageService imageService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "地产列表";
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
        Page<Propertys> page = propertysService.page(pageNum, length);

        // 循环查找每个地产的楼盘信息
        for (Propertys propertys : page.getContent()) {
            propertys.setChildList(propertysService.iPageByParentId(propertys.getId()));
            propertys.setChildNum(propertys.getChildList().size());
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("/childList")
    public void childList(HttpServletResponse response,
                          Integer parentId,
                          Integer draw,
                          Integer start,
                          Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Propertys> page = propertysService.pageChild(parentId, pageNum, length);

        // 循环查找每个楼盘的户型信息
        for (Propertys propertys : page.getContent()) {
            propertys.setChildNum(imageService.iFindList(propertys.getId(), Constant.IMAGE_PROPERTYS).size());
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    /**
     * 删除地产信息
     *
     * @param propertyId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer propertyId) {
        try {
            propertysService.deleteById(propertyId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增地产信息
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(ServletRequest request, Integer id, String name, MultipartRequest multipartRequest) {
        try {
            Propertys propertys = null;

            if (null != id) {
                propertys = propertysService.getById(id);
            } else {
                propertys = new Propertys();
            }

            propertys.setName(name);
            propertys.setParentId(0);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                Map<String, Object> map = ImageUtil.saveImage(request, multipartFile, false);
                propertys.setCover(map.get("imgURL").toString());
            }

            if (null != id) {
                propertysService.update(propertys);
            } else {
                propertys.setCreateTime(new Date());
                propertysService.create(propertys);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping("childInfo")
    public String childInfo(ModelMap model, Integer id) {
        model.addAttribute("parentId", id);
        return "楼盘列表";
    }

    @RequestMapping("addChild")
    public String addChild(ModelMap model, Integer id, Integer parentId) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Propertys property = propertysService.getById(id);
            model.addAttribute("propertyInfo", property);

            // 如果灵感集不为空，则查询对应的图片集合
            if (null != property) {
                List<Image> imageList = imageService.iFindList(property.getId(), Constant.IMAGE_PROPERTYS);
                for (Image image : imageList) {
                    map = new HashMap<String, Object>();
                    map.put("id", image.getId());
                    map.put("path", image.getPath());
                    map.put("description", image.getDescription());

                    list.add(map);
                }
            }
        }

        model.addAttribute("parentId", parentId);
        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "新增楼盘";
    }

    /**
     * 新增楼盘信息
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/addChildInfo")
    @ResponseBody
    public Integer addChildInfo(ServletRequest request,
                                Integer id,
                                String name,
                                String address,
                                String labels,
                                Integer parentId,
                                String tempAddImages,
                                String tempDelImages,
                                MultipartRequest multipartRequest) {
        try {
            Propertys propertys = null;
            Image image = null;

            if (null != id) {
                propertys = propertysService.getById(id);
            } else {
                propertys = new Propertys();
            }

            propertys.setName(name);
            propertys.setAddress(address);
            propertys.setLabels(labels);
            propertys.setParentId(parentId);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                Map<String, Object> map = ImageUtil.saveImage(request, multipartFile, false);
                propertys.setCover(map.get("imgURL").toString());
            }

            if (null != id) {
                propertysService.update(propertys);
            } else {
                propertys.setCreateTime(new Date());
                propertysService.create(propertys);
            }

            // 保存户型图片信息
            String[] addStrings = tempAddImages.split(",");
            for (String addStr : addStrings) {
                if (null != addStr && !addStr.equals("")) {
                    image = imageService.getById(Integer.parseInt(addStr));
                    image.setObjectId(propertys.getId());
                    image.setObjectType(Constant.IMAGE_PROPERTYS);

                    imageService.update(image);
                }
            }

            // 删除户型图片信息
            String[] delStrings = tempDelImages.split(",");
            for (String delStr : delStrings) {
                if (null != delStr && !delStr.equals("")) {
                    imageService.deleteById(Integer.parseInt(delStr));
                }
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
