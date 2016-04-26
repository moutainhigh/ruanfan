package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.ReplysDao;
import com.sixmac.entity.Replys;
import com.sixmac.service.ReplysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/4/14 0014 下午 6:25.
 */
@Service
public class ReplysServiceImpl implements ReplysService {

    @Autowired
    private ReplysDao replysDao;

    @Override
    public List<Replys> findAll() {
        return replysDao.findAll();
    }

    @Override
    public Page<Replys> find(int pageNum, int pageSize) {
        return replysDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Replys> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Replys getById(int id) {
        return replysDao.findOne(id);
    }

    @Override
    public Replys deleteById(int id) {
        Replys replys = getById(id);
        replysDao.delete(replys);
        return replys;
    }

    @Override
    public Replys create(Replys replys) {
        return replysDao.save(replys);
    }

    @Override
    public Replys update(Replys replys) {
        return replysDao.save(replys);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Replys> iPageByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        return replysDao.pageByUserId(userId, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }
}