package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.AddressDao;
import com.sixmac.entity.Address;
import com.sixmac.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/4/7 0007 下午 4:03.
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> findAll() {
        return addressDao.findAll();
    }

    @Override
    public Page<Address> find(int pageNum, int pageSize) {
        return addressDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Address> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Address getById(int id) {
        return addressDao.findOne(id);
    }

    @Override
    public Address deleteById(int id) {
        Address address = getById(id);
        addressDao.delete(address);
        return address;
    }

    @Override
    public Address create(Address address) {
        return addressDao.save(address);
    }

    @Override
    public Address update(Address address) {
        return addressDao.save(address);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Address> findListByUserId(Integer userId) {
        return addressDao.findListByUserId(userId);
    }

    @Override
    public Address findDefaultByUserId(Integer userId) {
        return addressDao.findDefaultByUserId(userId);
    }
}