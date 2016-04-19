package com.sixmac.dao;

import com.sixmac.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:42.
 */
public interface CommentDao extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {

    @Query("select a from Comment a where a.objectId = ?1 and a.objectType = ?2")
    public List<Comment> iFindList(Integer objectId, Integer objectType);

    @Query("select a from Comment a where a.user.id = ?1 and a.objectId = ?2 and a.objectType = ?3")
    public List<Comment> iFindList(Integer userId, Integer objectId, Integer objectType);

    @Query("select a from Comment a where a.createTime > ?1")
    public List<Comment> findListNew(Date oldDate);

    @Query("select a from Comment a where a.objectId = ?1 and a.objectType = ?2")
    public Page<Comment> pageByObjectIdIdAndObjectType(Integer objectId, Integer objectType, Pageable pageable);

    @Query("select a from Comment a where a.user.id = ?1")
    public Page<Comment> page(Integer userId, Pageable pageable);
}