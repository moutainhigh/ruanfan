package com.sixmac.service;

import com.sixmac.entity.Designers;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/3/7 0007 下午 1:52.
 */
public interface DesignersService extends ICommonService<Designers> {

    // 设计师列表
    public Page<Designers> iPage(Integer type, Integer styleId, Integer areaId, Integer pageNum, Integer pageSize);
}