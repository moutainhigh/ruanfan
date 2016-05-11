package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.VrcustomDao;
import com.sixmac.entity.Vrcustom;
import com.sixmac.service.OperatisService;
import com.sixmac.service.VrcustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11 0011 下午 2:14.
 */
@Service
public class VrcustomServiceImpl implements VrcustomService {

    @Autowired
    private VrcustomDao vrcustomDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Vrcustom> findAll() {
        return vrcustomDao.findAll();
    }

    @Override
    public Page<Vrcustom> find(int pageNum, int pageSize) {
        return vrcustomDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Vrcustom> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Vrcustom getById(int id) {
        return vrcustomDao.findOne(id);
    }

    @Override
    public Vrcustom deleteById(int id) {
        Vrcustom vrcustom = getById(id);
        vrcustomDao.delete(vrcustom);
        return vrcustom;
    }

    @Override
    public Vrcustom create(Vrcustom vrcustom) {
        return vrcustomDao.save(vrcustom);
    }

    @Override
    public Vrcustom update(Vrcustom vrcustom) {
        return vrcustomDao.save(vrcustom);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteById(HttpServletRequest request, Integer id) {
        Vrcustom vrcustom = getById(id);

        operatisService.addOperatisInfo(request, "删除id为 " + vrcustom.getId() + " 的VR虚拟设计");

        vrcustomDao.delete(vrcustom);
    }
}