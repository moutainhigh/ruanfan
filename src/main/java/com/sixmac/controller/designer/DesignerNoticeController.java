package com.sixmac.controller.designer;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.controller.merchant.MerchantIndexController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Designers;
import com.sixmac.entity.Merchants;
import com.sixmac.entity.Notices;
import com.sixmac.service.DesignersService;
import com.sixmac.service.ImageService;
import com.sixmac.service.MerchantsService;
import com.sixmac.service.NoticesService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
@Controller
@RequestMapping(value = "designer/notice")
public class DesignerNoticeController extends CommonController {

    @Autowired
    private DesignersService designersService;

    @Autowired
    private NoticesService noticesService;

    @RequestMapping("index")
    public String index(ModelMap model, HttpServletRequest request) {
        Designers designers = DesignerIndexController.getDesigner(request, model, designersService);
        if (designers.getIsCheck() != Constant.CHECK_STATUS_SUCCESS) {
            return "designer/个人资料待审核";
        }

        return "designer/系统消息列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length,
                     ModelMap model) {
        Designers designers = DesignerIndexController.getDesigner(request, model, designersService);

        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Notices> page = noticesService.page(designers.getId(), Constant.NOTICES_TYPE_DESIGNER, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Integer delete(Integer id) {
        try {
            noticesService.deleteById(id);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
