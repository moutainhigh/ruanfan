package com.sixmac.service;

import com.sixmac.entity.Notices;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/3/18 0018 下午 5:55.
 */
public interface NoticesService extends ICommonService<Notices> {

    public Page<Notices> page(Integer sourceId, Integer sourceType, int pagenum, int pagesize);
}