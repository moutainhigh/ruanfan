package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Report;
import com.sixmac.service.OperatisService;
import com.sixmac.service.ReportService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
@Controller
@RequestMapping(value = "backend/report")
public class ReportController extends CommonController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private OperatisService operatisService;

    @RequestMapping("index")
    public String index() {
        return "backend/举报列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String userName,
                     String sourceName,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Report> page = reportService.page(userName, sourceName, pageNum, length);

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("/update")
    @ResponseBody
    public Integer update(HttpServletRequest request, Integer id) {
        if (id != null) {
            Report report = reportService.getById(id);
            report.setIsIgnore(1);
            reportService.update(report);

            operatisService.addOperatisInfo(request, "忽略用户 " + report.getUser().getNickName() + " 的举报");

            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 批量删除举报信息
     *
     * @param ids
     * @return
     */
    @RequestMapping("/batchDel")
    @ResponseBody
    public Integer batchDel(HttpServletRequest request, String ids) {
        try {
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            reportService.deleteAll(request, arrayId);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
