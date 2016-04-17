package com.sixmac.dao;

import com.sixmac.entity.Reserve;
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
public interface ReserveDao extends JpaRepository<Reserve, Integer>, JpaSpecificationExecutor<Reserve> {

    @Query("select a from Reserve a where a.objectId = ?1 and a.type = ?2 order by a.id desc")
    public List<Reserve> iFindListByDesignerId(Integer objectId, Integer type);

    @Query("select a from Reserve a where a.reseTime > ?1")
    public List<Reserve> findListNew(Date oldDate);

    @Query("select a from Reserve a where a.objectId = ?1 ")
    public Page<Reserve> iFindPageByDesignerId(Integer objectId, Pageable pageable);

    @Query("select a from Reserve a where a.user.id = ?1 and a.objectId = ?2 and a.type = ?3")
    public Reserve iFindOneByParams(Integer userId, Integer objectId, Integer type);
}

