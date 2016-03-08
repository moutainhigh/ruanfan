package com.sixmac.controller.api;

import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
import com.sixmac.service.FeedbackService;
import com.sixmac.service.ImageService;
import com.sixmac.service.JournalService;
import com.sixmac.service.UsersService;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.ImageUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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
    private JournalService journalService;

    @Autowired
    private ImageService imageService;

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
     * 发布日志
     *
     * @param response
     * @param userId
     * @param content
     * @param multipartRequest
     */
    @RequestMapping(value = "/addJournal")
    public void addJournal(ServletRequest request, HttpServletResponse response, Integer userId, String content, MultipartRequest multipartRequest) {
        if (null == userId || null == content) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 获取图片集合
        List<MultipartFile> list = multipartRequest.getFiles("imgList");

        Journal journal = new Journal();
        journal.setUser(usersService.getById(userId));
        journal.setContent(content);
        journal.setCreateTime(new Date());

        // 保存日志信息
        journalService.create(journal);

        try {
            // 保存日志图片集合
            Map<String, Object> map = null;
            Image image = null;
            for (MultipartFile file : list) {
                if (null != file) {
                    map = ImageUtil.saveImage(request, file, false);
                    image = new Image();
                    image.setPath(map.get("imgURL").toString());
                    image.setWidth(map.get("imgWidth").toString());
                    image.setHeight(map.get("imgHeight").toString());
                    image.setObjectId(journal.getId());
                    image.setObjectType(Constant.IMAGE_JOURNAL);
                    image.setCreateTime(new Date());

                    imageService.create(image);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebUtil.printApi(response, new Result(true));
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
