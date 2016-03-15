package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.*;
import com.sixmac.entity.Coupon;
import com.sixmac.entity.Usercoupon;
import com.sixmac.entity.Users;
import com.sixmac.entity.Usersother;
import com.sixmac.service.UsersService;
import com.sixmac.utils.PathUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:55.
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private UsercouponDao usercouponDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private UsersotherDao usersotherDao;

    @Override
    public List<Users> findAll() {
        return usersDao.findAll();
    }

    @Override
    public Page<Users> find(int pageNum, int pageSize) {
        return usersDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Users> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Users getById(int id) {
        return usersDao.findOne(id);
    }

    @Override
    @Transactional
    public Users deleteById(int id) {
        Users users = getById(id);
        usersDao.delete(users);
        return users;
    }

    @Override
    @Transactional
    public Users create(Users users) {
        return usersDao.save(users);
    }

    @Override
    @Transactional
    public Users update(Users users) {
        return usersDao.save(users);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Boolean login(HttpServletRequest request, String username, String password, String remark) {
        return null;
    }

    @Override
    public Boolean login(HttpServletRequest request, String username, String password, String type, String remark) {
        return null;
    }

    @Override
    public void logOut(HttpServletRequest request, String type) {
        HttpSession session = request.getSession(false);
        if (Constant.MEMBER_TYPE_GLOBLE.equals(type)) {
            session.removeAttribute(Constant.SESSION_MEMBER_GLOBLE);
        } else if (Constant.MEMBER_TYPE_BUSINESS.equals(type)) {
            session.removeAttribute(Constant.SESSION_MEMBER_BUSINESS);
        }

    }

    @Override
    @Transactional
    public void usedCoupon(Integer userId, Integer couponId) {
        Usercoupon usercoupon = new Usercoupon();
        usercoupon.setUser(usersDao.findOne(userId));
        usercoupon.setCoupon(couponDao.findOne(couponId));
        usercoupon.setStatus(Constant.COUPON_STATUS_YES);

        usercouponDao.save(usercoupon);
    }

    @Override
    public Users iLogin(String mobile, String password) {
        Users users = usersDao.iLogin(mobile, password);
        if (null != users) {
            users.setHeadPath(PathUtils.getRemotePath() + users.getHeadPath());
        }
        return users;
    }

    @Override
    @Transactional
    public Users iTLogin(Integer type, String openId, String head, String nickname) {
        Usersother usersother = usersDao.iTLogin(openId, type);
        Users users = null;
        // 如果第三方用户信息不存在，则执行注册操作
        if (null == usersother) {
            // 此处执行注册操作
            users = new Users();
            users.setNickName(nickname);
            users.setHeadPath(head);
            users.setCity(cityDao.findOne(1));
            users.setScore(0);
            users.setType(1);
            users.setStatus(0);
            users.setCreateTime(new Date());

            usersDao.save(users);

            // 注册完毕后，添加该用户的第三方信息
            Usersother others = new Usersother();
            others.setType(type);
            others.setOpenId(openId);
            others.setUser(users);

            usersotherDao.save(others);
        } else {
            users = usersother.getUser();
        }

        users.setHeadPath(PathUtils.getRemotePath() + users.getHeadPath());

        return users;
    }

    @Override
    public Users iRegister(Users users) {
        return null;
    }

    @Override
    public Page<Usercoupon> iPageByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Usercoupon> page = usercouponDao.findAll(new Specification<Usercoupon>() {
            @Override
            public Predicate toPredicate(Root<Usercoupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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

        return page;
    }

    @Override
    public Users iFindOneByMobile(String mobile) {
        Users users = usersDao.iFindOneByMobile(mobile);
        users.setHeadPath(PathUtils.getRemotePath() + users.getHeadPath());
        return users;
    }
}