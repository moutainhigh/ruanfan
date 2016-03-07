package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.GamsDao;
import com.sixmac.entity.Gams;
import com.sixmac.service.GamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 上午 10:51.
 */
@Service
public class GamsServiceImpl implements GamsService {

    @Autowired
    private GamsDao gamsDao;

    @Override
    public List<Gams> findAll() {
        return gamsDao.findAll();
    }

    @Override
    public Page<Gams> find(int pageNum, int pageSize) {
        return gamsDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Gams> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Gams getById(int id) {
        return gamsDao.findOne(id);
    }

    @Override
    public Gams deleteById(int id) {
        Gams gams = getById(id);
        gamsDao.delete(gams);
        return gams;
    }

    @Override
    public Gams create(Gams gams) {
        return gamsDao.save(gams);
    }

    @Override
    public Gams update(Gams gams) {
        return gamsDao.save(gams);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Gams> iFindList(Integer objectId, Integer objectType, Integer type, Integer sort) {
        if (sort == Constant.SORT_TYPE_ASC) {
            return gamsDao.iFindListASC(objectId, objectType, type);
        } else {
            return gamsDao.iFindListDESC(objectId, objectType, type);
        }
    }
}