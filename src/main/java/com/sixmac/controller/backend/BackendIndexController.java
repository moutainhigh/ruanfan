package com.sixmac.controller.backend;

import com.sixmac.core.Constant;
import com.sixmac.entity.*;
import com.sixmac.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wangbin on 2015/7/29.
 */
@Controller
@RequestMapping(value = "backend")
public class BackendIndexController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private SysusersService sysusersService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private AfflatusService afflatusService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private PackagesService packagesService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private SpikesService spikesService;

    @Autowired
    private ModulesService modulesService;

    @Autowired
    private RoleModulesService roleModulesService;

    @Autowired
    private RecordCountService recordCountService;

    @RequestMapping(value = "/dashboard")
    public String dashboard(HttpServletRequest request,
                            ModelMap model) {
        // 获取当前登录人信息
        Integer loginUserId = (Integer) request.getSession().getAttribute(Constant.CURRENT_USER_ID);
        Sysusers sysusers = sysusersService.getById(loginUserId);

        // 根据当前登录人的角色id获取对应的权限列表
        List<Modules> modulesList = modulesService.findFirstList(sysusers.getRole().getId());
        List<Rolemodules> roleModuleList = roleModulesService.findListByRoleId(sysusers.getRole().getId());

        for (Modules module : modulesList) {
            for (Rolemodules roleModule : roleModuleList) {
                if (module.getId() == roleModule.getModule().getParentId()) {
                    module.getModuleList().add(roleModule.getModule());
                }
            }
        }

        // 将该用户信息和权限列表放入界面中
        request.getSession().setAttribute("menu_moduleList", modulesList);
        request.getSession().setAttribute("menu_sysUser", sysusers);

        // 只有当前角色是超级管理员时，才查询以下数据
        if (sysusers.getRole().getId() == Constant.ADMIN_ID) {
            // 查询新增会员数量
            List<Users> addUserList = usersService.findListNew();
            model.addAttribute("addUserNum", addUserList.size());

            //查询新增订单
            List<Orders> addOrdersList = ordersService.findListNew();
            model.addAttribute("addOrdersNum", addOrdersList.size());

            //查询新增评价
            List<Comment> addCommentsList = commentService.findListNew();
            model.addAttribute("addCommentsNum", addCommentsList.size());

            //查询新增预约
            List<Reserve> addReserveList = reserveService.findListNew();
            model.addAttribute("addReserveNum", addReserveList.size());

            //查询待审核商品
            List<Products> isCheckList = productsService.findListCheck();
            model.addAttribute("checkNum", isCheckList.size());

            //查询带待审核灵感图
            List<Afflatus> afflatusesList = afflatusService.findListByStatus();
            model.addAttribute("afflausesNum", afflatusesList.size());

            //查询新增日志
            List<Journal> journalsList = journalService.FindListNew();
            model.addAttribute("journalsNum", journalsList.size());


            //查询待发货订单数量
            List<Orders> list1 = ordersService.findListByStatus(Constant.ORDERS_STATUS_001);
            model.addAttribute("num1", list1.size());

            // 查询待支付订单数量
            List<Orders> list2 = ordersService.findListByStatus(Constant.ORDERS_STATUS_000);
            model.addAttribute("num2", list2.size());

            //查询待确认订单数量
            List<Orders> list3 = ordersService.findListByStatus(Constant.ORDERS_STATUS_002);
            model.addAttribute("num3", list3.size());

            //查询已完成订单数量
            List<Orders> list4 = ordersService.findListByStatus(Constant.ORDERS_STATUS_004);
            model.addAttribute("num4", list4.size());


            // 查询商品总数
            List<Products> productsList = productsService.findList();
            model.addAttribute("productTotalNum", productsList.size());

            //查询上架商品数量
            List<Products> productsAddList = productsService.findListAdd();
            model.addAttribute("addNum", productsAddList.size());

            //查询下架商品数量
            List<Products> productsesDownList = productsService.findListDown();
            model.addAttribute("downNum", productsesDownList.size());

            //查询商品套餐数量
            List<Packages> packagesList = packagesService.findAll();
            model.addAttribute("packagesNum", packagesList.size());

            //查询秒杀商品数量
            List<Spikes> spikesList = spikesService.findAll();
            model.addAttribute("spikesNum", spikesList.size());

            //查询昨日新增商品
            List<Products> newList = productsService.findListNew();
            model.addAttribute("productNewNum", newList.size());

            // 查询今日访问人数和在线人数
            RecordCount recordCount = recordCountService.getById(1);
            model.addAttribute("visitCount", recordCount.getVisitCount());
            model.addAttribute("onlineCount", recordCount.getOnlineCount());
        }

        return "backend/控制面板";
    }
}
