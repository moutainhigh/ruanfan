package com.sixmac.dao;

import com.sixmac.entity.ShareRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/5/5 0005 下午 5:50.
 */
public interface ShareRecordDao extends JpaRepository<ShareRecord, Integer> {

    @Query("select a from ShareRecord a where a.user.id = ?1 and a.objectId = ?2 and a.objectType = ?3")
    public ShareRecord findOneByParams(Integer userId, Integer objectId, Integer objectType);
}