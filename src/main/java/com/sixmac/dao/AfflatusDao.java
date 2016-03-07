package com.sixmac.dao;

import com.sixmac.entity.Afflatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:40.
 */
public interface AfflatusDao extends JpaRepository<Afflatus, Integer>, JpaSpecificationExecutor<Afflatus> {

    @Query("select a from Afflatus a where a.type = ?1 and a.style.id = ?2 and a.area.id = ?3")
    public List<Afflatus> iFindLoveList(Integer type, Integer styleId, Integer areaId);
}