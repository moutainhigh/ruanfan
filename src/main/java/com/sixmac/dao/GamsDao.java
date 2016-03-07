package com.sixmac.dao;

import com.sixmac.entity.Gams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:43.
 */
public interface GamsDao extends JpaRepository<Gams, Integer> {

    @Query("select a from Gams a where a.objectId = ?1 and a.objectType = ?2 and a.type = ?3 order by a.id asc")
    public List<Gams> iFindListASC(Integer objectId, Integer objectType, Integer type);

    @Query("select a from Gams a where a.objectId = ?1 and a.objectType = ?2 and a.type = ?3 order by a.id desc")
    public List<Gams> iFindListDESC(Integer objectId, Integer objectType, Integer type);
}