package com.sixmac.controller.api;

import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.Designers;
import com.sixmac.entity.Reserve;
import com.sixmac.entity.Styles;
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

    /**
     * 设计师列表
     *
     * @param request
     * @param response
     * @param type
     * @param styleId
     * @param areaId
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer type,
                     Integer styleId,
                     Integer areaId,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Designers> page = designersService.iPage(type, styleId, areaId, pageNum, pageSize);

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
