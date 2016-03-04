package com.sixmac.dao;

import com.sixmac.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:39.
 */
public interface UsersDao extends JpaRepository<Users, Integer> {

}