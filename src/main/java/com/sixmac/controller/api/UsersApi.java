package com.sixmac.controller.api;

import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
import com.sixmac.service.CityService;
import com.sixmac.service.FeedbackService;
import com.sixmac.service.OrdersinfoService;
import com.sixmac.service.UsersService;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.ImageUtil;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Controller
@RequestMapping(value = "api/users")
public class UsersApi {

    @Autowired
    private UsersService usersService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private OrdersinfoService ordersinfoService;

    @Autowired
    private CityService cityService;

    /**
     * 登录
     *
     * @param response
     * @param mobile
     * @param password
     */
    @RequestMapping(value = "/login")
    public void login(HttpServletResponse response, String mobile, String password) {
        if (null == mobile || null == password) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Users users = usersService.iLogin(mobile, password);

        if (null == users) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, new Result(true).data(users));
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param response
     * @param userId
     */
    @RequestMapping(value = "/info")
    public void info(HttpServletResponse response, Integer userId) {
        if (null == userId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Users users = usersService.getById(userId);

        if (null == users) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, new Result(true).data(users));
    }

    /**
     * 根据用户id修改用户信息
     * @param request
     * @param response
     * @param userId
     * @param password
     * @param multipartRequest
     * @param nickname
     * @param cityId
     * @param comName
     * @param comArea
     */
    @RequestMapping(value = "/updateInfo")
    public void updateInfo(ServletRequest request,
                           HttpServletResponse response,
                           Integer userId,
                           String password,
                           MultipartRequest multipartRequest,
                           String nickname,
                           Integer cityId,
                           String comName,
                           String comArea) {
        if (null == userId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Users users = usersService.getById(userId);

        if (null == users) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
        }

        if (null != password && !password.equals("")) {
            users.setPassword(password);
        }

        try {
            MultipartFile multipartFile = multipartRequest.getFile("head");
            if (null != multipartFile) {
                Map<String, Object> map = ImageUtil.saveImage(request, multipartFile, false);
                users.setHeadPath(map.get("imgURL").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != nickname && !nickname.equals("")) {
            users.setNickName(nickname);
        }

        if (null != cityId) {
            users.setCity(cityService.getById(cityId));
        }

        if (null != comName && !comName.equals("")) {
            users.setComName(comName);
        }

        if (null != comArea && !comArea.equals("")) {
            users.setComArea(comArea);
        }

        usersService.update(users);

        WebUtil.printApi(response, new Result(true).data(users));
    }

    /**
     * 评价订单
     *
     * @param response
     * @param userId
     * @param isHide
     * @param commentList
     */
    @RequestMapping(value = "/commentOrders")
    public void commentOrders(HttpServletResponse response, Integer userId, Integer isHide, String commentList) {
        if (null == userId || null == isHide || null == commentList) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        try {
            JSONArray orderinfos = JSONArray.fromObject(commentList);
            Map<String, Object> mapInfo = null;
            Ordersinfo ordersinfo = null;
            for (Object orderMap : orderinfos) {
                // 获取单个订单详情
                mapInfo = JsonUtil.jsontoMap(orderMap);
                ordersinfo = ordersinfoService.getById(Integer.parseInt(mapInfo.get("orderinfoId").toString()));
                ordersinfo.setStar(Integer.parseInt(mapInfo.get("star").toString()));
                ordersinfo.setComment(mapInfo.get("content").toString());

                ordersinfoService.update(ordersinfo);
            }

            WebUtil.printApi(response, new Result(true));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
        }
    }

    /**
     * 我的优惠券列表
     *
     * @param response
     * @param userId
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/couponList")
    public void couponList(HttpServletResponse response,
                           Integer userId,
                           Integer pageNum,
                           Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        List<Coupon> list = new ArrayList<Coupon>();
        Page<Usercoupon> page = usersService.iPageByUserId(userId, pageNum, pageSize);

        // 读取当前用户的优惠券列表
        Coupon coupon = null;
        for (Usercoupon userCoupon : page.getContent()) {
            coupon = userCoupon.getCoupon();
            coupon.setStatus(userCoupon.getStatus());
            list.add(coupon);
        }

        Map<java.lang.String, Object> dataMap = APIFactory.fittingPlus(page, list);
        WebUtil.printApi(response, new Result(true).data(dataMap));
    }

    /**
     * 查询用户积分
     *
     * @param response
     * @param userId
     */
    @RequestMapping(value = "/score")
    public void score(HttpServletResponse response, Integer userId) {
        if (null == userId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Users users = usersService.getById(userId);

        if (null == users) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("score", users.getScore());

        WebUtil.printApi(response, new Result(true).data(map));
    }

    /**
     * 添加反馈
     *
     * @param request
     * @param response
     * @param userId
     * @param type
     * @param content
     * @param multipartRequest
     */
    @RequestMapping(value = "/addFeedBack")
    public void addFeedBack(ServletRequest request, HttpServletResponse response, Integer userId, String type, String content, MultipartRequest multipartRequest) {
        if (null == userId || null == type || null == content) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        MultipartFile multipartFile = multipartRequest.getFile("pic");

        Feedback feedback = new Feedback();
        feedback.setUser(usersService.getById(userId));
        feedback.setType(type);
        feedback.setDescription(content);
        if (null != multipartFile) {
            try {
                Map<String, Object> map = ImageUtil.saveImage(request, multipartFile, false);
                feedback.setPath(map.get("imgURL").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        feedback.setCreateTime(new Date());

        feedbackService.create(feedback);

        WebUtil.printApi(response, new Result(true));
    }
}
