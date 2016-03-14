package com.sixmac.controller;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Virtuals;
import com.sixmac.service.StylesService;
import com.sixmac.service.VirtualsService;
import com.sixmac.service.VrtypeService;
import com.sixmac.utils.ImageUtil;
import com.sixmac.utils.WebUtil;
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
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
@Controller
@RequestMapping(value = "virtuals")
public class VirtualsController extends CommonController {

    @Autowired
    private VirtualsService virtualsService;

    @Autowired
    private StylesService stylesService;

    @Autowired
    private VrtypeService vrtypeService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "虚拟体验列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String name,
                     Integer styleId,
                     Integer typeId,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Virtuals> page = virtualsService.iPage(name, styleId, typeId, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Virtuals virtuals = virtualsService.getById(id);
            model.addAttribute("virtuals", virtuals);
        }

        return "新增虚拟体验";
    }

    /**
     * 删除VR虚拟
     *
     * @param virtualId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer virtualId) {
        try {
            virtualsService.deleteById(virtualId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增VR虚拟
     *
     * @param id
     * @param styleId
     * @param typeId
     * @param name
     * @param labels
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(ServletRequest request, Integer id, Integer styleId, Integer typeId, String name, String labels, String url, MultipartRequest multipartRequest) {
        try {
            Virtuals virtuals = new Virtuals();
            virtuals.setStyle(stylesService.getById(styleId));
            virtuals.setType(vrtypeService.getById(typeId));
            virtuals.setName(name);
            virtuals.setLabels(labels);
            virtuals.setUrl(url);
            virtuals.setCreateTime(new Date());

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                Map<String, Object> map = ImageUtil.saveImage(request, multipartFile, false);
                virtuals.setCover(map.get("imgURL").toString());
            }

            if (null != id) {
                virtuals.setId(id);
                virtualsService.update(virtuals);
            } else {
                virtualsService.create(virtuals);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
