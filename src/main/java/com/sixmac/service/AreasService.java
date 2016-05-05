package com.sixmac.service;

import com.sixmac.entity.Areas;
import com.sixmac.service.common.ICommonService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/3/9 0009 下午 3:29.
 */
public interface AreasService extends ICommonService<Areas> {

    // 根据区域id查询对应的灵感图信息集合
    public Integer findListByAreaId(Integer areaId);

    public void deleteById(HttpServletRequest request, Integer id);
}