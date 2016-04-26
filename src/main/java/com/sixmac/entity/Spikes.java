package com.sixmac.entity;

import com.sixmac.entity.vo.AppraisalVo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "spikes")
public class Spikes {
    private Integer id;
    private String name;
    private String price;
    private String oldPrice;
    private Integer coverId;
    private Date startTime;
    private Date endTime;
    private Integer count;
    private String labels;
    private String colors;
    private String sizes;
    private String materials;
    private String description;
    private Date createTime;
    private String cover;
    private Integer status;
    private List<Image> imageList = new ArrayList<Image>();
    private List<AppraisalVo> appraisalVoList = new ArrayList<AppraisalVo>();

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Integer getCoverId() {
        return coverId;
    }

    public void setCoverId(Integer coverId) {
        this.coverId = coverId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startTime")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endTime")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Transient
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Transient
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Transient
    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    @Transient
    public List<AppraisalVo> getAppraisalVoList() {
        return appraisalVoList;
    }

    public void setAppraisalVoList(List<AppraisalVo> appraisalVoList) {
        this.appraisalVoList = appraisalVoList;
    }
}
