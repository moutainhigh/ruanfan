package com.sixmac.dao;

import com.sixmac.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:43.
 */
public interface JournalDao extends JpaRepository<Journal, Integer>, JpaSpecificationExecutor<Journal> {

    @Query("select a from Journal a where a.user.id = ?1")
    public List<Journal> iFindListByUserId(Integer userId);
}