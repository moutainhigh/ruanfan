package com.sixmac.dao;

import com.sixmac.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:45.
 */
public interface ProductsDao extends JpaRepository<Products, Integer>, JpaSpecificationExecutor<Products> {

    @Query("select a from Products a where a.isCheck = 1 and a.isAdd = 0 and a.status = 0 and a.type = ?1 order by a.id desc")
    public List<Products> iFindList(Integer type);
}