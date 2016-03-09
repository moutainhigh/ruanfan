package com.sixmac.controller;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Image;
import com.sixmac.service.AfflatusService;
import com.sixmac.service.ImageService;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
