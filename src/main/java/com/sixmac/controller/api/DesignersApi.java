package com.sixmac.controller.api;

import com.sixmac.common.exception.GeneralException;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
import com.sixmac.service.*;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/7 0007.
 */
@Controller
@RequestMapping(value = "api/designers")
public class DesignersApi extends CommonController {

    @Autowired
    private DesignersService designersService;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private GamsService gamsService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AttentionsService attentionsService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private WorksService worksService;

    @Autowired
    private CollectService collectService;

    /**
     * @api {post} /api/designers/list 设计师列表
     * @apiName designers.list
     * @apiGroup designers
     * @apiParam {Integer} type 类型，1=独立设计师，2=设计公司       <必传 />
     * @apiParam {String} nickname 设计师昵称
     * @apiParam {Integer} cityId 所在城市id
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数      <必传 />
     * @apiSuccess {Object} list 设计师列表
     * @apiSuccess {Integer} list.id 设计师id
     * @apiSuccess {String} list.nickname 设计师昵称
     * @apiSuccess {String} list.mobile 手机号
     * @apiSuccess {String} list.head 头像
     * @apiSuccess {Integer} list.type 类型，1=独立设计师，2=设计公司
     * @apiSuccess {String} list.proof 资质证明（图片路径）
     * @apiSuccess {Integer} list.star 星级
     * @apiSuccess {String} list.price 价格
     * @apiSuccess {String} list.sign 签名
     * @apiSuccess {String} list.content 描述
     * @apiSuccess {Text} list.description 软装介绍（富文本）
     * @apiSuccess {Text} list.descs 个性定制介绍（富文本）
     * @apiSuccess {Integer} list.isAuth 认证状态，0=未认证，1=已认证
     * @apiSuccess {Integer} list.status 状态
     * @apiSuccess {String} list.createTime 注册时间
     * @apiSuccess {Integer} list.cityId 所在城市id
     * @apiSuccess {Object} list.worksList 最新的设计作品列表（倒序，三张）
     * @apiSuccess {Integer} list.worksList.id 设计作品id
     * @apiSuccess {String} list.worksList.name 设计作品名称
     * @apiSuccess {String} list.worksList.labels 标签
     * @apiSuccess {String} list.worksList.description 描述
     * @apiSuccess {String} list.worksList.createTime 创建时间
     * @apiSuccess {String} list.worksList.cover 封面图
     * @apiSuccess {Integer} list.fansNum 粉丝数
     * @apiSuccess {Integer} list.reserveNum 预约数
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     Integer type,
                     String nickname,
                     Integer cityId,
                     Integer pageNum,
                     Integer pageSize) {
        if (/*null == type || */null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Designers> page = designersService.iPage(type, nickname, cityId, pageNum, pageSize);

        for (Designers designer : page.getContent()) {
            // 设置设计师所属城市id
            designer.setCityId(designer.getCity().getId());

            // 获取每个独立设计师的最新三张作品图片
            if (designer.getType() == Constant.DESIGNER_TYPE_ONE) {
                designer.setWorksList(worksService.iFindThreeNewWorksByDesignerId(designer.getId()));
            } else {
                designer.setWorksList(new ArrayList<Works>());
            }

            // 获取粉丝数
            designer.setFansNum(attentionsService.iFindList(designer.getId(), Constant.ATTENTION_DESIGNERS).size());

            // 获取预约数
            designer.setReserveNum(reserveService.iFindListByDesignerId(designer.getId()).size());
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "city", "password", "area", "designer", "isCheck", "gamsList", "commentList", "objectId", "objectType", "isGam", "gamNum", "commentNum", "labelList", "thuPath", "width", "height", "isAttention", "isCut");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/designers/workList 设计作品列表
     * @apiName designers.workList
     * @apiGroup designers
     * @apiParam {Integer} designerId 设计师id     <必传 />
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数      <必传 />
     * @apiSuccess {Object} list 设计作品列表
     * @apiSuccess {Integer} list.id 设计作品id
     * @apiSuccess {String} list.name 设计作品名称
     * @apiSuccess {String} list.labels 标签
     * @apiSuccess {String} list.description 描述
     * @apiSuccess {String} list.createTime 创建时间
     * @apiSuccess {String} list.cover 封面图
     * @apiSuccess {String} list.designerHead 设计师头像
     * @apiSuccess {Integer} list.reserveNum 预约数
     * @apiSuccess {Integer} list.gamNum 点赞数
     * @apiSuccess {Integer} list.commentNum 评论数
     * @apiSuccess {Integer} list.collectNum 收藏数
     */
    @RequestMapping(value = "/workList")
    public void workList(HttpServletResponse response,
                         Integer designerId,
                         Integer pageNum,
                         Integer pageSize) {
        if (null == designerId || null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Works> page = designersService.iPageWorks(designerId, pageNum, pageSize);

        for (Works work : page.getContent()) {
            work.setCover(imageService.getById(work.getCoverId()).getPath());

            // 设计师头像、预约数、点赞数、评论数、收藏数
            work.setDesignerHead(work.getDesigner().getHead());

            work.setReserveNum(reserveService.iFindListByDesignerId(work.getDesigner().getId()).size());

            work.setGamNum(gamsService.iFindList(work.getId(), Constant.GAM_WORKS, Constant.GAM_LOVE, Constant.SORT_TYPE_ASC).size());

            work.setCommentNum(commentService.iFindList(work.getId(), Constant.COMMENT_WORKS).size());

            work.setCollectNum(collectService.iFindList(work.getId(), Constant.COLLECT_WORKS).size());
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "designer", "coverId", "isCut", "imageList", "commentList", "gamsList", "isGam", "isCollect", "comment");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/designers/workInfo 设计作品详情
     * @apiName designers.workInfo
     * @apiGroup designers
     * @apiParam {Integer} workId 设计作品id     <必传 />
     * @apiParam {Integer} userId 用户id
     * @apiSuccess {Object} workInfo 设计作品列表
     * @apiSuccess {Integer} workInfo.id 设计作品id
     * @apiSuccess {String} workInfo.name 设计作品名称
     * @apiSuccess {String} workInfo.labels 标签
     * @apiSuccess {String} workInfo.description 描述
     * @apiSuccess {String} workInfo.createTime 创建时间
     * @apiSuccess {String} workInfo.cover 封面图
     * @apiSuccess {String} workInfo.designerHead 设计师头像
     * @apiSuccess {Integer} workInfo.reserveNum 预约数
     * @apiSuccess {Integer} workInfo.gamNum 点赞数
     * @apiSuccess {Integer} workInfo.isGam 是否点赞，0=是，1=否
     * @apiSuccess {Integer} workInfo.isCollect 是否收藏，0=是，1=否
     * @apiSuccess {Integer} workInfo.showNum 人气
     * @apiSuccess {Integer} workInfo.commentNum 评论数
     * @apiSuccess {Integer} workInfo.collectNum 收藏数
     * @apiSuccess {Object} workInfo.imageList 设计作品图片列表
     * @apiSuccess {Integer} workInfo.imageList.id 图片id
     * @apiSuccess {String} workInfo.imageList.path 图片路径
     * @apiSuccess {String} workInfo.imageList.description 图片描述
     * @apiSuccess {String} workInfo.imageList.demo 图片备注
     * @apiSuccess {String} workInfo.imageList.createTime 创建时间
     * @apiSuccess {Object} workInfo.imageList.labelList 图片标签列表
     * @apiSuccess {Integer} workInfo.imageList.labelList.id 标签id
     * @apiSuccess {String} workInfo.imageList.labelList.name 标签名称
     * @apiSuccess {String} workInfo.imageList.labelList.description 标签描述
     * @apiSuccess {String} workInfo.imageList.labelList.leftPoint 标签左边距（实际使用时，数值乘以二）
     * @apiSuccess {String} workInfo.imageList.labelList.topPoint 标签上边距（实际使用时，数值乘以二）
     * @apiSuccess {Object} workInfo.commentList 评论列表
     * @apiSuccess {Integer} workInfo.commentList.id 评论id
     * @apiSuccess {String} workInfo.commentList.content 评论内容
     * @apiSuccess {String} workInfo.commentList.createTime 评论时间
     * @apiSuccess {Integer} workInfo.commentList.userId 评论人id
     * @apiSuccess {String} workInfo.commentList.userName 评论人昵称
     * @apiSuccess {String} workInfo.commentList.userHead 评论人头像
     * @apiSuccess {Object} workInfo.commentList.replysList 评论回复列表
     * @apiSuccess {Integer} workInfo.commentList.replysList.id 评论回复id
     * @apiSuccess {String} workInfo.commentList.replysList.content 评论回复内容
     * @apiSuccess {String} workInfo.commentList.replysList.createTime 评论回复时间
     * @apiSuccess {Integer} workInfo.commentList.replysList.userId 评论回复人id
     * @apiSuccess {String} workInfo.commentList.replysList.userName 评论回复人名称
     * @apiSuccess {String} workInfo.commentList.replysList.userHead 评论回复人头像
     * @apiSuccess {Object} workInfo.gamsList 点赞列表
     * @apiSuccess {Integer} workInfo.gamsList.id 点赞id
     * @apiSuccess {String} workInfo.gamsList.description 描述
     * @apiSuccess {String} workInfo.gamsList.gamUserId 点赞人id
     * @apiSuccess {Integer} workInfo.gamsList.gamHead 点赞人头像
     */
    @RequestMapping(value = "/workInfo")
    public void workInfo(HttpServletResponse response,
                         Integer workId,
                         Integer userId) {
        if (null == workId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Works work = worksService.getById(workId);

        if (null == work) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        work.setShowNum(work.getShowNum() + 1);
        worksService.update(work);

        work.setCover(imageService.getById(work.getCoverId()).getPath());

        // 设计师头像、预约数、点赞数、评论数、收藏数
        work.setDesignerHead(work.getDesigner().getHead());

        work.setReserveNum(reserveService.iFindListByDesignerId(work.getDesigner().getId()).size());

        List<Gams> gamsList = gamsService.iFindList(work.getId(), Constant.GAM_WORKS, Constant.GAM_LOVE, Constant.SORT_TYPE_ASC);

        work.setGamNum(gamsList.size());

        work.setGamsList(gamsList);

        List<Comment> commentList = commentService.iFindList(work.getId(), Constant.COMMENT_WORKS);

        work.setCommentNum(commentList.size());

        work.setCommentList(commentList);

        work.setCollectNum(collectService.iFindList(work.getId(), Constant.COLLECT_WORKS).size());

        work.setImageList(imageService.iFindList(workId, Constant.IMAGE_WORKS));

        if (null != userId) {
            Gams gams = gamsService.iFindOne(userId, workId, Constant.GAM_WORKS, Constant.GAM_LOVE);
            work.setIsGam(null != gams ? Constant.GAM_LOVE_YES : Constant.GAM_LOVE_NO);

            Collect collect = collectService.iFindOne(userId, workId, Constant.COLLECT_WORKS);
            work.setIsCollect(null != collect ? Constant.GAM_LOVE_YES : Constant.GAM_LOVE_NO);
        }

        Result obj = new Result(true).data(createMap("workInfo", work));
        String result = JsonUtil.obj2ApiJson(obj, "designer", "coverId", "isCut", "objectId", "objectType", "user", "type", "comment");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/designers/info 查看设计师详情
     * @apiName designers.info
     * @apiGroup designers
     * @apiParam {Integer} designerId 设计师id     <必传 />
     * @apiParam {Integer} userId 当前登录用户id
     * @apiSuccess {Object} designerInfo 设计师详情
     * @apiSuccess {Integer} designerInfo.id 设计师id
     * @apiSuccess {String} designerInfo.nickName 设计师昵称
     * @apiSuccess {String} designerInfo.mobile 手机号
     * @apiSuccess {String} designerInfo.head 头像
     * @apiSuccess {Integer} designerInfo.type 类型，1=独立设计师，2=设计公司
     * @apiSuccess {String} designerInfo.proof 资质证明（图片路径）
     * @apiSuccess {Integer} designerInfo.star 星级
     * @apiSuccess {String} designerInfo.price 价格
     * @apiSuccess {String} designerInfo.sign 签名
     * @apiSuccess {String} designerInfo.content 描述
     * @apiSuccess {Text} designerInfo.description 软装介绍（富文本）
     * @apiSuccess {Text} designerInfo.descs 个性定制介绍（富文本）
     * @apiSuccess {Integer} designerInfo.isAuth 认证状态，0=未认证，1=已认证
     * @apiSuccess {Integer} designerInfo.status 状态
     * @apiSuccess {String} designerInfo.createTime 注册时间
     * @apiSuccess {Object} designerInfo.commentList 评论列表
     * @apiSuccess {Integer} designerInfo.commentList.id 评论id
     * @apiSuccess {String} designerInfo.commentList.content 评论内容
     * @apiSuccess {String} designerInfo.commentList.createTime 评论时间
     * @apiSuccess {Integer} designerInfo.commentList.userId 评论人id
     * @apiSuccess {String} designerInfo.commentList.userName 评论人昵称
     * @apiSuccess {String} designerInfo.commentList.userHead 评论人头像
     * @apiSuccess {Object} designerInfo.commentList.replysList 评论回复列表
     * @apiSuccess {Integer} designerInfo.commentList.replysList.id 评论回复id
     * @apiSuccess {String} designerInfo.commentList.replysList.content 评论回复内容
     * @apiSuccess {String} designerInfo.commentList.replysList.createTime 评论回复时间
     * @apiSuccess {Integer} designerInfo.commentList.replysList.userId 评论回复人id
     * @apiSuccess {String} designerInfo.commentList.replysList.userName 评论回复人名称
     * @apiSuccess {String} designerInfo.commentList.replysList.userHead 评论回复人头像
     * @apiSuccess {Integer} designerInfo.cityId 所在城市id
     * @apiSuccess {Object} designerInfo.worksList 最新的设计作品列表（倒序，三张）
     * @apiSuccess {Integer} designerInfo.worksList.id 设计作品id
     * @apiSuccess {String} designerInfo.worksList.name 设计作品名称
     * @apiSuccess {String} designerInfo.worksList.labels 标签
     * @apiSuccess {String} designerInfo.worksList.description 描述
     * @apiSuccess {String} designerInfo.worksList.createTime 创建时间
     * @apiSuccess {String} designerInfo.worksList.cover 封面图
     * @apiSuccess {Integer} designerInfo.fansNum 粉丝数
     * @apiSuccess {Integer} designerInfo.reserveNum 预约数
     * @apiSuccess {Integer} designerInfo.gamNum 点赞数
     * @apiSuccess {Integer} designerInfo.commentNum 评论数
     * @apiSuccess {Integer} designerInfo.workNum 作品数
     * @apiSuccess {Integer} designerInfo.isGam 是否点赞，0=是，1=否（当userId传入时才回返回值，否则返回""）
     * @apiSuccess {Integer} designerInfo.isAttention 是否关注，0=是，1=否（当userId传入时才回返回值，否则返回""）
     */
    @RequestMapping("info")
    public void designerInfo(HttpServletResponse response,
                             Integer designerId,
                             Integer userId) {
        if (null == designerId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        Designers designers = designersService.getById(designerId);

        if (null == designers) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        designers.setShowNum(designers.getShowNum() + 1);
        designersService.update(designers);

        designers.setCityId(designers.getCity().getId());

        // 获取粉丝数
        designers.setFansNum(attentionsService.iFindList(designerId, Constant.ATTENTION_DESIGNERS).size());

        // 获取预约数
        designers.setReserveNum(reserveService.iFindListByDesignerId(designerId).size());

        // 查询评论列表
        designers.setCommentList(commentService.iFindList(designerId, Constant.COMMENT_DESIGNERS));

        // 设置评论数
        designers.setCommentNum(designers.getCommentList().size());

        // 查询点赞列表
        designers.setGamsList(gamsService.iFindList(designerId, Constant.GAM_DESIGNERS, Constant.GAM_LOVE, Constant.SORT_TYPE_DESC));

        // 设置点赞数
        designers.setGamNum(designers.getGamsList().size());

        // 如果userId不为空时，查询是否点赞过和是否关注过
        if (null != userId) {
            Gams gams = gamsService.iFindOne(userId, designerId, Constant.GAM_DESIGNERS, Constant.GAM_LOVE);
            if (null != gams) {
                designers.setIsGam(Constant.GAM_LOVE_YES);
            } else {
                designers.setIsGam(Constant.GAM_LOVE_NO);
            }

            Attentions attentions = attentionsService.iFindOne(userId, designerId, Constant.ATTENTION_DESIGNERS);
            if (null != attentions) {
                designers.setIsAttention(Constant.ATTENTION_STATUS_YES);
            } else {
                designers.setIsAttention(Constant.ATTENTION_STATUS_NO);
            }
        }

        designers.setWorksList(worksService.iFindThreeNewWorksByDesignerId(designerId));

        Result obj = new Result(true).data(createMap("designerInfo", designers));
        String result = JsonUtil.obj2ApiJson(obj, "city", "comment", "password", "designer", "isCheck", "objectId", "objectType", "gamsList", "thuPath", "width", "height", "user", "labelList", "isCut");
        WebUtil.printApi(response, result);
    }
}
