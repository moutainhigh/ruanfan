package com.sixmac.controller.api;

import com.sixmac.common.exception.GeneralException;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Attentions;
import com.sixmac.entity.Designers;
import com.sixmac.entity.Reserve;
import com.sixmac.entity.Works;
import com.sixmac.service.*;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.DateUtils;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
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
    private UsersService usersService;

    @Autowired
    private StylesService stylesService;

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

    /**
     * 设计师列表
     *
     * @param response
     * @param type
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletResponse response,
                     Integer type,
                     String nickname,
                     Integer cityId,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == type || null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Designers> page = designersService.iPage(type, nickname, cityId, pageNum, pageSize);

        for (Designers designer : page.getContent()) {
            designer.setCityId(designer.getCity().getId());
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "city", "password", "area", "isCheck", "status", "gamsList", "commentList");
        WebUtil.printApi(response, result);
    }

    /**
     * 根据设计师id查询作品列表
     *
     * @param response
     * @param designerId
     * @param pageNum
     * @param pageSize
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
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "designer", "coverId");
        WebUtil.printApi(response, result);
    }

    /**
     * 查看设计师详情
     *
     * @param response
     * @param designerId
     */
    @RequestMapping("info")
    public void designerInfo(HttpServletResponse response,
                             Integer designerId) {
        if (null == designerId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        Designers designers = designersService.getById(designerId);

        if (null == designers) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        designers.setCityId(designers.getCity().getId());

        // 查询评论列表
        designers.setCommentList(commentService.iFindList(designerId, Constant.COMMENT_DESIGNERS));

        // 查询点赞列表
        designers.setGamsList(gamsService.iFindList(designerId, Constant.GAM_DESIGNERS, Constant.GAM_LOVE, Constant.SORT_TYPE_DESC));

        Result obj = new Result(true).data(createMap("designerInfo", designers));
        String result = JsonUtil.obj2ApiJson(obj, "city", "password", "isCheck", "status", "objectId", "objectType");
        WebUtil.printApi(response, result);
    }

    /**
     * 关注or取消关注
     *
     * @param response
     * @param userId
     * @param designerId
     * @param action
     */
    @RequestMapping("/attention")
    public void share(HttpServletResponse response, Integer userId, Integer designerId, Integer action) {
        if (null == userId || null == designerId || null == action) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Attentions attentions = attentionsService.iFindOne(userId, designerId, Constant.ATTENTION_DESIGNERS);

        // action（关注状态字段）,0表示关注，1表示取消关注
        if (action == 0) {
            try {
                if (null != attentions) {
                    WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0007));
                    return;
                }

                attentionsService.iCreate(usersService.getById(userId), designerId, Constant.ATTENTION_DESIGNERS);
            } catch (GeneralException e) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
                return;
            }
        } else {
            if (null == attentions) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0008));
                return;
            }

            attentionsService.deleteById(attentions.getId());
        }

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 预约设计师
     *
     * @param response
     * @param userId
     * @param designerId
     * @param name
     * @param mobile
     * @param email
     * @param styleId
     * @param address
     * @param resTime
     * @param remark
     */
    @RequestMapping("/reserve")
    public void reserve(HttpServletResponse response,
                        Integer userId,
                        Integer designerId,
                        String name,
                        String mobile,
                        String email,
                        Integer styleId,
                        String address,
                        String resTime,
                        String remark) {
        if (null == userId || null == designerId || null == name || null == mobile || null == email || null == styleId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Reserve reserve = new Reserve();
        reserve.setName(name);
        reserve.setMobile(mobile);
        reserve.setEmail(email);
        reserve.setUser(usersService.getById(userId));
        reserve.setDesigner(designersService.getById(designerId));
        if (null != resTime) {
            try {
                Date date = DateUtils.stringToDateWithFormat(resTime, "yyyy-MM-dd HH:mm:ss");
                reserve.setReseTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        reserve.setStyle(stylesService.getById(styleId));
        reserve.setAddress(address);
        reserve.setRemark(remark);
        reserve.setReseAddress(address);
        reserve.setCreateTime(new Date());
        reserve.setStatus(0);

        reserveService.create(reserve);

        WebUtil.printApi(response, new Result(true));
    }
}
