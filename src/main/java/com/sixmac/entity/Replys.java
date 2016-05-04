package com.sixmac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Entity
@Table(name = "replys")
public class Replys {
    private Integer id;
    private Comment comment;
    private Integer replySourceId;
    private Integer replySourceType;
    private String sourceName;
    private String sourceHead;
    private String content;
    private Date createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "commentId")
    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Integer getReplySourceId() {
        return replySourceId;
    }

    public void setReplySourceId(Integer replySourceId) {
        this.replySourceId = replySourceId;
    }

    public Integer getReplySourceType() {
        return replySourceType;
    }

    public void setReplySourceType(Integer replySourceType) {
        this.replySourceType = replySourceType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceHead() {
        return sourceHead;
    }

    public void setSourceHead(String sourceHead) {
        this.sourceHead = sourceHead;
    }
}
