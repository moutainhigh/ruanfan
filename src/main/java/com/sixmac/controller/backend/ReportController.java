package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Report;
import com.sixmac.service.ReportService;
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
@RequestMapping(value = "backend/report")
public class ReportController extends CommonController {

    @Autowired
    private ReportService reportService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "backend/举报列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer userId,
                     Integer sourceId,
                     Integer type,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);
        Page<Report> page = reportService.page(userId, sourceId, type, pageNum, length);

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("/update")
    @ResponseBody
    public Integer update(Integer id) {
        if (id != null) {
            Report report = reportService.getById(id);
            report.setIsCut(1);
            report.setIsIgnore(1);
            reportService.update(report);
            return 1;
        } else {
            return -1;
        }
    }
}
