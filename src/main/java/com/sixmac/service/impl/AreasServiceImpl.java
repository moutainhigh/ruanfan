package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.AreasDao;
import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Areas;
import com.sixmac.service.AreasService;
import com.sixmac.service.OperatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/9 0009 下午 3:29.
 */
@Service
public class AreasServiceImpl implements AreasService {

    @Autowired
    private AreasDao areasDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Areas> findAll() {
        return areasDao.findAll();
    }

    @Override
    public Page<Areas> find(int pageNum, int pageSize) {
        return areasDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Areas> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Areas getById(int id) {
        return areasDao.findOne(id);
    }

    @Override
    public Areas deleteById(int id) {
        Areas areas = getById(id);
        areasDao.delete(areas);
        return areas;
    }

    @Override
    public Areas create(Areas areas) {
        return areasDao.save(areas);
    }

    @Override
    public Areas update(Areas areas) {
        return areasDao.save(areas);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Integer findListByAreaId(Integer areaId) {
        List<Afflatus> afflatusList = areasDao.iFindListByAreaId(areaId);

        return afflatusList.size();
    }

    @Override
    public void deleteById(HttpServletRequest request, Integer id) {
        Areas areas = getById(id);

        operatisService.addOperatisInfo(request, "删除灵感图区域 " + areas.getName());

        areasDao.delete(areas);
    }
}