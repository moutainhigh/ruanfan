package com.sixmac.service;

import com.sixmac.entity.Operatis;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public interface OperatisService extends ICommonService<Operatis> {

    // 查询操作日志列表（分页）
    public Page<Operatis> page(String name, String roleName, int pageNum, int pageSize);

    // 记录操作
    public void addOperatisInfo(HttpServletRequest request, String description);
}
