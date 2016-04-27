package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/11 0011.
 */
@Entity
@Table(name = "packageproducts")
public class Packageproducts {
    private Integer id;
    private Integer type;
    private Packages packages;
    private Products product;
    private String path;
    private String colors;
    private String sizes;
    private String materials;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}
