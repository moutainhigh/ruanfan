package com.sixmac.service.impl;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.dao.CommentDao;
import com.sixmac.dao.ReplysDao;
import com.sixmac.entity.Comment;
import com.sixmac.entity.Replys;
import com.sixmac.service.CommentService;
import com.sixmac.utils.PathUtils;
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
 * Created by Administrator on 2016/3/7 0007 下午 12:02.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ReplysDao replysDao;

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
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Comment getById(int id) {
        return commentDao.findOne(id);
    }

    @Override
    public Comment deleteById(int id) {
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
            comment.getUser().setHeadPath(PathUtils.getRemotePath() + comment.getUser().getHeadPath());
            comment.setUserId(comment.getUser().getId());
            comment.setUserName(comment.getUser().getNickName());
            comment.setUserHead(comment.getUser().getHeadPath());

            replysList = replysDao.findListByCommentId(comment.getId());
            for (Replys replys : replysList) {
                replys.setUserId(replys.getUser().getId());
                replys.setUserName(replys.getUser().getNickName());
                replys.setUserHead(replys.getUser().getHeadPath());
            }

            comment.setReplysList(replysList);
        }

        return list;
    }

    @Override
    public List<Comment> iFindList(Integer userId, Integer objectId, Integer objectType) {
        List<Comment> list = commentDao.iFindList(userId, objectId, objectType);

        for (Comment comment : list) {
            comment.getUser().setHeadPath(PathUtils.getRemotePath() + comment.getUser().getHeadPath());
            comment.setUserId(comment.getUser().getId());
            comment.setUserName(comment.getUser().getNickName());
            comment.setUserHead(comment.getUser().getHeadPath());
        }

        return list;
    }

    @Override
    public Page<Comment> page(String mobile, Integer objectType, int pageNum, int pageSize) {
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
    public List<Comment> findListNew() {
        return commentDao.findListNew(CommonController.getOldDate());
    }
}