package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.ShareRecordDao;
import com.sixmac.dao.UsersDao;
import com.sixmac.entity.ShareRecord;
import com.sixmac.entity.Users;
import com.sixmac.service.ShareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/5/5 0005 下午 5:50.
 */
@Service
public class ShareRecordServiceImpl implements ShareRecordService {

    @Autowired
    private ShareRecordDao shareRecordDao;

    @Autowired
    private UsersDao usersDao;

    @Override
    public List<ShareRecord> findAll() {
        return shareRecordDao.findAll();
    }

    @Override
    public Page<ShareRecord> find(int pageNum, int pageSize) {
        return shareRecordDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<ShareRecord> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public ShareRecord getById(int id) {
        return shareRecordDao.findOne(id);
    }

    @Override
    public ShareRecord deleteById(int id) {
        ShareRecord shareRecord = getById(id);
        shareRecordDao.delete(shareRecord);
        return shareRecord;
    }

    @Override
    public ShareRecord create(ShareRecord shareRecord) {
        return shareRecordDao.save(shareRecord);
    }

    @Override
    public ShareRecord update(ShareRecord shareRecord) {
        return shareRecordDao.save(shareRecord);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    @Transactional
    public void addShareRecord(Integer userId, Integer objectId, Integer objectType, Integer score) {
        ShareRecord tempShareRecord = shareRecordDao.findOneByParams(userId, objectId, objectType);
        // 查询是否有分享记录，如果有，直接跳过
        if (null == tempShareRecord) {
            Users users = usersDao.findOne(userId);
            users.setScore(users.getScore() + score);

            usersDao.save(users);

            ShareRecord shareRecord = new ShareRecord();
            shareRecord.setUser(users);
            shareRecord.setObjectId(objectId);
            shareRecord.setObjectType(objectType);

            shareRecordDao.save(shareRecord);
        }
    }
}