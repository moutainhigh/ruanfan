package com.sixmac.dao;

import com.sixmac.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public interface ReportDao extends JpaRepository<Report, Integer>, JpaSpecificationExecutor<Report> {

    @Query("select a from Report a where a.comment.id = ?1")
    public List<Report> findListByCommentId(Integer commentId);
}
