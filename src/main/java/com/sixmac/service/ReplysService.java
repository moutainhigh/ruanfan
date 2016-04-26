package com.sixmac.service;

import com.sixmac.entity.Replys;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/4/14 0014 下午 6:24.
 */
public interface ReplysService extends ICommonService<Replys> {

    // 根据用户id查询回复列表（分页）
    public Page<Replys> iPageByUserId(Integer userId, Integer pageNum, Integer pageSize);
}