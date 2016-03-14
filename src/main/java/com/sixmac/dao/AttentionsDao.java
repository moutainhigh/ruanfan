package com.sixmac.dao;

import com.sixmac.entity.Attentions;
import com.sixmac.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:41.
 */
public interface AttentionsDao extends JpaRepository<Attentions, Integer> {

    @Query("select a from Attentions a where a.user.id = ?1 and a.objectId = ?2 and a.objectType = ?3")
    public Attentions iFindOne(Integer userId, Integer objectId, Integer objectType);

    @Query("select a from Attentions a where a.objectId = ?1 and a.objectType = ?2 order by a.id desc")
    public List<Attentions> iFindList(Integer objectId, Integer objectType);
}