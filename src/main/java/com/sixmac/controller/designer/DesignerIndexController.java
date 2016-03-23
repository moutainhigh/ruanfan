package com.sixmac.controller.designer;

import com.sixmac.controller.common.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/3/23 0023.
 */
@Controller
@RequestMapping(value = "designer")
public class DesignerIndexController extends CommonController {

    @RequestMapping(value = "/dashboard")
    public String dashboard(HttpServletRequest request,
                            HttpServletResponse response,
                            ModelMap model) {
        return "designer/控制面板";
    }
}
