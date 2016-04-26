package com.sixmac.service;

import com.sixmac.entity.Spikes;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 10:28.
 */
public interface SpikesService extends ICommonService<Spikes> {

    // 秒杀列表
    public Page<Spikes> page(String name, Integer status, Integer pageNum, Integer pageSize);

    // 查询未开始和正在进行中的秒杀列表
    public Page<Spikes> page(Integer pageNum, Integer pageSize);
}