package com.sixmac.service;

import com.sixmac.entity.Propertys;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 10:13.
 */
public interface PropertysService extends ICommonService<Propertys> {

    // 地产列表
    public Page<Propertys> iPage(String name, String address, Integer pageNum, Integer pageSize);

    // 根据地产id查询楼盘列表
    public List<Propertys> iPageByParentId(Integer parentId);

    // 根据地产id查询楼盘列表
    public List<Propertys> pageByParentId(Integer parentId);

    // 地产列表
    public Page<Propertys> page(Integer pageNum, Integer pageSize);

    // 根据地产id查询对应的楼盘列表
    public Page<Propertys> pageChild(Integer parentId, Integer pageNum, Integer pageSize);

    // 查询楼盘列表
    public Page<Propertys> pageChild(Integer pageNum, Integer pageSize);

    public void deleteById(HttpServletRequest request, Integer id);

    // 查询序号，turn=0代表查询排序号第一位的地产信息，turn=1代表查询排序号最后一位的地产信息
    // parentId=0时，代表查询的是地产，否则查询对应地产下的楼盘信息
    public Propertys getShowTurn(Integer turn, Integer parentId);

    // 排序，turn=0，上移；turn=1，下移
    public Integer changeShowTurn(Integer propertyId,Integer turn);
}