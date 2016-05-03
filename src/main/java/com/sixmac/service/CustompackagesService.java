package com.sixmac.service;

import com.sixmac.entity.Custompackages;
import com.sixmac.entity.vo.PackageVo;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008 下午 3:09.
 */
public interface CustompackagesService extends ICommonService<Custompackages> {

    // 根据户型id查询对应的套餐列表
    public List<PackageVo> findListByCustominfoId(Integer customInfoId, Integer areaId);

    // 根据户型id删除该户型的所有套餐信息
    public void deleteAllInfoByCustomInfoId(Integer customInfoId);

    // 根据户型id查询对应的套餐列表
    public List<Custompackages> findListByCustomInfoId(Integer customInfoId);
}