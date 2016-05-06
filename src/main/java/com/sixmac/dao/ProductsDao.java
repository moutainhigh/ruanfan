package com.sixmac.dao;

import com.sixmac.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:45.
 */
public interface ProductsDao extends JpaRepository<Products, Integer>, JpaSpecificationExecutor<Products> {

    @Query("select a from Products a where a.isCheck = 1 and a.isAdd = 0 and a.status = 0 and a.type = ?1 order by a.id desc")
    public List<Products> iFindList(Integer type);

    @Query("select a from Products a where a.isCheck = 1 and a.isAdd = 0 and a.status = 0 and a.type = ?1 and a.sort.id = ?2 and a.id <> ?3")
    public List<Products> iFindList(Integer type, Integer sortId, Integer productId);

    @Query("select a from Products a where a.status = 0")
    public List<Products> findList();

    @Query("select a from Products a where a.isCheck = 0")
    public List<Products> findListCheck();

    @Query("select a from Products a where a.isAdd = 0")
    public List<Products> findListAdd();

    @Query("select a from Products a where a.isAdd = 1")
    public List<Products> findListDown();

    @Query("select a from Products a where a.createTime > ?1")
    public List<Products> findListNew(Date yesterday);

    @Query("select a from Products a where a.status = 0 and a.isAdd = 0 and a.isCheck = 1")
    public List<Products> findListWithSuccess();

    @Query("select a from Products a where a.status = 0 and a.isAdd = 0 and a.isCheck = 1 and a.merchant.id = ?1")
    public List<Products> findListWithSuccess(Integer merchantId);
}