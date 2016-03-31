package com.sixmac.entity.vo;

/**
 * Created by Administrator on 2016/3/31 0031.
 */

/**
 * 关注和粉丝信息
 */
public class AttentionVo {

    /**
     * 目标id
     */
    private Integer id;

    /**
     * 目标名称
     */
    private String name;

    /**
     * 目标头像
     */
    private String path;

    /**
     * 目标类型
     */
    private Integer type;

    /**
     * 描述
     */
    private String description;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
