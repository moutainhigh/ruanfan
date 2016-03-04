package com.sixmac.dao;

import com.sixmac.entity.Notices;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:44.
 */
public interface NoticesDao extends JpaRepository<Notices, Integer> {

}