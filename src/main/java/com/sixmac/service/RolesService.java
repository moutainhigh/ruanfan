package com.sixmac.service;

import com.sixmac.entity.Roles;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/4/2 0002 21:37.
 */
public interface RolesService extends ICommonService<Roles> {

    public Page<Roles> page(Integer pageNum, Integer pageSize);
}