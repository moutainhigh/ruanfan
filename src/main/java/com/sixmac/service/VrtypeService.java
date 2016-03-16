package com.sixmac.service;

import com.sixmac.entity.Vrtype;
import com.sixmac.service.common.ICommonService;

/**
 * Created by Administrator on 2016/3/14 0014 上午 11:45.
 */
public interface VrtypeService extends ICommonService<Vrtype> {

    // 根据虚拟体验分类id查询对应的虚拟体验信息集合
    public Integer findListByTypeId(Integer vrTypeId);
}