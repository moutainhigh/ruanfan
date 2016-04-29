package com.sixmac.dao;

import com.sixmac.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:42.
 */
public interface CouponDao extends JpaRepository<Coupon, Integer> {

    @Query("select a from Coupon a where a.couponNum = ?1")
    public Coupon getByNum(String couponNum);

    @Query("select a from Coupon a where a.merchant.id = ?1")
    public Page<Coupon> pageByMerchantId(Integer merchantId, Pageable pageable);
}