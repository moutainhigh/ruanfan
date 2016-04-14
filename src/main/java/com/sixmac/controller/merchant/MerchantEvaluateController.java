package com.sixmac.controller.merchant;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Merchants;
import com.sixmac.entity.Ordersinfo;
import com.sixmac.service.MerchantsService;
import com.sixmac.service.OrdersinfoService;
import com.sixmac.utils.ConfigUtil;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
@Controller
@RequestMapping(value = "merchant/ordersinfo")
public class MerchantEvaluateController extends CommonController {

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

        return "merchant/评价列表";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String mobile,
                     String nickName,
                     Integer draw,
                     Integer start,
                     Integer length,
                     ModelMap model) {
        Merchants merchants = MerchantIndexController.getMerchant(request, model, merchantsService);

        int pageNum = getPageNum(start, length);

        Page<Ordersinfo> page = ordersinfoService.page(merchants.getId(),mobile, nickName, pageNum, length);
        List<Ordersinfo> list = page.getContent();
        if(list != null && !list.isEmpty()) {
            for (Ordersinfo info : list) {
                info.setProductPath(ConfigUtil.getString("upload.url") + info.getProductPath());
            }
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(ServletRequest request, HttpServletResponse response, Integer id) {
        if (id != null) {
            ordersinfoService.deleteById(id);
            return 1;
        } else {
            WebUtil.printJson(response, new Result(false).msg("评价不存在"));
        }
        return 0;
    }

    @RequestMapping("/batchDel")
    @ResponseBody
    public Integer batchDel(String ids) {
        try {
            // 将界面上的id数组格式的字符串解析成int类型的数组
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            ordersinfoService.deleteAll(arrayId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}