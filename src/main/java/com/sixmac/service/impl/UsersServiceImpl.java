package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.UsersDao;
import com.sixmac.entity.Users;
import com.sixmac.service.UsersService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:55.
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersDao usersDao;

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
    public Users deleteById(int id) {
        Users users = getById(id);
        usersDao.delete(users);
        return users;
    }

    @Override
    public Users create(Users users) {
        return usersDao.save(users);
    }

    @Override
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
    public void logOut(HttpServletRequest request,String type) {
        HttpSession session = request.getSession(false);
        if(Constant.MEMBER_TYPE_GLOBLE.equals(type)){
            session.removeAttribute(Constant.SESSION_MEMBER_GLOBLE);
        }else if(Constant.MEMBER_TYPE_BUSINESS.equals(type)){
            session.removeAttribute(Constant.SESSION_MEMBER_BUSINESS);
        }

    }
}