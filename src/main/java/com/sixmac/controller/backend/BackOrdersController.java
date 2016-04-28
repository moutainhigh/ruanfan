package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Orders;
import com.sixmac.entity.Ordersinfo;
import com.sixmac.service.OrdersService;
import com.sixmac.service.OrdersinfoService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
@Controller
@RequestMapping(value = "backend/orders")
public class BackOrdersController extends CommonController {

    @Autowired
    private OrdersService orderService;

    @Autowired
    private OrdersinfoService ordersinfoService;

    @RequestMapping(value = "/index1", method = RequestMethod.GET)
    public String index1(ModelMap model) {
        return "backend/订单列表";
    }

    @RequestMapping(value = "/index2", method = RequestMethod.GET)
    public String index2(ModelMap model) {
        return "backend/套餐订单";
    }

    @RequestMapping(value = "/index3", method = RequestMethod.GET)
    public String index3(ModelMap model) {
        return "backend/秒杀订单";
    }

    @RequestMapping("/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String orderNum,
                     String mobile,
                     String nickName,
                     Integer status,
                     Integer type,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Orders> page = orderService.page(orderNum, mobile, nickName, status, type, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping(value = "/detail")
    public String detail(ModelMap model, Integer id) {
        List<Ordersinfo> list = new ArrayList<Ordersinfo>();

        // 如果id不为空，则代表编辑
        if (null != id) {
            Orders orders = orderService.getById(id);
            model.addAttribute("orders", orders);

            if (null != orders) {
                list = ordersinfoService.findListByOrderId(orders.getId());
            }

            model.addAttribute("orderInfoList", list);
        }

        return "backend/订单详情";
    }

    @RequestMapping(value = "/changeStatus")
    @ResponseBody
    public Integer changeStatus(Integer id) {
        try {
            Orders orders = orderService.getById(id);
            orders.setStatus(Constant.ORDERS_STATUS_002);

            orderService.update(orders);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
