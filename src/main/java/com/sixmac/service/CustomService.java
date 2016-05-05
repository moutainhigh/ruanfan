package com.sixmac.service;

import com.sixmac.entity.Custom;
import com.sixmac.service.common.ICommonService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008 下午 3:07.
 */
public interface CustomService extends ICommonService<Custom> {

    // 根据楼盘名称模糊查询楼盘信息
    public Custom findOneByParams(String name);

    // 查询推荐楼盘信息
    public Custom findOneByHot();

    public void deleteById(HttpServletRequest request,Integer id);
}