package com.sixmac.service;

import com.sixmac.entity.HotWords;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27 0027 下午 1:47.
 */
public interface HotWordsService extends ICommonService<HotWords> {

    // 查询热词
    public void searchWord(String words);

    // 查询搜索量排行前几位的热词
    public List<HotWords> findList(Integer size);
}