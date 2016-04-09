package com.sixmac.dao;

import com.sixmac.entity.Reserve;
import com.sixmac.entity.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:47.
 */
public interface RolesDao extends JpaRepository<Roles, Integer>, JpaSpecificationExecutor<Reserve> {

    @Query("select a from Roles a where a.id <> 1")
    public Page<Roles> page(Pageable pageable);
}