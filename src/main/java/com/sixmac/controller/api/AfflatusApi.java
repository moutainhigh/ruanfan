package com.sixmac.controller.api;

import com.sixmac.common.exception.GeneralException;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Collect;
import com.sixmac.entity.Image;
import com.sixmac.entity.Label;
import com.sixmac.service.*;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.PathUtils;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Controller
@RequestMapping(value = "api/afflatus")
public class AfflatusApi extends CommonController {

    @Autowired
    private AfflatusService afflatusService;

    @Autowired
    private GamsService gamsService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private LabelService labelService;

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

        for (Afflatus afflatus : page.getContent()) {
            afflatus.setCover(PathUtils.getRemotePath() + imageService.getById(afflatus.getCoverId()).getPath());
            afflatus.setDesignerHead(PathUtils.getRemotePath() + afflatus.getDesigner().getHead());
            afflatus.setDesignerName(afflatus.getDesigner().getNickName());

            // 分享、收藏、点赞、预约数
            afflatus.setCollectNum(collectService.iFindList(afflatus.getId(), Constant.COLLECT_AFFLATUS).size());

            afflatus.setGamNum(gamsService.iFindList(afflatus.getId(), Constant.GAM_AFFLATUS, Constant.GAM_LOVE, Constant.SORT_TYPE_ASC).size());

            afflatus.setReserveNum(reserveService.iFindListByDesignerId(afflatus.getDesigner().getId()).size());
        }

        Map<java.lang.String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "designer", "style", "area", "coverId", "gamsList", "loveList", "commentList", "labelList", "imageList");
        WebUtil.printApi(response, result);
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
            return;
        }

        // 分享、收藏、点赞、预约数
        afflatus.setCollectNum(collectService.iFindList(afflatus.getId(), Constant.COLLECT_AFFLATUS).size());

        afflatus.setGamNum(gamsService.iFindList(afflatus.getId(), Constant.GAM_AFFLATUS, Constant.GAM_LOVE, Constant.SORT_TYPE_ASC).size());

        afflatus.setReserveNum(reserveService.iFindListByDesignerId(afflatus.getDesigner().getId()).size());

        // 查询评论列表
        afflatus.setCommentList(commentService.iFindList(afflatusId, Constant.COMMENT_AFFLATUS));

        // 查询点赞列表
        afflatus.setGamsList(gamsService.iFindList(afflatusId, Constant.GAM_AFFLATUS, Constant.GAM_LOVE, Constant.SORT_TYPE_DESC));

        // 查询猜你所想列表
        afflatus.setLoveList(afflatusService.iFindLoveList(afflatus.getId(), afflatus.getType(), afflatus.getStyle().getId(), afflatus.getArea().getId()));

        // 查询图片列表
        List<Image> imageList = imageService.iFindList(afflatusId, Constant.IMAGE_AFFLATUS);

        // 查询标签列表
        for (Image image : imageList) {
            image.setLabelList(labelService.findListByParams(image.getId(), Constant.LABEL_AFFLATUS));
        }

        afflatus.setImageList(imageList);

        // 查看详情的同时，增加浏览量
        afflatus.setShowNum(afflatus.getShowNum() + 1);
        afflatusService.update(afflatus);

        afflatus.setCover(PathUtils.getRemotePath() + imageService.getById(afflatus.getCoverId()).getPath());
        afflatus.setDesignerHead(PathUtils.getRemotePath() + afflatus.getDesigner().getHead());
        afflatus.setDesignerName(afflatus.getDesigner().getNickName());

        Result obj = new Result(true).data(createMap("afflatusInfo", afflatus));
        String result = JsonUtil.obj2ApiJson(obj, "designer", "style", "area", "coverId", "city", "objectId", "objectType", "password");
        WebUtil.printApi(response, result);
    }
}
