package com.sixmac.dao;

import com.sixmac.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:43.
 */
public interface MerchantsDao extends JpaRepository<Merchants, Integer> {

    @Query("select a from Merchants a where a.style.id = ?1")
    public List<Merchants> iFindListByStyleId(Integer styleId);
}