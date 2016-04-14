package com.sixmac.controller.designer;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.controller.merchant.MerchantIndexController;
import com.sixmac.core.Constant;
import com.sixmac.entity.*;
import com.sixmac.service.*;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
@Controller
@RequestMapping(value = "designer/works")
public class DesignerWorksController extends CommonController {

    @Autowired
    private WorksService worksService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DesignersService designersService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap model, HttpServletRequest request) {
        Designers designers = DesignerIndexController.getDesigner(request, model, designersService);
        if (designers.getIsCheck() != Constant.CHECK_STATUS_SUCCESS) {
            return "designer/个人资料待审核";
        }

        return "designer/作品列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String name,
                     Integer status,
                     Integer areas,
                     Integer stytle,
                     Integer draw,
                     Integer start,
                     Integer length,
                     ModelMap model) {
        Designers designers = DesignerIndexController.getDesigner(request, model, designersService);

        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Works> page = worksService.page(designers.getId(),name,status,areas,stytle,pageNum, length);

        for (Works works : page.getContent()) {
            works.setCommentNum(commentService.iFindList(works.getId(), Constant.COMMENT_WORKS).size());
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping(value = "/detail")
    public String detail(ModelMap model, Integer id) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则代表编辑
        if(id != null) {
            Works works = worksService.getById(id);
            model.addAttribute("works", works);

            // 如果作品不为空，则查询对应的图片集合
            if (null != works) {
                List<Image> imageList = imageService.iFindList(works.getId(), Constant.IMAGE_WORKS);
                for (Image image : imageList) {
                    map = new HashMap<String, Object>();
                    map.put("id", image.getId());
                    map.put("path", image.getPath());

                    list.add(map);
                }
            }

        }

        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "designer/作品详情";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Integer delete(Integer id) {
        try {
            worksService.deleteById(id);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Works works = worksService.getById(id);
            model.addAttribute("works", works);

            if (null != works) {
                List<Image> imageList = imageService.iFindList(works.getId(), Constant.IMAGE_WORKS);
                for (Image image : imageList) {
                    map = new HashMap<String, Object>();
                    map.put("id", image.getId());
                    map.put("path", image.getPath());

                    list.add(map);
                }
            }
        }
        model.addAttribute("imageList", JSONArray.fromObject(list));
        return "designer/新增作品";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Integer save(HttpServletRequest request,
                        ModelMap model,
                        Integer id,
                        String name,
                        String labels,
                        String description,
                        Integer settingCover,
                        String tempAddImageIds,
                        String tempDelImageIds) {
        try {
            Designers designers = DesignerIndexController.getDesigner(request, model, designersService);

            String[] addImageIds = tempAddImageIds.split(",");
            String[] delImageIds = tempDelImageIds.split(",");
            Works works = null;

            if (null == id) {
                works = new Works();
            } else {
                works = worksService.getById(id);
            }

            works.setName(name);
            works.setLabels(labels);
            works.setDesigner(designers);
            works.setDescription(description);
            works.setCoverId(settingCover);
            works.setCreateTime(new Date());

            worksService.update(works);

            // 保存商品图片集合
            Image image = null;
            for (String imageId : addImageIds) {
                if (null != imageId && !imageId.equals("")) {
                    image = imageService.getById(Integer.parseInt(imageId));
                    image.setObjectId(works.getId());
                    image.setObjectType(Constant.IMAGE_WORKS);

                    imageService.update(image);
                }
            }

            // 删除商品图片
            for (String imageId : delImageIds) {
                if (null != imageId && !imageId.equals("")) {
                    imageService.deleteById(Integer.parseInt(imageId));
                }
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping("/batchDel")
    @ResponseBody
    public Integer batchDel(String ids) {
        try {
            // 将界面上的id数组格式的字符串解析成int类型的数组
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            worksService.deleteAll(arrayId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
