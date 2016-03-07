package com.sixmac.service;

import com.sixmac.entity.Virtuals;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/3/7 0007 下午 3:13.
 */
public interface VirtualsService extends ICommonService<Virtuals> {

    // 根据名称、风格id和分类id查询VR虚拟列表
    public Page<Virtuals> iPage(String name, Integer styleId, Integer typeId, Integer pageNum, Integer pageSize);
}