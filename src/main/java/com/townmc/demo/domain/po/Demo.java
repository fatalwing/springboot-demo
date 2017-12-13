package com.townmc.demo.domain.po;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商家模型
 */
@Entity
public class Demo extends CommonColumn implements Serializable {
    public enum DemoStatus {normal, warn}

    @Id
    private String demoId;
    private String demoName;
    @Enumerated(EnumType.STRING)
    private DemoStatus status;

    public DemoStatus getStatus() {
        return status;
    }

    public void setStatus(DemoStatus status) {
        this.status = status;
    }

    public String getDemoId() {
        return demoId;
    }

    public void setDemoId(String demoId) {
        this.demoId = demoId;
    }

    public String getDemoName() {
        return demoName;
    }

    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }
}
