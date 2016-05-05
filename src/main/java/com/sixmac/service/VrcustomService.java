package com.sixmac.service;

import com.sixmac.entity.Vrcustom;
import com.sixmac.service.common.ICommonService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/4/11 0011 下午 2:14.
 */
public interface VrcustomService extends ICommonService<Vrcustom> {

    public void deleteById(HttpServletRequest request, Integer id);
}