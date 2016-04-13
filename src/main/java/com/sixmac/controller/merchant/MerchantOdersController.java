package com.sixmac.controller.merchant;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Merchants;
import com.sixmac.entity.Orders;
import com.sixmac.entity.Ordersinfo;
import com.sixmac.service.MerchantsService;
import com.sixmac.service.OrdersService;
import com.sixmac.service.OrdersinfoService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
@Controller
@RequestMapping(value = "merchant/orders")
public class MerchantOdersController extends CommonController {

    @Autowired
    private OrdersService Ordersservice;

    @Autowired
    private OrdersinfoService ordersinfoService;

    @Autowired
    private MerchantsService merchantsService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap model, HttpServletRequest request) {
        Merchants merchants = MerchantIndexController.getMerchant(request, model, merchantsService);
        if (merchants.getIsCheck() != Constant.CHECK_STATUS_SUCCESS) {
            return "merchant/个人资料待审核";
        }

        return "merchant/订单列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String orderNum,
                     String mobile,
                     String productName,
                     Integer status,
                     Date startTime,
                     Date endTime,
                     Integer type,
                     Integer draw,
                     Integer start,
                     Integer length,
                     ModelMap model) {
        Merchants merchants = MerchantIndexController.getMerchant(request, model, merchantsService);
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Orders> page = Ordersservice.page(merchants.getId(),orderNum,mobile,productName,status,type, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping(value = "/detail")
    public String detail(ModelMap model, Integer id) {
        List<Ordersinfo> list = new ArrayList<Ordersinfo>();

        // 如果id不为空，则代表编辑
        if (null != id) {
            Orders orders = Ordersservice.getById(id);
            model.addAttribute("orders", orders);

            if (null != orders) {
                list = ordersinfoService.findListByOrderId(orders.getId());
            }

            model.addAttribute("orderInfoList", list);
        }

        return "merchant/订单详情";
    }
}
