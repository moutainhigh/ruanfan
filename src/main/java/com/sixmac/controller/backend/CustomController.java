package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Custom;
import com.sixmac.entity.Custominfo;
import com.sixmac.entity.Custompackages;
import com.sixmac.service.*;
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

    @Autowired
    private CustompackagesService custompackagesService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AreasService areasService;

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
        Page<Custominfo> page = custominfoService.pageByCustomId(parentId, pageNum, length);

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

    @RequestMapping("/deleteChild")
    @ResponseBody
    public Integer deleteChild(Integer customInfoId) {
        try {
            custominfoService.deleteById(customInfoId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Integer save(ServletRequest request, Integer id, String productName, MultipartRequest multipartRequest) {
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
        return "backend/在线定制户型列表";
    }

    @RequestMapping("addChild")
    public String addChild(ModelMap model, Integer id, Integer parentId) {
        model.addAttribute("parentId", parentId);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Custominfo custominfo = custominfoService.getById(id);
            model.addAttribute("customInfo", custominfo);

            // 如果楼盘信息不为空，则查询对应的户型信息集合
            if (null != custominfo) {
                List<Custompackages> custompackagesList = custompackagesService.findListByCustomInfoId(id);
                for (Custompackages custompackages : custompackagesList) {
                    map = new HashMap<String, Object>();
                    map.put("id", custompackages.getCoverId());
                    map.put("path", imageService.getById(custompackages.getCoverId()).getPath());
                    map.put("areaId", custompackages.getArea().getId());
                    map.put("name", custompackages.getName());

                    list.add(map);
                }
            }
        }

        model.addAttribute("packageList", JSONArray.fromObject(list));

        return "backend/新增户型";
    }

    @RequestMapping("/addChildInfo")
    @ResponseBody
    public Integer addChildInfo(Integer id,
                                Integer parentId,
                                String name,
                                String[] nameTemp,
                                String[] areaList,
                                String[] imageIdTemp,
                                MultipartRequest multipartRequest) {
        try {
            Custominfo custominfo = null;

            if (null != id) {
                custominfo = custominfoService.getById(id);
            } else {
                custominfo = new Custominfo();
            }

            custominfo.setCustom(customService.getById(parentId));
            custominfo.setName(name);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                String path = QiNiuUploadImgUtil.upload(multipartFile);
                custominfo.setPath(path);
            }

            if (null != id) {
                custominfoService.update(custominfo);
            } else {
                custominfo.setCreateTime(new Date());
                custominfoService.create(custominfo);
            }

            // 先删除所有户型套餐信息
            custompackagesService.deleteAllInfoByCustomInfoId(custominfo.getId());

            // 保存户型图片信息
            Custompackages custompackages = null;

            for (int i = 0; i < imageIdTemp.length; i++) {
                if (!imageIdTemp[i].equals("")) {
                    custompackages = new Custompackages();
                    custompackages.setName(nameTemp[i]);
                    custompackages.setCustominfo(custominfo);
                    custompackages.setArea(areasService.getById(Integer.parseInt(areaList[i])));
                    custompackages.setCoverId(Integer.parseInt(imageIdTemp[i]));

                    custompackagesService.create(custompackages);
                }
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
