package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Image;
import com.sixmac.entity.Label;
import com.sixmac.entity.Virtuals;
import com.sixmac.service.*;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/3/9 0009.
 */
@Controller
@RequestMapping(value = "backend/afflatus")
public class AfflatusController extends CommonController {

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

    @Autowired
    private VrtypeService vrtypeService;

    @Autowired
    private VirtualsService virtualsService;

    @Autowired
    private MessageService messageService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "backend/灵感图集列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String afflatusName,
                     String designerName,
                     Integer status,
                     Integer styleId,
                     Integer areaId,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Afflatus> page = afflatusService.page(afflatusName, designerName, status, styleId, areaId, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
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

        return "backend/新增灵感集";
    }

    @RequestMapping("show")
    public String show(ModelMap model, Integer id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
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

        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "backend/灵感集详情";
    }

    /**
     * 保存灵感集
     *
     * @param id
     * @param name
     * @param type
     * @param styleId
     * @param areaId
     * @param designerId
     * @param settingCover
     * @param labels
     * @param tempAddImageIds
     * @param tempDelImageIds
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(Integer id,
                        String name,
                        Integer type,
                        Integer styleId,
                        Integer areaId,
                        Integer designerId,
                        Integer settingCover,
                        String labels,
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
            afflatus.setDesigner(designersService.getById(designerId));
            afflatus.setStyle(stylesService.getById(styleId));
            afflatus.setArea(areasService.getById(areaId));
            afflatus.setLabels(labels);
            afflatus.setCoverId(settingCover);

            // 保存灵感集信息
            if (null != id) {
                afflatusService.update(afflatus);
            } else {
                afflatus.setShowNum(0);
                afflatus.setShareNum(0);
                afflatus.setStatus(1);
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
            model.put("labelList", JSONArray.fromObject(labelList.size() == 0 ? null : labelList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "backend/图片锚点";
    }

    /**
     * 灵感集转换为虚拟体验
     *
     * @param afflatusId
     * @param typeId
     * @return
     */
    @RequestMapping("/changeVR")
    @ResponseBody
    public Integer changeVR(Integer afflatusId, Integer typeId) {
        try {
            // 根据灵感集id获取灵感集详情
            Afflatus afflatus = afflatusService.getById(afflatusId);

            // 创建虚拟体验实体类，准备开始复制信息
            Virtuals virtuals = new Virtuals();
            virtuals.setName(afflatus.getName());
            virtuals.setStyle(afflatus.getStyle());
            virtuals.setType(vrtypeService.getById(typeId));
            virtuals.setLabels(afflatus.getLabels());
            virtuals.setCover(imageService.getById(afflatus.getCoverId()).getPath());
            virtuals.setUrl("");
            virtuals.setCreateTime(new Date());

            // 保存虚拟体验信息
            virtualsService.create(virtuals);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 审核灵感集
     *
     * @param afflatusId
     * @param status
     * @return
     */
    @RequestMapping("/changeCheck")
    @ResponseBody
    public Integer changeCheck(Integer afflatusId, Integer status, String reason) {
        try {
            afflatusService.changeCheck(afflatusId, status, reason);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
