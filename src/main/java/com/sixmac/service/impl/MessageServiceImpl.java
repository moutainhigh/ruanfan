package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.MessageDao;
import com.sixmac.entity.Message;
import com.sixmac.service.MessageService;
import com.sixmac.service.OperatisService;
import org.apache.commons.lang.StringUtils;
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
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/14 0014 下午 12:55.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Message> findAll() {
        return messageDao.findAll();
    }

    @Override
    public Page<Message> find(int pageNum, int pageSize) {
        return messageDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Message> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Message getById(int id) {
        return messageDao.findOne(id);
    }

    @Override
    public Message deleteById(int id) {
        Message message = getById(id);
        messageDao.delete(message);
        return message;
    }

    @Override
    public Message create(Message message) {
        return messageDao.save(message);
    }

    @Override
    public Message update(Message message) {
        return messageDao.save(message);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Message> findListByType(String type) {
        return messageDao.findListByType(type);
    }

    @Override
    public Page<Message> pageByType(String type, Integer pageNum, Integer pageSize) {
        return messageDao.pageByType(type, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Message> page(final String title, final String type, final String description, int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id", "updateTime");

        Page<Message> page = messageDao.findAll(new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(title)) {
                    Predicate pre = cb.like(root.get("title").as(String.class), "%" + title + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(type)) {
                    Predicate pre = cb.like(root.get("type").as(String.class), "%" + type + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(description)) {
                    Predicate pre = cb.like(root.get("description").as(String.class), "%" + description + "%");
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
    public void deleteById(HttpServletRequest request, Integer id) {
        Message message = getById(id);

        operatisService.addOperatisInfo(request, "删除消息 " + message.getTitle());

        messageDao.delete(message);
    }

    @Override
    public void deleteAll(HttpServletRequest request, int[] ids) {
        for (int id : ids) {
            deleteById(request, id);
        }
    }
}