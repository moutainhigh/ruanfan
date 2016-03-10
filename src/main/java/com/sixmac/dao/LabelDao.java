package com.sixmac.dao;

import com.sixmac.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/10 0010 下午 3:09.
 */
public interface LabelDao extends JpaRepository<Label, Integer> {

    @Query("select a from Label a where a.objectId = ?1 and a.objectType = ?2")
    public List<Label> findListByParams(Integer objectId, Integer objectType);

    @Query("select a from Label a where a.objectId = ?1 and a.labelId = ?2")
    public Label findOneByObjectIdAndLabelId(Integer objectId, String labelId);

    @Query("select a from Label a where a.labelId = ?1")
    public Label findOneByLabelId(String labelId);
}