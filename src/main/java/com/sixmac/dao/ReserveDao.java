package com.sixmac.dao;

import com.sixmac.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:47.
 */
public interface ReserveDao extends JpaRepository<Reserve, Integer>, JpaSpecificationExecutor<Reserve> {

    @Query("select a from Reserve a where a.designer.id = ?1 order by a.id desc")
    public List<Reserve> iFindListByDesignerId(Integer designerId);

    //@Query("select a from Reserve a where a.name like ?1 and a.mobile like ?2 and a.email like ?3 and a.designer.nickName = ?4")
    //public Page<Reserve> pageTable(String name, String mobile, String email, Integer styleId, Pageable pageable);
}