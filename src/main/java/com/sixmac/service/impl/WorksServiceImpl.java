package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.ImageDao;
import com.sixmac.dao.WorksDao;
import com.sixmac.entity.Works;
import com.sixmac.service.WorksService;
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
 * Created by Administrator on 2016/3/11 0011 下午 5:00.
 */
@Service
public class WorksServiceImpl implements WorksService {

    @Autowired
    private WorksDao worksDao;

    @Autowired
    private ImageDao imageDao;

    @Override
    public List<Works> findAll() {
        return worksDao.findAll();
    }

    @Override
    public Page<Works> find(int pageNum, int pageSize) {
        return worksDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Works> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Works getById(int id) {
        return worksDao.findOne(id);
    }

    @Override
    public Works deleteById(int id) {
        Works works = getById(id);
        worksDao.delete(works);
        return works;
    }

    @Override
    public Works create(Works works) {
        return worksDao.save(works);
    }

    @Override
    public Works update(Works works) {
        return worksDao.save(works);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Works> iFindThreeNewWorksByDesignerId(Integer designerId) {
        List<Works> worksList = worksDao.iFindThreeNewWorksByDesignerId(designerId);

        if (null != worksList) {
            // 读取作品信息，并根据作品信息获取对应的图片信息
            for (Works work : worksList) {
                work.setCover(imageDao.findOne(work.getCoverId()).getPath());
            }
        }
        return worksList;
    }

    @Override
    public Page<Works> page(final Integer designerId, final String name, final Integer status, final Integer areas, final Integer stytle, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Works> page = worksDao.findAll(new Specification<Works>() {
            @Override
            public Predicate toPredicate(Root<Works> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (designerId != null) {
                    Predicate pre = cb.equal(root.get("designer").get("id").as(Integer.class), designerId);
                    predicateList.add(pre);
                }
                if (null != name && name != "") {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }
                if (status != null) {
                    Predicate pre = cb.equal(root.get("afflatus").get("status").as(Integer.class), status);
                    predicateList.add(pre);
                }
                if (areas != null) {
                    Predicate pre = cb.equal(root.get("areas").get("id").as(Integer.class), areas);
                    predicateList.add(pre);
                }
                if (stytle != null) {
                    Predicate pre = cb.equal(root.get("stytle").get("id").as(Integer.class), stytle);
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