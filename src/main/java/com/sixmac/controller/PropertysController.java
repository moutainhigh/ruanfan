package com.sixmac.controller;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Propertys;
import com.sixmac.entity.Vrtype;
import com.sixmac.service.PropertysService;
import com.sixmac.service.VrtypeService;
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
@RequestMapping(value = "propertys")
public class PropertysController extends CommonController {

    @Autowired
    private VrtypeService vrtypeService;

    @Autowired
    private PropertysService propertysService;

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
        Page<Propertys> page = propertysService.find(pageNum, length);

        // 循环查找每个地产的楼盘信息
        for (Propertys propertys : page.getContent()) {
            propertys.setChildList(propertysService.iPageByParentId(propertys.getId()));
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    /**
     * 删除虚拟体验分类
     *
     * @param virtualTypeId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer virtualTypeId) {
        try {
            vrtypeService.deleteById(virtualTypeId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增虚拟体验分类
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(Integer id, String name) {
        try {
            Vrtype vrtype = null;

            if (null != id) {
                vrtype = vrtypeService.getById(id);
            } else {
                vrtype = new Vrtype();
            }

            vrtype.setName(name);
            vrtype.setUpdateTime(new Date());

            if (null != id) {
                vrtypeService.update(vrtype);
            } else {
                vrtypeService.create(vrtype);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
