package com.sixmac.controller;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Afflatus;
import com.sixmac.service.AfflatusService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/9 0009.
 */
@Controller
@RequestMapping(value = "afflatus")
public class AfflatusController extends CommonController {

    @Autowired
    private AfflatusService afflatusService;

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
}
