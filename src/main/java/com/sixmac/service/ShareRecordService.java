package com.sixmac.service;

import com.sixmac.entity.ShareRecord;
import com.sixmac.service.common.ICommonService;

/**
 * Created by Administrator on 2016/5/5 0005 下午 5:50.
 */
public interface ShareRecordService extends ICommonService<ShareRecord> {

    // 增加分享记录，同时增加该用户的积分数
    public void addShareRecord(Integer userId, Integer objectId, Integer objectType, Integer score);
}