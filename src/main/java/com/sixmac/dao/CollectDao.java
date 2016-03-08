package com.sixmac.dao;

import com.sixmac.entity.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 上午 11:43.
 */
public interface CollectDao extends JpaRepository<Collect, Integer>, JpaSpecificationExecutor<Collect> {

    @Query("select a from Collect a where a.user.id = ?1 and a.objectId = ?2 and a.objectType = ?3")
    public Collect iFindOne(Integer userId, Integer objectId, Integer objectType);
}