package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.AfflatusDao;
import com.sixmac.dao.MerchantsDao;
import com.sixmac.dao.StylesDao;
import com.sixmac.dao.VirtualsDao;
import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Merchants;
import com.sixmac.entity.Styles;
import com.sixmac.entity.Virtuals;
import com.sixmac.service.OperatisService;
import com.sixmac.service.StylesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 2:23.
 */
@Service
public class StylesServiceImpl implements StylesService {

    @Autowired
    private StylesDao stylesDao;

    @Autowired
    private AfflatusDao afflatusDao;

    @Autowired
    private MerchantsDao merchantsDao;

    @Autowired
    private VirtualsDao virtualsDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Styles> findAll() {
        return stylesDao.findAll();
    }

    @Override
    public Page<Styles> find(int pageNum, int pageSize) {
        return stylesDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Styles> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Styles getById(int id) {
        return stylesDao.findOne(id);
    }

    @Override
    public Styles deleteById(int id) {
        Styles styles = getById(id);
        stylesDao.delete(styles);
        return styles;
    }

    @Override
    public Styles create(Styles styles) {
        return stylesDao.save(styles);
    }

    @Override
    public Styles update(Styles styles) {
        return stylesDao.save(styles);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Integer findListByStyleId(Integer styleId) {
        Integer count = 0;

        // 获取与该风格关联的灵感集信息
        List<Afflatus> afflatusList = afflatusDao.iFindListByStyleId(styleId);

        // 获取与该风格关联的商户信息
        List<Merchants> merchantsList = merchantsDao.iFindListByStyleId(styleId);

        // 获取与该风格关联的虚拟体验信息
        List<Virtuals> virtualsList = virtualsDao.iFindListByStyleId(styleId);

        // 将所有关联数量相加，并返回
        count = afflatusList.size() + merchantsList.size() + virtualsList.size();

        return count;
    }

    @Override
    public Styles getFirstStyle() {
        List<Styles> list = stylesDao.findAll();
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void deleteById(HttpServletRequest request, Integer id) {
        Styles styles = getById(id);

        operatisService.addOperatisInfo(request, "删除风格分类 " + styles.getName());

        stylesDao.delete(styles);
    }
}