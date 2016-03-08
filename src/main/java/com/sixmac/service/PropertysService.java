package com.sixmac.service;

import com.sixmac.entity.Propertys;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/3/8 0008 上午 10:13.
 */
public interface PropertysService extends ICommonService<Propertys> {

    // 楼盘列表
    public Page<Propertys> iPage(String name, Integer pageNum, Integer pageSize);
}