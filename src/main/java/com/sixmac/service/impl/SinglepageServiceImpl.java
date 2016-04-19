package com.sixmac.service.impl;

import com.sixmac.dao.SinglepageDao;
import com.sixmac.entity.Singlepage;
import com.sixmac.service.SinglepageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
@Service
public class SinglepageServiceImpl implements SinglepageService {

    @Autowired
    private SinglepageDao dao;

    @Override
    public List<Singlepage> findAll() {
        return dao.findAll();
    }

    @Override
    public Page<Singlepage> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<Singlepage> find(int pageNum) {
        return null;
    }

    @Override
    public Singlepage getById(int id) {
        return dao.findOne(id);
    }

    @Override
    public Singlepage deleteById(int id) {
        return null;
    }

    @Override
    public Singlepage create(Singlepage singlepage) {
        return this.update(singlepage);
    }

    @Override
    public Singlepage update(Singlepage singlepage) {
        return dao.save(singlepage);
    }

    @Override
    public void deleteAll(int[] ids) {

    }

    @Override
    public Page<Singlepage> page(final String title, final String content, int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Singlepage> page = dao.findAll(new Specification<Singlepage>() {
            @Override
            public Predicate toPredicate(Root<Singlepage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (StringUtils.isNotBlank(title)) {
                    Predicate pre = cb.like(root.get("title").as(String.class), "%" + title + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(content)) {
                    Predicate pre = cb.like(root.get("content").as(String.class), "%" + content + "%");
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
