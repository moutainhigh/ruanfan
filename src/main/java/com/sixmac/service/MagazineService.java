package com.sixmac.service;

import com.sixmac.entity.Magazine;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/3/7 0007 下午 3:30.
 */
public interface MagazineService extends ICommonService<Magazine> {

    // 根据月份查询杂志列表
    public Page<Magazine> iPage(Integer month, Integer pageNum, Integer pageSize);

    // 根据名称和月份查询杂志列表
    public Page<Magazine> page(String name, Integer month, Integer pageNum, Integer pageSize);
}