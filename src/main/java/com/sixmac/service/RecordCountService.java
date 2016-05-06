package com.sixmac.service;

import com.sixmac.entity.RecordCount;
import com.sixmac.service.common.ICommonService;

/**
 * Created by Administrator on 2016/5/6 0006 上午 11:50.
 */
public interface RecordCountService extends ICommonService<RecordCount> {

    // 增加今日访问人数和在线人数
    public void addCount(Integer visitCount, Integer onlineCount);

    // 清空今日访问人数和在线人数
    public void clearCount();
}