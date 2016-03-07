package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.AfflatusDao;
import com.sixmac.entity.Afflatus;
import com.sixmac.service.AfflatusService;
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
 * Created by Administrator on 2016/3/4 0004 下午 5:10.
 */
@Service
public class AfflatusServiceImpl implements AfflatusService {

    @Autowired
    private AfflatusDao afflatusDao;

    @Override
    public List<Afflatus> findAll() {
        return afflatusDao.findAll();
    }

    @Override
    public Page<Afflatus> find(int pageNum, int pageSize) {
        return afflatusDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Afflatus> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Afflatus getById(int id) {
        return afflatusDao.findOne(id);
    }

    @Override
    public Afflatus deleteById(int id) {
        Afflatus afflatus = getById(id);
        afflatusDao.delete(afflatus);
        return afflatus;
    }

    @Override
    public Afflatus create(Afflatus afflatus) {
        return afflatusDao.save(afflatus);
    }

    @Override
    public Afflatus update(Afflatus afflatus) {
        return afflatusDao.save(afflatus);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Afflatus> iPage(Integer type, Integer styleId, Integer areaId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Afflatus> page = afflatusDao.findAll(new Specification<Afflatus>() {
            @Override
            public Predicate toPredicate(Root<Afflatus> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
                    predicateList.add(pre);
                }
                if (styleId != null) {
                    Predicate pre = cb.equal(root.get("style").get("id").as(Integer.class), styleId);
                    predicateList.add(pre);
                }
                if (areaId != null) {
                    Predicate pre = cb.equal(root.get("area").get("id").as(Integer.class), areaId);
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
    public List<Afflatus> iFindLoveList(Integer type, Integer styleId, Integer areaId) {
        return afflatusDao.iFindLoveList(type, styleId, areaId);
    }
}