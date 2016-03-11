package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/11 0011.
 */
@Entity
@Table(name = "packageproducts")
public class Packageproducts {
    private Integer id;
    private Packages packages;
    private Products product;
    private String path;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "packageId")
    public Packages getPackages() {
        return packages;
    }

    public void setPackages(Packages packages) {
        this.packages = packages;
    }

    @ManyToOne
    @JoinColumn(name = "productId")
    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
