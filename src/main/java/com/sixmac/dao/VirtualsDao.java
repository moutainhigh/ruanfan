package com.sixmac.dao;

import com.sixmac.entity.Virtuals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:48.
 */
public interface VirtualsDao extends JpaRepository<Virtuals, Integer>, JpaSpecificationExecutor<Virtuals> {

}