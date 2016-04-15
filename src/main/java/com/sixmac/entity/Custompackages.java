package com.sixmac.entity;

import com.sixmac.entity.vo.PackageVo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
@Entity
@Table(name = "custompackages")
public class Custompackages {
    private int id;
    private String name;
    private Integer coverId;
    private Custominfo custominfo;
    private Areas area;
    private List<PackageVo> packageList = new ArrayList<PackageVo>();

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCoverId() {
        return coverId;
    }

    public void setCoverId(Integer coverId) {
        this.coverId = coverId;
    }

    @ManyToOne
    @JoinColumn(name = "custominfoId")
    public Custominfo getCustominfo() {
        return custominfo;
    }

    public void setCustominfo(Custominfo custominfo) {
        this.custominfo = custominfo;
    }

    @ManyToOne
    @JoinColumn(name = "areaId")
    public Areas getArea() {
        return area;
    }

    public void setArea(Areas area) {
        this.area = area;
    }

    @Transient
    public List<PackageVo> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<PackageVo> packageList) {
        this.packageList = packageList;
    }
}
