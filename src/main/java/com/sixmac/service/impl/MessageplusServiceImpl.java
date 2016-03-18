package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.MessageplusDao;
import com.sixmac.entity.Messageplus;
import com.sixmac.service.MessageplusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/18 0018 下午 3:37.
 */
@Service
public class MessageplusServiceImpl implements MessageplusService {

    @Autowired
    private MessageplusDao messageplusDao;

    @Override
    public List<Messageplus> findAll() {
        return messageplusDao.findAll();
    }

    @Override
    public Page<Messageplus> find(int pageNum, int pageSize) {
        return messageplusDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Messageplus> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Messageplus getById(int id) {
        return messageplusDao.findOne(id);
    }

    @Override
    public Messageplus deleteById(int id) {
        Messageplus messageplus = getById(id);
        messageplusDao.delete(messageplus);
        return messageplus;
    }

    @Override
    public Messageplus create(Messageplus messageplus) {
        return messageplusDao.save(messageplus);
    }

    @Override
    public Messageplus update(Messageplus messageplus) {
        return messageplusDao.save(messageplus);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }
}