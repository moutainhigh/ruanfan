package com.sixmac.service;

import com.sixmac.entity.Journal;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 下午 2:34.
 */
public interface JournalService extends ICommonService<Journal> {

    // 根据用户id查询对应的日志列表（分页）
    public Page<Journal> iPage(Integer userId, Integer pageNum, Integer pageSize);

    // 根据用户id查询对应的日志列表
    public List<Journal> iFindListByUserId(Integer userId);

    //查询新增日志
    public List<Journal> FindListNew();

    public void deleteById(HttpServletRequest request, Integer id);
}