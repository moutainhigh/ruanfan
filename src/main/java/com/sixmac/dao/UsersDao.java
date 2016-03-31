package com.sixmac.dao;

import com.sixmac.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:39.
 */
public interface UsersDao extends JpaRepository<Users, Integer>, JpaSpecificationExecutor<Users> {

    @Query("select a from Users a where a.mobile = ?1 and a.password = ?2")
    public Users iLogin(String mobile, String password);

    @Query("select a from Users a where a.mobile = ?1")
    public Users iLogin(String mobile);

    @Query("select a from Usersother a where a.openId = ?1 and a.type = ?2")
    public Usersother iTLogin(String openId, Integer type);

    @Query("select a from Users a where a.mobile = ?1")
    public Users iFindOneByMobile(String mobile);

    @Query("select a from Sysusers a where a.account = ?1 and a.password = ?2")
    public Sysusers sysUserLogin(String account, String password);

    @Query("select a from Merchants a where a.email = ?1 and a.password = ?2")
    public Merchants merchantLogin(String email, String password);

    @Query("select a from Designers a where a.mobile = ?1 and a.password = ?2")
    public Designers designerLogin(String mobile, String password);
}