package com.sixmac.service;

import com.sixmac.entity.*;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:52.
 */
public interface UsersService extends ICommonService<Users> {

    // 总后台管理员登录
    public Sysusers sysUserLogin(HttpSession session, String username, String password);

    // 商户登录
    public Merchants merchantLogin(HttpSession session, String username, String password);

    // 设计师登录
    public Designers desingerLogin(HttpSession session, String username, String password);

    public void logOut(HttpServletRequest request);

    // 将用户的优惠券标记为已使用
    public void usedCoupon(Integer userId, Integer couponId);

    // 移动端用户登录
    public Users iLogin(String mobile, String password);

    // 移动端用户通过手机号登录
    public Users iLogin(String mobile);

    // 移动端用户第三方登录
    public Users iTLogin(Integer type, String openId, String head, String nickname);

    // 移动端用户注册
    public Users iRegister(Users users);

    // 移动端根据用户id获取用户优惠券列表
    public Page<Usercoupon> iPageByUserId(Integer userId, Integer pageNum, Integer pageSize);

    // 移动端根据用户手机号获取用户信息
    public Users iFindOneByMobile(String mobile);

    public Page<Users> page(String mobile, String nickName, Integer status, Integer type, Integer pageNum, Integer pageSize);

    // 查询新增会员列表
    public List<Users> findListNew();
}