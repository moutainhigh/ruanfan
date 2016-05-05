package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.CustominfoDao;
import com.sixmac.dao.CustompackagesDao;
import com.sixmac.entity.Custominfo;
import com.sixmac.service.CustominfoService;
import com.sixmac.service.CustompackagesService;
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
 * Created by Administrator on 2016/4/8 0008 下午 3:09.
 */
@Service
public class CustominfoServiceImpl implements CustominfoService {

    @Autowired
    private CustominfoDao custominfoDao;

    @Autowired
    private CustompackagesService custompackagesService;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Custominfo> findAll() {
        return custominfoDao.findAll();
    }

    @Override
    public Page<Custominfo> find(int pageNum, int pageSize) {
        return custominfoDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Custominfo> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Custominfo getById(int id) {
        return custominfoDao.findOne(id);
    }

    @Override
    @Transactional
    public Custominfo deleteById(int id) {
        Custominfo custominfo = getById(id);

        // 删除户型详情时，删除该户型的所有套餐信息
        custompackagesService.deleteAllInfoByCustomInfoId(id);

        custominfoDao.delete(custominfo);
        return custominfo;
    }

    @Override
    public Custominfo create(Custominfo custominfo) {
        return custominfoDao.save(custominfo);
    }

    @Override
    public Custominfo update(Custominfo custominfo) {
        return custominfoDao.save(custominfo);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Custominfo> findListByCustomId(Integer customId) {
        return custominfoDao.findListByCustomId(customId);
    }

    @Override
    public Page<Custominfo> pageByCustomId(Integer customId, Integer pageNum, Integer pageSize) {
        return custominfoDao.pageByCustomId(customId, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    @Transactional
    public void deleteById(HttpServletRequest request, Integer id) {
        Custominfo custominfo = getById(id);

        operatisService.addOperatisInfo(request, "删除在线定制楼盘 " + custominfo.getCustom().getName() + " 下的户型 " + custominfo.getName());

        // 删除户型详情时，删除该户型的所有套餐信息
        custompackagesService.deleteAllInfoByCustomInfoId(id);

        custominfoDao.delete(custominfo);
    }
}