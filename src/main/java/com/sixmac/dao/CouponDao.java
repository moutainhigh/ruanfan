package com.sixmac.dao;

import com.sixmac.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:42.
 */
public interface CouponDao extends JpaRepository<Coupon, Integer> {

    @Query("select a from Coupon a where a.couponNum = ?1")
    public Coupon getByNum(String couponNum);
}