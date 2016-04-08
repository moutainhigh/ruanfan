package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Propertyinfo;
import com.sixmac.entity.Propertys;
import com.sixmac.service.PropertyinfoService;
import com.sixmac.service.PropertysService;
import com.sixmac.utils.PathUtils;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
@Controller
@RequestMapping(value = "backend/custom")
public class CustomController extends CommonController {

    @Autowired
    private PropertysService propertysService;

    @Autowired
    private PropertyinfoService propertyInfoService;

    @RequestMapping(value = "/index")
    public String index(ModelMap model) {
        return "backend/定制楼盘列表";
    }

    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Propertys> page = propertysService.pageChild(pageNum, length);

        // 循环查找每个楼盘的户型信息
        for (Propertys propertys : page.getContent()) {
            propertys.setChildNum(propertyInfoService.findListByPropertyId(propertys.getId()).size());
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

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

    @RequestMapping("/save")
    @ResponseBody
    public Integer save(ServletRequest request, Integer id, String productName) {
        try {
            Propertys propertys = null;

            if (null != id) {
                propertys = propertysService.getById(id);
            } else {
                propertys = new Propertys();
            }

            propertys.setName(productName);
            propertys.setParentId(1);

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
        return "backend/定制楼盘列表";
    }

    @RequestMapping("addChild")
    public String addChild(ModelMap model, Integer id, Integer parentId) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Propertys property = propertysService.getById(id);
            model.addAttribute("Propertyinfo", property);

            // 如果楼盘信息不为空，则查询对应的户型信息集合
            if (null != property) {
                List<Propertyinfo> propertyList = propertyInfoService.findListByPropertyId(id);
                for (Propertyinfo propertyInfo : propertyList) {
                    map = new HashMap<String, Object>();
                    map.put("id", propertyInfo.getId());
                    map.put("path", propertyInfo.getPath());
                    map.put("serverPath", propertyInfo.getServerPath());

                    list.add(map);
                }
            }
        }

        model.addAttribute("parentId", parentId);
        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "backend/新增户型";
    }

    @RequestMapping("/addChildInfo")
    @ResponseBody
    public Integer addChildInfo(ServletRequest request,
                                Integer id,
                                String name,
                                Integer parentId,
                                String hxImages,
                                String kfImages
    ) {
        try {
            Propertys propertys = null;

            if (null != id) {
                propertys = propertysService.getById(id);
            } else {
                propertys = new Propertys();
            }

            propertys.setName(name);
            propertys.setParentId(parentId);

            if (null != id) {
                propertysService.update(propertys);
            } else {
                propertys.setCreateTime(new Date());
                propertysService.create(propertys);
            }

            // 先清除所有相关联的户型信息
            propertyInfoService.clearInfoByPropertyId(propertys.getId());

            // 保存户型图片信息
            String[] hxStrings = hxImages.split(",");
            String[] kfStrings = kfImages.split(",");

            Propertyinfo propertyInfo = null;

            for (int i = 0; i < hxStrings.length; i++) {
                if (!hxStrings[i].equals("") && !kfStrings[i].equals("")) {
                    propertyInfo = new Propertyinfo();
                    propertyInfo.setPath(hxStrings[i].replace(PathUtils.getRemotePath(), ""));
                    propertyInfo.setServerPath(kfStrings[i].replace(PathUtils.getRemotePath(), ""));
                    propertyInfo.setProperty(propertys);

                    propertyInfoService.create(propertyInfo);
                }
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
