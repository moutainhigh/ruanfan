package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.VersionsDao;
import com.sixmac.entity.Versions;
import com.sixmac.service.VersionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/6/21 0021 上午 11:43.
 */
@Service
public class VersionsServiceImpl implements VersionsService {

    @Autowired
    private VersionsDao versionsDao;

    @Override
    public List<Versions> findAll() {
        return versionsDao.findAll();
    }

    @Override
    public Page<Versions> find(int pageNum, int pageSize) {
        return versionsDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Versions> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Versions getById(int id) {
        return versionsDao.findOne(id);
    }

    @Override
    public Versions deleteById(int id) {
        Versions versions = getById(id);
        versionsDao.delete(versions);
        return versions;
    }

    @Override
    public Versions create(Versions versions) {
        return versionsDao.save(versions);
    }

    @Override
    public Versions update(Versions versions) {
        return versionsDao.save(versions);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }
}