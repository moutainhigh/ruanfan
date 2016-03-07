package com.sixmac.service;

import com.sixmac.entity.Attentions;
import com.sixmac.entity.Users;
import com.sixmac.service.common.ICommonService;

/**
 * Created by Administrator on 2016/3/7 0007 下午 2:39.
 */
public interface AttentionsService extends ICommonService<Attentions> {

    // 添加关注信息
    public void iCreate(Users users, Integer objectId, Integer objectType);

    // 根据用户id，关注目标id和关注目标类型查询关注信息
    public Attentions iFindOne(Integer userId, Integer objectId, Integer objectType);
}