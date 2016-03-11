package com.sixmac.service;

import com.sixmac.entity.Usercoupon;
import com.sixmac.entity.Users;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:52.
 */
public interface UsersService extends ICommonService<Users> {

    public Boolean login(HttpServletRequest request, String username, String password, String remark);

    public Boolean login(HttpServletRequest request, String username, String password, String type, String remark);

    public void logOut(HttpServletRequest request, String type);

    // 将用户的优惠券标记为已使用
    public void usedCoupon(Integer userId, Integer couponId);

    // 移动端用户登录
    public Users iLogin(String mobile, String password);

    // 移动端用户第三方登录
    public Users iTLogin(Integer type, String openId, String head, String nickname);

    // 移动端用户注册
    public Users iRegister(Users users);

    // 移动端根据用户id获取用户优惠券列表
    public Page<Usercoupon> iPageByUserId(Integer userId, Integer pageNum, Integer pageSize);

    // 移动端根据用户手机号获取用户信息
    public Users iFindOneByMobile(String mobile);
}