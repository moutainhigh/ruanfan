package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.SpikesDao;
import com.sixmac.entity.Spikes;
import com.sixmac.service.OperatisService;
import com.sixmac.service.SpikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 10:28.
 */
@Service
public class SpikesServiceImpl implements SpikesService {

    @Autowired
    private SpikesDao spikesDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Spikes> findAll() {
        return spikesDao.findAll();
    }

    @Override
    public Page<Spikes> find(int pageNum, int pageSize) {
        return spikesDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Spikes> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Spikes getById(int id) {
        return spikesDao.findOne(id);
    }

    @Override
    public Spikes deleteById(int id) {
        Spikes spikes = getById(id);
        spikesDao.delete(spikes);
        return spikes;
    }

    @Override
    public Spikes create(Spikes spikes) {
        return spikesDao.save(spikes);
    }

    @Override
    public Spikes update(Spikes spikes) {
        return spikesDao.save(spikes);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Spikes> page(String name, Integer status, Integer pageNum, Integer pageSize) {
        if (null == status) {
            status = 3;
        }

        // 根据传入的状态判断
        switch (status) {
            case 0:
                return spikesDao.findAllNoStart(name, new Date(), new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
            case 1:
                return spikesDao.findAllWorking(name, new Date(), new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
            case 2:
                return spikesDao.findAllFinish(name, new Date(), new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
            case 3:
                return spikesDao.findAllNoThing(name, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
        }

        return null;
    }

    @Override
    public Page<Spikes> page(Integer pageNum, Integer pageSize) {
        return spikesDao.findAllWorking(new Date(), new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    @Transactional
    public void deleteById(HttpServletRequest request, Integer id) {
        operatisService.addOperatisInfo(request, "删除秒杀商品 " + spikesDao.findOne(id).getName());

        deleteById(id);
    }

}