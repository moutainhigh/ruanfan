package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Reserve;
import com.sixmac.service.ReserveService;
import com.sixmac.utils.JsonUtil;
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
 * Created by Administrator on 2016/3/30 0030.
 */
@Controller
@RequestMapping(value = "backend/reserve")
public class ReserveController extends CommonController {

    @Autowired
    private ReserveService reserveServicee;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "backend/预约列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String name,
                     String mobile,
                     String email,
                     String nickName,
                     Integer status,
                     Integer draw,
                     Integer start,
        Integer length) {
            if (null == start || start == 0) {
                start = 1;
            }
            int pageNum = getPageNum(start, length);
            Page<Reserve> page = reserveServicee.page(name, mobile, email, nickName, status, pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.printJson(response, result);
        }

        @RequestMapping(value = "/detail")
        public String detail(ModelMap model, Integer id) {
            // 如果id不为空，则代表编辑
            if (null != id) {
                Reserve reserve = reserveServicee.getById(id);
                model.addAttribute("reserve", reserve);
            }

        return "backend/预约详情";
    }

    @RequestMapping("/batchConfirm")
    @ResponseBody
    public Integer batchConfirm(String ids, String reserveTime, String reserveAddress) {
        try {
            // 将界面上的id数组格式的字符串解析成int类型的数组
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);

            reserveServicee.batchConfirm(arrayId, reserveTime, reserveAddress);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
