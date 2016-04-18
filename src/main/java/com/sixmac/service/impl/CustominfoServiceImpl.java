package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.CustominfoDao;
import com.sixmac.entity.Custominfo;
import com.sixmac.service.CustominfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008 下午 3:09.
 */
@Service
public class CustominfoServiceImpl implements CustominfoService {

    @Autowired
    private CustominfoDao custominfoDao;

    @Override
    public List<Custominfo> findAll() {
        return custominfoDao.findAll();
    }

    @Override
    public Page<Custominfo> find(int pageNum, int pageSize) {
        return custominfoDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Custominfo> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Custominfo getById(int id) {
        return custominfoDao.findOne(id);
    }

    @Override
    public Custominfo deleteById(int id) {
        Custominfo custominfo = getById(id);
        custominfoDao.delete(custominfo);
        return custominfo;
    }

    @Override
    public Custominfo create(Custominfo custominfo) {
        return custominfoDao.save(custominfo);
    }

    @Override
    public Custominfo update(Custominfo custominfo) {
        return custominfoDao.save(custominfo);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Custominfo> findListByCustomId(Integer customId) {
        return custominfoDao.findListByCustomId(customId);
    }
}