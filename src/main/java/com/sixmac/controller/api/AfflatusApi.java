package com.sixmac.controller.api;

import com.sixmac.common.exception.GeneralException;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Collect;
import com.sixmac.service.*;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Controller
@RequestMapping(value = "api/afflatus")
public class AfflatusApi {

    @Autowired
    private AfflatusService afflatusService;

    @Autowired
    private GamsService gamsService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CommentService commentService;

    /**
     * 灵感集列表
     *
     * @param response
     * @param type
     * @param styleId
     * @param areaId
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     Integer type,
                     Integer styleId,
                     Integer areaId,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Afflatus> page = afflatusService.iPage(type, styleId, areaId, pageNum, pageSize);

        Map<java.lang.String, Object> dataMap = APIFactory.fitting(page);
        WebUtil.printApi(response, new Result(true).data(dataMap));
    }

    /**
     * 查看灵感集详情
     *
     * @param response
     * @param afflatusId
     */
    @RequestMapping("info")
    public void afflatusInfo(HttpServletResponse response,
                             Integer afflatusId) {
        if (null == afflatusId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        Afflatus afflatus = afflatusService.getById(afflatusId);

        if (null == afflatus) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
        }

        // 查询评论列表
        afflatus.setCommentList(commentService.iFindList(afflatusId, Constant.COMMENT_AFFLATUS));

        // 查询点赞列表
        afflatus.setGamsList(gamsService.iFindList(afflatusId, Constant.GAM_AFFLATUS, Constant.GAM_LOVE, Constant.SORT_TYPE_DESC));

        // 查询猜你所想列表
        afflatus.setLoveList(afflatusService.iFindLoveList(afflatus.getType(), afflatus.getStyle().getId(), afflatus.getArea().getId()));

        // 查看详情的同时，增加浏览量
        afflatus.setShowNum(afflatus.getShowNum() + 1);
        afflatusService.update(afflatus);

        WebUtil.printApi(response, new Result(true).data(afflatus));
    }

    @RequestMapping("/collect")
    public void collect(HttpServletResponse response, Integer userId, Integer afflatusId, Integer action) {
        // action（收藏状态字段）,0表示收藏，1表示取消收藏
        String msg = "";
        if (null == userId || null == afflatusId || null == action) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        if (action == 0) {
            try {
                collectService.iCreate(usersService.getById(userId), afflatusId, Constant.COLLECT_AFFLATUS);
            } catch (GeneralException e) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
            }
            msg = "灵感集收藏成功";
        } else {
            Collect collect = collectService.iFindOne(userId, afflatusId, Constant.COLLECT_AFFLATUS);
            if (null != collect) {
                collectService.deleteById(collect.getId());
            }
            msg = "灵感集取消收藏成功";
        }

        WebUtil.printApi(response, new Result(true).msg(msg));
    }

    @RequestMapping("/share")
    public void share(HttpServletResponse response, Integer afflatusId) {
        if (null == afflatusId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 增加灵感集的分享数
        Afflatus afflatus = afflatusService.getById(afflatusId);

        if (null == afflatus) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
        }

        afflatus.setShareNum(afflatus.getShareNum() + 1);

        afflatusService.update(afflatus);

        WebUtil.printApi(response, new Result(true));
    }
}
