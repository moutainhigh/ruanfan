package com.sixmac.service.impl;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.dao.*;
import com.sixmac.entity.*;
import com.sixmac.service.OperatisService;
import com.sixmac.service.UsersService;
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
    private SysusersDao sysusersDao;

    @Autowired
    private UsercouponDao usercouponDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private UsersotherDao usersotherDao;

    @Autowired
    private AttentionsDao attentionsDao;

    @Autowired
    private OperatisService operatisService;

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
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Users getById(int id) {
        Users users = usersDao.findOne(id);

        // 查询粉丝数
        users.setFansNum(attentionsDao.findListByUserIdPlus(id).size());

        // 查询关注数
        users.setAttentionNum(attentionsDao.iFindListByUserId(id).size());

        return users;
    }

    @Override
    public Users deleteById(int id) {
        Users users = getById(id);
        users.setStatus(Constant.BANNED_STATUS_NO);
        usersDao.save(users);
        return users;
    }

    @Override
    @Transactional
    public void deleteById(HttpServletRequest request, Integer id) {
        Users users = getById(id);
        users.setStatus(Constant.BANNED_STATUS_NO);
        usersDao.save(users);

        operatisService.addOperatisInfo(request, "删除用户 " + users.getNickName());
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
            // 更新最后一次登录时间
            sysusers.setLoginTime(new Date());
            sysusersDao.save(sysusers);

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
        return usersDao.iLogin(mobile, password);
    }

    @Override
    public Users iLogin(String mobile) {
        return usersDao.iLogin(mobile);
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

        return users;
    }

    @Override
    public Users iRegister(Users users) {
        return null;
    }

    @Override
    public Page<Usercoupon> iPageByUserId(final Integer userId, Integer pageNum, Integer pageSize) {
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

                Predicate pre1 = cb.equal(root.get("coupon").get("isCheck").as(Integer.class), Constant.CHECK_STATUS_SUCCESS);
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
    public Users iFindOneByMobile(String mobile) {
        Users users = usersDao.iFindOneByMobile(mobile);
        return users;
    }

    @Override
    public Page<Users> page(final String mobile, final String nickName, final Integer status, final Integer type, Integer pageNum, Integer pageSize) {
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

    @Override
    public List<Users> findListNew() {
        return usersDao.findListNew(CommonController.getOldDate());
    }

    @Override
    public void deleteAll(HttpServletRequest request, int[] ids) {
        for (int id : ids) {
            deleteById(request, id);
        }
    }

    @Override
    public void getScore(Integer userId, Integer score) {
        Users users = usersDao.findOne(userId);

        if (null != users) {
            users.setScore(users.getScore() + score);

            usersDao.save(users);
        }
    }

    // 将登录人的信息放入session中
    private void putSession(HttpSession session, Integer id, String name, Integer type) {
        session.setAttribute(Constant.CURRENT_USER_ID, id);
        session.setAttribute(Constant.CURRENT_USER_NAME, name);
        session.setAttribute(Constant.CURRENT_USER_TYPE, type);
    }
}