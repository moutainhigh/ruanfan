package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.*;
import com.sixmac.entity.Afflatus;
import com.sixmac.entity.Image;
import com.sixmac.entity.Message;
import com.sixmac.entity.vo.BeanVo;
import com.sixmac.service.AfflatusService;
import com.sixmac.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 5:10.
 */
@Service
public class AfflatusServiceImpl implements AfflatusService {

    @Autowired
    private AfflatusDao afflatusDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private CollectDao collectDao;

    @Autowired
    private GamsDao gamsDao;

    @Autowired
    private ReserveDao reserveDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private LabelDao labelDao;

    @Override
    public List<Afflatus> findAll() {
        return afflatusDao.findAll();
    }

    @Override
    public Page<Afflatus> find(int pageNum, int pageSize) {
        return afflatusDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Afflatus> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Afflatus getById(int id) {
        return afflatusDao.findOne(id);
    }

    @Override
    public Afflatus deleteById(int id) {
        Afflatus afflatus = getById(id);
        afflatusDao.delete(afflatus);
        return afflatus;
    }

    @Override
    public Afflatus create(Afflatus afflatus) {
        return afflatusDao.save(afflatus);
    }

    @Override
    public Afflatus update(Afflatus afflatus) {
        return afflatusDao.save(afflatus);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Afflatus> page(String afflatusName, String designerName, Integer status, Integer styleId, Integer areaId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Afflatus> page = afflatusDao.findAll(new Specification<Afflatus>() {
            @Override
            public Predicate toPredicate(Root<Afflatus> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (afflatusName != null && !afflatusName.equals("")) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + afflatusName + "%");
                    predicateList.add(pre);
                }
                if (designerName != null && !designerName.equals("")) {
                    Predicate pre = cb.like(root.get("designer").get("nickName").as(String.class), "%" + designerName + "%");
                    predicateList.add(pre);
                }
                if (null != status) {
                    Predicate pre = cb.equal(root.get("status").as(Integer.class), status);
                    predicateList.add(pre);
                }
                if (styleId != null) {
                    Predicate pre = cb.equal(root.get("style").get("id").as(Integer.class), styleId);
                    predicateList.add(pre);
                }
                if (areaId != null) {
                    Predicate pre = cb.equal(root.get("area").get("id").as(Integer.class), areaId);
                    predicateList.add(pre);
                }
                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }

                if (result != null) {
                    query.where(result);
                }
                return query.getGroupRestriction();
            }

        }, pageRequest);

        return page;
    }

    @Override
    public Page<Afflatus> iPage(Integer type, Integer styleId, Integer areaId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Afflatus> page = afflatusDao.findAll(new Specification<Afflatus>() {
            @Override
            public Predicate toPredicate(Root<Afflatus> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (null != type) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
                    predicateList.add(pre);
                }
                if (styleId != null) {
                    Predicate pre = cb.equal(root.get("style").get("id").as(Integer.class), styleId);
                    predicateList.add(pre);
                }
                if (areaId != null) {
                    Predicate pre = cb.equal(root.get("area").get("id").as(Integer.class), areaId);
                    predicateList.add(pre);
                }
                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }

                if (result != null) {
                    query.where(result);
                }
                return query.getGroupRestriction();
            }

        }, pageRequest);

        return page;
    }

    @Override
    public List<BeanVo> iFindLoveList(Integer afflatusId, Integer type, Integer styleId, Integer areaId) {
        List<Afflatus> list = afflatusDao.iFindLoveList(afflatusId, type, styleId, areaId);
        List<BeanVo> beanVoList = new ArrayList<BeanVo>();
        BeanVo beanVo = null;

        for (Afflatus afflatus : list) {
            beanVo = new BeanVo();
            beanVo.setId(afflatus.getId());
            beanVo.setName(afflatus.getName());
            beanVo.setPath(PathUtils.getRemotePath() + imageDao.findOne(afflatus.getCoverId()).getPath());

            beanVoList.add(beanVo);
        }

        return beanVoList;
    }

    @Override
    @Transactional
    public void changeCheck(Integer afflatusId, Integer status, String reason) {
        Afflatus afflatus = afflatusDao.findOne(afflatusId);
        afflatus.setStatus(status);

        // 修改审核状态
        afflatusDao.save(afflatus);

        // 添加系统消息
        Message message = new Message();
        message.setTitle("系统消息");
        message.setType(Constant.MESSAGE_DESIGNERS);
        if (afflatus.getType() == 1) {
            message.setDescription("发布的灵感单图审核" + (status == 1 ? "通过" : "不通过，驳回原因：" + reason));
        } else {
            message.setDescription("发布的灵感套图 " + afflatus.getName() + " 审核" + (status == 1 ? "通过" : "不通过，驳回原因：" + reason));
        }
        message.setCreateTime(new Date());

        messageDao.save(message);
    }
}