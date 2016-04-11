package com.sixmac.service;

import com.sixmac.entity.Modules;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/4/7 0007 下午 5:46.
 */
public interface ModulesService extends ICommonService<Modules> {

    // 根据权限模块父级id查询所对应的权限列表
    public List<Modules> findListByParentId(Integer parentId);
}