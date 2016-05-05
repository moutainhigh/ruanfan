package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.VrtypeDao;
import com.sixmac.entity.Virtuals;
import com.sixmac.entity.Vrtype;
import com.sixmac.service.OperatisService;
import com.sixmac.service.VrtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/14 0014 上午 11:45.
 */
@Service
public class VrtypeServiceImpl implements VrtypeService {

    @Autowired
    private VrtypeDao vrtypeDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Vrtype> findAll() {
        return vrtypeDao.findAll();
    }

    @Override
    public Page<Vrtype> find(int pageNum, int pageSize) {
        return vrtypeDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Vrtype> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Vrtype getById(int id) {
        return vrtypeDao.findOne(id);
    }

    @Override
    public Vrtype deleteById(int id) {
        Vrtype vrtype = getById(id);
        vrtypeDao.delete(vrtype);
        return vrtype;
    }

    @Override
    public Vrtype create(Vrtype vrtype) {
        return vrtypeDao.save(vrtype);
    }

    @Override
    public Vrtype update(Vrtype vrtype) {
        return vrtypeDao.save(vrtype);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Integer findListByTypeId(Integer vrTypeId) {
        List<Virtuals> virtualsList = vrtypeDao.iFindListByTypeId(vrTypeId);

        return virtualsList.size();
    }

    @Override
    public void deleteById(HttpServletRequest request, Integer id) {
        Vrtype vrtype = getById(id);

        operatisService.addOperatisInfo(request, "删除虚拟体验分类 " + vrtype.getName());

        vrtypeDao.delete(vrtype);
    }
}