package com.sixmac.service;

import com.sixmac.entity.Letterreply;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/4/19 0019 下午 6:30.
 */
public interface LetterreplyService extends ICommonService<Letterreply> {

    // 查询回复列表
    public Page<Letterreply> pageByUserId(Integer userId, Integer pageNum, Integer pageSize);
}