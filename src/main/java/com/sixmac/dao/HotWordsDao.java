package com.sixmac.dao;

import com.sixmac.entity.HotWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27 0027 下午 1:47.
 */
public interface HotWordsDao extends JpaRepository<HotWords, Integer> {

    @Query("select a from HotWords a where a.words = ?1")
    public HotWords findOneByWord(String words);

    @Query("select a from HotWords a order by a.count desc")
    public List<HotWords> findListDesc();
}