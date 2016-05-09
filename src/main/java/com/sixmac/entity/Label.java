package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/10 0010.
 */
@Entity
@Table(name = "label")
public class Label {
    private Integer id;
    private String name;
    private Integer objectId;
    private Integer objectType;
    private String height;
    private String width;
    private Products product;
    private Integer productId;
    private String productCover;
    private String labelId;
    private String description;
    private Double leftPoint;
    private Double topPoint;

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

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @ManyToOne
    @JoinColumn(name = "productId")
    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    @Transient
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Transient
    public String getProductCover() {
        return productCover;
    }

    public void setProductCover(String productCover) {
        this.productCover = productCover;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLeftPoint() {
        return leftPoint;
    }

    public void setLeftPoint(Double leftPoint) {
        this.leftPoint = leftPoint;
    }

    public Double getTopPoint() {
        return topPoint;
    }

    public void setTopPoint(Double topPoint) {
        this.topPoint = topPoint;
    }
}
