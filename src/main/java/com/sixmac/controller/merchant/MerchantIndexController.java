package com.sixmac.controller.merchant;

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
@RequestMapping(value = "merchant")
public class MerchantIndexController extends CommonController {

    @RequestMapping(value = "/dashboard")
    public String dashboard(HttpServletRequest request,
                            HttpServletResponse response,
                            ModelMap model) {
        return "merchant/控制面板";
    }
}
