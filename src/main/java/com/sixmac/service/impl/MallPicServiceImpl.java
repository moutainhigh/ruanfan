package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.MallPicDao;
import com.sixmac.entity.MallPic;
import com.sixmac.service.MallPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/5/5 0005 下午 6:26.
 */
@Service
public class MallPicServiceImpl implements MallPicService {

    @Autowired
    private MallPicDao mallPicDao;

    @Override
    public List<MallPic> findAll() {
        return mallPicDao.findAll();
    }

    @Override
    public Page<MallPic> find(int pageNum, int pageSize) {
        return mallPicDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<MallPic> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public MallPic getById(int id) {
        return mallPicDao.findOne(id);
    }

    @Override
    public MallPic deleteById(int id) {
        MallPic mallPic = getById(id);
        mallPicDao.delete(mallPic);
        return mallPic;
    }

    @Override
    public MallPic create(MallPic mallPic) {
        return mallPicDao.save(mallPic);
    }

    @Override
    public MallPic update(MallPic mallPic) {
        return mallPicDao.save(mallPic);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<MallPic> page(Integer pageNum, Integer pageSize) {
        return mallPicDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id"));
    }
}