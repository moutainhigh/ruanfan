package com.sixmac.dao;

import com.sixmac.entity.Custom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008 下午 3:07.
 */
public interface CustomDao extends JpaRepository<Custom, Integer> {

    @Query("select a from Custom a where a.name = ?1")
    public Custom findOneByParams(String name);
}