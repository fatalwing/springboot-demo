package com.townmc.boot.domain.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Demo extends CommonColumn {
    @Id
    private String id; // 应用id
    private Integer channelId; //通道ID
    private String name; // 应用名称
    private String developerId; // 所属开发者
    private String logo; // 应用logo
    private String token; // 用于签名计算的token
    private String aesKey; // 用于aes加解密的key
    private String verify_url; // 核销地址
}
