package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.AttentionsDao;
import com.sixmac.entity.Attentions;
import com.sixmac.entity.Users;
import com.sixmac.service.AttentionsService;
import com.sixmac.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 2:39.
 */
@Service
public class AttentionsServiceImpl implements AttentionsService {

    @Autowired
    private AttentionsDao attentionsDao;

    @Override
    public List<Attentions> findAll() {
        return attentionsDao.findAll();
    }

    @Override
    public Page<Attentions> find(int pageNum, int pageSize) {
        return attentionsDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Attentions> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Attentions getById(int id) {
        return attentionsDao.findOne(id);
    }

    @Override
    public Attentions deleteById(int id) {
        Attentions attentions = getById(id);
        attentionsDao.delete(attentions);
        return attentions;
    }

    @Override
    public Attentions create(Attentions attentions) {
        return attentionsDao.save(attentions);
    }

    @Override
    public Attentions update(Attentions attentions) {
        return attentionsDao.save(attentions);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void iCreate(Users users, Integer objectId, Integer objectType) {
        Attentions attentions = new Attentions();
        attentions.setUser(users);
        attentions.setObjectId(objectId);
        attentions.setObjectType(objectType);

        attentionsDao.save(attentions);
    }

    @Override
    public Attentions iFindOne(Integer userId, Integer objectId, Integer objectType) {
        Attentions attentions = attentionsDao.iFindOne(userId, objectId, objectType);
        if (null != attentions && null != attentions.getUser()) {
            attentions.getUser().setHeadPath(PathUtils.getRemotePath() + attentions.getUser().getHeadPath());
        }
        return attentions;
    }

    @Override
    public List<Attentions> iFindList(Integer objectId, Integer objectType) {
        List<Attentions> list = attentionsDao.iFindList(objectId, objectType);

        for (Attentions attentions : list) {
            attentions.getUser().setHeadPath(PathUtils.getRemotePath() + attentions.getUser().getHeadPath());
        }

        return list;
    }

    @Override
    public List<Attentions> iFindListByUserId(Integer userId) {
        return attentionsDao.iFindListByUserId(userId);
    }

    @Override
    public Page<Attentions> iPage(Integer userId, Integer pageNum, Integer pageSize) {
        return attentionsDao.pageByUserId(userId, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Attentions> iPageFans(Integer userId, Integer pageNum, Integer pageSize) {
        return attentionsDao.pageFansByUserId(userId, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }
}