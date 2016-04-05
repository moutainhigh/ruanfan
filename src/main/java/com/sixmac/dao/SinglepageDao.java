package com.sixmac.dao;

import com.sixmac.entity.Singlepage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public interface SinglepageDao extends JpaRepository<Singlepage, Integer>, JpaSpecificationExecutor<Singlepage> {

}
