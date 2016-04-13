package com.sixmac.service;

import com.sixmac.entity.Image;
import com.sixmac.entity.Works;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/3/11 0011 下午 5:00.
 */
public interface WorksService extends ICommonService<Works> {

    // 根据设计师id查询最新的三张设计作品图片（倒序）
    public List<Works> iFindThreeNewWorksByDesignerId(Integer designerId);

    //根据设计师的id查询设计作品
    public Page<Works> page(Integer designerId,String name, Integer status, Integer areas, Integer stytle, Integer pageNum, Integer pageSize);
}