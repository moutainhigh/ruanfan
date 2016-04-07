package com.sixmac.service;

import com.sixmac.entity.Rolemodules;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/4/2 0002 21:14.
 */
public interface RoleModulesService extends ICommonService<Rolemodules> {

    // 根据角色id查询角色对应的权限列表
    public List<Rolemodules> findListByRoleId(Integer roleId);

    // 根据角色id删除角色对应的权限
    public void deleteByRoleId(Integer roleId);
}