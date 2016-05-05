package com.sixmac.service;

import com.sixmac.entity.Custominfo;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008 下午 3:08.
 */
public interface CustominfoService extends ICommonService<Custominfo> {

    // 根据设计定制id查询户型列表
    public List<Custominfo> findListByCustomId(Integer customId);

    // 根据设计定制id查询户型列表（分页）
    public Page<Custominfo> pageByCustomId(Integer customId, Integer pageNum, Integer pageSize);

    public void deleteById(HttpServletRequest request, Integer id);
}