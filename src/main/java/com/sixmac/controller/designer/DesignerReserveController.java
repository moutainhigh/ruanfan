package com.sixmac.controller.designer;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Designers;
import com.sixmac.entity.Reserve;
import com.sixmac.service.DesignersService;
import com.sixmac.service.ReserveService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.AbstractDocument;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
@Controller
@RequestMapping(value = "designer/reserve")
public class DesignerReserveController extends CommonController{

    @Autowired
    private DesignersService designersService;

    @Autowired
    private ReserveService reserveService;

    @RequestMapping(value = "/index")
    public String index(ModelMap model, HttpServletRequest request) {
        Designers designers = DesignerIndexController.getDesigner(request,model,designersService);

        if(designers.getIsCheck() != Constant.CHECK_STATUS_SUCCESS) {
            return "designer/个人资料待审核";
        }

        return "designer/预约列表";
    }

    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length,
                     ModelMap model) {
        Designers designers = DesignerIndexController.getDesigner(request,model,designersService);

        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);

        Page<Reserve> page = reserveService.iFindPageByDesignerId(designers.getId(),pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);

    }


}
