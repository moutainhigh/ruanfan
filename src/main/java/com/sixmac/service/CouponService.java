package com.sixmac.service;

import com.sixmac.entity.Coupon;
import com.sixmac.entity.Usercoupon;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

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

    // 根据商户id查询对应的优惠券集合
    public Page<Coupon> pageByMerchantId(Integer merchantId, Integer pageNum, Integer pageSize);

    // 根据优惠券id获取有关联的所有用户信息
    public List<Usercoupon> findListByCouponId(Integer couponId);

    // 根据优惠券id删除有关联的所有用户信息
    public void deleteAllListByCouponId(Integer couponId);
}