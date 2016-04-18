package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.MerchantsDao;
import com.sixmac.dao.MessageplusDao;
import com.sixmac.entity.Merchants;
import com.sixmac.entity.Messageplus;
import com.sixmac.service.MerchantsService;
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
 * Created by Administrator on 2016/3/8 0008 上午 11:33.
 */
@Service
public class MerchantsServiceImpl implements MerchantsService {

    @Autowired
    private MerchantsDao merchantsDao;

    @Autowired
    private MessageplusDao messageplusDao;

    @Override
    public List<Merchants> findAll() {
        return merchantsDao.findAll();
    }

    @Override
    public Page<Merchants> find(int pageNum, int pageSize) {
        return merchantsDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Merchants> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Merchants getById(int id) {
        return merchantsDao.findOne(id);
    }

    @Override
    public Merchants deleteById(int id) {
        Merchants merchants = getById(id);
        merchants.setIsCut(Constant.IS_CUT_YES);
        merchantsDao.save(merchants);
        return merchants;
    }

    @Override
    public Merchants create(Merchants merchants) {
        return merchantsDao.save(merchants);
    }

    @Override
    public Merchants update(Merchants merchants) {
        return merchantsDao.save(merchants);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Merchants> findListByEmail(String email) {
        return merchantsDao.findListByEmail(email);
    }

    @Override
    public Messageplus findReasonByMerchantId(Integer merchantId) {
        List<Messageplus> list = merchantsDao.findReasonByMerchantId(merchantId);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Page<Merchants> page(String email, String nickName, Integer status, Integer isCheck, Integer type, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Merchants> page = merchantsDao.findAll(new Specification<Merchants>() {
            @Override
            public Predicate toPredicate(Root<Merchants> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (null != email) {
                    Predicate pre = cb.like(root.get("email").as(String.class), "%" + email + "%");
                    predicateList.add(pre);
                }

                if (null != nickName) {
                    Predicate pre = cb.like(root.get("nickName").as(String.class), "%" + nickName + "%");
                    predicateList.add(pre);
                }

                if (null != status) {
                    Predicate pre = cb.equal(root.get("status").as(Integer.class), status);
                    predicateList.add(pre);
                }

                if (null != isCheck) {
                    Predicate pre = cb.equal(root.get("isCheck").as(Integer.class), isCheck);
                    predicateList.add(pre);
                }

                if (null != type) {
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
    public void changeCheck(Integer merchantId, Integer isCheck, String reason) {
        Merchants merchants = merchantsDao.findOne(merchantId);
        merchants.setIsCheck(isCheck);

        // 修改审核状态
        merchantsDao.save(merchants);

        // 添加系统消息
        Messageplus message = new Messageplus();
        message.setTitle("系统消息");
        message.setSourceId(merchantId);
        message.setType(Constant.MESSAGE_PLUS_MERCHANTS);
        message.setDescription("账号审核" + (isCheck == 1 ? "成功" : "失败，失败原因：" + reason));
        message.setCreateTime(new Date());

        messageplusDao.save(message);
    }

    @Override
    public Page<Merchants> page(Integer styleId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Merchants> page = merchantsDao.findAll(new Specification<Merchants>() {
            @Override
            public Predicate toPredicate(Root<Merchants> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (null != styleId) {
                    Predicate pre = cb.equal(root.get("style").get("id").as(Integer.class), styleId);
                    predicateList.add(pre);
                }

                // 审核通过的商户才可以被查询
                Predicate pre1 = cb.equal(root.get("isCheck").as(Integer.class), Constant.CHECK_STATUS_SUCCESS);
                predicateList.add(pre1);

                // 没有封禁的商户才可以被查询
                Predicate pre2 = cb.equal(root.get("status").as(Integer.class), Constant.BANNED_STATUS_YES);
                predicateList.add(pre2);

                // 没有被逻辑删除的商户才可以被查询
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

        for (Merchants merchant : page.getContent()) {
            merchant.setShowNum(merchant.getShowNum() + 1);
            merchantsDao.save(merchant);
        }

        return page;
    }
}