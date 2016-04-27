package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.entity.Comment;
import com.sixmac.service.CommentService;
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
 * Created by Administrator on 2016/4/1 0001.
 */
@Controller
@RequestMapping(value = "backend/comment")
public class CommentController extends CommonController {

    @Autowired
    private CommentService commentservice;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "backend/评论列表";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void list(HttpServletResponse response,
                     String mobile,
                     Integer type,
                     Integer draw,
                     Integer start,
                     Integer length) {
        int pageNum = getPageNum(start, length);

        Page<Comment> page = commentservice.page(mobile, type, pageNum, length);
        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Integer id) {
        try {
            commentservice.deleteById(id);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
