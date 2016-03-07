package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.DesignersDao;
import com.sixmac.dao.WorksDao;
import com.sixmac.entity.Designers;
import com.sixmac.entity.Works;
import com.sixmac.service.DesignersService;
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
 * Created by Administrator on 2016/3/7 0007 下午 1:52.
 */
@Service
public class DesignersServiceImpl implements DesignersService {

    @Autowired
    private DesignersDao designersDao;

    @Autowired
    private WorksDao worksDao;

    @Override
    public List<Designers> findAll() {
        return designersDao.findAll();
    }

    @Override
    public Page<Designers> find(int pageNum, int pageSize) {
        return designersDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Designers> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Designers getById(int id) {
        return designersDao.findOne(id);
    }

    @Override
    public Designers deleteById(int id) {
        Designers designers = getById(id);
        designersDao.delete(designers);
        return designers;
    }

    @Override
    public Designers create(Designers designers) {
        return designersDao.save(designers);
    }

    @Override
    public Designers update(Designers designers) {
        return designersDao.save(designers);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Designers> iPage(Integer type, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Designers> page = designersDao.findAll(new Specification<Designers>() {
            @Override
            public Predicate toPredicate(Root<Designers> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
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
    public Page<Works> iPageWorks(Integer designerId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Works> page = worksDao.findAll(new Specification<Works>() {
            @Override
            public Predicate toPredicate(Root<Works> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (designerId != null) {
                    Predicate pre = cb.equal(root.get("designer").get("id").as(Integer.class), designerId);
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