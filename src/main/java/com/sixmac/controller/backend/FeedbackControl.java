package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Feedback;
import com.sixmac.service.FeedbackService;
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
 * Created by Administrator on 2016/3/29 0029.
 */
@Controller
@RequestMapping(value = "backend/feedback")
public class FeedbackControl extends CommonController {

    @Autowired
    private FeedbackService service;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "backend/反馈列表";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String nickName,
                     String mobile,
                     String mail,
                     Integer draw,
                     Integer start,
                     Integer length) {

        int pageNum = getPageNum(start, length);

        Page<Feedback> page = service.page(nickName, mobile, mail, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }


    @RequestMapping("/update")
    @ResponseBody
    public Integer update(Integer id) {
        if (id != null) {
            Feedback feedback = service.getById(id);
            feedback.setStatus(1);

            service.update(feedback);
            return 1;
        } else {
            return -1;
        }
    }
}
