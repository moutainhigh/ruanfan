package com.sixmac.service;

import com.sixmac.entity.Reserve;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 2:24.
 */
public interface ReserveService extends ICommonService<Reserve> {

    // 根据设计师id查询预约信息集合
    public List<Reserve> iFindListByDesignerId(Integer designerId);
}