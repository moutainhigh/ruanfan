package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
import com.sixmac.entity.vo.CodeVo;
import com.sixmac.service.*;
import com.sixmac.utils.*;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private OrdersService ordersService;

    @Autowired
    private OrdersinfoService ordersinfoService;

    @Autowired
    private CityService cityService;

    @Autowired
    private MessageService messageService;

    /**
     * @api {post} /api/users/sendCode 发送验证码
     * @apiName users.sendCode
     * @apiGroup users
     * @apiParam {String} mobile 手机号码       <必传 />
     * @apiParam {String} type 验证码类型，注册=register，忘记密码=forgetPwd       <必传 />
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
        String code = RandomUtil.getCode();

        // 发送验证码（云片网）
        try {
            String text = "【软范网】您的验证码是" + code;
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
        WebUtil.printApi(response, new Result(true));
    }

    /**
     * @api {post} /api/users/register 注册
     * @apiName users.register
     * @apiGroup users
     * @apiParam {String} mobile 手机号码       <必传 />
     * @apiParam {String} password 密码，MD5密文       <必传 />
     * @apiParam {String} nickname 昵称       <必传 />
     * @apiParam {Stream} head 头像（二级制流文件）       <必传 />
     * @apiParam {String} code 验证码       <必传 />
     * @apiParam {String} codeType 验证码类型，注册=register，忘记密码=forgetPwd       <必传 />
     * @apiSuccess {Object} userInfo 用户信息
     * @apiSuccess {Integer} userInfo.id 用户id
     * @apiSuccess {String} userInfo.mobile 手机号
     * @apiSuccess {String} userInfo.nickName 昵称
     * @apiSuccess {String} userInfo.headPath 头像
     * @apiSuccess {Integer} userInfo.score 积分
     * @apiSuccess {String} userInfo.comName 小区名称
     * @apiSuccess {String} userInfo.comArea 小区面积
     * @apiSuccess {String} userInfo.createTime 注册时间
     * @apiSuccess {Integer} userInfo.cityId 所在城市id
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
        if (null == mobile || null == password || null == nickname || null == code || null == codeType) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 检测验证码
        if (checkCode(response, mobile, code, codeType)) {
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
            users.setHeadPath(Constant.DEFAULT_HEAD_PATH);

            try {
                users.setHeadPath(QiNiuUploadImgUtil.upload(multipartFile));
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
    }

    /**
     * @api {post} /api/users/login 登录
     * @apiName users.login
     * @apiGroup users
     * @apiParam {String} mobile 手机号码       <必传 />
     * @apiParam {String} password 密码，MD5密文       <必传 />
     * @apiSuccess {Object} userInfo 用户信息
     * @apiSuccess {Integer} userInfo.id 用户id
     * @apiSuccess {String} userInfo.mobile 手机号
     * @apiSuccess {String} userInfo.nickName 昵称
     * @apiSuccess {String} userInfo.headPath 头像
     * @apiSuccess {Integer} userInfo.score 积分
     * @apiSuccess {String} userInfo.comName 小区名称
     * @apiSuccess {String} userInfo.comArea 小区面积
     * @apiSuccess {String} userInfo.createTime 注册时间
     * @apiSuccess {Integer} userInfo.cityId 所在城市id
     */
    @RequestMapping(value = "/login")
    public void login(HttpServletResponse response, String mobile, String password) {
        if (null == mobile || null == password) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Users user = usersService.iLogin(mobile);

        if (null == user) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0018));
            return;
        }

        Users users = usersService.iLogin(mobile, password);

        if (null == users) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0019));
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
     * @api {post} /api/users/tLogin 第三方登录
     * @apiName users.tLogin
     * @apiGroup users
     * @apiParam {Integer} type 第三方类型，1=微信，2=QQ，3=新浪微博       <必传 />
     * @apiParam {String} openId 唯一标识       <必传 />
     * @apiParam {String} head 头像路径       <必传 />
     * @apiParam {String} nickname 昵称       <必传 />
     * @apiSuccess {Object} userInfo 用户信息
     * @apiSuccess {Integer} userInfo.id 用户id
     * @apiSuccess {String} userInfo.mobile 手机号
     * @apiSuccess {String} userInfo.nickName 昵称
     * @apiSuccess {String} userInfo.headPath 头像
     * @apiSuccess {Integer} userInfo.score 积分
     * @apiSuccess {String} userInfo.comName 小区名称
     * @apiSuccess {String} userInfo.comArea 小区面积
     * @apiSuccess {String} userInfo.createTime 注册时间
     * @apiSuccess {Integer} userInfo.cityId 所在城市id
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
     * @api {post} /api/users/forgetPwd 忘记密码
     * @apiName users.forgetPwd
     * @apiGroup users
     * @apiParam {String} mobile 手机号       <必传 />
     * @apiParam {String} password 新密码，MD5密文       <必传 />
     * @apiParam {String} code 验证码       <必传 />
     * @apiParam {String} codeType 验证码类型，注册=register，忘记密码=forgetPwd       <必传 />
     */
    @RequestMapping(value = "/forgetPwd")
    public void forgetPwd(HttpServletResponse response, String mobile, String password, String code, String codeType) {
        if (null == mobile || null == password || null == code || null == codeType) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        // 检测验证码
        if (checkCode(response, mobile, code, codeType)) {
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
    }

    /**
     * @api {post} /api/users/info 查询用户信息
     * @apiName users.info
     * @apiGroup users
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiSuccess {Object} userInfo 用户信息
     * @apiSuccess {Integer} userInfo.id 用户id
     * @apiSuccess {String} userInfo.mobile 手机号
     * @apiSuccess {String} userInfo.nickName 昵称
     * @apiSuccess {String} userInfo.headPath 头像
     * @apiSuccess {Integer} userInfo.score 积分
     * @apiSuccess {String} userInfo.comName 小区名称
     * @apiSuccess {String} userInfo.comArea 小区面积
     * @apiSuccess {String} userInfo.createTime 注册时间
     * @apiSuccess {Integer} userInfo.cityId 所在城市id
     * @apiSuccess {Integer} userInfo.fansNum 粉丝数
     * @apiSuccess {Integer} userInfo.attentionNUm 关注数
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
     * @api {post} /api/users/updateHead 修改头像
     * @apiName users.updateHead
     * @apiGroup users
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {Stream} head 头像文件，二进制流       <必传 />
     */
    @RequestMapping(value = "/updateHead")
    public void updateHead(ServletRequest request,
                           HttpServletResponse response,
                           Integer userId,
                           MultipartRequest multipartRequest) {
        if (null == userId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Users users = usersService.getById(userId);

        if (null == users) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0015));
            return;
        }

        try {
            MultipartFile multipartFile = multipartRequest.getFile("head");
            if (null != multipartFile) {
                users.setHeadPath(QiNiuUploadImgUtil.upload(multipartFile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        usersService.update(users);

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * @api {post} /api/users/updateInfo 修改用户信息
     * @apiName users.updateInfo
     * @apiGroup users
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {String} password 密码，MD5密文
     * @apiParam {String} nickname 昵称
     * @apiParam {Integer} cityId 所属城市id
     * @apiParam {String} comName 小区名称
     * @apiParam {String} comArea 小区面积
     */
    @RequestMapping(value = "/updateInfo")
    public void updateInfo(HttpServletResponse response,
                           Integer userId,
                           String password,
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
     * @api {post} /api/users/commentOrders 评价订单
     * @apiName users.commentOrders
     * @apiGroup users
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {Integer} isHide 是否匿名，0=是，1=否       <必传 />
     * @apiParam {Object} commentList 评价详情（json格式字符串）       <必传 />
     * @apiParam {Integer} commentList.orderInfoId 订单详情id       <必传 />
     * @apiParam {Integer} commentList.star 星级       <必传 />
     * @apiParam {String} commentList.content 评价内容       <必传 />
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
                ordersinfo = ordersinfoService.getById(Integer.parseInt(mapInfo.get("orderInfoId").toString()));
                ordersinfo.setStar(Integer.parseInt(mapInfo.get("star").toString()));
                ordersinfo.setComment(mapInfo.get("content").toString());

                ordersinfoService.update(ordersinfo);
            }

            // 评价完成之后修改订单状态
            Orders orders = ordersinfo.getOrder();
            if (null != orders) {
                orders.setStatus(Constant.ORDERS_STATUS_004);
                ordersService.update(orders);
            }

            WebUtil.printApi(response, new Result(true));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0001));
        }
    }

    /**
     * @api {post} /api/users/couponList 我的优惠券列表
     * @apiName users.couponList
     * @apiGroup users
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数       <必传 />
     * @apiSuccess {Object} list 优惠券列表
     * @apiSuccess {Integer} list.id 优惠券id
     * @apiSuccess {Integer} list.name 优惠券名称
     * @apiSuccess {Integer} list.cover 封面
     * @apiSuccess {Integer} list.couponNum 优惠券编号（待定）
     * @apiSuccess {Integer} list.money 金额
     * @apiSuccess {Integer} list.type 类型，0=无限制，1=满减
     * @apiSuccess {Integer} list.maxMoney 满减金额
     * @apiSuccess {Integer} list.startDate 开始日期
     * @apiSuccess {Integer} list.endDate 结束日期
     * @apiSuccess {Integer} list.createTime 创建时间
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
     * @api {post} /api/users/score 用户积分
     * @apiName users.score
     * @apiGroup users
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiSuccess {String} score 用户积分数
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
     * @api {post} /api/users/addFeedBack 添加反馈
     * @apiName users.addFeedBack
     * @apiGroup users
     * @apiParam {Integer} userId 用户id       <必传 />
     * @apiParam {String} type 问题种类（文字）       <必传 />
     * @apiParam {String} content 反馈内容       <必传 />
     * @apiParam {Stream} pic 图片（二进制流文件）
     */
    @RequestMapping(value = "/addFeedBack")
    public void addFeedBack(ServletRequest request, HttpServletResponse response, Integer userId, String type, String content, @RequestParam("pic") MultipartFile multipartFile) {
        if (null == userId || null == type || null == content) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Feedback feedback = new Feedback();
        feedback.setUser(usersService.getById(userId));
        feedback.setType(type);
        feedback.setDescription(content);
        if (null != multipartFile) {
            try {
                feedback.setPath(QiNiuUploadImgUtil.upload(multipartFile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        feedback.setCreateTime(new Date());

        feedbackService.create(feedback);

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * @api {post} /api/users/messageList 系统消息列表
     * @apiName users.messageList
     * @apiGroup users
     * @apiSuccess {Object} list 系统消息列表
     * @apiSuccess {Integer} list.id 消息id
     * @apiSuccess {String} list.title 标题
     * @apiSuccess {String} list.description 描述
     * @apiSuccess {String} list.createTime 创建时间
     */
    @RequestMapping(value = "/messageList")
    public void messageList(HttpServletResponse response) {
        List<Message> list = messageService.findListByType(Constant.MESSAGE_STATUS_USER);

        Result obj = new Result(true).data(list);
        String result = JsonUtil.obj2ApiJson(obj, "type");
        WebUtil.printApi(response, result);
    }

    /**
     * 检测验证码是否正确
     *
     * @param response
     * @param mobile
     * @param code
     * @param type
     */
    public Boolean checkCode(HttpServletResponse response, String mobile, String code, String type) {
        Boolean flag = false;
        Boolean result = false;

        try {
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
                return result;
            } else if (!tempCodeVo.getType().equals(type)) {
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0005));
                return result;
            } else if (DateUtils.secondCompare(tempCodeVo.getCreateTime(), 600)) {
                // 如果存在，则检测是否超时，如果超时，返回错误信息
                WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0014));
                return result;
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

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
