package com.sixmac.service.impl;


import com.sixmac.dao.ReportDao;
import com.sixmac.entity.Report;
import com.sixmac.service.ReportService;
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
 * Created by Administrator on 2016/3/29 0029.
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public Page<Report> page(Integer userId, Integer sourceId, Integer type, int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Report> page = reportDao.findAll(new Specification<Report>() {

            @Override
            public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (userId != null) {
                    Predicate pre = cb.like(root.get("userId").as(String.class), "%" + userId + "%");
                    predicateList.add(pre);
                }
                if (sourceId != null) {
                    Predicate pre = cb.like(root.get("sourceId").as(String.class), "%" + sourceId + "%");
                    predicateList.add(pre);
                }
                if (type != null) {
                    Predicate pre = cb.like(root.get("type").as(String.class), "%" + type + "%");
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
    public List<Report> findAll() {
        return null;
    }

    @Override
    public Page<Report> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<Report> find(int pageNum) {
        return null;
    }

    @Override
    public Report getById(int id) {
        return reportDao.findOne(id);
    }

    @Override
    public Report deleteById(int id) {
        return null;
    }

    @Override
    public Report create(Report report) {
        return null;
    }

    @Override
    public Report update(Report report) {
        return reportDao.save(report);
    }

    @Override
    public void deleteAll(int[] ids) {

    }

}
