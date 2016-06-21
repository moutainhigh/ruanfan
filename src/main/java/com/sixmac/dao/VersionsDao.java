package com.sixmac.dao;

import com.sixmac.entity.Versions;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/6/21 0021 上午 11:43.
 */
public interface VersionsDao extends JpaRepository<Versions, Integer> {

}