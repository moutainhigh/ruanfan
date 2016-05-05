package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.SysusersDao;
import com.sixmac.entity.Sysusers;
import com.sixmac.service.OperatisService;
import com.sixmac.service.SysusersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/23 0023 下午 12:02.
 */
@Service
public class SysusersServiceImpl implements SysusersService {

    @Autowired
    private SysusersDao sysusersDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Sysusers> findAll() {
        return sysusersDao.findAll();
    }

    @Override
    public Page<Sysusers> find(int pageNum, int pageSize) {
        return sysusersDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Sysusers> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Sysusers getById(int id) {
        return sysusersDao.findOne(id);
    }

    @Override
    public Sysusers deleteById(int id) {
        sysusersDao.delete(id);
        return null;
    }

    @Override
    @Transactional
    public void deleteById(HttpServletRequest request, Integer id) {
        Sysusers sysusers = getById(id);

        operatisService.addOperatisInfo(request, "删除管理员 " + sysusers.getAccount());

        sysusersDao.delete(sysusers);
    }

    @Override
    @Transactional
    public void deleteAll(HttpServletRequest request, int[] ids) {
        for (int id : ids) {
            deleteById(request, id);
        }
    }

    @Override
    public Sysusers create(Sysusers sysusers) {
        return sysusersDao.save(sysusers);
    }

    @Override
    public Sysusers update(Sysusers sysusers) {
        return sysusersDao.save(sysusers);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Sysusers> page(final String account, final Integer roleId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Sysusers> page = sysusersDao.findAll(new Specification<Sysusers>() {
            @Override
            public Predicate toPredicate(Root<Sysusers> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (null != account && account != "") {
                    Predicate pre = cb.like(root.get("account").as(String.class), "%" + account + "%");
                    predicateList.add(pre);
                }
                if (roleId != null) {
                    Predicate pre = cb.equal(root.get("role").get("id").as(Integer.class), roleId);
                    predicateList.add(pre);
                }
                Predicate pre1 = cb.notEqual(root.get("role").get("id").as(Integer.class), 1);
                predicateList.add(pre1);
                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }

                if (result != null) {
                    query.where(result);
                }
                return query.getGroupRestriction();
            }

        }, pageRequest);

        return page;
    }

    @Override
    public List<Sysusers> findListByRoleId(Integer roleId) {
        return sysusersDao.findListByRoleId(roleId);
    }
}