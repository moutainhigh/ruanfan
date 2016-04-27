package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
import com.sixmac.service.*;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private HotWordsService hotWordsService;

    /**
     * @api {post} /api/afflatus/hotWordList 热词列表
     * @apiName afflatus.hotWordList
     * @apiGroup afflatus
     * * @apiParam {Integer} size 热词个数，如果不传，默认搜索三个热词
     * @apiSuccess {Object} list 热词list
     * @apiSuccess {Integer} list.id 热词id
     * @apiSuccess {String} list.words 热词名称
     */
    @RequestMapping(value = "/hotWordList")
    public void hotWordList(HttpServletResponse response, Integer size) {
        if (null == size) {
            size = 3;
        }

        List<HotWords> list = hotWordsService.findList(size);

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj, "count");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/afflatus/list 灵感集列表
     * @apiName afflatus.list
     * @apiGroup afflatus
     * @apiParam {String} key 名称
     * @apiParam {String} labels 标签
     * @apiParam {Integer} type 类型：1单图 2套图      <必传 />
     * @apiParam {Integer} styleId 风格id
     * @apiParam {Integer} areaId 区域id
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数      <必传 />
     * @apiSuccess {Object} list 灵感集列表
     * @apiSuccess {Integer} list.id 灵感集id
     * @apiSuccess {String} list.name 灵感集名称
     * @apiSuccess {Integer} list.type 类型：1单图   2套图
     * @apiSuccess {String} list.url 链接
     * @apiSuccess {Integer} list.showNum 浏览量
     * @apiSuccess {Integer} list.shareNum 分享数
     * @apiSuccess {Integer} list.isAuth 认证状态，0=未认证，1=已认证
     * @apiSuccess {String} list.labels 标签
     * @apiSuccess {Integer} list.status 状态，0=待审核，1=审核通过，2=审核不通过
     * @apiSuccess {String} list.createTime 创建时间
     * @apiSuccess {String} list.cover 封面图
     * @apiSuccess {Integer} list.collectNum 收藏数
     * @apiSuccess {Integer} list.gamNum 点赞数
     * @apiSuccess {Integer} list.reserveNum 预约数
     * @apiSuccess {Integer} list.designerId 设计师id
     * @apiSuccess {String} list.designerHead 设计师头像
     * @apiSuccess {String} list.designerName 设计师名称
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     String key,
                     String labels,
                     Integer type,
                     Integer styleId,
                     Integer areaId,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        if (StringUtils.isNotEmpty(key)) {
            hotWordsService.searchWord(key);
        }

        Page<Afflatus> page = afflatusService.iPage(key, labels, type, styleId, areaId, pageNum, pageSize);

        for (Afflatus afflatus : page.getContent()) {
            afflatus.setCover(imageService.getById(afflatus.getCoverId()).getPath());
            afflatus.setDesignerId(afflatus.getDesigner().getId());
            afflatus.setDesignerHead(afflatus.getDesigner().getHead());
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
     * @api {post} /api/afflatus/info 灵感集详情
     * @apiName afflatus.info
     * @apiGroup afflatus
     * @apiParam {Integer} afflatusId 灵感集id     <必传 />
     * @apiParam {Integer} userId 用户id
     * @apiSuccess {Object} afflatusInfo 灵感集详情
     * @apiSuccess {Integer} afflatusInfo.id 灵感集id
     * @apiSuccess {String} afflatusInfo.name 灵感集名称
     * @apiSuccess {Integer} afflatusInfo.type 类型：1单图   2套图
     * @apiSuccess {Integer} afflatusInfo.showNum 浏览量
     * @apiSuccess {Integer} afflatusInfo.shareNum 分享数
     * @apiSuccess {Integer} afflatusInfo.isComment 是否评论  0=是，1=否
     * @apiSuccess {Integer} afflatusInfo.isGam 是否点赞  0=是，1=否
     * @apiSuccess {Integer} afflatusInfo.isCollect 是否收藏  0=是，1=否
     * @apiSuccess {String} afflatusInfo.description 文字描述
     * @apiSuccess {String} afflatusInfo.labels 标签
     * @apiSuccess {String} afflatusInfo.url 链接
     * @apiSuccess {Integer} afflatusInfo.isAuth 认证状态，0=未认证，1=已认证
     * @apiSuccess {Integer} afflatusInfo.status 状态，0=待审核，1=审核通过，2=审核不通过
     * @apiSuccess {String} afflatusInfo.createTime 创建时间
     * @apiSuccess {String} afflatusInfo.cover 封面图
     * @apiSuccess {Object} afflatusInfo.loveList 猜你所想列表
     * @apiSuccess {Integer} afflatusInfo.loveList.id 猜你所想灵感集id
     * @apiSuccess {String} afflatusInfo.loveList.name 猜你所想灵感集名称
     * @apiSuccess {String} afflatusInfo.loveList.path 猜你所想灵感集封面图
     * @apiSuccess {Object} afflatusInfo.commentList 评论列表
     * @apiSuccess {Integer} afflatusInfo.commentList.id 评论id
     * @apiSuccess {Integer} afflatusInfo.commentList.userId 评论人id
     * @apiSuccess {Integer} afflatusInfo.commentList.userName 评论人昵称
     * @apiSuccess {Integer} afflatusInfo.commentList.userHead 评论人头像
     * @apiSuccess {Integer} afflatusInfo.commentList.content 评论内容
     * @apiSuccess {Integer} afflatusInfo.commentList.createTime 评论时间
     * @apiSuccess {Integer} afflatusInfo.collectNum 收藏数
     * @apiSuccess {Integer} afflatusInfo.gamNum 点赞数
     * @apiSuccess {Integer} afflatusInfo.reserveNum 预约数
     * @apiSuccess {String} afflatusInfo.designerId 设计师id
     * @apiSuccess {String} afflatusInfo.designerHead 设计师头像
     * @apiSuccess {String} afflatusInfo.designerName 设计师名称
     * @apiSuccess {Object} afflatusInfo.imageList 详情图片列表
     * @apiSuccess {Integer} afflatusInfo.imageList.id 图片id
     * @apiSuccess {String} afflatusInfo.imageList.path 图片路径
     * @apiSuccess {String} afflatusInfo.imageList.description 图片描述
     * @apiSuccess {String} afflatusInfo.imageList.demo 图片备注
     * @apiSuccess {String} afflatusInfo.imageList.createTime 创建时间
     * @apiSuccess {Object} afflatusInfo.imageList.labelList 图片标签列表
     * @apiSuccess {Integer} afflatusInfo.imageList.labelList.id 标签id
     * @apiSuccess {Integer} afflatusInfo.imageList.labelList.productId 商品id
     * @apiSuccess {String} afflatusInfo.imageList.labelList.name 标签名称
     * @apiSuccess {String} afflatusInfo.imageList.labelList.description 标签描述
     * @apiSuccess {String} afflatusInfo.imageList.labelList.leftPoint 标签左边距（实际使用时，数值乘以二）
     * @apiSuccess {String} afflatusInfo.imageList.labelList.topPoint 标签上边距（实际使用时，数值乘以二）
     */
    @RequestMapping("info")
    public void afflatusInfo(HttpServletResponse response,
                             Integer afflatusId,
                             Integer userId) {
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

        // 如果userId不为空的话，查询是否评论过、点赞过
        if (null != userId) {
            List<Comment> commentList = commentService.iFindList(userId, afflatusId, Constant.COMMENT_AFFLATUS);
            afflatus.setIsComment(null == commentList || commentList.size() == 0 ? Constant.COMMENT_STATUS_NO : Constant.COMMENT_STATUS_YES);

            Gams gams = gamsService.iFindOne(userId, afflatusId, Constant.GAM_AFFLATUS, Constant.GAM_LOVE);
            afflatus.setIsGam(null == gams ? Constant.GAM_LOVE_NO : Constant.GAM_LOVE_YES);

            Collect collect = collectService.iFindOne(userId, afflatusId, Constant.COLLECT_AFFLATUS);
            afflatus.setIsCollect(null == collect ? Constant.GAM_LOVE_NO : Constant.GAM_LOVE_YES);
        }

        // 查看详情的同时，增加浏览量
        afflatus.setShowNum(afflatus.getShowNum() + 1);
        afflatusService.update(afflatus);

        afflatus.setCover(imageService.getById(afflatus.getCoverId()).getPath());
        afflatus.setDesignerId(afflatus.getDesigner().getId());
        afflatus.setDesignerHead(afflatus.getDesigner().getHead());
        afflatus.setDesignerName(afflatus.getDesigner().getNickName());

        Result obj = new Result(true).data(createMap("afflatusInfo", afflatus));
        String result = JsonUtil.obj2ApiJson(obj, "designer", "style", "area", "coverId", "city", "objectId", "objectType", "password", "user", "product", "thuPath", "width", "height", "labelId");
        WebUtil.printApi(response, result);
    }
}
