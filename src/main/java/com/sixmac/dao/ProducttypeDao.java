package com.sixmac.dao;

import com.sixmac.entity.Products;
import com.sixmac.entity.Producttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:46.
 */
public interface ProducttypeDao extends JpaRepository<Producttype, Integer> {

    @Query("select a from Products a where a.sort.id = ?1")
    public List<Products> findProductListByProductTypeId(Integer productTypeId);
}