package com.sixmac.service;

import com.sixmac.entity.Operatis;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public interface OperatisService extends ICommonService<Operatis> {

    public Page<Operatis> findPage(Operatis operatis, int pageNum, int pageSize);

    public Page<Operatis> page(String name, String roleName, int pageNum, int pageSize);

}
