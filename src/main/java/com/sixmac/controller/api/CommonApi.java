package com.sixmac.controller.api;

import com.sixmac.common.exception.GeneralException;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
import com.sixmac.service.*;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.DateUtils;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
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
    private WorksService worksService;

    @Autowired
    private VirtualsService virtualsService;

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

    @Autowired
    private StylesService stylesService;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private ImageService imageService;

    /**
     * @api {post} /api/common/collectList 收藏列表
     * @apiName common.collectList
     * @apiGroup common
     * @apiParam {Integer} userId 用户id      <必传 />
     * @apiParam {Integer} type 类型，1=灵感集，2=设计作品，3=VR虚拟      <必传 />
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数      <必传 />
     * @apiSuccess {Object} list 收藏列表  返回类型有三种，灵感集，设计作品，VR虚拟
     * @apiSuccess {Object} following
     * @apiSuccess {Object} list 灵感集列表
     * @apiSuccess {Integer} list.id 灵感集id
     * @apiSuccess {String} list.name 灵感集名称
     * @apiSuccess {Integer} list.type 类型：1单图   2套图
     * @apiSuccess {Integer} list.showNum 浏览量
     * @apiSuccess {Integer} list.shareNum 分享数
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
     * @apiSuccess {Object} or
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
     * @apiSuccess {Object} or
     * @apiSuccess {Object} list VR虚拟列表
     * @apiSuccess {Integer} list.id VR虚拟id
     * @apiSuccess {String} list.name VR虚拟名称
     * @apiSuccess {String} list.labels 标签
     * @apiSuccess {String} list.cover 封面图
     * @apiSuccess {String} list.url 链接地址
     * @apiSuccess {String} list.createTime 创建时间
     * @apiSuccess {String} list.isGam 是否点赞  0=是，1=否
     * @apiSuccess {String} list.isCollect 是否收藏  0=是，1=否
     * @apiSuccess {Object} list.gamsList 点赞列表
     * @apiSuccess {Integer} list.gamsList.id 点赞id
     * @apiSuccess {String} list.gamsList.description 描述
     * @apiSuccess {Integer} list.gamsList.gamUserId 点赞人id
     * @apiSuccess {String} list.gamsList.gamHead 点赞人头像
     */
    @RequestMapping(value = "/collectList")
    public void collectList(HttpServletResponse response,
                            Integer userId,
                            Integer type,
                            Integer pageNum,
                            Integer pageSize) {
        if (null == type || null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        List<Afflatus> afflatusList = new ArrayList<Afflatus>();
        List<Works> worksList = new ArrayList<Works>();
        List<Virtuals> virtualsList = new ArrayList<Virtuals>();

        Page<Collect> page = collectService.iPage(type, userId, pageNum, pageSize);

        for (Collect collect : page.getContent()) {
            switch (type) {
                case 1:
                    // 灵感集
                    Afflatus afflatus = afflatusService.getById(collect.getObjectId());
                    if (null != afflatus) {
                        afflatusList.add(afflatus);
                    }
                    break;
                case 2:
                    // 设计作品
                    Works works = worksService.getById(collect.getObjectId());
                    if (null != works) {
                        worksList.add(works);
                    }
                    break;
                case 3:
                    // VR虚拟
                    Virtuals virtuals = virtualsService.getById(collect.getObjectId());
                    if (null != virtuals) {
                        virtualsList.add(virtuals);
                    }
                    break;
            }
        }
        Map<String, Object> dataMap = null;

        switch (type) {
            case 1:
                for (Afflatus afflatus : afflatusList) {
                    if (null == afflatus) continue;

                    afflatus.setCover(imageService.getById(afflatus.getCoverId()).getPath());
                    afflatus.setDesignerId(afflatus.getDesigner().getId());
                    afflatus.setDesignerHead(afflatus.getDesigner().getHead());
                    afflatus.setDesignerName(afflatus.getDesigner().getNickName());

                    // 分享、收藏、点赞、预约数
                    afflatus.setCollectNum(collectService.iFindList(afflatus.getId(), Constant.COLLECT_AFFLATUS).size());

                    afflatus.setGamNum(gamsService.iFindList(afflatus.getId(), Constant.GAM_AFFLATUS, Constant.GAM_LOVE, Constant.SORT_TYPE_ASC).size());

                    afflatus.setReserveNum(reserveService.iFindListByDesignerId(afflatus.getDesigner().getId()).size());
                }

                dataMap = APIFactory.fittingPlus(page, afflatusList);
                break;
            case 2:
                for (Works work : worksList) {
                    if (null == work) continue;

                    work.setCover(imageService.getById(work.getCoverId()).getPath());

                    // 设计师头像、预约数、点赞数、评论数、收藏数
                    work.setDesignerHead(work.getDesigner().getHead());

                    work.setReserveNum(reserveService.iFindListByDesignerId(work.getDesigner().getId()).size());

                    work.setGamNum(gamsService.iFindList(work.getId(), Constant.GAM_WORKS, Constant.GAM_LOVE, Constant.SORT_TYPE_ASC).size());

                    work.setCommentNum(commentService.iFindList(work.getId(), Constant.COMMENT_WORKS).size());

                    work.setCollectNum(collectService.iFindList(work.getId(), Constant.COLLECT_WORKS).size());
                }

                dataMap = APIFactory.fittingPlus(page, worksList);
                break;
            case 3:
                for (Virtuals virtuals : virtualsList) {
                    if (null == virtuals) continue;

                    virtuals.setGamsList(gamsService.iFindList(virtuals.getId(), Constant.GAM_VIRTUALS, Constant.GAM_LOVE, Constant.SORT_TYPE_DESC));
                }

                dataMap = APIFactory.fittingPlus(page, virtualsList);
                break;
        }

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user", "objectId", "objectType", "designer", "coverId", "isCut", "style", "type", "area");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/common/collect 收藏 or 取消收藏
     * @apiName common.collect
     * @apiGroup common
     * @apiParam {Integer} userId 用户id      <必传 />
     * @apiParam {Integer} objectId 目标id        <必传 />
     * @apiParam {Integer} objectType 目标类型，1=灵感集，2=设计作品，3=VR虚拟     <必传 />
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
     * @apiParam {Integer} objectType 评论对象类型，1=设计师，2=设计作品，3=灵感集，4=日志     <必传 />
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
     * @api {post} /api/common/reserve 预约
     * @apiName common.reserve
     * @apiGroup common
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {Integer} type 预约类型，1=设计师，2=设计定制套餐       <必传 />
     * @apiParam {Integer} objectId 目标id       <必传 />
     * @apiParam {String} name 姓名       <必传 />
     * @apiParam {String} mobile 联系方式       <必传 />
     * @apiParam {String} email 电子邮箱        <必传 />
     * @apiParam {Integer} styleId 喜爱的风格id      <必传 />
     * @apiParam {String} address 地址
     * @apiParam {String} resTime 预约时间
     * @apiParam {String} remark 备注（留言）
     */
    @RequestMapping("/reserve")
    public void reserve(HttpServletResponse response,
                        Integer userId,
                        Integer type,
                        Integer objectId,
                        String name,
                        String mobile,
                        String email,
                        Integer styleId,
                        String address,
                        String resTime,
                        String remark) {
        if (null == userId || null == type || null == objectId || null == name || null == mobile || null == email || null == styleId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 已经预约过的，不允许再次预约
        Reserve tempReserve = reserveService.iFindOneByParams(userId, objectId, type);
        if (null != tempReserve) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0025));
            return;
        }

        Reserve reserve = new Reserve();
        reserve.setType(type);
        reserve.setName(name);
        reserve.setMobile(mobile);
        reserve.setEmail(email);
        reserve.setUser(usersService.getById(userId));
        reserve.setObjectId(objectId);
        if (null != resTime) {
            try {
                Date date = DateUtils.stringToDateWithFormat(resTime, "yyyy-MM-dd HH:mm:ss");
                reserve.setReseTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        reserve.setStyles(stylesService.getById(styleId));
        reserve.setAddress(address);
        reserve.setRemark(remark);
        reserve.setReseAddress(address);
        reserve.setCreateTime(new Date());
        reserve.setStatus(0);

        reserveService.create(reserve);

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * @api {post} /api/common/provinceList 省份城市列表
     * @apiName common.provinceList
     * @apiGroup common
     * @apiSuccess {Object} provinceList 省份列表
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

    /**
     * @api {post} /api/common/cityList 城市列表
     * @apiName common.cityList
     * @apiGroup common
     * @apiSuccess {Object} cityList 城市list
     * @apiSuccess {Integer} cityList.id 城市id
     * @apiSuccess {String} cityList.name 城市名称
     */
    @RequestMapping(value = "/cityList")
    public void cityList(HttpServletResponse response) {
        List<City> list = cityService.findAll();

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj, "province");
        WebUtil.printApi(response, result);
    }
}
