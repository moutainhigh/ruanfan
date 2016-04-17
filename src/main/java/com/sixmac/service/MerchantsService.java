package com.sixmac.service;

import com.sixmac.entity.Merchants;
import com.sixmac.entity.Messageplus;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 11:33.
 */
public interface MerchantsService extends ICommonService<Merchants> {

    // 根据Email查询是否有相同的商家信息
    public List<Merchants> findListByEmail(String email);

    // 根据商家id查询该商家审核失败的原因
    public Messageplus findReasonByMerchantId(Integer merchantId);

    public Page<Merchants> page(String email, String nickName, Integer status, Integer isCheck, Integer type, Integer pageNum, Integer pageSize);

    // 审核商户
    public void changeCheck(Integer merchantId, Integer isCheck, String reason);

    public Page<Merchants> page(Integer styleId, Integer pageNum, Integer pageSize);
}