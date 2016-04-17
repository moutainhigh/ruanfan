package com.sixmac.dao;

import com.sixmac.entity.Custompackages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008 下午 3:08.
 */
public interface CustompackagesDao extends JpaRepository<Custompackages, Integer> {

    @Query("select a from Custompackages a where a.custominfo.id = ?1")
    public List<Custompackages> findListByCustominfoId(Integer custominfoId);

    @Query("select a from Custompackages a where a.custominfo.id = ?1 and a.area.id = ?2")
    public List<Custompackages> findListByCustominfoId(Integer custominfoId, Integer areaId);
}