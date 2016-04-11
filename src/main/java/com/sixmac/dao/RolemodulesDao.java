package com.sixmac.dao;

import com.sixmac.entity.Rolemodules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:47.
 */
public interface RolemodulesDao extends JpaRepository<Rolemodules, Integer> {

    @Query("select a from Rolemodules a where a.role.id = ?1")
    public List<Rolemodules> findListByRoleId(Integer roleId);

    @Query("select a from Rolemodules a where a.role.id = ?1 group by a.module.parentId")
    public List<Rolemodules> findListByRoleIdGroupByParentId(Integer roleId);
}