package com.sixmac.service;

import com.sixmac.entity.Custom;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008 下午 3:07.
 */
public interface CustomService extends ICommonService<Custom> {

    // 根据楼盘名称模糊查询楼盘列表
    public List<Custom> findListByParams(String name);
}