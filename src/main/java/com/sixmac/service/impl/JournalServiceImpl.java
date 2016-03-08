package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.JournalDao;
import com.sixmac.entity.Journal;
import com.sixmac.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 下午 2:34.
 */
@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    private JournalDao journalDao;

    @Override
    public List<Journal> findAll() {
        return journalDao.findAll();
    }

    @Override
    public Page<Journal> find(int pageNum, int pageSize) {
        return journalDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Journal> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Journal getById(int id) {
        return journalDao.findOne(id);
    }

    @Override
    public Journal deleteById(int id) {
        Journal journal = getById(id);
        journalDao.delete(journal);
        return journal;
    }

    @Override
    public Journal create(Journal journal) {
        return journalDao.save(journal);
    }

    @Override
    public Journal update(Journal journal) {
        return journalDao.save(journal);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }
}