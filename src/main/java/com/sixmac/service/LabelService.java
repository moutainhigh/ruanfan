package com.sixmac.service;

import com.sixmac.entity.Label;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/3/10 0010 下午 3:09.
 */
public interface LabelService extends ICommonService<Label> {

    // 根据目标id和目标类型查询标签列表
    public List<Label> findListByParams(Integer objectId, Integer objectType);

    // 根据目标id和标签id查询标签最后停留的位置
    public Label findLastPosition(Integer objectId, String labelId);

    // 根据目标id和标签id查询标签最后停留的位置
    public Label searchByLabelId(String labelId);
}