package com.sixmac.controller;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Styles;
import com.sixmac.service.StylesService;
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
@RequestMapping(value = "style")
public class StyleController extends CommonController {

    @Autowired
    private StylesService stylesService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "风格分类";
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
        Page<Styles> page = stylesService.find(pageNum, length);

        // 循环查找每个风格的关联数量
        for (Styles styles : page.getContent()) {
            styles.setProductNum(stylesService.findListByStyleId(styles.getId()));
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    /**
     * 删除风格分类
     *
     * @param styleId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer styleId) {
        try {
            stylesService.deleteById(styleId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增风格分类
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(Integer id, String name) {
        try {
            Styles style = null;

            if (null != id) {
                style = stylesService.getById(id);
            } else {
                style = new Styles();
            }

            style.setName(name);
            style.setUpdateTime(new Date());

            if (null != id) {
                stylesService.update(style);
            } else {
                stylesService.create(style);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
