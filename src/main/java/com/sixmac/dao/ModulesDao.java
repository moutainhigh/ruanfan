package com.sixmac.dao;

import com.sixmac.entity.Modules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:44.
 */
public interface ModulesDao extends JpaRepository<Modules, Integer> {

    @Query("select a from Modules a where a.parentId = ?1")
    public List<Modules> findListByParentId(Integer parentId);
}