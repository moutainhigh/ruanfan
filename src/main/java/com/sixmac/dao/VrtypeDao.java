package com.sixmac.dao;

import com.sixmac.entity.Virtuals;
import com.sixmac.entity.Vrtype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:48.
 */
public interface VrtypeDao extends JpaRepository<Vrtype, Integer> {

    @Query("select a from Virtuals a where a.type.id = ?1")
    public List<Virtuals> iFindListByTypeId(Integer vrTypeId);
}