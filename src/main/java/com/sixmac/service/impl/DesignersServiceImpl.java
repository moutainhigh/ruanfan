package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.DesignersDao;
import com.sixmac.dao.MessageplusDao;
import com.sixmac.dao.WorksDao;
import com.sixmac.entity.Designers;
import com.sixmac.entity.Messageplus;
import com.sixmac.entity.Works;
import com.sixmac.service.DesignersService;
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
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 1:52.
 */
@Service
public class DesignersServiceImpl implements DesignersService {

    @Autowired
    private DesignersDao designersDao;

    @Autowired
    private WorksDao worksDao;

    @Autowired
    private MessageplusDao messageplusDao;

    @Override
    public List<Designers> findAll() {
        return designersDao.findAll();
    }

    @Override
    public Page<Designers> find(int pageNum, int pageSize) {
        return designersDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Designers> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Designers getById(int id) {
        Designers designers = designersDao.findOne(id);
        List<Works> list = worksDao.iFindThreeNewWorksByDesignerId(id);
        designers.setWorkNum(null == list || list.size() == 0 ? 0 : list.size());
        return designers;
    }

    @Override
    public Designers deleteById(int id) {
        Designers designers = getById(id);
        designers.setIsCut(Constant.IS_CUT_YES);
        designersDao.save(designers);
        return designers;
    }

    @Override
    public Designers create(Designers designers) {
        return designersDao.save(designers);
    }

    @Override
    public Designers update(Designers designers) {
        return designersDao.save(designers);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Designers> iPage(Integer type, String nickname, Integer cityId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Designers> page = designersDao.findAll(new Specification<Designers>() {
            @Override
            public Predicate toPredicate(Root<Designers> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
                    predicateList.add(pre);
                }
                if (nickname != null) {
                    Predicate pre = cb.like(root.get("nickName").as(String.class), "%" + nickname + "%");
                    predicateList.add(pre);
                }
                if (cityId != null) {
                    Predicate pre = cb.equal(root.get("city").get("id").as(Integer.class), cityId);
                    predicateList.add(pre);
                }

                // 审核通过的设计师才可以被查询
                Predicate pre1 = cb.equal(root.get("isCheck").as(Integer.class), Constant.CHECK_STATUS_SUCCESS);
                predicateList.add(pre1);

                // 没有封禁的设计师才可以被查询
                Predicate pre2 = cb.equal(root.get("status").as(Integer.class), Constant.BANNED_STATUS_YES);
                predicateList.add(pre2);

                // 没有被（逻辑）删除的设计师才可以被查询
                Predicate pre3 = cb.equal(root.get("isCut").as(Integer.class), Constant.IS_CUT_NO);
                predicateList.add(pre3);

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
    public Page<Works> iPageWorks(Integer designerId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Works> page = worksDao.findAll(new Specification<Works>() {
            @Override
            public Predicate toPredicate(Root<Works> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (designerId != null) {
                    Predicate pre = cb.equal(root.get("designer").get("id").as(Integer.class), designerId);
                    predicateList.add(pre);
                }

                Predicate pre1 = cb.equal(root.get("designer").get("isCut").as(Integer.class), Constant.IS_CUT_NO);
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
    public Page<Designers> page(String mobile, String nickName, Integer status, Integer isCheck, Integer type, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Designers> page = designersDao.findAll(new Specification<Designers>() {
            @Override
            public Predicate toPredicate(Root<Designers> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (mobile != null) {
                    Predicate pre = cb.like(root.get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }

                if (nickName != null) {
                    Predicate pre = cb.like(root.get("nickName").as(String.class), "%" + nickName + "%");
                    predicateList.add(pre);
                }

                if (status != null) {
                    Predicate pre = cb.equal(root.get("status").as(Integer.class), status);
                    predicateList.add(pre);
                }

                if (isCheck != null) {
                    Predicate pre = cb.equal(root.get("isCheck").as(Integer.class), isCheck);
                    predicateList.add(pre);
                }

                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
                    predicateList.add(pre);
                }

                Predicate pre1 = cb.equal(root.get("isCut").as(Integer.class), Constant.IS_CUT_NO);
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
    @Transactional
    public void changeCheck(Integer designerId, Integer isCheck, String reason) {
        Designers designers = designersDao.findOne(designerId);
        designers.setIsCheck(isCheck);

        // 修改审核状态
        designersDao.save(designers);

        // 添加系统消息
        Messageplus message = new Messageplus();
        message.setTitle("系统消息");
        message.setSourceId(designerId);
        message.setType(Constant.MESSAGE_PLUS_DESIGNERS);
        message.setDescription("账号审核" + (isCheck == 1 ? "成功" : "失败，失败原因：" + reason));
        message.setCreateTime(new Date());

        messageplusDao.save(message);
    }

    @Override
    public List<Designers> findListByMobile(String mobile) {
        return designersDao.findListByMobile(mobile);
    }

    @Override
    public Messageplus findReasonByDesignerId(Integer designerId) {
        List<Messageplus> list = designersDao.findListByDesignerId(designerId);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}