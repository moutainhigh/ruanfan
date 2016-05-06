package com.sixmac.dao;

import com.sixmac.entity.Merchants;
import com.sixmac.entity.Messageplus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:43.
 */
public interface MerchantsDao extends JpaRepository<Merchants, Integer>, JpaSpecificationExecutor<Merchants> {

    @Query("select a from Merchants a where a.style.id = ?1")
    public List<Merchants> iFindListByStyleId(Integer styleId);

    @Query("select a from Merchants a where a.email = ?1")
    public List<Merchants> findListByEmail(String email);

    @Query("select a from Messageplus a where a.sourceId = ?1 and a.type = 2 order by a.id desc")
    public List<Messageplus> findReasonByMerchantId(Integer merchantId);

    @Query("select a from Merchants a where a.status = 0 and a.isCut = 0 and a.isCheck = 1")
    public List<Merchants> findListWithSuccess();

    @Query("select a from Merchants a where a.status = 0 and a.isCut = 0 and a.isCheck = 1 and a.city.id = ?1")
    public List<Merchants> findListWithSuccess(Integer cityId);
}