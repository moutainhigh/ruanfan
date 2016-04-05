package com.sixmac.dao;

import com.sixmac.entity.Sysusers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:48.
 */
public interface SysusersDao extends JpaRepository<Sysusers, Integer>, JpaSpecificationExecutor<Sysusers> {

    @Query("select a from Sysusers a where a.role.id = ?1")
    public List<Sysusers> findListByRoleId(Integer roleId);
}