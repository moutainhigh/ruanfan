package com.sixmac.service;

import com.sixmac.entity.Users;
import com.sixmac.service.common.ICommonService;

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
}