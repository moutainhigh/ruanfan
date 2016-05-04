package com.sixmac.service;

import com.sixmac.entity.Notices;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/3/18 0018 下午 5:55.
 */
public interface NoticesService extends ICommonService<Notices> {

    // 根据所属目标id和所属目标类型查询系统消息列表（分页）
    public Page<Notices> page(Integer sourceId, Integer sourceType, Integer pageNum, Integer pageSize);
}