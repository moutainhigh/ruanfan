package com.sixmac.service;

import com.sixmac.entity.Message;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/3/14 0014 下午 12:55.
 */
public interface MessageService extends ICommonService<Message> {

    // 根据消息所属类型查询消息列表
    public List<Message> findListByType(Integer type);
}