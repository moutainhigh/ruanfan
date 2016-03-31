package com.sixmac.dao;

import com.sixmac.entity.Usercoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:48.
 */
public interface UsercouponDao extends JpaRepository<Usercoupon, Integer>, JpaSpecificationExecutor<Usercoupon> {

    @Query("select a from Usercoupon a where a.user.id = ?1 and a.coupon.id = ?2")
    public Usercoupon getByUserIdAndCouponId(Integer userId, Integer couponId);
}