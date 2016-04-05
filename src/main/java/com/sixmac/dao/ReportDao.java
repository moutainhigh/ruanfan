package com.sixmac.dao;

import com.sixmac.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public interface ReportDao  extends JpaRepository<Report, Integer>, JpaSpecificationExecutor<Report> {
}
