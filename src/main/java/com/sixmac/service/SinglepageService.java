package com.sixmac.service;

import com.sixmac.entity.Singlepage;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public interface SinglepageService extends ICommonService<Singlepage> {

    public Page<Singlepage> page(String title, String content, int pageNum, int pageSize);

}
