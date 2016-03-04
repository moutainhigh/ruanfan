package com.sixmac.dao;

import com.sixmac.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:42.
 */
public interface CityDao extends JpaRepository<City, Integer> {

}