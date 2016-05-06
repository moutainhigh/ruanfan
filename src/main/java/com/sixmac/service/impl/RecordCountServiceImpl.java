package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.RecordCountDao;
import com.sixmac.entity.RecordCount;
import com.sixmac.service.RecordCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/5/6 0006 上午 11:50.
 */
@Service
public class RecordCountServiceImpl implements RecordCountService {

    @Autowired
    private RecordCountDao recordCountDao;

    @Override
    public List<RecordCount> findAll() {
        return recordCountDao.findAll();
    }

    @Override
    public Page<RecordCount> find(int pageNum, int pageSize) {
        return recordCountDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<RecordCount> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public RecordCount getById(int id) {
        return recordCountDao.findOne(id);
    }

    @Override
    public RecordCount deleteById(int id) {
        RecordCount recordCount = getById(id);
        recordCountDao.delete(recordCount);
        return recordCount;
    }

    @Override
    public RecordCount create(RecordCount recordCount) {
        return recordCountDao.save(recordCount);
    }

    @Override
    public RecordCount update(RecordCount recordCount) {
        return recordCountDao.save(recordCount);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void addCount(Integer visitCount, Integer onlineCount) {
        RecordCount recordCount = recordCountDao.findOne(1);
        recordCount.setVisitCount(recordCount.getVisitCount() + visitCount);
        recordCount.setOnlineCount(recordCount.getOnlineCount() + onlineCount);

        recordCountDao.save(recordCount);
    }

    @Override
    public void clearCount() {
        RecordCount recordCount = recordCountDao.findOne(1);
        recordCount.setVisitCount(0);
        recordCount.setOnlineCount(0);

        recordCountDao.save(recordCount);
    }
}