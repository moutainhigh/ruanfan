package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.LetterreplyDao;
import com.sixmac.entity.Letterreply;
import com.sixmac.service.LetterreplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19 0019 下午 6:34.
 */
@Service
public class LetterreplyServiceImpl implements LetterreplyService {

    @Autowired
    private LetterreplyDao letterreplyDao;

    @Override
    public List<Letterreply> findAll() {
        return letterreplyDao.findAll();
    }

    @Override
    public Page<Letterreply> find(int pageNum, int pageSize) {
        return letterreplyDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Letterreply> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Letterreply getById(int id) {
        return letterreplyDao.findOne(id);
    }

    @Override
    public Letterreply deleteById(int id) {
        Letterreply letterreply = getById(id);
        letterreplyDao.delete(letterreply);
        return letterreply;
    }

    @Override
    public Letterreply create(Letterreply letterreply) {
        return letterreplyDao.save(letterreply);
    }

    @Override
    public Letterreply update(Letterreply letterreply) {
        return letterreplyDao.save(letterreply);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Letterreply> pageByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        return letterreplyDao.pageByUserId(userId, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }
}