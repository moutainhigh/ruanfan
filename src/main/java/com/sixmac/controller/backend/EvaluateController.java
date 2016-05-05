package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Ordersinfo;
import com.sixmac.service.OperatisService;
import com.sixmac.service.OrdersinfoService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
@Controller
@RequestMapping(value = "backend/ordersinfo")
public class EvaluateController extends CommonController {

    @Autowired
    private OrdersinfoService ordersinfoService;

    @Autowired
    private OperatisService operatisService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "backend/评价列表";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void list(HttpServletResponse response,
                     String mobile,
                     String productName,
                     String orderNum,
                     Integer draw,
                     Integer start,
                     Integer length) {

        int pageNum = getPageNum(start, length);

        Page<Ordersinfo> page = ordersinfoService.page(mobile, productName, orderNum, pageNum, length);

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(HttpServletRequest request, Integer id) {
        if (id != null) {
            ordersinfoService.deleteInfo(request, id);
            return 1;
        }
        return 0;
    }

    @RequestMapping("/batchDel")
    @ResponseBody
    public Integer batchDel(HttpServletRequest request, String ids) {
        try {
            // 将界面上的id数组格式的字符串解析成int类型的数组
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            ordersinfoService.batchDeleteInfo(request, arrayId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
