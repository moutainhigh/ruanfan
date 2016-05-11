package com.sixmac.service.impl;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.dao.CommentDao;
import com.sixmac.dao.ReplysDao;
import com.sixmac.dao.ReportDao;
import com.sixmac.entity.Comment;
import com.sixmac.entity.Replys;
import com.sixmac.entity.Report;
import com.sixmac.service.CommentService;
import com.sixmac.service.OperatisService;
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
 * Created by Administrator on 2016/3/7 0007 下午 12:02.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ReplysDao replysDao;

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    @Override
    public Page<Comment> find(int pageNum, int pageSize) {
        return commentDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Comment> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Comment getById(int id) {
        return commentDao.findOne(id);
    }

    @Override
    @Transactional
    public Comment deleteById(int id) {
        // 先删除评论回复，再删除与该评论相关的举报信息，然后删除评论消息
        List<Replys> replysList = replysDao.findListByCommentId(id);
        for (Replys replys : replysList) {
            replysDao.delete(replys.getId());
        }

        List<Report> reportList = reportDao.findListByCommentId(id);
        for (Report report : reportList) {
            reportDao.delete(report.getId());
        }

        Comment comment = getById(id);
        commentDao.delete(comment);
        return comment;
    }

    @Override
    public Comment create(Comment comment) {
        return commentDao.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return commentDao.save(comment);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Comment> iFindList(Integer objectId, Integer objectType) {
        List<Replys> replysList = null;
        List<Comment> list = commentDao.iFindList(objectId, objectType);

        for (Comment comment : list) {
            comment.setUserId(comment.getUser().getId());
            comment.setUserName(comment.getUser().getNickName());
            comment.setUserHead(comment.getUser().getHeadPath());

            replysList = replysDao.findListByCommentId(comment.getId());

            comment.setReplysList(replysList);
        }

        return list;
    }

    @Override
    public List<Comment> iFindList(Integer userId, Integer objectId, Integer objectType) {
        List<Comment> list = commentDao.iFindList(userId, objectId, objectType);

        for (Comment comment : list) {
            comment.setUserId(comment.getUser().getId());
            comment.setUserName(comment.getUser().getNickName());
            comment.setUserHead(comment.getUser().getHeadPath());
        }

        return list;
    }

    @Override
    public Page<Comment> page(final String mobile, final Integer objectType, int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Comment> page = commentDao.findAll(new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(mobile)) {
                    Predicate pre = cb.like(root.get("user").get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }
                if (objectType != null) {
                    Predicate pre = cb.equal(root.get("objectType").as(Integer.class), objectType);
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
    public Page<Comment> page(Integer objectId, Integer objectType, int pageNum, int pageSize) {

        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");
        Page<Comment> page = commentDao.pageByObjectIdIdAndObjectType(objectId, objectType, pageRequest);

        return page;
    }

    @Override
    public List<Comment> findListNew() {
        return commentDao.findListNew(CommonController.getOldDate());
    }

    @Override
    public Page<Comment> page(Integer userId, int pageNum, int pageSize) {
        // List<Replys> replysList = null;
        Page<Comment> page = commentDao.iPage(userId, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

        for (Comment comment : page.getContent()) {
            comment.setUserId(comment.getUser().getId());
            comment.setUserName(comment.getUser().getNickName());
            comment.setUserHead(comment.getUser().getHeadPath());

            /*replysList = replysDao.findListByCommentId(comment.getId());

            comment.setReplysList(replysList);*/
        }

        return page;
    }

    @Override
    @Transactional
    public void deleteById(HttpServletRequest request, Integer id) {
        // 先删除评论回复，再删除与该评论相关的举报信息，然后删除评论消息
        List<Replys> replysList = replysDao.findListByCommentId(id);
        for (Replys replys : replysList) {
            replysDao.delete(replys.getId());
        }

        List<Report> reportList = reportDao.findListByCommentId(id);
        for (Report report : reportList) {
            reportDao.delete(report.getId());
        }

        Comment comment = getById(id);

        operatisService.addOperatisInfo(request, "删除用户 " + (null == comment.getUser().getNickName() ? comment.getUser().getMobile() : comment.getUser().getNickName()) + " 的评论");

        commentDao.delete(comment);
    }
}