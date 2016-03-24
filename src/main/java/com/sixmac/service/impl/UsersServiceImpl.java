package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.*;
import com.sixmac.entity.*;
import com.sixmac.service.UsersService;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
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
        users.setStatus(Constant.BANNED_STATUS_NO);
        usersDao.save(users);
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
    public Sysusers sysUserLogin(HttpSession session, String username, String password) {
        Sysusers sysusers = usersDao.sysUserLogin(username, password);
        if (null != sysusers) {
            putSession(session, sysusers.getId(), sysusers.getAccount(), Constant.MASTER_TYPE_ADMIN);
            return sysusers;
        }

        return null;
    }

    @Override
    public Merchants merchantLogin(HttpSession session, String username, String password) {
        Merchants merchants = usersDao.merchantLogin(username, password);
        if (null != merchants) {
            putSession(session, merchants.getId(), merchants.getNickName(), Constant.MASTER_TYPE_MERCHANT);
            return merchants;
        }

        return null;
    }

    @Override
    public Designers desingerLogin(HttpSession session, String username, String password) {
        Designers designers = usersDao.designerLogin(username, password);
        if (null != designers) {
            putSession(session, designers.getId(), designers.getNickName(), Constant.MASTER_TYPE_DESIGNER);
            return designers;
        }

        return null;
    }

    @Override
    public void logOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.removeAttribute(Constant.CURRENT_USER_ID);
        session.removeAttribute(Constant.CURRENT_USER_NAME);
        session.removeAttribute(Constant.CURRENT_USER_TYPE);
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
            users.setHeadPath(Constant.DEFAULT_HEAD_PATH);

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
        return users;
    }

    @Override
    public Page<Users> page(String mobile, String nickName, Integer status, Integer type, Integer pageNum, Integer pageSize) {
        Page<Users> page = usersDao.findAll(new Specification<Users>() {
            @Override
            public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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

                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
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

        }, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

        return page;
    }

    // 将登录人的信息放入session中
    private void putSession(HttpSession session, Integer id, String name, Integer type) {
        session.setAttribute(Constant.CURRENT_USER_ID, id);
        session.setAttribute(Constant.CURRENT_USER_NAME, name);
        session.setAttribute(Constant.CURRENT_USER_TYPE, type);
    }
}