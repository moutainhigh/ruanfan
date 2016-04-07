package com.sixmac.controller;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Designers;
import com.sixmac.entity.Merchants;
import com.sixmac.entity.Sysusers;
import com.sixmac.service.*;
import com.sixmac.utils.IdenCode;
import com.sixmac.utils.Md5Util;
import com.sixmac.utils.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.krb5.internal.crypto.Des;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangbin on 2015/7/29.
 */
@Controller
@RequestMapping(value = "")
public class IndexController extends CommonController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private SysusersService sysusersService;

    @Autowired
    private MerchantsService merchantsService;

    @Autowired
    private DesignersService designersService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StylesService stylesService;

    @RequestMapping(value = "/login")
    public String login(String error,
                        ModelMap model) {
        if (StringUtils.isNotBlank(error)) {
            model.addAttribute("error", error);
        }
        return "登录";
    }

    @RequestMapping(value = "/login/register")
    public String register(HttpServletRequest request) {
        request.getSession().removeAttribute("error");
        return "注册";
    }

    @RequestMapping(value = "/login/registerInfo")
    @ResponseBody
    public Integer registerInfo(Integer type,
                                String mobile,
                                String email,
                                String nickName,
                                String password) {
        try {
            // 先判断注册类型
            if (type == 2) {
                // 商家注册
                Merchants merchants = new Merchants();
                merchants.setNickName(nickName);
                merchants.setPassword(Md5Util.md5(password));
                merchants.setEmail(email);
                merchants.setHead(Constant.DEFAULT_HEAD_PATH);
                merchants.setType(Constant.MERCHANT_TYPE_TWO);
                merchants.setStyle(stylesService.getById(1));
                merchants.setCity(cityService.getById(1));
                merchants.setIsCheck(Constant.CHECK_STATUS_DEFAULT);
                merchants.setStatus(Constant.BANNED_STATUS_YES);
                merchants.setCreateTime(new Date());

                merchantsService.create(merchants);
            } else {
                // 设计师注册
                Designers designers = new Designers();
                designers.setNickName(nickName);
                designers.setMobile(mobile);
                designers.setPassword(Md5Util.md5(password));
                designers.setHead(Constant.DEFAULT_HEAD_PATH);
                designers.setType(Constant.DESIGNER_TYPE_ONE);
                designers.setCity(cityService.getById(1)); // 默认城市为北京
                designers.setStar(0);
                designers.setIsCheck(Constant.CHECK_STATUS_DEFAULT);
                designers.setIsCut(Constant.IS_CUT_NO);
                designers.setStatus(Constant.BANNED_STATUS_YES);
                designers.setCreateTime(new Date());

                designersService.create(designers);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @RequestMapping(value = "/login/check")
    public String checkLogin(String account,
                             String password,
                             Integer type,
                             HttpServletRequest request,
                             String code,
                             ModelMap model) {
        HttpSession session = request.getSession();

        // 验证码
        if (!code.equalsIgnoreCase((String) request.getSession().getAttribute("code"))) {
            model.addAttribute("error", "验证码不正确");
            return "redirect:/login";
        }

        // 根据当前登录人的类型进行不同类型的登录操作
        switch (type) {
            case 1:
                // 管理员
                Sysusers sysusers = usersService.sysUserLogin(session, account, Md5Util.md5(password));
                if (null != sysusers) {
                    return "redirect:/backend/dashboard";
                }
                break;
            case 2:
                // 商户
                Merchants merchants = usersService.merchantLogin(session, account, Md5Util.md5(password));
                if (null != merchants) {
                    if (merchants.getIsCut() == Constant.IS_CUT_YES) {
                        model.addAttribute("error", "商家不存在");
                        return "redirect:/login";
                    }
                    if (merchants.getStatus() == Constant.BANNED_STATUS_NO) {
                        model.addAttribute("error", "该商家已被禁用");
                        return "redirect:/login";
                    }
                    return "merchant/控制面板";
                }
                break;
            case 3:
                // 设计师
                Designers designers = usersService.desingerLogin(session, account, Md5Util.md5(password));
                if (null != designers) {
                    if (designers.getIsCut() == Constant.IS_CUT_YES) {
                        model.addAttribute("error", "设计师不存在");
                        return "redirect:/login";
                    }
                    if (designers.getStatus() == Constant.BANNED_STATUS_NO) {
                        model.addAttribute("error", "该设计师已被禁用");
                        return "redirect:/login";
                    }
                    return "designer/控制面板";
                }
                break;
        }

        model.addAttribute("error", "用户名或密码错误!");
        return "redirect:/login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request,
                         ModelMap model) {
        usersService.logOut(request);
        return "登录";
    }

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request) {
        // 根据当前登录人的不同类型，选择跳转到不同的后台
        HttpSession session = request.getSession();
        Integer type = (Integer) session.getAttribute(Constant.CURRENT_USER_TYPE);

        String url = "";

        switch (type) {
            case 1:
                url = "redirect:/backend/dashboard";
                break;
            case 2:
                url = "redirect:/merchant/dashboard";
                break;
            case 3:
                url = "redirect:/designer/dashboard";
                break;
        }

        return url;
    }

    /**
     * 生成验证码
     */
    @RequestMapping(value = "/login/getIdenCode")
    public void getIdenCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 生成验证码
            IdenCode.getIdenCode(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册时检测邮箱是否存在
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/login/emailCheck")
    @ResponseBody
    public Map<String, String> emailCheck(String email) {
        Map<String, String> map = new HashMap<String, String>();
        List<Merchants> merchantsList = merchantsService.findListByEmail(email);
        if (null == merchantsList || merchantsList.size() == 0) {
            map.put("ok", "");
        } else {
            map.put("error", "邮箱已存在");
        }
        return map;
    }

    /**
     * 注册时检测手机号是否存在
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/login/mobileCheck")
    @ResponseBody
    public Map<String, String> mobileCheck(String mobile) {
        Map<String, String> map = new HashMap<String, String>();
        List<Designers> designersList = designersService.findListByMobile(mobile);
        if (null == designersList || designersList.size() == 0) {
            map.put("ok", "");
        } else {
            map.put("error", "手机号已存在");
        }
        return map;
    }

    /**
     * 检测当前登录人旧密码是否正确
     *
     * @param request
     * @param response
     * @param oldPwd
     */
    @RequestMapping("/check/oldPwd")
    public void checkOldPwd(HttpServletRequest request,
                            HttpServletResponse response,
                            String oldPwd) {
        Integer id = (Integer) request.getSession().getAttribute(Constant.CURRENT_USER_ID);
        Integer type = (Integer) request.getSession().getAttribute(Constant.CURRENT_USER_TYPE);

        // type有三种类型
        // 1、管理员
        // 2、商家
        // 3、设计师
        switch (type) {
            case 1:
                Sysusers sysusers = sysusersService.getById(id);
                checkOldPwd(oldPwd, sysusers.getPassword(), request, response);
                break;
            case 2:
                Merchants merchants = merchantsService.getById(id);
                checkOldPwd(oldPwd, merchants.getPassword(), request, response);
                break;
            case 3:
                Designers designers = designersService.getById(id);
                checkOldPwd(oldPwd, designers.getPassword(), request, response);
                break;
        }
    }

    /**
     * 检测旧密码是否正确
     *
     * @param oldPwd
     * @param pwd
     * @param request
     * @param response
     */
    private void checkOldPwd(String oldPwd, String pwd, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> result = new HashMap<String, String>();
        if (!pwd.equals(Md5Util.md5(oldPwd))) {
            result.put("error", "旧密码不正确!");
            WebUtil.print(response, result);
        } else {
            result.put("ok", "");
            WebUtil.print(response, result);
        }
    }

    @RequestMapping("/modifyPwd")
    public void modifyPwd(HttpServletRequest request,
                          HttpServletResponse response,
                          String oldPwd,
                          String newPwd) {
        /*Member member = null;
        try {
            member = loginService.getMember(request,Constant.MEMBER_TYPE_GLOBLE);
            if(null != member){
                if(member.getPassword().equals(Md5Util.md5(oldPwd))){
                    member.setPassword(Md5Util.md5(newPwd));
                    memberService.update(member);
                    loginService.logOut(request,Constant.MEMBER_TYPE_GLOBLE);
                    WebUtil.print(response, new Result(true).msg("修改密码成功！请重新登录！"));
                }else{
                    WebUtil.print(response, new Result(false).msg("旧密码错误，修改密码失败！"));
                }
            }else{
                WebUtil.print(response, new Result(false).msg("当前用户未登录！"));
            }
        } catch (Exception e) {
            GeneralExceptionHandler.log("修改密码失败", e);
            WebUtil.print(response, new Result(false).msg("修改密码失败！"));
        }*/
    }

}
