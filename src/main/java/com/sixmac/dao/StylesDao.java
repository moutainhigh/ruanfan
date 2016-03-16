package com.sixmac.dao;

import com.sixmac.entity.Products;
import com.sixmac.entity.Styles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:47.
 */
public interface StylesDao extends JpaRepository<Styles, Integer> {

    @Query("select a from Products a where a.sort.id = ?1")
    public List<Products> findProductListByStyleId(Integer styleId);
}