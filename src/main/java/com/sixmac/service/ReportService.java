package com.sixmac.service;

import com.sixmac.entity.Report;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public interface ReportService extends ICommonService<Report> {

    public Page<Report> page(String userName, String sourceName, Integer pageNum, Integer pageSize);

    public void deleteById(HttpServletRequest request, Integer id);

    public void deleteAll(HttpServletRequest request, int[] ids);
}
