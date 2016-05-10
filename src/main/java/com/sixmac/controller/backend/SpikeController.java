package com.sixmac.controller.backend;

import com.sixmac.common.DataTableFactory;
import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.entity.Image;
import com.sixmac.entity.Spikes;
import com.sixmac.service.ImageService;
import com.sixmac.service.OperatisService;
import com.sixmac.service.SpikesService;
import com.sixmac.utils.DateUtils;
import com.sixmac.utils.WebUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
@Controller
@RequestMapping(value = "backend/spikes")
public class SpikeController extends CommonController {

    @Autowired
    private SpikesService spikesService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private OperatisService operatisService;

    @RequestMapping("index")
    public String index() {
        return "backend/秒杀列表";
    }

    @RequestMapping("/list")
    public void list(HttpServletResponse response,
                     String name,
                     Integer status,
                     Integer draw,
                     Integer start,
                     Integer length) {
        if (null == start || start == 0) {
            start = 1;
        }
        int pageNum = getPageNum(start, length);

        name = "%" + name + "%";

        Page<Spikes> page = spikesService.page(name, status, pageNum, length);

        // 判断当前秒杀的状态
        for (Spikes spike : page.getContent()) {
            if (spike.getStartTime().after(new Date())) {
                spike.setStatus(0);
            }
            if (spike.getStartTime().before(new Date()) && spike.getEndTime().after(new Date())) {
                spike.setStatus(1);
            }
            if (spike.getEndTime().before(new Date())) {
                spike.setStatus(2);
            }
        }

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping("add")
    public String add(ModelMap model, Integer id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        // 如果id不为空，则该操作是编辑，否则是新增
        if (null != id) {
            Spikes spikes = spikesService.getById(id);
            model.addAttribute("spikes", spikes);

            // 如果秒杀详情不为空，则查询对应的图片集合
            if (null != spikes) {
                List<Image> imageList = imageService.iFindList(spikes.getId(), Constant.IMAGE_SPIKES);
                for (Image image : imageList) {
                    map = new HashMap<String, Object>();
                    map.put("id", image.getId());
                    map.put("path", image.getPath());

                    list.add(map);
                }
            }
        }

        model.addAttribute("imageList", JSONArray.fromObject(list));

        return "backend/新增秒杀";
    }

    /**
     * 删除秒杀信息
     *
     * @param spikeId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(HttpServletRequest request, Integer spikeId) {
        try {
            spikesService.deleteById(request, spikeId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 新增秒杀信息
     *
     * @param id
     * @param name
     * @param price
     * @param oldPrice
     * @param startTime
     * @param endTime
     * @param coverId
     * @param labels
     * @param colors
     * @param sizes
     * @param materials
     * @param content
     * @param tempAddImageIds
     * @param tempDelImageIds
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(HttpServletRequest request,
                        Integer id,
                        String name,
                        String price,
                        String oldPrice,
                        String startTime,
                        String endTime,
                        Integer coverId,
                        String labels,
                        String colors,
                        String sizes,
                        String materials,
                        String content,
                        String tempAddImageIds,
                        String tempDelImageIds) {
        try {
            String[] addImageIds = tempAddImageIds.split(",");
            String[] delImageIds = tempDelImageIds.split(",");
            Spikes spikes = null;

            if (null == id) {
                spikes = new Spikes();
            } else {
                spikes = spikesService.getById(id);
            }

            spikes.setName(name);
            spikes.setPrice(price);
            spikes.setOldPrice(oldPrice);
            spikes.setCoverId(coverId);
            spikes.setStartTime(DateUtils.stringToDateWithFormat(startTime, "yyyy-MM-dd HH:mm:ss"));
            spikes.setEndTime(DateUtils.stringToDateWithFormat(endTime, "yyyy-MM-dd HH:mm:ss"));
            spikes.setLabels(labels);
            spikes.setColors(colors.substring(0, colors.length() - 1));
            spikes.setSizes(sizes);
            spikes.setMaterials(materials);
            spikes.setDescription(content);

            if (null == id) {
                spikes.setShowNum(0);
                spikes.setCount(0);
                spikes.setCreateTime(new Date());
                spikesService.create(spikes);
            } else {
                spikesService.update(spikes);
            }

            // 保存商品图片集合
            Image image = null;
            for (String imageId : addImageIds) {
                if (null != imageId && !imageId.equals("")) {
                    image = imageService.getById(Integer.parseInt(imageId));
                    image.setObjectId(spikes.getId());
                    image.setObjectType(Constant.IMAGE_SPIKES);

                    imageService.update(image);
                }
            }

            // 删除商品图片
            for (String imageId : delImageIds) {
                if (null != imageId && !imageId.equals("")) {
                    imageService.deleteById(Integer.parseInt(imageId));
                }
            }

            operatisService.addOperatisInfo(request, null == id ? "新增" : "修改" + "秒杀商品 " + spikes.getName());

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
