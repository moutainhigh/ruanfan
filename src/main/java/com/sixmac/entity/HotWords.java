package com.sixmac.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/4/27 0027.
 */
@Entity
@Table(name = "hotwords")
public class HotWords {
    private Integer id;
    private String words;
    private Integer count;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
