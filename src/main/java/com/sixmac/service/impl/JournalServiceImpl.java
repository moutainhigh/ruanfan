package com.sixmac.service.impl;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.dao.JournalDao;
import com.sixmac.entity.Journal;
import com.sixmac.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

    @Override
    public Page<Journal> iPage(final Integer userId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Journal> page = journalDao.findAll(new Specification<Journal>() {
            @Override
            public Predicate toPredicate(Root<Journal> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (userId != null) {
                    Predicate pre = cb.equal(root.get("user").get("id").as(Integer.class), userId);
                    predicateList.add(pre);
                }
                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }

                if (result != null) {
                    query.where(result);
                }
                return query.getGroupRestriction();
            }

        }, pageRequest);

        return page;
    }

    @Override
    public List<Journal> iFindListByUserId(Integer userId) {
        return journalDao.iFindListByUserId(userId);
    }

    @Override
    public List<Journal> FindListNew() {
        return journalDao.findListNew(CommonController.getOldDate());
    }
}