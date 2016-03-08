package com.sixmac.dao;

import com.sixmac.entity.Shopcar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 4:39.
 */
public interface ShopcarDao extends JpaRepository<Shopcar, Integer> {

    @Query("select a from Shopcar a where a.user.id = ?1")
    public List<Shopcar> findListByUserId(Integer userId);
}