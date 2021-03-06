package com.sixmac.service;

import com.sixmac.entity.Shopcar;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 11:40.
 */
public interface ShopcarService extends ICommonService<Shopcar> {

    // 根据用户id清空购物车
    public void iCleanAllByUserId(Integer userId);

    public Page<Shopcar> iPage(Integer userId, Integer pageNum, Integer pageSize);

    // 根据用户id清空购物车
    public List<Shopcar> findListByUserId(Integer userId);

    public List<Shopcar> findListByParams(Integer userId, Integer productId, String colors, String sizes, String materials);
}