package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
@Entity
@Table(name = "custompackages")
public class Custompackages {
    private int id;
    private Custominfo custominfo;
    private Areas area;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
