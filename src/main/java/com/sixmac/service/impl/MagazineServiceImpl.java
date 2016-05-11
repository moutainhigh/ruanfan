package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.ImageDao;
import com.sixmac.dao.MagazineDao;
import com.sixmac.entity.Image;
import com.sixmac.entity.Magazine;
import com.sixmac.service.MagazineService;
import com.sixmac.service.OperatisService;
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
 * Created by Administrator on 2016/3/7 0007 下午 3:30.
 */
@Service
public class MagazineServiceImpl implements MagazineService {

    @Autowired
    private MagazineDao magazineDao;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private OperatisService operatisService;

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
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Magazine getById(int id) {
        return magazineDao.findOne(id);
    }

    @Override
    @Transactional
    public Magazine deleteById(int id) {
        Magazine magazine = getById(id);

        // 删除杂志对应的图片信息
        List<Image> list = imageDao.iFindList(id, Constant.IMAGE_MAGAZINE);

        for (Image image : list) {
            imageDao.delete(image.getId());
        }

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
    public Page<Magazine> iPage(final Integer month, Integer pageNum, Integer pageSize) {
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
    public Page<Magazine> page(final String name, final Integer month, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Magazine> page = magazineDao.findAll(new Specification<Magazine>() {
            @Override
            public Predicate toPredicate(Root<Magazine> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (name != null) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }
                if (month != null) {
                    Predicate pre = cb.equal(root.get("month").as(Integer.class), month);
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

    @Override
    @Transactional
    public void deleteById(HttpServletRequest request, Integer id) {
        Magazine magazine = getById(id);

        // 删除杂志对应的图片信息
        List<Image> list = imageDao.iFindList(id, Constant.IMAGE_MAGAZINE);

        for (Image image : list) {
            imageDao.delete(image.getId());
        }

        operatisService.addOperatisInfo(request, "删除杂志 " + magazine.getName());

        magazineDao.delete(magazine);
    }
}