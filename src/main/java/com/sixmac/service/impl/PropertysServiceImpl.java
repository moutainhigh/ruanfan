package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.PropertysDao;
import com.sixmac.entity.Propertys;
import com.sixmac.service.OperatisService;
import com.sixmac.service.PropertysService;
import org.apache.commons.lang.StringUtils;
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
 * Created by Administrator on 2016/3/8 0008 上午 10:13.
 */
@Service
public class PropertysServiceImpl implements PropertysService {

    @Autowired
    private PropertysDao propertysDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Propertys> findAll() {
        return propertysDao.findAll();
    }

    @Override
    public Page<Propertys> find(int pageNum, int pageSize) {
        return propertysDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Propertys> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Propertys getById(int id) {
        return propertysDao.findOne(id);
    }

    @Override
    public Propertys deleteById(int id) {
        Propertys propertys = getById(id);
        propertysDao.delete(propertys);
        return propertys;
    }

    @Override
    public Propertys create(Propertys propertys) {
        return propertysDao.save(propertys);
    }

    @Override
    public Propertys update(Propertys propertys) {
        return propertysDao.save(propertys);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Propertys> iPage(final String name, final String address, final Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Propertys> page = propertysDao.findAll(new Specification<Propertys>() {
            @Override
            public Predicate toPredicate(Root<Propertys> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (StringUtils.isNotEmpty(name)) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }

                if (StringUtils.isNotEmpty(address)) {
                    Predicate pre = cb.like(root.get("address").as(String.class), "%" + address + "%");
                    predicateList.add(pre);
                }

                Predicate pre1 = cb.equal(root.get("parentId").as(Integer.class), 0);
                predicateList.add(pre1);

                Predicate pre2 = cb.notEqual(root.get("id").as(Integer.class), 0);
                predicateList.add(pre2);

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
    public List<Propertys> iPageByParentId(Integer parentId) {
        return propertysDao.iPageByParentId(parentId);
    }

    @Override
    public List<Propertys> pageByParentId(Integer parentId) {
        return null;
    }

    @Override
    public Page<Propertys> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "showTurn");

        Page<Propertys> page = propertysDao.findAll(new Specification<Propertys>() {
            @Override
            public Predicate toPredicate(Root<Propertys> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Predicate pre = cb.notEqual(root.get("id").as(Integer.class), 0);
                predicateList.add(pre);

                Predicate pre1 = cb.equal(root.get("parentId").as(Integer.class), 0);
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
    public Page<Propertys> pageChild(final Integer parentId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "showTurn");

        Page<Propertys> page = propertysDao.findAll(new Specification<Propertys>() {
            @Override
            public Predicate toPredicate(Root<Propertys> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Predicate pre = cb.notEqual(root.get("id").as(Integer.class), 0);
                predicateList.add(pre);


                if (parentId != null) {
                    Predicate pre1 = cb.equal(root.get("parentId").as(Integer.class), parentId);
                    predicateList.add(pre1);
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
    public Page<Propertys> pageChild(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Propertys> page = propertysDao.findAll(new Specification<Propertys>() {
            @Override
            public Predicate toPredicate(Root<Propertys> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Predicate pre = cb.notEqual(root.get("id").as(Integer.class), 0);
                predicateList.add(pre);

                Predicate pre1 = cb.notEqual(root.get("parentId").as(Integer.class), 0);
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
    public void deleteById(HttpServletRequest request, Integer id) {
        Propertys propertys = getById(id);

        if (propertys.getParentId() == 0) {
            // parentId为0，代表是地产，此时直接删除
            operatisService.addOperatisInfo(request, "删除地产 " + propertys.getName());
        } else {
            Propertys parentProperty = propertysDao.findOne(propertys.getParentId());
            operatisService.addOperatisInfo(request, "删除地产 " + parentProperty.getName() + " 下的楼盘 " + propertys.getName());
        }

        propertysDao.delete(propertys);
    }

    @Override
    public Propertys getShowTurn(Integer turn, Integer parentId) {
        List<Propertys> list = propertysDao.findListByShowTurn(parentId);

        if (null != list && list.size() > 0) {
            if (turn == 0) {
                return list.get(list.size() - 1);
            } else {
                return list.get(0);
            }
        }

        return null;
    }

    @Override
    @Transactional
    public Integer changeShowTurn(Integer propertyId, Integer turn) {
        Propertys baseProperty = propertysDao.findOne(propertyId);
        Propertys sourceProperty = propertysDao.getByShowTurn(baseProperty.getParentId(), turn == 0 ? baseProperty.getShowTurn() - 1 : baseProperty.getShowTurn() + 1);
        Integer showTurn = 0;

        if (null == sourceProperty) {
            if (turn == 0) {
                // 已经是第一条了，无法上移
                return -1;
            } else {
                // 已经是最后一条了，无法下移
                return -2;
            }
        } else {
            showTurn = sourceProperty.getShowTurn();
            sourceProperty.setShowTurn(baseProperty.getShowTurn());
            propertysDao.save(sourceProperty);

            baseProperty.setShowTurn(showTurn);
            propertysDao.save(baseProperty);

            return 1;
        }
    }
}