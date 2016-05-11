package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.PrivateletterDao;
import com.sixmac.entity.Privateletter;
import com.sixmac.service.PrivateletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19 0019 下午 4:56.
 */
@Service
public class PrivateletterServiceImpl implements PrivateletterService {

    @Autowired
    private PrivateletterDao privateletterDao;

    @Override
    public List<Privateletter> findAll() {
        return privateletterDao.findAll();
    }

    @Override
    public Page<Privateletter> find(int pageNum, int pageSize) {
        return privateletterDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Privateletter> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Privateletter getById(int id) {
        return privateletterDao.findOne(id);
    }

    @Override
    public Privateletter deleteById(int id) {
        Privateletter privateletter = getById(id);
        privateletterDao.delete(privateletter);
        return privateletter;
    }

    @Override
    public Privateletter create(Privateletter privateletter) {
        return privateletterDao.save(privateletter);
    }

    @Override
    public Privateletter update(Privateletter privateletter) {
        return privateletterDao.save(privateletter);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Privateletter> pageByReceiveUser(Integer userId, Integer pageNum, Integer pageSize) {
        return privateletterDao.pageByReceiveUser(userId, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Privateletter> pageWithDialogue(Integer userId, Integer otherUserId, Integer pageNum, Integer pageSize) {
        return privateletterDao.pageWithDialogue(userId, otherUserId, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }
}