package com.sixmac.controller.designer;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Designers;
import com.sixmac.entity.Messageplus;
import com.sixmac.service.CityService;
import com.sixmac.service.DesignersService;
import com.sixmac.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/23 0023.
 */
@Controller
@RequestMapping(value = "designer")
public class DesignerIndexController extends CommonController {

    @Autowired
    private DesignersService designersService;

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/dashboard")
    public String dashboard(HttpServletRequest request, ModelMap model) {
        Designers designers = getDesigner(request, model, designersService);
        if (designers.getIsCheck() != Constant.CHECK_STATUS_SUCCESS) {
            return "designer/个人资料待审核";
        }

        return "designer/控制面板";
    }

    @RequestMapping(value = "/info")
    public String info(HttpServletRequest request, ModelMap model) {
        Designers designers = getDesigner(request, model, designersService);

        if (designers.getIsCheck() == Constant.CHECK_STATUS_SUCCESS) {
            return "designer/个人资料审核通过";
        } else {
            return "designer/个人资料待审核";
        }
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(HttpServletRequest request,
                        Integer id,
                        String mobile,
                        Integer type,
                        String nickName,
                        Integer cityId,
                        String content,
                        MultipartRequest multipartRequest) {
        try {
            Designers designers = designersService.getById(id);
            designers.setMobile(mobile);
            designers.setType(type);
            designers.setNickName(nickName);
            designers.setCity(cityService.getById(cityId));
            designers.setDescription(content);
            designers.setIsCheck(Constant.CHECK_STATUS_DEFAULT);

            // 保存资质证明
            MultipartFile proof = multipartRequest.getFile("mainImage2");
            if (null != proof) {
                Map<String, Object> map = ImageUtil.saveImage(request, proof, false);
                designers.setProof(map.get("imgURL").toString());
            }

            // 保存头像
            MultipartFile head = multipartRequest.getFile("mainImage");
            if (null != head) {
                Map<String, Object> map = ImageUtil.saveImage(request, head, false);
                designers.setHead(map.get("imgURL").toString());
            }

            designersService.update(designers);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @RequestMapping(value = "/savePlus")
    @ResponseBody
    public Integer savePlus(HttpServletRequest request,
                            Integer id,
                            Integer cityId,
                            String content,
                            MultipartRequest multipartRequest) {
        try {
            Designers designers = designersService.getById(id);
            designers.setCity(cityService.getById(cityId));
            designers.setDescription(content);

            // 保存头像
            MultipartFile head = multipartRequest.getFile("mainImage");
            if (null != head) {
                Map<String, Object> map = ImageUtil.saveImage(request, head, false);
                designers.setHead(map.get("imgURL").toString());
            }

            designersService.update(designers);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前登录的设计师信息
     *
     * @param request
     * @param model
     * @return
     */
    public static Designers getDesigner(HttpServletRequest request, ModelMap model, DesignersService designersService) {
        Integer designerId = (Integer) request.getSession().getAttribute(Constant.CURRENT_USER_ID);
        Designers designers = designersService.getById(designerId);
        model.addAttribute("designer", designers);

        // 查询审核失败的原因
        Messageplus messageplus = designersService.findReasonByDesignerId(designerId);
        if (null != messageplus) {
            model.addAttribute("reasonInfo", messageplus.getDescription());
        }

        return designers;
    }

    /**
     * 获取当前登录的设计师信息
     *
     * @param request
     * @return
     */
    public static Designers getDesigner(HttpServletRequest request, DesignersService designersService) {
        Integer designerId = (Integer) request.getSession().getAttribute(Constant.CURRENT_USER_ID);
        return designersService.getById(designerId);
    }
}
