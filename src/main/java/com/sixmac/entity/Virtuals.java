package com.sixmac.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "virtuals")
public class Virtuals {
    private Integer id;
    private String name;
    private Styles style;
    private Vrtype type;
    private String labels;
    private String cover;
    private String url;
    private Integer afflatusId;
    private Integer isAuth;
    private Date createTime;
    private Integer isGam;
    private Integer isCollect;
    private List<Gams> gamsList = new ArrayList<Gams>();
    private Integer shareNum;
    private Integer collectNum;

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

    @ManyToOne
    @JoinColumn(name = "styleId")
    public Styles getStyle() {
        return style;
    }

    public void setStyle(Styles style) {
        this.style = style;
    }

    @ManyToOne
    @JoinColumn(name = "typeId")
    public Vrtype getType() {
        return type;
    }

    public void setType(Vrtype type) {
        this.type = type;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getAfflatusId() {
        return afflatusId;
    }

    public void setAfflatusId(Integer afflatusId) {
        this.afflatusId = afflatusId;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
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

    @Transient
    public List<Gams> getGamsList() {
        return gamsList;
    }

    public void setGamsList(List<Gams> gamsList) {
        this.gamsList = gamsList;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    @Transient
    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }
}
