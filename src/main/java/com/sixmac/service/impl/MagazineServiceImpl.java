package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.MagazineDao;
import com.sixmac.entity.Magazine;
import com.sixmac.service.MagazineService;
import com.sixmac.utils.PathUtils;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 3:30.
 */
@Service
public class MagazineServiceImpl implements MagazineService {

    @Autowired
    private MagazineDao magazineDao;

    @Override
    public List<Magazine> findAll() {
        return magazineDao.findAll();
    }

    @Override
    public Page<Magazine> find(int pageNum, int pageSize) {
        return magazineDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Magazine> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Magazine getById(int id) {
        return magazineDao.findOne(id);
    }

    @Override
    public Magazine deleteById(int id) {
        Magazine magazine = getById(id);
        magazineDao.delete(magazine);
        return magazine;
    }

    @Override
    public Magazine create(Magazine magazine) {
        return magazineDao.save(magazine);
    }

    @Override
    public Magazine update(Magazine magazine) {
        return magazineDao.save(magazine);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Magazine> iPage(Integer month, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Magazine> page = magazineDao.findAll(new Specification<Magazine>() {
            @Override
            public Predicate toPredicate(Root<Magazine> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (month != null) {
                    Predicate pre = cb.equal(root.get("month").as(Integer.class), month);
                    predicateList.add(pre);
                }

                if (result != null) {
                    query.where(result);
                }
                return query.getGroupRestriction();
            }

        }, pageRequest);

        for (Magazine magazine : page.getContent()) {
            magazine.setCover(PathUtils.getRemotePath() + magazine.getCover());
        }

        return page;
    }
}