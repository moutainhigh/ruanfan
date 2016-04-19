package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.VirtualsDao;
import com.sixmac.entity.Virtuals;
import com.sixmac.service.VirtualsService;
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
 * Created by Administrator on 2016/3/7 0007 下午 3:15.
 */
@Service
public class VirtualsServiceImpl implements VirtualsService {

    @Autowired
    private VirtualsDao virtualsDao;

    @Override
    public List<Virtuals> findAll() {
        return virtualsDao.findAll();
    }

    @Override
    public Page<Virtuals> find(int pageNum, int pageSize) {
        return virtualsDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Virtuals> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Virtuals getById(int id) {
        return virtualsDao.findOne(id);
    }

    @Override
    public Virtuals deleteById(int id) {
        Virtuals virtuals = getById(id);
        virtualsDao.delete(virtuals);
        return virtuals;
    }

    @Override
    public Virtuals create(Virtuals virtuals) {
        return virtualsDao.save(virtuals);
    }

    @Override
    public Virtuals update(Virtuals virtuals) {
        return virtualsDao.save(virtuals);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Virtuals> iPage(final String name, final Integer styleId, final Integer typeId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Virtuals> page = virtualsDao.findAll(new Specification<Virtuals>() {
            @Override
            public Predicate toPredicate(Root<Virtuals> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (name != null) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }
                if (styleId != null) {
                    Predicate pre = cb.equal(root.get("style").get("id").as(Integer.class), styleId);
                    predicateList.add(pre);
                }
                if (typeId != null) {
                    Predicate pre = cb.equal(root.get("type").get("id").as(Integer.class), typeId);
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
}