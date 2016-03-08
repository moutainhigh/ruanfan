package com.sixmac.dao;

import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:39.
 */
public interface UsersDao extends JpaRepository<Users, Integer> {

    @Query("select a from Users a where a.mobile = ?1 and a.password = ?2")
    public Users iLogin(String mobile, String password);
}