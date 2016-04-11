package com.sixmac.dao;

import com.sixmac.entity.Packageproducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021 下午 2:28.
 */
public interface PackageproductsDao extends JpaRepository<Packageproducts, Integer> {

    @Query("select a from Packageproducts a where a.packages.id = ?1 and a.type = ?2")
    public List<Packageproducts> findListByPackageId(Integer packageId, Integer type);
}