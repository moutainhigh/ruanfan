package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.RolemodulesDao;
import com.sixmac.entity.Rolemodules;
import com.sixmac.service.RoleModulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/4/2 0002 21:14.
 */
@Service
public class RolemodulesServiceImpl implements RoleModulesService {

    @Autowired
    private RolemodulesDao rolemodulesDao;

    @Override
    public List<Rolemodules> findAll() {
        return rolemodulesDao.findAll();
    }

    @Override
    public Page<Rolemodules> find(int pageNum, int pageSize) {
        return rolemodulesDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Rolemodules> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Rolemodules getById(int id) {
        return rolemodulesDao.findOne(id);
    }

    @Override
    public Rolemodules deleteById(int id) {
        Rolemodules rolemodules = getById(id);
        rolemodulesDao.delete(rolemodules);
        return rolemodules;
    }

    @Override
    public Rolemodules create(Rolemodules rolemodules) {
        return rolemodulesDao.save(rolemodules);
    }

    @Override
    public Rolemodules update(Rolemodules rolemodules) {
        return rolemodulesDao.save(rolemodules);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Rolemodules> findListByRoleId(Integer roleId) {
        return rolemodulesDao.findListByRoleId(roleId);
    }

    @Override
    @Transactional
    public void deleteByRoleId(Integer roleId) {
        List<Rolemodules> list = rolemodulesDao.findListByRoleId(roleId);
        for (Rolemodules rolemodules : list) {
            deleteById(rolemodules.getId());
        }
    }
}