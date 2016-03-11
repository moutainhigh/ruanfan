package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.WorksDao;
import com.sixmac.entity.Works;
import com.sixmac.service.WorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/11 0011 下午 5:00.
 */
@Service
public class WorksServiceImpl implements WorksService {

    @Autowired
    private WorksDao worksDao;

    @Override
    public List<Works> findAll() {
        return worksDao.findAll();
    }

    @Override
    public Page<Works> find(int pageNum, int pageSize) {
        return worksDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Works> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Works getById(int id) {
        return worksDao.findOne(id);
    }

    @Override
    public Works deleteById(int id) {
        Works works = getById(id);
        worksDao.delete(works);
        return works;
    }

    @Override
    public Works create(Works works) {
        return worksDao.save(works);
    }

    @Override
    public Works update(Works works) {
        return worksDao.save(works);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }
}