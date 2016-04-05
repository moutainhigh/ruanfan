package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Orders;
import com.sixmac.service.IncomService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
@Controller
@RequestMapping(value = "backend/income")
public class IncomeController extends CommonController {

    @Autowired
    private IncomService incomeservice;

    @RequestMapping(value = "/index")
    public String index(ModelMap model) {
        return "backend/收入列表";
    }

    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     String orderNum,
                     String mobile,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Orders> page = incomeservice.page(orderNum, mobile, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }
}
