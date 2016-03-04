package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "city")
public class City {
    private int id;
    private String name;
    private int provinceId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "provinceId")
    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
