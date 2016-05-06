package com.sixmac.dao;

import com.sixmac.entity.RecordCount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/5/6 0006 上午 11:50.
 */
public interface RecordCountDao extends JpaRepository<RecordCount, Integer> {

}