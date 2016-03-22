package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Areas;
import com.sixmac.service.AreasService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16 0016.
 */
@Controller
@RequestMapping(value = "backend/afflatusArea")
public class AfflatusAreaController extends CommonController {

    @Autowired
    private AreasService areasService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "backend/灵感图区域";
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
        Page<Areas> page = areasService.find(pageNum, length);

        // 循环查找每个区域的关联数量
        for (Areas areas : page.getContent()) {
            areas.setProductNum(areasService.findListByAreaId(areas.getId()));
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    /**
     * 删除灵感图区域
     *
     * @param afflatusAreaId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer afflatusAreaId) {
        try {
            areasService.deleteById(afflatusAreaId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增灵感图区域
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(Integer id, String name) {
        try {
            Areas areas = null;

            if (null != id) {
                areas = areasService.getById(id);
            } else {
                areas = new Areas();
            }

            areas.setName(name);
            areas.setUpdateTime(new Date());

            if (null != id) {
                areasService.update(areas);
            } else {
                areasService.create(areas);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
