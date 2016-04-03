package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.CommentDao;
import com.sixmac.entity.Comment;
import com.sixmac.service.CommentService;
import com.sixmac.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 12:02.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

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
        List<Comment> list = commentDao.iFindList(objectId, objectType);

        for (Comment comment : list) {
            comment.getUser().setHeadPath(PathUtils.getRemotePath() + comment.getUser().getHeadPath());
            comment.setUserId(comment.getUser().getId());
            comment.setUserName(comment.getUser().getNickName());
            comment.setUserHead(comment.getUser().getHeadPath());
        }

        return list;
    }

    @Override
    public List<Comment> iFindListByUserId(Integer userId, Integer objectId, Integer objectType) {
        List<Comment> list = commentDao.iFindList(userId, objectId, objectType);

        for (Comment comment : list) {
            comment.getUser().setHeadPath(PathUtils.getRemotePath() + comment.getUser().getHeadPath());
            comment.setUserId(comment.getUser().getId());
            comment.setUserName(comment.getUser().getNickName());
            comment.setUserHead(comment.getUser().getHeadPath());
        }

        return list;
    }
}