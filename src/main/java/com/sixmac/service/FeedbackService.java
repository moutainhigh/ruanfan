package com.sixmac.service;

import com.sixmac.entity.Feedback;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/3/8 0008 下午 2:21.
 */
public interface FeedbackService extends ICommonService<Feedback> {

    public Page<Feedback> page(String nickName, String mobile, String mail, int pageNum, int pageSize);

    public void deleteById(HttpServletRequest request, Integer id);

    public void deleteAll(HttpServletRequest request, int[] ids);
}