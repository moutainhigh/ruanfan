package com.sixmac.controller.designer;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Comment;
import com.sixmac.entity.Designers;
import com.sixmac.entity.Replys;
import com.sixmac.service.CommentService;
import com.sixmac.service.DesignersService;
import com.sixmac.service.ReplysService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
@Controller
@RequestMapping(value = "designer/comment")
public class DesignerCommentController extends CommonController {

    @Autowired
    private DesignersService designersService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReplysService replysService;

    @RequestMapping(value = "/index")
    public String index(ModelMap model, HttpServletRequest request) {
        Designers designers = DesignerIndexController.getDesigner(request, model, designersService);
        if (designers.getIsCheck() != Constant.CHECK_STATUS_SUCCESS) {
            return "designer/个人资料待审核";
        }

        return "designer/评论列表";
    }

    @RequestMapping(value = "list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length,
                     ModelMap model) {
        Designers designers = DesignerIndexController.getDesigner(request, model, designersService);

        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);

        Page<Comment> page1 = commentService.page(designers.getId(), Constant.COMMENT_WORKS, pageNum, length);
        Map<String, Object> result1 = DataTableFactory.fitting(draw, page1);

        Page<Comment> page2 = commentService.page(designers.getId(), Constant.COMMENT_AFFLATUS, pageNum, length);
        Map<String, Object> result2 = DataTableFactory.fitting(draw, page2);

        WebUtil.printJson(response, result1);
        WebUtil.printJson(response, result2);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Integer delete(Integer id) {
        try {
            commentService.deleteById(id);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @RequestMapping(value = "/addReply")
    @ResponseBody
    public Integer save(HttpServletRequest request,
                        Integer commentId,
                        String replyInfo,
                        ModelMap model) {
        try {
            Designers designers = DesignerIndexController.getDesigner(request, model, designersService);

            Replys replys = new Replys();
            replys.setComment(commentService.getById(commentId));
            replys.setReplySourceId(designers.getId());
            replys.setReplySourceType(Constant.ATTENTION_DESIGNERS);
            replys.setSourceName(designers.getNickName());
            replys.setSourceHead(designers.getHead());
            replys.setContent(replyInfo);
            replys.setCreateTime(new Date());

            replysService.create(replys);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
