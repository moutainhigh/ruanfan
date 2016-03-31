package com.sixmac.service;

import com.sixmac.entity.Coupon;
import com.sixmac.entity.Usercoupon;
import com.sixmac.service.common.ICommonService;

/**
 * Created by Administrator on 2016/3/22 0022 上午 11:53.
 */
public interface CouponService extends ICommonService<Coupon> {

    // 根据优惠券号查找优惠券信息
    public Coupon getByNum(String couponNum);

    // 添加用户优惠券关联信息
    public void addUserCouponInfo(Integer userId, Coupon coupon);

    // 根据优惠券id和用户id查询对应的用户优惠券信息
    public Usercoupon getByUserIdAndCouponId(Integer userId, Integer couponId);
}