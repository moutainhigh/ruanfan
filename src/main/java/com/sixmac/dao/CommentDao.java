package com.sixmac.dao;

import com.sixmac.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:42.
 */
public interface CommentDao extends JpaRepository<Comment, Integer> {

    @Query("select a from Comment a where a.objectId = ?1 and a.objectType = ?2")
    public List<Comment> iFindList(Integer objectId, Integer objectType);
}