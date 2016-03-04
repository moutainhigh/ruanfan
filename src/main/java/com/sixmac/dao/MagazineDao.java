package com.sixmac.dao;

import com.sixmac.entity.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:43.
 */
public interface MagazineDao extends JpaRepository<Magazine, Integer> {

}