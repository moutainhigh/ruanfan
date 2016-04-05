package com.sixmac.service.impl;

import com.sixmac.dao.OperatisDao;
import com.sixmac.entity.Operatis;
import com.sixmac.service.OperatisService;
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
 * Created by Administrator on 2016/3/30 0030.
 */
@Service
public class OperatisServiceImpl implements OperatisService {

    @Autowired
    private OperatisDao dao;

    @Override
    public Page<Operatis> findPage(Operatis operatis, int pageNum, int pageSize) {
        return dao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Operatis> page(String name, String roleName, int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Operatis> page = dao.findAll(new Specification<Operatis>() {
            @Override
            public Predicate toPredicate(Root<Operatis> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (null != name) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }
                if (roleName != null) {
                    Predicate pre = cb.like(root.get("roleName").as(String.class), "%" + roleName + "%");
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
    public List<Operatis> findAll() {
        return null;
    }

    @Override
    public Page<Operatis> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<Operatis> find(int pageNum) {
        return null;
    }

    @Override
    public Operatis getById(int id) {
        return null;
    }

    @Override
    public Operatis deleteById(int id) {
        dao.delete(id);
        return null;
    }

    @Override
    public Operatis create(Operatis operatis) {
        return null;
    }

    @Override
    public Operatis update(Operatis operatis) {
        return null;
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }
}
