package com.sixmac.dao;

import com.sixmac.entity.Replys;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:46.
 */
public interface ReplysDao extends JpaRepository<Replys, Integer> {

    @Query("select a from Replys a where a.comment.id = ?1")
    public List<Replys> findListByCommentId(Integer commentId);

    @Query("select a from Replys a where a.comment.user.id = ?1")
    public Page<Replys> pageByUserId(Integer userId, Pageable pageable);
}