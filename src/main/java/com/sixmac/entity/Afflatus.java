package com.sixmac.entity;

import com.sixmac.entity.vo.BeanVo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "afflatus")
public class Afflatus {
    private Integer id;
    private String name;
    private Designers designer;
    private Integer type;
    private Styles style;
    private Areas area;
    private Integer coverId;
    private Integer showNum;
    private Integer shareNum;
    private String labels;
    private String url;
    private Integer status;
    private Date createTime;
    private String cover;
    private String description;
    private List<Gams> gamsList = new ArrayList<Gams>();
    private List<BeanVo> loveList = new ArrayList<BeanVo>();
    private List<Comment> commentList = new ArrayList<Comment>();
    private Integer collectNum;
    private Integer gamNum;
    private Integer reserveNum;
    private Integer designerId;
    private String designerHead;
    private String designerName;
    private List<Image> imageList = new ArrayList<Image>();
    private Integer isComment;
    private Integer isGam;
    private Integer isCollect;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "designerId")
    public Designers getDesigner() {
        return designer;
    }

    public void setDesigner(Designers designer) {
        this.designer = designer;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @OneToOne
    @JoinColumn(name = "styleId")
    public Styles getStyle() {
        return style;
    }

    public void setStyle(Styles style) {
        this.style = style;
    }

    @OneToOne
    @JoinColumn(name = "areaId")
    public Areas getArea() {
        return area;
    }

    public void setArea(Areas area) {
        this.area = area;
    }

    public Integer getCoverId() {
        return coverId;
    }

    public void setCoverId(Integer coverId) {
        this.coverId = coverId;
    }

    public Integer getShowNum() {
        return showNum;
    }

    public void setShowNum(Integer showNum) {
        this.showNum = showNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    public List<Gams> getGamsList() {
        return gamsList;
    }

    public void setGamsList(List<Gams> gamsList) {
        this.gamsList = gamsList;
    }

    @Transient
    public List<BeanVo> getLoveList() {
        return loveList;
    }

    public void setLoveList(List<BeanVo> loveList) {
        this.loveList = loveList;
    }

    @Transient
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Transient
    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    @Transient
    public Integer getGamNum() {
        return gamNum;
    }

    public void setGamNum(Integer gamNum) {
        this.gamNum = gamNum;
    }

    @Transient
    public Integer getReserveNum() {
        return reserveNum;
    }

    public void setReserveNum(Integer reserveNum) {
        this.reserveNum = reserveNum;
    }

    @Transient
    public Integer getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Integer designerId) {
        this.designerId = designerId;
    }

    @Transient
    public String getDesignerHead() {
        return designerHead;
    }

    public void setDesignerHead(String designerHead) {
        this.designerHead = designerHead;
    }

    @Transient
    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    @Transient
    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    @Transient
    public Integer getIsComment() {
        return isComment;
    }

    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }

    @Transient
    public Integer getIsGam() {
        return isGam;
    }

    public void setIsGam(Integer isGam) {
        this.isGam = isGam;
    }

    @Transient
    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }
}
