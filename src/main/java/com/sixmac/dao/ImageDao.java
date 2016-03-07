package com.sixmac.dao;

import com.sixmac.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 涂奕恒 on 2014/12/4 16:16.
 */
public interface ImageDao extends JpaRepository<Image, Integer> {

    @Query("select a from Image a where a.objectId = ?1 and a.objectType = ?2")
    public List<Image> iFindList(Integer objectId, Integer objectType);
}