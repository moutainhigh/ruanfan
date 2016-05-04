package com.sixmac.controller.designer;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Designers;
import com.sixmac.entity.Image;
import com.sixmac.entity.Label;
import com.sixmac.service.*;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/3/9 0009.
 */
@Controller
@RequestMapping(value = "designer/afflatus")
public class DesignerAfflatusController extends CommonController {

    @Autowired
    private AfflatusService afflatusService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AreasService areasService;

    @Autowired
    private StylesService stylesService;

    @Autowired
    private DesignersService designersService;

    @Autowired
    private LabelService labelService;

    @RequestMapping("index")
    public String index(ModelMap model, HttpServletRequest request) {
        Designers designers = DesignerIndexController.getDesigner(request, model, designersService);
        if (designers.getIsCheck() != Constant.CHECK_STATUS_SUCCESS) {
            return "designer/个人资料待审核";
        }

        return "designer/灵感图集列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String afflatusName,
                     Integer status,
                     Integer styleId,
                     Integer areaId,
                     Integer draw,
                     Integer start,
                     Integer length) {
        Designers designers = DesignerIndexController.getDesigner(request, designersService);

        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Afflatus> page = afflatusService.page(designers.getId(), afflatusName, status, styleId, areaId, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id, Integer type) {
        model.addAttribute("backType", type);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Afflatus afflatus = afflatusService.getById(id);
            model.addAttribute("afflatus", afflatus);

            // 如果灵感集不为空，则查询对应的图片集合
            if (null != afflatus) {
                List<Image> imageList = imageService.iFindList(afflatus.getId(), Constant.IMAGE_AFFLATUS);
                for (Image image : imageList) {
                    map = new HashMap<String, Object>();
                    map.put("id", image.getId());
                    map.put("path", image.getPath());

                    list.add(map);
                }
            }
        }

        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "designer/新增灵感集";
    }

    /**
     * 保存灵感集
     *
     * @param id
     * @param name
     * @param type
     * @param styleId
     * @param areaId
     * @param settingCover
     * @param labels
     * @param tempAddImageIds
     * @param tempDelImageIds
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(HttpServletRequest request,
                        Integer id,
                        String name,
                        Integer type,
                        Integer styleId,
                        Integer areaId,
                        Integer settingCover,
                        String description,
                        String labels,
                        String url,
                        String tempAddImageIds,
                        String tempDelImageIds) {
        try {
            String[] addImageIds = tempAddImageIds.split(",");
            String[] delImageIds = tempDelImageIds.split(",");

            Afflatus afflatus = new Afflatus();
            if (null != id) {
                afflatus = afflatusService.getById(id);
            }
            afflatus.setName(name);
            afflatus.setType(type);
            afflatus.setDesigner(DesignerIndexController.getDesigner(request, designersService));
            afflatus.setStyle(stylesService.getById(styleId));
            afflatus.setArea(areasService.getById(areaId));
            afflatus.setLabels(labels);
            afflatus.setUrl(url);
            afflatus.setCoverId(settingCover);
            afflatus.setDescription(description);

            // 保存灵感集信息
            if (null != id) {
                afflatusService.update(afflatus);
            } else {
                afflatus.setIsAuth(Constant.AUTH_STATUS_NO);
                afflatus.setShowNum(0);
                afflatus.setShareNum(0);
                afflatus.setStatus(Constant.CHECK_STATUS_DEFAULT);
                afflatus.setCreateTime(new Date());
                afflatusService.create(afflatus);
            }

            // 保存灵感集图片集合
            Image image = null;
            for (String imageId : addImageIds) {
                if (null != imageId && !imageId.equals("")) {
                    image = imageService.getById(Integer.parseInt(imageId));
                    image.setObjectId(afflatus.getId());
                    image.setObjectType(Constant.IMAGE_AFFLATUS);

                    imageService.update(image);
                }
            }

            // 删除灵感集图片
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

    /**
     * 删除灵感集
     *
     * @param afflatusId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Integer delete(Integer afflatusId) {
        try {
            afflatusService.deleteById(afflatusId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 灵感集提交审核
     *
     * @param afflatusId
     * @return
     */
    @RequestMapping(value = "/subToCheck")
    @ResponseBody
    public Integer subToCheck(Integer afflatusId) {
        try {
            Afflatus afflatus = afflatusService.getById(afflatusId);
            afflatus.setStatus(Constant.CHECK_STATUS_DEFAULT);

            afflatusService.update(afflatus);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 系列图添加标签
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/addTempLabel")
    public String addTempLabel(Integer id, ModelMap model) {
        try {
            Image image = imageService.getById(id);
            // 查询标签信息
            List<Label> labelList = labelService.findListByParams(image.getId(), Constant.LABEL_AFFLATUS);
            model.put("imageInfo", image);
            model.put("objectType", Constant.LABEL_AFFLATUS);
            model.put("labelList", JSONArray.fromObject(labelList.size() == 0 ? null : labelList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "designer/图片锚点";
    }


    @RequestMapping("/batchDel")
    @ResponseBody
    public Integer batchDel(String ids) {
        try {
            // 将界面上的id数组格式的字符串解析成int类型的数组
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            afflatusService.deleteAll(arrayId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
