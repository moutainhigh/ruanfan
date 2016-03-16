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

    @Query("select a from Afflatus a where a.id <> ?1 and a.type = ?2 and a.style.id = ?3 and a.area.id = ?4")
    public List<Afflatus> iFindLoveList(Integer afflatusId, Integer type, Integer styleId, Integer areaId);

    @Query("select a from Afflatus a where a.style.id = ?1")
    public List<Afflatus> iFindListByStyleId(Integer styleId);
}