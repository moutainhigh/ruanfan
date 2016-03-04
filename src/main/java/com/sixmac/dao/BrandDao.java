package com.sixmac.dao;

import com.sixmac.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:41.
 */
public interface BrandDao extends JpaRepository<Brand, Integer> {

}