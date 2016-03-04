package com.sixmac.dao;

import com.sixmac.entity.Propertys;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:46.
 */
public interface PropertysDao extends JpaRepository<Propertys, Integer> {

}