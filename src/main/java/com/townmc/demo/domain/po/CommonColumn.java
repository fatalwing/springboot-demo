package com.townmc.demo.domain.po;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 所有表都包含的列
 */
@MappedSuperclass
public class CommonColumn {

    /**
     * 用于乐观锁
     */
    @Version
    private Long version;

    /**
     * 该条数据产生时间
     */
    @Column(name = "date_created", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private Date dateCreated;

    /**
     * 该条数据最后修改时间
     */
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private Date lastUpdated;

    /**
     * 该条数据是否为逻辑删除状态
     */
    @Column(name = "is_deleted", nullable = false, columnDefinition = "INT default 0")
    @Generated(GenerationTime.INSERT)
    private Integer isDeleted;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
