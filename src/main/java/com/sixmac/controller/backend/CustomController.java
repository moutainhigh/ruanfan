package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Custom;
import com.sixmac.entity.Custominfo;
import com.sixmac.service.CustomService;
import com.sixmac.service.CustominfoService;
import com.sixmac.utils.ImageUtil;
import com.sixmac.utils.PathUtils;
import com.sixmac.utils.QiNiuUploadImgUtil;
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
 * Created by Administrator on 2016/4/8 0008.
 */
@Controller
@RequestMapping(value = "backend/custom")
public class CustomController extends CommonController {

    @Autowired
    private CustomService customService;

    @Autowired
    private CustominfoService custominfoService;

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
        Page<Custom> page = customService.find(pageNum, length);

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer customId) {
        try {
            customService.deleteById(customId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Integer save(ServletRequest request, Integer id, String productName,MultipartRequest multipartRequest) {
        try {
            Custom custom = null;

            if (null != id) {
                custom = customService.getById(id);
            } else {
                custom = new Custom();
            }

            custom.setName(productName);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                String url = QiNiuUploadImgUtil.upload(multipartFile);
                custom.setCover(url);
            }

            if (null != id) {
                customService.update(custom);
            } else {
                custom.setCreateTime(new Date());
                customService.create(custom);
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
    public String addChild(ModelMap model, Integer id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Custom custom = customService.getById(id);
            model.addAttribute("custom", custom);

            // 如果楼盘信息不为空，则查询对应的户型信息集合
            if (null != custom) {
                List<Custominfo> custominfoList = custominfoService.findListByCustomId(id);
                for (Custominfo custominfo : custominfoList) {
                    map = new HashMap<String, Object>();
                    map.put("id", custominfo.getId());
                    map.put("path", custominfo.getPath());

                    list.add(map);
                }
            }
        }

        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "backend/新增户型";
    }

    @RequestMapping("/addChildInfo")
    @ResponseBody
    public Integer addChildInfo(ServletRequest request,
                                Integer id,
                                String name,
                                String hxImages
    ) {
        try {
            Custom custom = null;

            if (null != id) {
                custom = customService.getById(id);
            } else {
                custom = new Custom();
            }

            custom.setName(name);

            if (null != id) {
                customService.update(custom);
            } else {
                custom.setCreateTime(new Date());
                customService.create(custom);
            }


            // 保存户型图片信息
            String[] hxStrings = hxImages.split(",");

            Custominfo custominfo = null;

            for (int i = 0; i < hxStrings.length; i++) {
                if (!hxStrings[i].equals("")) {
                    custominfo = new Custominfo();
                    custominfo.setPath(hxStrings[i].replace(PathUtils.getRemotePath(), ""));
                    custominfo.setCustom(custom);

                    custominfoService.create(custominfo);
                }
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
