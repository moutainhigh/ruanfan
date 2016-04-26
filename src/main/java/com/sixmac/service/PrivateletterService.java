package com.sixmac.service;

import com.sixmac.entity.Privateletter;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19 0019 下午 4:49.
 */
public interface PrivateletterService extends ICommonService<Privateletter> {

    // 根据用户id查询该用户的私信列表
    public Page<Privateletter> pageByReceiveUser(Integer userId, Integer pageNum, Integer pageSize);

    // 根据用户id和目标用户id查询私信对话列表（分页）
    public Page<Privateletter> pageWithDialogue(Integer userId, Integer otherUserId, Integer pageNum, Integer pageSize);
}