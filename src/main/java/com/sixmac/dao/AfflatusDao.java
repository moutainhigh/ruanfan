package com.sixmac.dao;

import com.sixmac.entity.Afflatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:40.
 */
public interface AfflatusDao extends JpaRepository<Afflatus, Integer>, JpaSpecificationExecutor<Afflatus> {

}