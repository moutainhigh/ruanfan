package com.sixmac.service;

import com.sixmac.entity.Designers;
import com.sixmac.entity.Messageplus;
import com.sixmac.entity.Works;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 1:52.
 */
public interface DesignersService extends ICommonService<Designers> {

    // 设计师列表
    public Page<Designers> iPage(Integer type, String nickname, Integer cityId, Integer pageNum, Integer pageSize);

    // 根据设计师id查询作品列表
    public Page<Works> iPageWorks(Integer designerId, Integer pageNum, Integer pageSize);

    // 设计师列表
    public Page<Designers> page(String mobile, String nickName, Integer status, Integer isCheck, Integer type, Integer pageNum, Integer pageSize);

    // 审核设计师
    public void changeCheck(Integer designerId, Integer isCheck, String reason);

    // 查询是否有相同手机号信息的设计师集合
    public List<Designers> findListByMobile(String mobile);

    // 根据设计师id查询该设计师审核失败的原因
    public Messageplus findReasonByDesignerId(Integer designerId);

    // 查询合格的设计师列表
    public List<Designers> findListWithSuccess();
}