package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.LabelDao;
import com.sixmac.entity.Label;
import com.sixmac.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/10 0010 下午 3:09.
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelDao labelDao;

    @Override
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    @Override
    public Page<Label> find(int pageNum, int pageSize) {
        return labelDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Label> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Label getById(int id) {
        return labelDao.findOne(id);
    }

    @Override
    public Label deleteById(int id) {
        Label label = getById(id);
        labelDao.delete(label);
        return label;
    }

    @Override
    public Label create(Label label) {
        return labelDao.save(label);
    }

    @Override
    public Label update(Label label) {
        return labelDao.save(label);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Label> findListByParams(Integer objectId, Integer objectType) {
        List<Label> list = labelDao.findListByParams(objectId, objectType);

        for (Label label : list) {
            label.setProductId(label.getProduct().getId());
        }

        return list;
    }

    @Override
    public Label findLastPosition(Integer objectId, String labelId) {
        return labelDao.findOneByObjectIdAndLabelId(objectId, labelId);
    }

    @Override
    public Label searchByLabelId(String labelId) {
        return labelDao.findOneByLabelId(labelId);
    }
}