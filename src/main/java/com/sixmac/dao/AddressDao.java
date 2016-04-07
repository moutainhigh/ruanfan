package com.sixmac.dao;

import com.sixmac.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/4/7 0007 下午 4:02.
 */
public interface AddressDao extends JpaRepository<Address, Integer> {

    @Query("select a from Address a where a.user.id = ?1")
    public List<Address> findListByUserId(Integer userId);

    @Query("select a from Address a where a.user.id = ?1 and a.isDefault = 1")
    public Address findDefaultByUserId(Integer userId);

    @Query("select a from Address a where a.id = ?1 and a.user.id = ?2")
    public Address findOneByIdAndUserId(Integer addressId, Integer userId);
}