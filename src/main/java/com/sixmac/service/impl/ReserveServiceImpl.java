package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.ReserveDao;
import com.sixmac.entity.Reserve;
import com.sixmac.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 2:24.
 */
@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private ReserveDao reserveDao;

    @Override
    public List<Reserve> findAll() {
        return reserveDao.findAll();
    }

    @Override
    public Page<Reserve> find(int pageNum, int pageSize) {
        return reserveDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Reserve> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Reserve getById(int id) {
        return reserveDao.findOne(id);
    }

    @Override
    public Reserve deleteById(int id) {
        Reserve reserve = getById(id);
        reserveDao.delete(reserve);
        return reserve;
    }

    @Override
    public Reserve create(Reserve reserve) {
        return reserveDao.save(reserve);
    }

    @Override
    public Reserve update(Reserve reserve) {
        return reserveDao.save(reserve);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Reserve> iFindListByDesignerId(Integer designerId) {
        return reserveDao.iFindListByDesignerId(designerId);
    }
}