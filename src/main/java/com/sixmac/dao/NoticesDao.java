package com.sixmac.dao;

import com.sixmac.entity.Notices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:44.
 */
public interface NoticesDao extends JpaRepository<Notices, Integer>, JpaSpecificationExecutor<Notices> {

    @Query("select a from Notices a where a.sourceId = ?1 and a.sourceType = ?2")
    public Page<Notices> pageBySourceIdAndSourceType(Integer sourceId, Integer sourceType, Pageable pageable);
}