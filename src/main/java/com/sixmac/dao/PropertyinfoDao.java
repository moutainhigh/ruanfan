package com.sixmac.dao;

import com.sixmac.entity.Propertyinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/18 0018 上午 10:25.
 */
public interface PropertyinfoDao extends JpaRepository<Propertyinfo, Integer> {

    @Query("select a from Propertyinfo a where a.property.id = ?1")
    public List<Propertyinfo> iFindListByPropertyId(Integer propertyId);
}