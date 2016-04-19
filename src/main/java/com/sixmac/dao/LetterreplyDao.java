package com.sixmac.dao;

import com.sixmac.entity.Letterreply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/4/19 0019 下午 6:30.
 */
public interface LetterreplyDao extends JpaRepository<Letterreply, Integer> {

    @Query("select a from Letterreply a where a.users.id = ?1")
    public Page<Letterreply> pageByUserId(Integer userId, Pageable pageable);
}