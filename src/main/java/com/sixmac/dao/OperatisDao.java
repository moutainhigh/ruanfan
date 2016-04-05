package com.sixmac.dao;

import com.sixmac.entity.Operatis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:44.
 */
public interface OperatisDao extends JpaRepository<Operatis, Integer>, JpaSpecificationExecutor<Operatis> {

}