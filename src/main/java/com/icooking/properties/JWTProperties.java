package com.icooking.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "icooking.jwt")
@Data
public class JWTProperties {

    /**
     * 用户JWT令牌生成相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
