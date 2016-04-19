package com.sixmac.dao;

import com.sixmac.entity.Privateletter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/4/19 0019 下午 4:49.
 */
public interface PrivateletterDao extends JpaRepository<Privateletter, Integer> {

    @Query("select a from Privateletter a where a.receiveUser.id = ?1")
    public Page<Privateletter> pageByReceiveUser(Integer userId, Pageable pageable);
}