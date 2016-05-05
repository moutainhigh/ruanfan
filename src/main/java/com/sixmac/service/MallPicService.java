package com.sixmac.service;

import com.sixmac.entity.MallPic;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/5/5 0005 下午 6:26.
 */
public interface MallPicService extends ICommonService<MallPic> {

    public Page<MallPic> page(Integer pageNum, Integer pageSize);
}