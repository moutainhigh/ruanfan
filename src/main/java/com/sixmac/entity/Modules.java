package com.sixmac.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "modules")
public class Modules {
    private Integer id;
    private String name;
    private Integer parentId;
    private List<Modules> moduleList;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Transient
    public List<Modules> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Modules> moduleList) {
        this.moduleList = moduleList;
    }
}
