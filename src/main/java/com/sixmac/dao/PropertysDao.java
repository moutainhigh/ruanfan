package com.sixmac.dao;

import com.sixmac.entity.Propertys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:46.
 */
public interface PropertysDao extends JpaRepository<Propertys, Integer>, JpaSpecificationExecutor<Propertys> {

    @Query("select a from Propertys a where a.parentId = ?1")
    public List<Propertys> iPageByParentId(Integer parentId);

    @Query("select a from Propertys a where a.parentId = ?1 order by a.showTurn desc")
    public List<Propertys> findListByShowTurn(Integer parentId);

    @Query("select a from Propertys a where a.parentId = ?1 and a.showTurn = ?2")
    public Propertys getByShowTurn(Integer parentId, Integer turn);
}