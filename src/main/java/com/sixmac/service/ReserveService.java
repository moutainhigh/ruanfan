package com.sixmac.service;

import com.sixmac.entity.Reserve;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 2:24.
 */

public interface ReserveService extends ICommonService<Reserve> {

    // 根据设计师id查询预约信息集合
    public List<Reserve> iFindListByDesignerId(Integer designerId);

    public Page<Reserve> page(String name, String mobile, String email, String nickName, Integer status, int pageNum, int pageSize);

    // 批量确认联系
    public void batchConfirm(int[] ids, String reserveTime, String reserveAddress);
}