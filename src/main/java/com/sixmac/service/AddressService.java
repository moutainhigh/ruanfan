package com.sixmac.service;

import com.sixmac.entity.Address;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/4/7 0007 下午 4:03.
 */
public interface AddressService extends ICommonService<Address> {

    // 根据用户id查询对应的收货地址列表
    public List<Address> findListByUserId(Integer userId);

    // 根据用户id查询对应的默认收货地址
    public Address findDefaultByUserId(Integer userId);

    // 根据收货地址id和用户id查询对应的收货地址详情
    public Address findOneByIdAndUserId(Integer addressId, Integer userId);
}