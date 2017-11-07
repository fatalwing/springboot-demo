package com.townmc.demo.domain.po;

import javax.persistence.*;

/**
 * 商家模型
 */
@Entity
public class Demo extends CommonColumn {
    @Id
    private String demoId;
    private String demoName;

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
