package com.sixmac.service;

import com.sixmac.entity.Roles;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/4/2 0002 21:37.
 */
public interface RolesService extends ICommonService<Roles> {

    public Page<Roles> page(Integer pageNum, Integer pageSize);

    public void deleteById(HttpServletRequest request, Integer id);
}