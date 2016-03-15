package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
import com.sixmac.entity.vo.CodeVo;
import com.sixmac.service.CityService;
import com.sixmac.service.FeedbackService;
import com.sixmac.service.OrdersinfoService;
import com.sixmac.service.UsersService;
import com.sixmac.utils.*;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Controller
@RequestMapping(value = "api/users")
public class UsersApi extends CommonController {

    public List<CodeVo> voList = new ArrayList<CodeVo>();

    @Autowired
    private UsersService usersService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private OrdersinfoService ordersinfoService;

    @Autowired
    private CityService cityService;

    /**
     * 发送验证码
     *
     * @param response
     * @param mobile
     */
    @RequestMapping(value = "/sendCode")
    public void sendCode(HttpServletResponse response,
                         String mobile,
                         String type) {
        if (null == mobile) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 生成验证码
        String code = RandomUtil.generateShortUuid();

        // 发送验证码（云片网）
        try {
            String text = "【软范】您的验证码是" + code;
            JavaSmsApi.sendSms(Constant.YUNPIAN_APPKEY, text, mobile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 将生成的验证码保存到voList中
        CodeVo codeVo = new CodeVo();
        codeVo.setMobile(mobile);
        codeVo.setCode(code);
        codeVo.setType(type);
        codeVo.setCreateTime(new Date());

        voList.add(codeVo);

        // 返回验证码
        WebUtil.printApi(response, new Result(true).data(createMap("code", code)));
    }

    /**
     * 注册
     *
     * @param response
     * @param mobile
     * @param password
     */
    @RequestMapping(value = "/register")
    public void register(ServletRequest request,
                         HttpServletResponse response,
                         String mobile,
                         String password,
                         String nickname,
                         MultipartRequest multipartRequest,
                         String code,
                         String codeType) {
        if (null == mobile || null == password || null == nickname /*|| null == code || null == codeType*/) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 检测验证码
        // checkCode(response, mobile, code, codeType);

        // 检测手机号是否唯一，如果不唯一，返回错误码
        if (null != usersService.iFindOneByMobile(mobile)) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0006));
            return;
        }

        // 获取头像
        MultipartFile multipartFile = multipartRequest.getFile("head");
        if (null == multipartFile) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Users users = new Users();
        users.setMobile(mobile);
        users.setPassword(password);
        users.setNickName(nickname);
        users.setCity(cityService.getById(1));
        users.setScore(0);
        users.setType(1);
        users.setStatus(0);
        users.setCreateTime(new Date());

        try {
            Map<String, Object> map = ImageUtil.saveImage(request, multipartFile, false);
            users.setHeadPath(map.get("imgURL").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 注册
        usersService.create(users);

        users.setCityId(1);

        users.setHeadPath(PathUtils.getRemotePath() + users.getHeadPath());

        Result obj = new Result(true).data(createMap("userInfo", users));
        String result = JsonUtil.obj2ApiJson(obj, "city", "password", "type");
        WebUtil.printApi(response, result);
    }

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
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0015));
            return;
        }

        // 如果用户被禁用，将不允许登录
        if (users.getStatus() == Constant.BANNED_STATUS_NO) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0013));
            return;
        }

        users.setCityId(users.getCity().getId());

        Result obj = new Result(true).data(createMap("userInfo", users));
        String result = JsonUtil.obj2ApiJson(obj, "city", "password", "type");
        WebUtil.printApi(response, result);
    }

    /**
     * 第三方登录
     *
     * @param response
     * @param type
     * @param openId
     * @param head
     * @param nickname
     */
    @RequestMapping(value = "/tLogin")
    public void tLogin(HttpServletResponse response,
                       Integer type,
                       String openId,
                       String head,
                       String nickname) {
        if (null == type || null == openId || null == head || null == nickname) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Users users = usersService.iTLogin(type, openId, head, nickname);

        if (null == users) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0015));
            return;
        }

        users.setCityId(users.getCity().getId());

        Result obj = new Result(true).data(createMap("userInfo", users));
        String result = JsonUtil.obj2ApiJson(obj, "city", "password", "type");
        WebUtil.printApi(response, result);
    }

    /**
     * 忘记密码
     *
     * @param response
     * @param mobile
     * @param password
     * @param code
     * @param codeType
     */
    @RequestMapping(value = "/forgetPwd")
    public void forgetPwd(HttpServletResponse response, String mobile, String password, String code, String codeType) {
        if (null == mobile || null == password/* || null == code || null == codeType*/) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 检测验证码
        // checkCode(response, mobile, code, codeType);

        // 根据手机号获取用户信息，并返回该用户的信息
        Users users = usersService.iFindOneByMobile(mobile);

        if (null == users) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0015));
            return;
        }

        users.setPassword(password);

        usersService.update(users);

        WebUtil.printApi(response, new Result(true));
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
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0015));
            return;
        }

        users.setCityId(users.getCity().getId());
        users.setHeadPath(PathUtils.getRemotePath() + users.getHeadPath());

        Result obj = new Result(true).data(createMap("userInfo", users));
        String result = JsonUtil.obj2ApiJson(obj, "city", "password", "type");
        WebUtil.printApi(response, result);
    }

    /**
     * 根据用户id修改用户信息
     *
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
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0015));
            return;
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

        WebUtil.printApi(response, new Result(true));
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
            coupon.setCover(PathUtils.getRemotePath() + coupon.getCover());
            list.add(coupon);
        }

        Map<String, Object> dataMap = APIFactory.fittingPlus(page, list);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
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
            return;
        }

        WebUtil.printApi(response, new Result(true).data(createMap("score", users.getScore())));
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

    /**
     * 检测验证码是否正确
     *
     * @param response
     * @param mobile
     * @param code
     * @param type
     */
    public void checkCode(HttpServletResponse response, String mobile, String code, String type) {
        Boolean flag = false;

        // 检查验证码是否正确，如果不正确，返回错误码
        CodeVo tempCodeVo = null;
        for (CodeVo codeVo : voList) {
            if (codeVo.getCode().equals(code) && codeVo.getMobile().equals(mobile)) {
                flag = true;
                tempCodeVo = codeVo;
            }
        }

        if (flag == false) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0004));
            return;
        } else if (!tempCodeVo.getType().equals(type)) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0005));
            return;
        } else if (DateUtils.secondCompare(tempCodeVo.getCreateTime(), 600)) {
            // 如果存在，则检测是否超时，如果超时，返回错误信息
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0014));
            return;
        }

        if (flag) {
            // 如果匹配到了，则移除缓存中的验证码实体信息
            Iterator iter = voList.iterator();
            CodeVo vo = null;
            while (iter.hasNext()) {
                vo = (CodeVo) iter.next();
                if (vo.getMobile().equals(mobile)) {
                    iter.remove();
                }
            }
        }
    }
}
