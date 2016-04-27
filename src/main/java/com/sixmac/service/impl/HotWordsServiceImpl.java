package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.HotWordsDao;
import com.sixmac.entity.HotWords;
import com.sixmac.service.HotWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/27 0027 下午 1:47.
 */
@Service
public class HotWordsServiceImpl implements HotWordsService {

    @Autowired
    private HotWordsDao hotWordsDao;

    @Override
    public List<HotWords> findAll() {
        return hotWordsDao.findAll();
    }

    @Override
    public Page<HotWords> find(int pageNum, int pageSize) {
        return hotWordsDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<HotWords> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public HotWords getById(int id) {
        return hotWordsDao.findOne(id);
    }

    @Override
    public HotWords deleteById(int id) {
        HotWords hotWords = getById(id);
        hotWordsDao.delete(hotWords);
        return hotWords;
    }

    @Override
    public HotWords create(HotWords hotWords) {
        return hotWordsDao.save(hotWords);
    }

    @Override
    public HotWords update(HotWords hotWords) {
        return hotWordsDao.save(hotWords);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void searchWord(String words) {
        // 先查询有没有该词，如果没有，新增一条记录；有的话，搜索量加1
        HotWords hotWords = hotWordsDao.findOneByWord(words);
        if (null == hotWords) {
            hotWords = new HotWords();
            hotWords.setWords(words);
            hotWords.setCount(0);
        } else {
            hotWords.setCount(hotWords.getCount() + 1);
        }
        hotWordsDao.save(hotWords);
    }

    @Override
    public List<HotWords> findList(Integer size) {
        List<HotWords> hotWordsList = hotWordsDao.findListDesc();
        List<HotWords> list = new ArrayList<HotWords>();

        if (null != hotWordsList && hotWordsList.size() > size) {
            for (int i = 0; i < size; i++) {
                list.add(hotWordsList.get(i));
            }
        } else {
            list = hotWordsList;
        }

        return list;
    }
}