package com.sixmac.dao;

import com.sixmac.entity.Virtuals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:48.
 */
public interface VirtualsDao extends JpaRepository<Virtuals, Integer>, JpaSpecificationExecutor<Virtuals> {

    @Query("select a from Virtuals a where a.style.id = ?1")
    public List<Virtuals> iFindListByStyleId(Integer styleId);

    @Query("select a from Virtuals a where a.afflatusId = ?1")
    public Virtuals iFindOneByAfflatusId(Integer afflatusId);
}