package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.CollectDao;
import com.sixmac.entity.Collect;
import com.sixmac.entity.Users;
import com.sixmac.service.CollectService;
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
 * Created by Administrator on 2016/3/7 0007 上午 11:43.
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectDao collectDao;

    @Override
    public List<Collect> findAll() {
        return collectDao.findAll();
    }

    @Override
    public Page<Collect> find(int pageNum, int pageSize) {
        return collectDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Collect> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Collect getById(int id) {
        return collectDao.findOne(id);
    }

    @Override
    public Collect deleteById(int id) {
        Collect collect = getById(id);
        collectDao.delete(collect);
        return collect;
    }

    @Override
    public Collect create(Collect collect) {
        return collectDao.save(collect);
    }

    @Override
    public Collect update(Collect collect) {
        return collectDao.save(collect);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void iCreate(Users user, Integer objectId, Integer objectType) {
        Collect collect = new Collect();
        collect.setUser(user);
        collect.setObjectId(objectId);
        collect.setObjectType(objectType);

        collectDao.save(collect);
    }

    @Override
    public Collect iFindOne(Integer userId, Integer objectId, Integer objectType) {
        return collectDao.iFindOne(userId, objectId, objectType);
    }

    @Override
    public Page<Collect> iPage(Integer userId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Collect> page = collectDao.findAll(new Specification<Collect>() {
            @Override
            public Predicate toPredicate(Root<Collect> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
    public List<Collect> iFindList(Integer objectId, Integer objectType) {
        return collectDao.iFindList(objectId, objectType);
    }
}