package com.sixmac.dao;

import com.sixmac.entity.Spikes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:47.
 */
public interface SpikesDao extends JpaRepository<Spikes, Integer>, JpaSpecificationExecutor<Spikes> {

    @Query("select a from Spikes a where a.name like ?1 and a.startTime > ?2")
    public Page<Spikes> findAllNoStart(String name, Date nowTime, Pageable pageable);

    @Query("select a from Spikes a where a.name like ?1 and a.startTime <= ?2 and a.endTime >= ?2")
    public Page<Spikes> findAllWorking(String name, Date nowTime, Pageable pageable);

    @Query("select a from Spikes a where a.name like ?1 and a.endTime < ?2")
    public Page<Spikes> findAllFinish(String name, Date nowTime, Pageable pageable);

    @Query("select a from Spikes a where a.name like ?1")
    public Page<Spikes> findAllNoThing(String name, Pageable pageable);

    @Query("select a from Spikes a where a.endTime >= ?1")
    public Page<Spikes> findAllWorking(Date nowTime, Pageable pageable);

}