package com.sixmac.controller.api;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.core.ErrorCode;
import com.sixmac.core.bean.Result;
import com.sixmac.entity.*;
import com.sixmac.entity.vo.AppraisalVo;
import com.sixmac.service.*;
import com.sixmac.utils.APIFactory;
import com.sixmac.utils.JsonUtil;
import com.sixmac.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
@Controller
@RequestMapping(value = "api/mall")
public class MallApi extends CommonController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private SpikesService spikesService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private PackagesService packagesService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private OrdersinfoService ordersinfoService;

    @Autowired
    private PackageproductsService packageproductsService;

    @Autowired
    private MallPicService mallPicService;

    @Autowired
    private CouponService couponService;

    /**
     * @api {post} /api/mall/bannerList 首页banner图列表
     * @apiName mall.bannerList
     * @apiGroup mall
     * @apiSuccess {Object} list 首页banner图列表
     * @apiSuccess {Integer} list.id banner图id
     * @apiSuccess {String} list.cover 图片路径
     * @apiSuccess {Integer} list.type 类型，1=商品，2=套餐商品，3=特价商品
     * @apiSuccess {Integer} list.sourceId 关联目标id
     * @apiSuccess {String} list.updateTime 更新时间
     */
    @RequestMapping(value = "/bannerList")
    public void bannerList(HttpServletResponse response) {
        List<Banner> list = bannerService.findAll();

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/mall/newProductList 首页三张商品图和商品套餐图
     * @apiName mall.newProductList
     * @apiGroup mall
     * @apiSuccess {Object} list 商品图列表
     * @apiSuccess {Integer} list.id 图片id（1=商品单品，2=艺术品，3=设计师品牌，4=商品套餐）
     * @apiSuccess {Integer} list.name 图片所属类别
     * @apiSuccess {String} list.cover 图片路径
     * @apiSuccess {String} list.updateTime 更新时间
     */
    @RequestMapping(value = "/newProductList")
    public void newProductList(HttpServletResponse response) {
        List<MallPic> list = mallPicService.findAll();

        Result obj = new Result(true).data(createMap("list", list));
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/mall/spikeList 秒杀列表
     * @apiName mall.spikeList
     * @apiGroup mall
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数       <必传 />
     * @apiSuccess {Object} list 秒杀列表
     * @apiSuccess {Integer} list.id 秒杀id
     * @apiSuccess {String} list.name 秒杀名称
     * @apiSuccess {String} list.price 价格
     * @apiSuccess {String} list.oldPrice 原价
     * @apiSuccess {String} list.startTime 开始时间
     * @apiSuccess {String} list.endTime 结束时间
     * @apiSuccess {Integer} list.count 交易数量
     * @apiSuccess {Integer} list.showNum 浏览量
     * @apiSuccess {String} list.labels 标签
     * @apiSuccess {String} list.colors 颜色
     * @apiSuccess {String} list.sizes 尺寸
     * @apiSuccess {String} list.materials 材质
     * @apiSuccess {String} list.description 描述
     * @apiSuccess {String} list.createTime 创建时间
     * @apiSuccess {String} list.cover 封面图
     */
    @RequestMapping(value = "/spikeList")
    public void spikeList(HttpServletResponse response,
                          Integer pageNum,
                          Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Spikes> page = spikesService.page(pageNum, pageSize);

        for (Spikes spikes : page.getContent()) {
            spikes.setCover(imageService.getById(spikes.getCoverId()).getPath());
        }

        Map<java.lang.String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "coverId", "appraisalVoList", "imageList");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/mall/spikeInfo 秒杀详情
     * @apiName mall.spikeInfo
     * @apiGroup mall
     * @apiParam {Integer} spikesId 秒杀id       <必传 />
     * @apiSuccess {Object} spikeInfo 秒杀详情
     * @apiSuccess {Integer} spikeInfo.id 秒杀id
     * @apiSuccess {String} spikeInfo.name 秒杀名称
     * @apiSuccess {String} spikeInfo.price 价格
     * @apiSuccess {String} spikeInfo.oldPrice 原价
     * @apiSuccess {String} spikeInfo.startTime 开始时间
     * @apiSuccess {String} spikeInfo.endTime 结束时间
     * @apiSuccess {Integer} spikeInfo.count 交易数量
     * @apiSuccess {Integer} spikeInfo.showNum 浏览量
     * @apiSuccess {String} spikeInfo.labels 标签
     * @apiSuccess {String} spikeInfo.colors 颜色
     * @apiSuccess {String} spikeInfo.sizes 尺寸
     * @apiSuccess {String} spikeInfo.materials 材质
     * @apiSuccess {String} spikeInfo.description 描述
     * @apiSuccess {String} spikeInfo.createTime 创建时间
     * @apiSuccess {String} spikeInfo.cover 封面图
     * @apiSuccess {Object} spikeInfo.imageList 秒杀图片列表
     * @apiSuccess {Integer} spikeInfo.imageList.id 图片id
     * @apiSuccess {String} spikeInfo.imageList.path 图片路径
     * @apiSuccess {String} spikeInfo.imageList.description 图片描述
     * @apiSuccess {String} spikeInfo.imageList.demo 图片备注
     * @apiSuccess {String} spikeInfo.imageList.createTime 创建时间
     */
    @RequestMapping("spikeInfo")
    public void spikeInfo(HttpServletResponse response,
                          Integer spikesId) {
        if (null == spikesId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        Spikes spikes = spikesService.getById(spikesId);

        if (null == spikes) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        // 修改秒杀商品浏览量
        spikes.setShowNum(spikes.getShowNum() + 1);
        spikesService.update(spikes);

        spikes.setCover(imageService.getById(spikes.getCoverId()).getPath());
        spikes.setImageList(imageService.iFindList(spikesId, Constant.IMAGE_SPIKES));

        // 截取掉最后一位英文逗号
        spikes.setColors(spikes.getColors().substring(0, spikes.getColors().length() - 1));

        Result obj = new Result(true).data(createMap("spikeInfo", spikes));
        String result = JsonUtil.obj2ApiJson(obj, "coverId", "labelList", "objectId", "objectType", "thuPath", "width", "height");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/mall/list 商品列表
     * @apiName mall.list
     * @apiGroup mall
     * @apiParam {Integer} type 分类，1=单品，2=艺术品，3=设计师品牌
     * @apiParam {String} name 名称
     * @apiParam {Integer} merchantId 商户id
     * @apiParam {Integer} brandId 品牌id
     * @apiParam {Integer} sortId 产品种类id
     * @apiParam {Integer} isHot 是否首页推荐，0=否，1=是
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数       <必传 />
     * @apiSuccess {Object} list 商品列表
     * @apiSuccess {Integer} list.id 商品id
     * @apiSuccess {String} list.name 商品名称
     * @apiSuccess {String} list.price 价格
     * @apiSuccess {String} list.oldPrice 原价
     * @apiSuccess {Integer} list.type 分类，1=单品，2=艺术品，3=设计师品牌
     * @apiSuccess {String} list.place 产地
     * @apiSuccess {String} list.labels 标签
     * @apiSuccess {String} list.colors 颜色
     * @apiSuccess {String} list.sizes 尺寸
     * @apiSuccess {String} list.materials 材质
     * @apiSuccess {String} list.description 描述
     * @apiSuccess {String} list.cover 封面图
     * @apiSuccess {Integer} list.merchantId 商户id
     * @apiSuccess {Integer} list.isCoupon 是否有范票，0=没有，1=有
     * @apiSuccess {String} list.merchantName 商户名称
     * @apiSuccess {String} list.merchantHead 商户头像
     * @apiSuccess {String} list.merchantDescription 商户描述
     * @apiSuccess {Integer} list.brandId 品牌id
     * @apiSuccess {String} list.brandName 品牌名称
     * @apiSuccess {Integer} list.sortId 分类id
     * @apiSuccess {String} list.sortName 分类名称
     */
    @RequestMapping(value = "list")
    public void list(HttpServletResponse response,
                     Integer type,
                     String name,
                     Integer merchantId,
                     Integer brandId,
                     Integer sortId,
                     Integer isHot,
                     Integer pageNum,
                     Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Products> page = productsService.iPage(type, name, merchantId, brandId, sortId, isHot, pageNum, pageSize);
        List<Coupon> couponList = null;

        for (Products products : page.getContent()) {
            products.setCover(imageService.getById(products.getCoverId()).getPath());
            products.setMerchantId(products.getMerchant().getId());
            couponList = couponService.findListByMerchantId(products.getMerchantId());
            products.setIsCoupon(null == couponList || couponList.size() == 0 ? 0 : 1);
            products.setMerchantName(products.getMerchant().getNickName());
            products.setMerchantHead(products.getMerchant().getHead());
            products.setMerchantDescription(products.getMerchant().getDescription());
            products.setSortId(products.getSort().getId());
            products.setSortName(products.getSort().getName());
            products.setImageList(imageService.iFindList(products.getId(), Constant.IMAGE_PRODUCTS));
        }

        Map<java.lang.String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "merchant", "brand", "sort", "coverId", "isHot", "isCheck", "appraisalVoList");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/mall/info 商品详情
     * @apiName mall.info
     * @apiGroup mall
     * @apiParam {Integer} productId 商品id       <必传 />
     * @apiSuccess {Object} productInfo 商品详情
     * @apiSuccess {Integer} productInfo.id 商品id
     * @apiSuccess {String} productInfo.name 商品名称
     * @apiSuccess {String} productInfo.price 价格
     * @apiSuccess {String} productInfo.oldPrice 原价
     * @apiSuccess {Integer} productInfo.type 分类，1=单品，2=艺术品，3=设计师品牌
     * @apiSuccess {String} productInfo.place 产地
     * @apiSuccess {String} productInfo.labels 标签
     * @apiSuccess {String} productInfo.colors 颜色
     * @apiSuccess {String} productInfo.sizes 尺寸
     * @apiSuccess {String} productInfo.materials 材质
     * @apiSuccess {String} productInfo.showNum 人气
     * @apiSuccess {String} productInfo.count 交易数量
     * @apiSuccess {String} productInfo.description 描述
     * @apiSuccess {String} productInfo.cover 封面图
     * @apiSuccess {Integer} productInfo.merchantId 商户id
     * @apiSuccess {Integer} productInfo.isCoupon 是否有范票，0=没有，1=有
     * @apiSuccess {String} productInfo.merchantName 商户名称
     * @apiSuccess {String} productInfo.merchantHead 商户头像
     * @apiSuccess {String} productInfo.merchantDescription 商户介绍
     * @apiSuccess {Integer} productInfo.brandId 品牌id
     * @apiSuccess {String} productInfo.brandName 品牌名称
     * @apiSuccess {Integer} productInfo.sortId 分类id
     * @apiSuccess {String} productInfo.sortName 分类名称
     * @apiSuccess {String} productInfo.createTime 创建时间
     * @apiSuccess {Object} productInfo.imageList 商品图片列表
     * @apiSuccess {Integer} productInfo.imageList.id 图片id
     * @apiSuccess {String} productInfo.imageList.path 图片路径
     * @apiSuccess {String} productInfo.imageList.description 图片描述
     * @apiSuccess {String} productInfo.imageList.demo 图片备注
     * @apiSuccess {String} productInfo.imageList.createTime 创建时间
     * @apiSuccess {Object} productInfo.imageList.labelList 图片标签列表
     * @apiSuccess {Integer} productInfo.imageList.labelList.id 标签id
     * @apiSuccess {String} productInfo.imageList.labelList.name 标签名称
     * @apiSuccess {String} productInfo.imageList.labelList.description 标签描述
     * @apiSuccess {String} productInfo.imageList.labelList.leftPoint 标签左边距（实际使用时，数值乘以二）
     * @apiSuccess {String} productInfo.imageList.labelList.topPoint 标签上边距（实际使用时，数值乘以二）
     * @apiSuccess {Object} productInfo.similarList 类似商品列表（信息与本接口相同）
     */
    @RequestMapping("info")
    public void info(HttpServletResponse response,
                     Integer productId) {
        if (null == productId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        Products products = productsService.getById(productId);

        if (null == products) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        products.setShowNum(products.getShowNum() + 1);
        productsService.update(products);

        products.setCover(imageService.getById(products.getCoverId()).getPath());
        products.setMerchantId(products.getMerchant().getId());
        List<Coupon> couponList = couponService.findListByMerchantId(products.getMerchantId());
        products.setIsCoupon(null == couponList || couponList.size() == 0 ? 0 : 1);
        products.setMerchantName(products.getMerchant().getNickName());
        products.setMerchantHead(products.getMerchant().getHead());
        products.setMerchantDescription(products.getMerchant().getDescription());
        products.setSortId(products.getSort().getId());
        products.setSortName(products.getSort().getName());
        products.setImageList(imageService.iFindList(products.getId(), Constant.IMAGE_PRODUCTS));
        products.setSimilarList(productsService.iFindListBySortAndStyle(productId, products.getType(), products.getSort().getId()));

        Result obj = new Result(true).data(createMap("productInfo", products));
        String result = JsonUtil.obj2ApiJson(obj, "merchant", "brand", "sort", "coverId", "isHot", "isAdd", "isCheck", "objectId", "objectType");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/mall/packageList 套餐列表
     * @apiName mall.packageList
     * @apiGroup mall
     * @apiParam {Integer} brandId 品牌id
     * @apiParam {String} name 套餐名称
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数       <必传 />
     * @apiSuccess {Object} list 套餐列表
     * @apiSuccess {Integer} list.id 套餐id
     * @apiSuccess {String} list.name 套餐名称
     * @apiSuccess {String} list.price 价格
     * @apiSuccess {String} list.oldPrice 原价
     * @apiSuccess {String} list.labels 标签
     * @apiSuccess {String} list.description 描述
     * @apiSuccess {Integer} list.count 交易数量
     * @apiSuccess {String} list.createTime 创建时间
     * @apiSuccess {Object} list.productsList 商品列表
     * @apiSuccess {Integer} list.productsList.id 商品id
     * @apiSuccess {String} list.productsList.name 商品名称
     * @apiSuccess {String} list.productsList.price 价格
     * @apiSuccess {String} list.productsList.oldPrice 原价
     * @apiSuccess {Integer} list.productsList.type 分类，1=单品，2=艺术品，3=设计师品牌
     * @apiSuccess {String} list.productsList.place 产地
     * @apiSuccess {String} list.productsList.labels 标签
     * @apiSuccess {String} list.productsList.colors 颜色
     * @apiSuccess {String} list.productsList.sizes 尺寸
     * @apiSuccess {String} list.productsList.materials 材质
     * @apiSuccess {String} list.productsList.description 描述
     * @apiSuccess {String} list.productsList.cover 封面图
     * @apiSuccess {Integer} list.productsList.merchantId 商户id
     * @apiSuccess {String} list.productsList.merchantName 商户名称
     * @apiSuccess {String} list.productsList.merchantHead 商户头像
     * @apiSuccess {String} list.productsList.merchantDescription 商户描述
     * @apiSuccess {Integer} list.productsList.brandId 品牌id
     * @apiSuccess {String} list.productsList.brandName 品牌名称
     * @apiSuccess {Integer} list.productsList.sortId 分类id
     * @apiSuccess {String} list.productsList.sortName 分类名称
     * @apiSuccess {String} list.cover 封面图
     * @apiSuccess {Integer} list.brandId 品牌id
     * @apiSuccess {String} list.brandName 品牌名称
     */
    @RequestMapping(value = "/packageList")
    public void packageList(HttpServletResponse response,
                            Integer brandId,
                            String name,
                            Integer pageNum,
                            Integer pageSize) {
        if (null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Packages> page = packagesService.iPage(brandId, name, pageNum, pageSize);

        // 获取该套餐对应的所有商品
        List<Packageproducts> list = null;
        List<Products> productList = new ArrayList<Products>();

        for (Packages packages : page.getContent()) {
            // 设置品牌信息
            packages.setBrandId(packages.getBrand().getId());
            packages.setBrandName(packages.getBrand().getName());

            // 获取商品列表
            list = packagesService.findListByPackageId(packages.getId());
            for (Packageproducts pack : list) {
                productList.add(pack.getProduct());
            }

            changeList(productList);

            packages.setProductsList(productList);

            productList = new ArrayList<Products>();
        }

        Map<java.lang.String, Object> dataMap = APIFactory.fitting(page);

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "merchant", "brand", "sort", "isHot", "isCheck", "isAdd", "productNum", "imageList", "similarList", "appraisalVoList");
        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /api/mall/packageInfo 套餐详情
     * @apiName mall.packageInfo
     * @apiGroup mall
     * @apiParam {Integer} packageId 套餐id       <必传 />
     * @apiSuccess {Object} packageInfo 套餐列表
     * @apiSuccess {Integer} packageInfo.id 套餐id
     * @apiSuccess {String} packageInfo.name 套餐名称
     * @apiSuccess {String} packageInfo.price 价格
     * @apiSuccess {String} packageInfo.oldPrice 原价
     * @apiSuccess {String} packageInfo.labels 标签
     * @apiSuccess {String} packageInfo.description 描述
     * @apiSuccess {Integer} packageInfo.showNum 人气
     * @apiSuccess {Integer} packageInfo.count 交易数量
     * @apiSuccess {String} packageInfo.createTime 创建时间
     * @apiSuccess {Object} packageInfo.productsList 商品列表
     * @apiSuccess {Integer} packageInfo.productsList.id 商品id
     * @apiSuccess {String} packageInfo.productsList.name 商品名称
     * @apiSuccess {String} packageInfo.productsList.price 价格
     * @apiSuccess {String} packageInfo.productsList.oldPrice 原价
     * @apiSuccess {Integer} packageInfo.productsList.type 分类，1=单品，2=艺术品，3=设计师品牌
     * @apiSuccess {String} packageInfo.productsList.place 产地
     * @apiSuccess {String} packageInfo.productsList.labels 标签
     * @apiSuccess {String} packageInfo.productsList.colors 颜色
     * @apiSuccess {String} packageInfo.productsList.sizes 尺寸
     * @apiSuccess {String} packageInfo.productsList.materials 材质
     * @apiSuccess {String} packageInfo.productsList.description 描述
     * @apiSuccess {String} packageInfo.productsList.cover 封面图
     * @apiSuccess {Integer} packageInfo.productsList.merchantId 商户id
     * @apiSuccess {String} packageInfo.productsList.merchantName 商户名称
     * @apiSuccess {String} packageInfo.productsList.merchantHead 商户头像
     * @apiSuccess {String} packageInfo.productsList.merchantDescription 商户介绍
     * @apiSuccess {Integer} packageInfo.productsList.brandId 品牌id
     * @apiSuccess {String} packageInfo.productsList.brandName 品牌名称
     * @apiSuccess {Integer} packageInfo.productsList.sortId 分类id
     * @apiSuccess {String} packageInfo.productsList.sortName 分类名称
     * @apiSuccess {String} packageInfo.cover 封面图
     * @apiSuccess {Integer} packageInfo.brandId 品牌id
     * @apiSuccess {String} packageInfo.brandName 品牌名称
     * @apiSuccess {Object} packageInfo.imageList 套餐图片列表
     * @apiSuccess {Integer} packageInfo.imageList.id 图片id
     * @apiSuccess {String} packageInfo.imageList.path 图片路径
     * @apiSuccess {String} packageInfo.imageList.description 描述
     * @apiSuccess {String} packageInfo.imageList.demo 备注
     * @apiSuccess {String} packageInfo.imageList.createTime 创建时间
     * @apiSuccess {Object} packageInfo.similarList 类似商品列表（信息与本接口相同）
     */
    @RequestMapping("packageInfo")
    public void packageInfo(HttpServletResponse response,
                            Integer packageId) {
        if (null == packageId) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }
        Packages packages = packagesService.getById(packageId);

        if (null == packages) {
            WebUtil.printApi(response, new Result(false).msg(ErrorCode.ERROR_CODE_0003));
            return;
        }

        packages.setShowNum(packages.getShowNum() + 1);
        packagesService.update(packages);

        // 设置品牌信息
        packages.setBrandId(packages.getBrand().getId());
        packages.setBrandName(packages.getBrand().getName());

        List<Products> productList = new ArrayList<Products>();

        // 获取商品列表
        List<Packageproducts> list = packagesService.findListByPackageId(packages.getId());
        for (Packageproducts pack : list) {
            productList.add(pack.getProduct());
        }

        changeList(productList);

        packages.setProductsList(productList);

        packages.setImageList(packageproductsService.findImageListByPackageId(packageId, Constant.PACKAGE_TYPE_PRODUCT));

        packages.setSimilarList(packagesService.iFindListByBrand(packageId, packages.getBrand().getId()));

        Result obj = new Result(true).data(createMap("packageInfo", packages));
        String result = JsonUtil.obj2ApiJson(obj, "merchant", "brand", "sort", "isHot", "isAdd", "isCheck", "productNum", "labelList", "objectId", "objectType", "thuPath", "width", "height");
        WebUtil.printApi(response, result);
    }

    private List<AppraisalVo> findList(Integer objectId, Integer type) {
        List<Ordersinfo> ordersinfoList = null;

        switch (type) {
            case 2:
                // 套餐订单
                ordersinfoList = ordersinfoService.findListByPackageOrderId(objectId);
                break;
            case 1:
            case 3:
                // 秒杀订单
                ordersinfoList = ordersinfoService.findListBySourceId(objectId, type);
                break;
        }

        return getList(ordersinfoList);
    }

    private List<AppraisalVo> getList(List<Ordersinfo> list) {
        List<AppraisalVo> appraisalVoList = new ArrayList<AppraisalVo>();
        AppraisalVo appra = null;

        for (Ordersinfo ordersInfo : list) {
            appra = new AppraisalVo();
            appra.setUserId(ordersInfo.getOrder().getUser().getId());
            appra.setUserName(ordersInfo.getOrder().getUser().getNickName());
            appra.setUserHead(ordersInfo.getOrder().getUser().getHeadPath());
            appra.setColors(ordersInfo.getColors());
            appra.setSizes(ordersInfo.getSizes());
            appra.setMaterials(ordersInfo.getMaterials());
            appra.setCount(ordersInfo.getCount());
            appra.setStar(ordersInfo.getStar());
            appra.setContent(ordersInfo.getComment());
            appra.setCreateTime(ordersInfo.getOrder().getCreateTime());

            appraisalVoList.add(appra);
        }

        return appraisalVoList;
    }

    private void changeList(List<Products> list) {
        for (Products product : list) {
            product.setCover(imageService.getById(product.getCoverId()).getPath());
            product.setMerchantId(product.getMerchant().getId());
            product.setMerchantName(product.getMerchant().getNickName());
            product.setMerchantHead(product.getMerchant().getHead());
            product.setMerchantDescription(product.getMerchant().getDescription());
            product.setSortId(product.getSort().getId());
            product.setSortName(product.getSort().getName());
        }
    }

    /**
     * @api {post} /api/mall/appraisalList 评价列表
     * @apiName mall.appraisalList
     * @apiGroup mall
     * @apiParam {Integer} id 关联目标id       <必传 />
     * @apiParam {Integer} type 评价类型，1=商品，2=套餐，3=秒杀       <必传 />
     * @apiParam {Integer} pageNum 页码       <必传 />
     * @apiParam {Integer} pageSize 每页显示条数       <必传 />
     * @apiSuccess {Object} list 商品评价列表
     * @apiSuccess {Integer} list.userId 评价人id
     * @apiSuccess {String} list.userName 评价人名称
     * @apiSuccess {String} list.userHead 评价人头像
     * @apiSuccess {String} list.colors 颜色
     * @apiSuccess {String} list.sizes 尺寸
     * @apiSuccess {String} list.materials 材质
     * @apiSuccess {Integer} list.count 数量
     * @apiSuccess {Integer} list.star 评价星级
     * @apiSuccess {String} list.content 评价内容
     * @apiSuccess {String} list.createTime 评价时间
     */
    @RequestMapping("appraisalList")
    public void appraisalList(HttpServletResponse response,
                              Integer id,
                              Integer type,
                              Integer pageNum,
                              Integer pageSize) {
        if (null == id || null == type || null == pageNum || null == pageSize) {
            WebUtil.printJson(response, new Result(false).msg(ErrorCode.ERROR_CODE_0002));
            return;
        }

        Page<Ordersinfo> page = null;

        switch (type) {
            case 2:
                // 套餐订单
                page = ordersinfoService.pageByPackageOrderId(id, pageNum, pageSize);
                break;
            case 1:
            case 3:
                // 秒杀订单
                page = ordersinfoService.pageBySourceId(id, type, pageNum, pageSize);
                break;
        }

        Map<String, Object> dataMap = APIFactory.fittingPlus(page, getList(page.getContent()));

        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
    }
}
