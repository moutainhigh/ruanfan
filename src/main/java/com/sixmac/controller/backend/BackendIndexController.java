package com.sixmac.controller.backend;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Designers;
import com.sixmac.entity.Merchants;
import com.sixmac.entity.Sysusers;
import com.sixmac.service.OrdersinfoService;
import com.sixmac.service.UsersService;
import com.sixmac.utils.IdenCode;
import com.sixmac.utils.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangbin on 2015/7/29.
 */
@Controller
@RequestMapping(value = "backend")
public class BackendIndexController extends CommonController {

    @Autowired
    private OrdersinfoService ordersinfoService;

    @RequestMapping(value = "/dashboard")
    public String dashboard(HttpServletRequest request,
                            HttpServletResponse response,
                            ModelMap model) {


        return "backend/控制面板";
    }
}
