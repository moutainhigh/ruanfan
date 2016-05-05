package com.sixmac.service;

import com.sixmac.entity.Sysusers;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/23 0023 下午 12:01.
 */
public interface SysusersService extends ICommonService<Sysusers> {

    // 管理员列表（分页）
    public Page<Sysusers> page(String account, Integer roleId, Integer pageNum, Integer pageSize);

    // 根据管理员的角色id查询对应角色的人员数量
    public List<Sysusers> findListByRoleId(Integer roleId);

    // 根据id删除管理员信息
    public void deleteById(HttpServletRequest request, Integer id);

    // 根据id批量删除管理员信息
    public void deleteAll(HttpServletRequest request,int[] ids);
}