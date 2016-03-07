package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.StylesDao;
import com.sixmac.entity.Styles;
import com.sixmac.service.StylesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 2:23.
 */
@Service
public class StylesServiceImpl implements StylesService {

    @Autowired
    private StylesDao stylesDao;

    @Override
    public List<Styles> findAll() {
        return stylesDao.findAll();
    }

    @Override
    public Page<Styles> find(int pageNum, int pageSize) {
        return stylesDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Styles> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Styles getById(int id) {
        return stylesDao.findOne(id);
    }

    @Override
    public Styles deleteById(int id) {
        Styles styles = getById(id);
        stylesDao.delete(styles);
        return styles;
    }

    @Override
    public Styles create(Styles styles) {
        return stylesDao.save(styles);
    }

    @Override
    public Styles update(Styles styles) {
        return stylesDao.save(styles);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }
}