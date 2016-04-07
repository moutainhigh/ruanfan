package com.sixmac.dao;

import com.sixmac.entity.Packageproducts;
import com.sixmac.entity.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:45.
 */
public interface PackagesDao extends JpaRepository<Packages, Integer>, JpaSpecificationExecutor<Packages> {

    @Query("select a from Packageproducts a where a.packages.id = ?1")
    public List<Packageproducts> findListByPackageId(Integer packageId);

}