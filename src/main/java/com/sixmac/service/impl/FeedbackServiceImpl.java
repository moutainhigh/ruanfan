package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.FeedbackDao;
import com.sixmac.entity.Feedback;
import com.sixmac.service.FeedbackService;
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
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/3/8 0008 下午 2:22.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    public List<Feedback> findAll() {
        return feedbackDao.findAll();
    }

    @Override
    public Page<Feedback> find(int pageNum, int pageSize) {
        return feedbackDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Feedback> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Feedback getById(int id) {
        return feedbackDao.findOne(id);
    }

    @Override
    public Feedback deleteById(int id) {
        Feedback feedback = getById(id);
        feedbackDao.delete(feedback);
        return feedback;
    }

    @Override
    public Feedback create(Feedback feedback) {
        return feedbackDao.save(feedback);
    }

    @Override
    public Feedback update(Feedback feedback) {
        return feedbackDao.save(feedback);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Feedback> page(String nickName, String mobile, String mail, int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Feedback> page = feedbackDao.findAll(new Specification<Feedback>() {
            @Override
            public Predicate toPredicate(Root<Feedback> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(nickName)) {
                    Predicate pre = cb.like(root.get("nickName").as(String.class), "%" + nickName + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(mobile)) {
                    Predicate pre = cb.like(root.get("mobile").as(String.class), "%" + mobile + "%");
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