package com.sixmac.dao;

import com.sixmac.entity.Brand;
import com.sixmac.entity.Packages;
import com.sixmac.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:41.
 */
public interface BrandDao extends JpaRepository<Brand, Integer> {

    @Query("select a from Packages a where a.brand.id = ?1")
    public List<Packages> findPackageListByBrandId(Integer brandId);
}