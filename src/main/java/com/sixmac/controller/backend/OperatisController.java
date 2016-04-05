package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Operatis;
import com.sixmac.service.OperatisService;
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
import java.util.Map;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
@Controller
@RequestMapping(value = "backend/operatis")
public class OperatisController extends CommonController {

    @Autowired
    private OperatisService operatisService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return "backend/操作日志";
    }

    @RequestMapping("/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String name,
                     String roleName,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Operatis> page = operatisService.page(name, roleName, pageNum, length);

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(ServletRequest request, HttpServletResponse response, Integer id) {
        if (id != null) {
            operatisService.deleteById(id);
            return 1;
        } else {
            WebUtil.printJson(response, new Result(false).msg("日志不存在"));
        }
        return -1;
    }

    @RequestMapping("/batchDel")
    @ResponseBody
    public Integer batchDel(String ids) {
        try {
            // 将界面上的id数组格式的字符串解析成int类型的数组
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            operatisService.deleteAll(arrayId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
