package com.sixmac.service;

import com.sixmac.entity.City;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 下午 5:28.
 */
public interface CityService extends ICommonService<City> {

    // 根据省份id查询城市列表
    public List<City> findListByProvinceId(Integer provinceId);
}