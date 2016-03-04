package com.sixmac.controller;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.service.UsersService;
import com.sixmac.utils.IdenCode;
import com.sixmac.utils.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangbin on 2015/7/29.
 */
@Controller
@RequestMapping(value = "")
public class IndexController extends CommonController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        String error,
                        ModelMap model) {
        if (StringUtils.isNotBlank(error)) {
            model.addAttribute("error", error);
        }
        return "登录";
    }

    @RequestMapping(value = "/login/check")
    public String checkLogin(String username,
                             String password,
                             HttpServletRequest request, HttpServletResponse response,
                             String remark,
                             String idencode,
                             ModelMap model) {
        // 验证码
        if (!idencode.equalsIgnoreCase((String) request.getSession().getAttribute("code"))) {
            model.addAttribute("error", "验证码不正确");
            return "redirect:/login";
        }
        Boolean success = usersService.login(request, username, Md5Util.md5(password), Constant.MEMBER_TYPE_GLOBLE, remark);
        if (success) {
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "用户名或密码错误!");
        return "redirect:/login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         ModelMap model) {
        usersService.logOut(request, Constant.MEMBER_TYPE_GLOBLE);
        return "登录";
    }

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:/dashboard";
    }


    @RequestMapping(value = "/dashboard")
    public String dashboard(HttpServletRequest request,
                            HttpServletResponse response,
                            ModelMap model) {
        return "控制面板";
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

    @RequestMapping("/check/oldPwd")
    public void checkOldPwd(HttpServletRequest request,
                            HttpServletResponse response,
                            String oldPwd) {
        Map<String, String> result = new HashMap<String, String>();
        // Member member = loginService.getMember(request, Constant.MEMBER_TYPE_GLOBLE);
        /*if(!member.getPassword().equals(Md5Util.md5(oldPwd))){
            result.put("error","旧密码不正确!");
            WebUtil.print(response, result);
        }else{
            result.put("ok","");
            WebUtil.print(response, result);
        }*/
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
