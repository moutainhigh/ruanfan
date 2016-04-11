package com.sixmac.service;

import com.sixmac.entity.Custominfo;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008 下午 3:08.
 */
public interface CustominfoService extends ICommonService<Custominfo> {

    // 根据设计定制id查询户型列表
    public List<Custominfo> findListByCustomId(Integer customId);
}