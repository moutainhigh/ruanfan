package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Image;
import com.sixmac.entity.Magazine;
import com.sixmac.service.ImageService;
import com.sixmac.service.MagazineService;
import com.sixmac.service.OperatisService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/3/15 0015.
 */
@Controller
@RequestMapping(value = "backend/magazine")
public class MagazineController extends CommonController {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private OperatisService operatisService;

    @RequestMapping("index")
    public String index() {
        return "backend/杂志列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String name,
                     Integer month,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Magazine> page = magazineService.page(name, month, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Magazine magazine = magazineService.getById(id);
            model.addAttribute("magazine", magazine);

            // 如果杂志不为空，则查询对应的图片集合
            if (null != magazine) {
                List<Image> imageList = imageService.iFindList(magazine.getId(), Constant.IMAGE_MAGAZINE);
                for (Image image : imageList) {
                    map = new HashMap<String, Object>();
                    map.put("id", image.getId());
                    map.put("path", image.getPath());

                    list.add(map);
                }
            }
        }

        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "backend/杂志详情";
    }

    /**
     * 删除杂志
     *
     * @param magazineId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(HttpServletRequest request, Integer magazineId) {
        try {
            magazineService.deleteById(request, magazineId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增杂志
     *
     * @param request
     * @param id
     * @param month
     * @param name
     * @param multipartRequest
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(HttpServletRequest request, Integer id, Integer month, String name, String tempAddImages, String tempDelImages, MultipartRequest multipartRequest) {
        try {
            Magazine magazine = null;

            if (null != id) {
                magazine = magazineService.getById(id);
            } else {
                magazine = new Magazine();
            }

            magazine.setName(name);
            magazine.setMonth(month);

            MultipartFile multipartFile = multipartRequest.getFile("mainImage");
            if (null != multipartFile) {
                String url = QiNiuUploadImgUtil.upload(multipartFile);
                magazine.setCover(url);
            }

            if (null != id) {
                magazineService.update(magazine);
            } else {
                magazine.setCreateTime(new Date());
                magazineService.create(magazine);
            }

            // 保存关联图片信息
            String[] strings = tempAddImages.split(",");
            Image image = null;

            for (String str : strings) {
                if (null != str && !str.equals("")) {
                    image = imageService.getById(Integer.parseInt(str));
                    image.setObjectId(magazine.getId());
                    image.setObjectType(Constant.IMAGE_MAGAZINE);

                    imageService.update(image);
                }
            }

            // 删除用户清除的图片信息
            String[] delStrings = tempDelImages.split(",");
            for (String str : delStrings) {
                if (null != str && !str.equals("")) {
                    imageService.deleteById(Integer.parseInt(str));
                }
            }

            operatisService.addOperatisInfo(request, null == id ? "新增" : "修改" + "杂志 " + magazine.getName());

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
