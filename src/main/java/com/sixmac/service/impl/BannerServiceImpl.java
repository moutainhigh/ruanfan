package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.BannerDao;
import com.sixmac.entity.Banner;
import com.sixmac.service.BannerService;
import com.sixmac.service.OperatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 10:23.
 */
@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Banner> findAll() {
        return bannerDao.findAll();
    }

    @Override
    public Page<Banner> find(int pageNum, int pageSize) {
        return bannerDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Banner> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Banner getById(int id) {
        return bannerDao.findOne(id);
    }

    @Override
    public Banner deleteById(int id) {
        Banner banner = getById(id);
        bannerDao.delete(banner);
        return banner;
    }

    @Override
    public Banner create(Banner banner) {
        return bannerDao.save(banner);
    }

    @Override
    public Banner update(Banner banner) {
        return bannerDao.save(banner);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteById(HttpServletRequest request, Integer id) {
        Banner banner = getById(id);

        operatisService.addOperatisInfo(request, "删除id为 " + banner.getId() + " 的广告banner");

        bannerDao.delete(banner);
    }
}