package com.sixmac.service;

import com.sixmac.entity.Orders;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public interface IncomService extends ICommonService<Orders> {

    public Page<Orders> page(String orderNum, String mobile, int pageNum, int pageSize);
}
