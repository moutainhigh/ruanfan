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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/common")
public class CommonApi extends CommonController {

    @Autowired
    private CollectService collectService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private AfflatusService afflatusService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private GamsService gamsService;

    /**
     * @api {post} /api/common/collectList 收藏列表
     * @apiName common.collectList
     * @apiGroup common
     * @apiParam {Integer} userId 用户id      <必传 />
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数      <必传 />
     * @apiSuccess {Object} list 收藏列表
     * @apiSuccess {Integer} list.id 收藏id
     * @apiSuccess {Integer} list.objectId 收藏目标id
     * @apiSuccess {Integer} list.objectType 收藏目标类型，1=灵感集，2=设计作品
     */
    @RequestMapping(value = "/collectList")
    public void collectList(HttpServletResponse response,
                            Integer userId,
                            Integer pageNum,
                            Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Collect> page = collectService.iPage(userId, pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/common/collect 收藏 or 取消收藏
     * @apiName common.collect
     * @apiGroup common
     * @apiParam {Integer} userId 用户id      <必传 />
     * @apiParam {Integer} objectId 目标id        <必传 />
     * @apiParam {Integer} objectType 目标类型，1=灵感集，2=设计作品     <必传 />
     * @apiParam {Integer} action 类型，0=收藏，1=取消收藏        <必传 />
     */
    @RequestMapping("/collect")
    public void collect(HttpServletResponse response, Integer userId, Integer objectId, Integer objectType, Integer action) {
        if (null == userId || null == objectId || null == objectType || null == action) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Collect collect = collectService.iFindOne(userId, objectId, objectType);

        // action（收藏状态字段）,0表示收藏，1表示取消收藏
        if (action == 0) {
            try {
                if (null != collect) {
                    WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0009));
                    return;
                }

                collectService.iCreate(usersService.getById(userId), objectId, objectType);
            } catch (GeneralException e) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
                return;
            }
        } else {
            if (null == collect) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0010));
                return;
            }

            collectService.deleteById(collect.getId());
        }

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * @api {post} /api/common/share 分享
     * @apiName common.share
     * @apiGroup common
     * @apiParam {Integer} objectId 分享目标id      <必传 />
     * @apiParam {Integer} objectType 分享类型，1=灵感集，2=日志       <必传 />
     */
    @RequestMapping("/share")
    public void share(HttpServletResponse response, Integer objectId, Integer objectType) {
        if (null == objectId || null == objectType) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 根据传入的类型判断分享的类型
        // 分享类型，1=灵感集，2=日志
        switch (objectType) {
            case 1:
                // 增加灵感集的分享数
                Afflatus afflatus = afflatusService.getById(objectId);

                if (null == afflatus) {
                    WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
                    return;
                }

                afflatus.setShareNum(afflatus.getShareNum() + 1);

                afflatusService.update(afflatus);
                break;
            case 2:
                // 增加日志的分享数
                Journal journal = journalService.getById(objectId);

                if (null == journal) {
                    WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
                    return;
                }

                journal.setShareNum(journal.getShareNum() + 1);

                journalService.update(journal);
                break;
        }

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * @api {post} /api/common/gam 点赞
     * @apiName common.gam
     * @apiGroup common
     * @apiParam {Integer} userId 点赞人id      <必传 />
     * @apiParam {Integer} objectId 点赞目标id      <必传 />
     * @apiParam {Integer} objectType 点赞类型，1=日志，2=灵感集，3=设计师，4=设计作品       <必传 />
     * @apiParam {Integer} type 操作：1=点赞，2=取消点赞       <必传 />
     */
    @RequestMapping("/gam")
    public void gam(HttpServletResponse response, Integer userId, Integer objectId, Integer objectType, Integer type) {
        if (null == userId || null == objectId || null == objectType || null == type) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Gams gams = gamsService.iFindOne(userId, objectId, objectType, Constant.GAM_LOVE);

        if (type == 1) {
            // type为1时，是点赞
            if (null != gams) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0020));
                return;
            }
            gams = new Gams();
            gams.setUser(usersService.getById(userId));
            gams.setObjectId(objectId);
            gams.setObjectType(objectType);
            gams.setType(Constant.GAM_LOVE);

            gamsService.create(gams);
        } else {
            // type为2时，是取消点赞
            if (null == gams) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0021));
                return;
            }

            gamsService.deleteById(gams.getId());
        }

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * @api {post} /api/common/comment 发布评论
     * @apiName common.comment
     * @apiGroup common
     * @apiParam {Integer} userId 用户id      <必传 />
     * @apiParam {Integer} objectId 评论对象id        <必传 />
     * @apiParam {Integer} objectType 评论对象类型，1=设计师，2=设计作品，3=灵感集     <必传 />
     * @apiParam {String} content 评论内容        <必传 />
     */
    @RequestMapping("/comment")
    public void comment(HttpServletResponse response, Integer userId, Integer objectId, Integer objectType, String content) {
        if (null == userId || null == objectId || null == objectType || null == content) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        try {
            Comment comment = new Comment();
            comment.setUser(usersService.getById(userId));
            comment.setObjectId(objectId);
            comment.setObjectType(objectType);
            comment.setContent(content);
            comment.setCreateTime(new Date());

            commentService.create(comment);

            WebUtil.printApi(response, new Result(true));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
        }
    }

    /**
     * @api {post} /api/common/provinceList 省份城市列表
     * @apiName common.provinceList
     * @apiGroup common
     * @apiSuccess {Object} provinceList 收藏列表
     * @apiSuccess {Integer} provinceList.id 省份id
     * @apiSuccess {String} provinceList.name 省份名称
     * @apiSuccess {Object} provinceList.cityList 城市list
     * @apiSuccess {Integer} provinceList.cityList.id 城市id
     * @apiSuccess {String} provinceList.cityList.name 城市名称
     */
    @RequestMapping(value = "/provinceList")
    public void provinceList(HttpServletResponse response) {
        List<Province> list = provinceService.findAll();

        for (Province province : list) {
            province.setCityList(cityService.findListByProvinceId(province.getId()));
        }

        Result obj = new Result(true).data(createMap("provinceList", list));
        String result = JsonUtil.obj2ApiJson(obj, "province");
        WebUtil.printApi(response, result);
    }
}
