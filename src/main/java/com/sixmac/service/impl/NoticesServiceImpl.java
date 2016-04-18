package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.NoticesDao;
import com.sixmac.entity.Notices;
import com.sixmac.service.NoticesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/18 0018 下午 5:55.
 */
@Service
public class NoticesServiceImpl implements NoticesService {

    @Autowired
    private NoticesDao noticesDao;

    @Override
    public List<Notices> findAll() {
        return noticesDao.findAll();
    }

    @Override
    public Page<Notices> find(int pageNum, int pageSize) {
        return noticesDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Notices> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Notices getById(int id) {
        return noticesDao.findOne(id);
    }

    @Override
    public Notices deleteById(int id) {
        Notices notices = getById(id);
        noticesDao.delete(notices);
        return notices;
    }

    @Override
    public Notices create(Notices notices) {
        return noticesDao.save(notices);
    }

    @Override
    public Notices update(Notices notices) {
        return noticesDao.save(notices);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Notices> page(Integer sourceId, Integer sourceType, int pagenum, int pagesize) {
        PageRequest pageRequest = new PageRequest(pagenum - 1, pagesize, Sort.Direction.ASC, "id");

        Page<Notices> page = noticesDao.pageBySourceIdAndSourceType(sourceId, sourceType, pageRequest);

        return page;
    }
}