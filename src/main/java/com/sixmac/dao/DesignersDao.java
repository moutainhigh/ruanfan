package com.sixmac.dao;

import com.sixmac.entity.Designers;
import com.sixmac.entity.Messageplus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:42.
 */
public interface DesignersDao extends JpaRepository<Designers, Integer>, JpaSpecificationExecutor<Designers> {

    @Query("select a from Designers a where a.mobile = ?1")
    public List<Designers> findListByMobile(String mobile);

    @Query("select a from Messageplus a where a.sourceId = ?1 and a.type = 1 order by a.id desc")
    public List<Messageplus> findListByDesignerId(Integer designerId);
}