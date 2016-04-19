package com.sixmac.service;

import com.sixmac.entity.Comment;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 12:02.
 */
public interface CommentService extends ICommonService<Comment> {

    // 根据评论对象id和评论对象类型查询列表
    public List<Comment> iFindList(Integer objectId, Integer objectType);

    // 根据用户id、评论对象id和评论对象类型查询列表
    public List<Comment> iFindList(Integer userId, Integer objectId, Integer objectType);

    // 根据用户账号、评论对象类型查询列表
    public Page<Comment> page(String mobile, Integer objectType, int pageNum, int pageSize);

    // 根据评论对象、评论对象类型查询列表
    public Page<Comment> page(Integer objectId, Integer objectType, int pageNum, int pageSize);

    //查询新增评论
    public List<Comment> findListNew();

    // 根据用户id查询列表
    public Page<Comment> page(Integer userId, int pageNum, int pageSize);
}