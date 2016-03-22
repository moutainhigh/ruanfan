package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.CouponDao;
import com.sixmac.entity.Coupon;
import com.sixmac.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/22 0022 上午 11:53.
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    @Override
    public List<Coupon> findAll() {
        return couponDao.findAll();
    }

    @Override
    public Page<Coupon> find(int pageNum, int pageSize) {
        return couponDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Coupon> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Coupon getById(int id) {
        return couponDao.findOne(id);
    }

    @Override
    public Coupon deleteById(int id) {
        Coupon coupon = getById(id);
        couponDao.delete(coupon);
        return coupon;
    }

    @Override
    public Coupon create(Coupon coupon) {
        return couponDao.save(coupon);
    }

    @Override
    public Coupon update(Coupon coupon) {
        return couponDao.save(coupon);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }
}