package com.sixmac.service;

import com.sixmac.entity.Afflatus;
import com.sixmac.entity.vo.BeanVo;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 5:10.
 */
public interface AfflatusService extends ICommonService<Afflatus> {

    // 灵感集列表
    public Page<Afflatus> page(String afflatusName, String designerName, Integer status, Integer styleId, Integer areaId, Integer pageNum, Integer pageSize);

    // 灵感集列表
    public Page<Afflatus> page(Integer designerId, String afflatusName, Integer status, Integer styleId, Integer areaId, Integer pageNum, Integer pageSize);

    // 灵感集列表
    public Page<Afflatus> iPage(String key, String labels, Integer type, Integer styleId, Integer areaId, Integer pageNum, Integer pageSize);

    // 查询猜你所想列表
    public List<BeanVo> iFindLoveList(Integer afflatusId, Integer type, Integer styleId, Integer areaId);

    // 审核灵感集
    public void changeCheck(HttpServletRequest request, Integer afflatusId, Integer status, String reason);

    // 根据设计师id查询对应的灵感集列表
    public List<Afflatus> findListByDesignerId(Integer designerId);

    //查询待审核灵感图
    public List<Afflatus> findListByStatus();
}