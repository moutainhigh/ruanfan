package com.sixmac.dao;

import com.sixmac.entity.Custominfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008 下午 3:08.
 */
public interface CustominfoDao extends JpaRepository<Custominfo, Integer> {

    @Query("select a from Custominfo a where a.custom.id = ?1")
    public List<Custominfo> findListByCustomId(Integer customId);
}