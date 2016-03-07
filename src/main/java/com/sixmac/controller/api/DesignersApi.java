package com.sixmac.controller.api;

import com.sixmac.common.exception.GeneralException;
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
public class DesignersApi {

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
                     Integer pageNum,
                     Integer pageSize) {
        if (null == type || null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Designers> page = designersService.iPage(type, pageNum, pageSize);

        Map<String, Object> dataMap = APIFactory.fitting(page);
        WebUtil.printApi(response, new Result(true).data(dataMap));
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

        Map<String, Object> dataMap = APIFactory.fitting(page);
        WebUtil.printApi(response, new Result(true).data(dataMap));
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
        }

        // 查询评论列表
        designers.setCommentList(commentService.iFindList(designerId, Constant.COMMENT_DESIGNERS));

        // 查询点赞列表
        designers.setGamsList(gamsService.iFindList(designerId, Constant.GAM_DESIGNERS, Constant.GAM_LOVE, Constant.SORT_TYPE_DESC));

        WebUtil.printApi(response, new Result(true).data(designers));
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
        // action（关注状态字段）,0表示关注，1表示取消关注
        String msg = "";
        if (null == userId || null == designerId || null == action) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        if (action == 0) {
            try {
                attentionsService.iCreate(usersService.getById(userId), designerId, Constant.ATTENTION_DESIGNERS);
            } catch (GeneralException e) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
            }
            msg = "关注成功";
        } else {
            Attentions attentions = attentionsService.iFindOne(userId, designerId, Constant.ATTENTION_DESIGNERS);
            if (null != attentions) {
                attentionsService.deleteById(attentions.getId());
            }
            msg = "取消关注成功";
        }

        WebUtil.printApi(response, new Result(true).msg(msg));
    }

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
        String msg = "";
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

        reserveService.create(reserve);

        WebUtil.printApi(response, new Result(true).msg(msg));
    }
}
