package com.sixmac.controller;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Image;
import com.sixmac.service.*;
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
 * Created by Administrator on 2016/3/9 0009.
 */
@Controller
@RequestMapping(value = "afflatus")
public class AfflatusController extends CommonController {

    @Autowired
    private AfflatusService afflatusService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AreasService areasService;

    @Autowired
    private StylesService stylesService;

    @Autowired
    private DesignersService designersService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "灵感图集";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String afflatusName,
                     String designerName,
                     Integer status,
                     Integer styleId,
                     Integer areaId,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Afflatus> page = afflatusService.page(afflatusName, designerName, status, styleId, areaId, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Afflatus afflatus = afflatusService.getById(id);
            model.addAttribute("afflatus", afflatus);

            // 如果灵感集不为空，则查询对应的图片集合
            if (null != afflatus) {
                List<Image> imageList = imageService.iFindList(afflatus.getId(), Constant.IMAGE_AFFLATUS);
                for (Image image : imageList) {
                    map = new HashMap<String, Object>();
                    map.put("id", image.getId());
                    map.put("path", image.getPath());

                    list.add(map);
                }
            }
        }

        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "新增灵感集";
    }

    /**
     * 保存灵感集
     *
     * @param response
     * @param id
     * @param name
     * @param type
     * @param styleId
     * @param areaId
     * @param labels
     * @param tempAddImageIds
     * @param tempDelImageIds
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(HttpServletResponse response,
                        Integer id,
                        String name,
                        Integer type,
                        Integer styleId,
                        Integer areaId,
                        Integer designerId,
                        String labels,
                        String tempAddImageIds,
                        String tempDelImageIds) {
        try {
            String[] addImageIds = tempAddImageIds.split(",");
            String[] delImageIds = tempDelImageIds.split(",");

            Afflatus afflatus = new Afflatus();
            afflatus.setName(name);
            afflatus.setType(type);
            afflatus.setDesigner(designersService.getById(designerId));
            afflatus.setStyle(stylesService.getById(styleId));
            afflatus.setArea(areasService.getById(areaId));
            afflatus.setLabels(labels);

            // 保存灵感集信息
            if (null != id) {
                afflatus.setId(id);
                afflatusService.update(afflatus);
            } else {
                afflatus.setCoverId(0);
                afflatus.setShowNum(0);
                afflatus.setShareNum(0);
                afflatus.setStatus(0);
                afflatus.setCreateTime(new Date());
                afflatusService.create(afflatus);
            }

            // 保存灵感集图片集合
            Image image = null;
            for (String imageId : addImageIds) {
                if (null != imageId && !imageId.equals("")) {
                    image = imageService.getById(Integer.parseInt(imageId));
                    image.setObjectId(afflatus.getId());
                    image.setObjectType(Constant.IMAGE_AFFLATUS);

                    imageService.update(image);
                }
            }

            // 删除灵感集图片
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
