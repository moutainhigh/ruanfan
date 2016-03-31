package com.sixmac.service;

import com.sixmac.entity.Attentions;
import com.sixmac.entity.Users;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 2:39.
 */
public interface AttentionsService extends ICommonService<Attentions> {

    // 添加关注信息
    public void iCreate(Users users, Integer objectId, Integer objectType);

    // 根据用户id，关注目标id和关注目标类型查询关注信息
    public Attentions iFindOne(Integer userId, Integer objectId, Integer objectType);

    // 根据关注目标id和关注目标类型查询关注信息集合
    public List<Attentions> iFindList(Integer objectId, Integer objectType);

    // 根据用户id查询该用户的关注信息集合
    public List<Attentions> iFindListByUserId(Integer userId);

    // 根据用户id查询关注列表（分页）
    public Page<Attentions> iPage(Integer userId, Integer pageNum, Integer pageSize);

    // 根据用户id查询关注列表（分页）
    public Page<Attentions> iPageFans(Integer userId, Integer pageNum, Integer pageSize);
}