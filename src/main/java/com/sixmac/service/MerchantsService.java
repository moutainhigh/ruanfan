package com.sixmac.service;

import com.sixmac.entity.Merchants;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 11:33.
 */
public interface MerchantsService extends ICommonService<Merchants> {

    // 根据Email查询是否有相同的商家信息
    public List<Merchants> findListByEmail(String email);
}