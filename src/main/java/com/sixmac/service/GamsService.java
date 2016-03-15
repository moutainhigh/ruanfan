package com.sixmac.service;

import com.sixmac.entity.Gams;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 上午 10:49.
 */
public interface GamsService extends ICommonService<Gams> {

    // 根据操作目标id和操作目标类型查询对应的点赞or转发列表
    public List<Gams> iFindList(Integer objectId, Integer objectType, Integer type, Integer sort);

    // 根据用户id，操作目标id、操作目标类型和操作类型查询详情
    public Gams iFindOne(Integer userId, Integer objectId, Integer objectType, Integer type);
}