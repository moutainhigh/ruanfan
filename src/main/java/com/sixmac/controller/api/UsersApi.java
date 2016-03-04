package com.sixmac.controller.api;

import com.sixmac.core.Constant;
import com.sixmac.entity.Users;
import com.sixmac.service.UsersService;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Controller
@RequestMapping(value = "api/users")
public class UsersApi {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/login")
    public void login(HttpServletRequest request, HttpServletResponse response, String name, String password) {
        Users users = new Users();
        users.setNickName(name);
        users.setPassword(password);

        WebUtil.print(response, Constant.EVENT_WINE_ONLINE_HOME);
    }
}
