package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.MerchantsDao;
import com.sixmac.entity.Merchants;
import com.sixmac.service.MerchantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 11:33.
 */
@Service
public class MerchantsServiceImpl implements MerchantsService {

    @Autowired
    private MerchantsDao merchantsDao;

    @Override
    public List<Merchants> findAll() {
        return merchantsDao.findAll();
    }

    @Override
    public Page<Merchants> find(int pageNum, int pageSize) {
        return merchantsDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Merchants> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Merchants getById(int id) {
        return merchantsDao.findOne(id);
    }

    @Override
    public Merchants deleteById(int id) {
        Merchants merchants = getById(id);
        merchantsDao.delete(merchants);
        return merchants;
    }

    @Override
    public Merchants create(Merchants merchants) {
        return merchantsDao.save(merchants);
    }

    @Override
    public Merchants update(Merchants merchants) {
        return merchantsDao.save(merchants);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Merchants> findListByEmail(String email) {
        return merchantsDao.findListByEmail(email);
    }
}