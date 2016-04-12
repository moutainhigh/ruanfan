package com.sixmac.entity;

import com.sixmac.entity.vo.PackageVo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
@Entity
@Table(name = "custominfo")
public class Custominfo {
    private int id;
    private Custom custom;
    private String name;
    private String path;
    private Date createTime;
    private List<PackageVo> packageList;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "customId")
    public Custom getCustom() {
        return custom;
    }

    public void setCustom(Custom custom) {
        this.custom = custom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Transient
    public List<PackageVo> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<PackageVo> packageList) {
        this.packageList = packageList;
    }
}
