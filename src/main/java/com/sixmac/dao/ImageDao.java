package com.sixmac.dao;

import com.sixmac.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 涂奕恒 on 2014/12/4 16:16.
 */
public interface ImageDao extends JpaRepository<Image, Integer> {

}