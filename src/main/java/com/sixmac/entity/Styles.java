package com.sixmac.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "styles")
public class Styles {
    private Integer id;
    private String name;
    private String backImg;
    private Date updateTime;
    private List<Merchants> merchantsList = new ArrayList<Merchants>();
    private Integer productNum;

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

    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Transient
    public List<Merchants> getMerchantsList() {
        return merchantsList;
    }

    public void setMerchantsList(List<Merchants> merchantsList) {
        this.merchantsList = merchantsList;
    }

    @Transient
    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }
}
