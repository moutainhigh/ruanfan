package com.sixmac.service;

import com.sixmac.entity.Collect;
import com.sixmac.entity.Users;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 上午 11:43.
 */
public interface CollectService extends ICommonService<Collect> {

    // 添加收藏
    public void iCreate(Users user, Integer objectId, Integer objectType);

    // 根据用户id、收藏目标id和收藏目标类型查询收藏信息
    public Collect iFindOne(Integer userId, Integer objectId, Integer objectType);

    // 根据用户id获取收藏列表
    public Page<Collect> iPage(Integer userId, Integer pageNum, Integer pageSize);

    // 根据用户id和收藏类型获取收藏列表
    public Page<Collect> iPage(Integer type, Integer userId, Integer pageNum, Integer pageSize);

    // 根据收藏目标id和收藏类型获取收藏列表
    public List<Collect> iFindList(Integer objectId, Integer objectType);
}