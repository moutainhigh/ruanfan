package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.FeedbackDao;
import com.sixmac.entity.Feedback;
import com.sixmac.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}