package com.sixmac.dao;

import com.sixmac.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:43.
 */
public interface MessageDao extends JpaRepository<Message, Integer> {

    @Query("select a from Message a where a.type = ?1 or a.type = ?2")
    public List<Message> findListByType(Integer type, Integer defaultId);
}