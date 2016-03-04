package com.sixmac.service;

import com.sixmac.entity.Afflatus;
import com.sixmac.service.common.ICommonService;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/3/4 0004 下午 5:10.
 */
public interface AfflatusService extends ICommonService<Afflatus> {

    public Page<Afflatus> iPage(Integer type, Integer styleId, Integer areaId, Integer pageNum, Integer pageSize);
}