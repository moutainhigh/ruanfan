package com.sixmac.dao;

import com.sixmac.entity.Designers;
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
}