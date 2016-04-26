package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Image;
import com.sixmac.entity.Propertyinfo;
import com.sixmac.entity.Propertys;
import com.sixmac.service.PropertyinfoService;
import com.sixmac.service.PropertysService;
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
 * Created by Administrator on 2016/3/16 0016.
 */
@Controller
@RequestMapping(value = "backend/propertys")
public class PropertysController extends CommonController {

    @Autowired
    private PropertysService propertysService;

    @Autowired
    private PropertyinfoService propertyInfoService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "backend/地产列表";
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
        Page<Propertys> page = propertysService.page(pageNum, length);

        // 循环查找每个地产的楼盘信息
        for (Propertys propertys : page.getContent()) {
            propertys.setChildList(propertysService.iPageByParentId(propertys.getId()));
            propertys.setChildNum(propertys.getChildList().size());
        }

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
        Page<Propertys> page = propertysService.pageChild(parentId, pageNum, length);

        // 循环查找每个楼盘的户型信息
        for (Propertys propertys : page.getContent()) {
            propertys.setChildNum(propertyInfoService.findListByPropertyId(propertys.getId()).size());
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    /**
     * 删除地产信息
     *
     * @param propertyId
     * @return
     */
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

    /**
     * 新增地产信息
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(ServletRequest request, Integer id, String name, MultipartRequest multipartRequest) {
        try {
            Propertys propertys = null;

            if (null != id) {
                propertys = propertysService.getById(id);
            } else {
                propertys = new Propertys();
            }

            propertys.setName(name);
            propertys.setParentId(0);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                String url = QiNiuUploadImgUtil.upload(multipartFile);
                propertys.setCover(url);
            }

            MultipartFile multipartFile2 = multipartRequest.getFile("mainImage2");
            if (null != multipartFile2) {
                String serverHead = QiNiuUploadImgUtil.upload(multipartFile2);
                propertys.setServerHead(serverHead);
            }

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
        return "backend/楼盘列表";
    }

    @RequestMapping("addChild")
    public String addChild(ModelMap model, Integer id, Integer parentId) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Propertys property = propertysService.getById(id);
            model.addAttribute("propertyInfo", property);

            // 如果楼盘信息不为空，则查询对应的户型信息集合
            if (null != property) {
                List<Propertyinfo> propertyList = propertyInfoService.findListByPropertyId(id);
                for (Propertyinfo propertyInfo : propertyList) {
                    map = new HashMap<String, Object>();
                    map.put("id", propertyInfo.getId());
                    map.put("path", propertyInfo.getPath());
                    map.put("serverPath", propertyInfo.getServerPath());
                    map.put("url", propertyInfo.getUrl());
                    map.put("qq", propertyInfo.getQq());

                    list.add(map);
                }
            }
        }

        model.addAttribute("parentId", parentId);
        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "backend/新增楼盘";
    }

    /**
     * 新增楼盘信息
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/addChildInfo")
    @ResponseBody
    public Integer addChildInfo(ServletRequest request,
                                Integer id,
                                String name,
                                String address,
                                String labels,
                                Integer parentId,
                                String hxImages,
                                String kfImages,
                                String serverQQ,
                                String description,
                                String urls,
                                String qqs,
                                MultipartRequest multipartRequest) {
        try {
            Propertys propertys = null;
            Image image = null;

            if (null != id) {
                propertys = propertysService.getById(id);
            } else {
                propertys = new Propertys();
            }

            propertys.setName(name);
            propertys.setAddress(address);
            propertys.setLabels(labels);
            propertys.setParentId(parentId);
            propertys.setServerQQ(serverQQ);
            propertys.setDescription(description);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                String url = QiNiuUploadImgUtil.upload(multipartFile);
                propertys.setCover(url);
            }

            MultipartFile multipartFile5 = multipartRequest.getFile("mainImage5");
            if (null != multipartFile5) {
                String serverHead = QiNiuUploadImgUtil.upload(multipartFile5);
                propertys.setServerHead(serverHead);
            }

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
            String[] urlStrings = urls.split(",");
            String[] qqStrings = qqs.split(",");

            Propertyinfo propertyInfo = null;

            for (int i = 0; i < hxStrings.length; i++) {
                if (!hxStrings[i].equals("") && !kfStrings[i].equals("") && !urlStrings[i].equals("") && !qqStrings[i].equals("")) {
                    propertyInfo = new Propertyinfo();
                    propertyInfo.setPath(hxStrings[i]);
                    propertyInfo.setServerPath(kfStrings[i]);
                    propertyInfo.setUrl(urlStrings[i]);
                    propertyInfo.setQq(qqStrings[i]);
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
