package com.sixmac.service;

import com.sixmac.entity.Banner;
import com.sixmac.service.common.ICommonService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/3/8 0008 上午 10:23.
 */
public interface BannerService extends ICommonService<Banner> {

    public void deleteById(HttpServletRequest request, Integer id);
}