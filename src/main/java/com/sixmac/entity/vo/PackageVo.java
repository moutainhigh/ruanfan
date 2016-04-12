package com.sixmac.entity.vo;

import com.sixmac.entity.Products;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class PackageVo {

    private Integer id;

    private String name;

    private List<Products> productsList = new ArrayList<Products>();

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

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }
}