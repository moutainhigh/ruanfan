package com.sixmac.service.impl;


import com.sixmac.core.Constant;
import com.sixmac.dao.ReportDao;
import com.sixmac.entity.Report;
import com.sixmac.service.OperatisService;
import com.sixmac.service.ReportService;
import org.apache.commons.lang.StringUtils;
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
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public Page<Report> page(final String userName, final String sourceName, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Report> page = reportDao.findAll(new Specification<Report>() {

            @Override
            public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (StringUtils.isNotEmpty(userName)) {
                    Predicate pre = cb.like(root.get("user").get("nickName").as(String.class), "%" + userName + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotEmpty(sourceName)) {
                    Predicate pre = cb.like(root.get("comment").get("user").get("nickName").as(String.class), "%" + sourceName + "%");
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
    @Transactional
    public void deleteById(HttpServletRequest request, Integer id) {
        Report report = getById(id);

        operatisService.addOperatisInfo(request, "删除用户 " + report.getUser().getNickName() + " 的举报");

        reportDao.delete(report);
    }

    @Override
    @Transactional
    public void deleteAll(HttpServletRequest request, int[] ids) {
        for (int id : ids) {
            deleteById(request, id);
        }
    }

    @Override
    public List<Report> findAll() {
        return reportDao.findAll();
    }

    @Override
    public Page<Report> find(int pageNum, int pageSize) {
        return reportDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Report> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Report getById(int id) {
        return reportDao.findOne(id);
    }

    @Override
    public Report deleteById(int id) {
        Report report = getById(id);
        reportDao.delete(report);
        return report;
    }

    @Override
    public Report create(Report report) {
        return reportDao.save(report);
    }

    @Override
    public Report update(Report report) {
        return reportDao.save(report);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

}
