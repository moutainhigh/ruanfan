package com.sixmac.service;

import com.sixmac.entity.Propertyinfo;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/3/18 0018 上午 10:25.
 */
public interface PropertyinfoService extends ICommonService<Propertyinfo> {

    // 根据楼盘id清除所有相关联的户型信息
    public void clearInfoByPropertyId(Integer propertyId);

    // 根据楼盘id查询对应的户型信息列表
    public List<Propertyinfo> findListByPropertyId(Integer propertyId);
}