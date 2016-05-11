package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.CityDao;
import com.sixmac.entity.City;
import com.sixmac.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 下午 5:29.
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    public List<City> findAll() {
        return cityDao.findAll();
    }

    @Override
    public Page<City> find(int pageNum, int pageSize) {
        return cityDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<City> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public City getById(int id) {
        return cityDao.findOne(id);
    }

    @Override
    public City deleteById(int id) {
        City city = getById(id);
        cityDao.delete(city);
        return city;
    }

    @Override
    public City create(City city) {
        return cityDao.save(city);
    }

    @Override
    public City update(City city) {
        return cityDao.save(city);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<City> findListByProvinceId(Integer provinceId) {
        return cityDao.findListByProvinceId(provinceId);
    }
}