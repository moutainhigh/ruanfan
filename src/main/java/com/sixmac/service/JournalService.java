package com.sixmac.service;

import com.sixmac.entity.Journal;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/3/8 0008 下午 2:34.
 */
public interface JournalService extends ICommonService<Journal> {

    // 日志列表
    public Page<Journal> iPage(Integer userId, Integer pageNum, Integer pageSize);
}