package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.ReserveDao;
import com.sixmac.entity.Reserve;
import com.sixmac.service.ReserveService;
import com.sixmac.utils.DateUtils;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

;

/**
 * Created by Administrator on 2016/3/7 0007 下午 2:24.
 */
@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private ReserveDao reserveDao;

    @Override
    public List<Reserve> findAll() {
        return reserveDao.findAll();
    }

    @Override
    public Page<Reserve> find(int pageNum, int pageSize) {
        return reserveDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Reserve> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Reserve getById(int id) {
        return reserveDao.findOne(id);
    }

    @Override
    public Reserve deleteById(int id) {
        Reserve reserve = getById(id);
        reserveDao.delete(reserve);
        return reserve;
    }

    @Override
    public Reserve create(Reserve reserve) {
        return reserveDao.save(reserve);
    }

    @Override
    public Reserve update(Reserve reserve) {
        return reserveDao.save(reserve);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    @Transactional
    public void batchConfirm(int[] ids, String reserveTime, String reserveAddress) {
        try {
            Reserve reserve = null;
            for (int id : ids) {
                reserve = getById(id);
                reserve.setReseTime(DateUtils.stringToDateWithFormat(reserveTime, "yyyy-MM-dd HH:ss:mm"));
                reserve.setReseAddress(reserveAddress);
                reserve.setStatus(1);
                update(reserve);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reserve> iFindListByDesignerId(Integer designerId) {
        return reserveDao.iFindListByDesignerId(designerId);
    }

    @Override
    public Page<Reserve> page(String name, String mobile, String email, String nickName, Integer status, int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Reserve> page = reserveDao.findAll(new Specification<Reserve>() {
            @Override
            public Predicate toPredicate(Root<Reserve> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (name != null) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }

                if (null != mobile) {
                    Predicate pre = cb.like(root.get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }

                if (email != null) {
                    Predicate pre = cb.like(root.get("email").as(String.class), "%" + email + "%");
                    predicateList.add(pre);
                }

                if (nickName != null) {
                    Predicate pre = cb.like(root.get("designer").get("nickName").as(String.class), "%" + nickName + "%");
                    predicateList.add(pre);
                }

                if (status != null) {
                    Predicate pre = cb.equal(root.get("status").as(Integer.class), status);
                    predicateList.add(pre);
                }

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

}