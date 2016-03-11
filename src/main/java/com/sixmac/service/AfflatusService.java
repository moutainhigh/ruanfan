package com.sixmac.service;

import com.sixmac.entity.Afflatus;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 5:10.
 */
public interface AfflatusService extends ICommonService<Afflatus> {

    // 灵感集列表
    public Page<Afflatus> page(String afflatusName, String designerName, Integer status, Integer styleId, Integer areaId, Integer pageNum, Integer pageSize);

    // 灵感集列表
    public Page<Afflatus> iPage(Integer type, Integer styleId, Integer areaId, Integer pageNum, Integer pageSize);

    // 查询猜你所想列表
    public List<Afflatus> iFindLoveList(Integer afflatusId, Integer type, Integer styleId, Integer areaId);
}