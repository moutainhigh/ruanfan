package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Comment;
import com.sixmac.entity.Gams;
import com.sixmac.entity.Image;
import com.sixmac.entity.Journal;
import com.sixmac.service.CommentService;
import com.sixmac.service.GamsService;
import com.sixmac.service.ImageService;
import com.sixmac.service.JournalService;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/6 0006.
 */
@Controller
@RequestMapping("backend/journal")
public class JournalController extends CommonController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private GamsService gamsService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    @RequestMapping("index")
    public String index(ModelMap model) {
        return "backend/日志列表";
    }

    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer userId,
                     Integer draw,
                     Integer start,
                     Integer length
    ) {
        if (null == start || start == 0) {
            start = 1;
        }

        int pageNum = getPageNum(start, length);
        Page<Journal> page = journalService.iPage(userId, pageNum, length);

        for (Journal journal : page.getContent()) {
            journal.setGamNum(gamsService.iFindList(journal.getId(), Constant.GAM_JOURNAL, Constant.GAM_LOVE, Constant.SORT_TYPE_ASC).size());
            journal.setCommentNum(commentService.iFindList(journal.getId(), Constant.COMMENT_JOURNAL).size());
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping(value = "/detail")
    public String detail(ModelMap model, Integer id) {
        List<Image> list = null;
        List<Map<String, Object>> imageList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        if (id != null) {
            Journal journal = journalService.getById(id);
            model.addAttribute("journal", journal);
            if (null != journal) {
                journal.setGamNum(gamsService.iFindList(journal.getId(), Constant.GAM_JOURNAL, Constant.GAM_LOVE, Constant.SORT_TYPE_ASC).size());
                journal.setCommentNum(commentService.iFindList(journal.getId(), Constant.COMMENT_JOURNAL).size());
                list = imageService.iFindList(journal.getId(), Constant.IMAGE_JOURNAL);

                for (Image image : list) {
                    map = new HashMap<String, Object>();
                    map.put("id", image.getId());
                    map.put("path", image.getPath());

                    imageList.add(map);
                }

            }
        }
        model.addAttribute("imageList", JSONArray.fromObject(imageList));
        return "backend/日志详情";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Integer delete(HttpServletResponse response, Integer id) {
        if (id != null) {
            journalService.deleteById(id);
            return 1;
        } else {
            WebUtil.printJson(response, new Result(false).msg("不存在"));
        }
        return 0;
    }
}
