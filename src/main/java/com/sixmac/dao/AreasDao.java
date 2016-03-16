package com.sixmac.dao;

import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Areas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:41.
 */
public interface AreasDao extends JpaRepository<Areas, Integer> {

    @Query("select a from Afflatus a where a.area.id = ?1")
    public List<Afflatus> iFindListByAreaId(Integer areaId);
}