package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.ShopcarDao;
import com.sixmac.entity.Shopcar;
import com.sixmac.service.ShopcarService;
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
 * Created by Administrator on 2016/3/8 0008 上午 11:41.
 */
@Service
public class ShopcarServiceImpl implements ShopcarService {

    @Autowired
    private ShopcarDao shopcarDao;

    @Override
    public List<Shopcar> findAll() {
        return shopcarDao.findAll();
    }

    @Override
    public Page<Shopcar> find(int pageNum, int pageSize) {
        return shopcarDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Shopcar> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Shopcar getById(int id) {
        return shopcarDao.findOne(id);
    }

    @Override
    @Transactional
    public Shopcar deleteById(int id) {
        Shopcar shopcar = getById(id);
        shopcarDao.delete(shopcar);
        return shopcar;
    }

    @Override
    @Transactional
    public Shopcar create(Shopcar shopcar) {
        return shopcarDao.save(shopcar);
    }

    @Override
    @Transactional
    public Shopcar update(Shopcar shopcar) {
        return shopcarDao.save(shopcar);
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
    public void iCleanAllByUserId(Integer userId) {
        List<Shopcar> list = shopcarDao.findListByUserId(userId);
        for (Shopcar shopcar : list) {
            shopcarDao.delete(shopcar.getId());
        }
    }

    @Override
    public Page<Shopcar> iPage(Integer userId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Shopcar> page = shopcarDao.findAll(new Specification<Shopcar>() {
            @Override
            public Predicate toPredicate(Root<Shopcar> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (userId != null) {
                    Predicate pre = cb.equal(root.get("user").get("id").as(Integer.class), userId);
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

        for (Shopcar shopCar : page.getContent()) {
            shopCar.setCover(PathUtils.getRemotePath() + shopCar.getCover());
        }

        return page;
    }
}