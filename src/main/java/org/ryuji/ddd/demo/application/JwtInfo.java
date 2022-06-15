package org.ryuji.ddd.demo.application;

import lombok.Data;

@Data
public class JwtInfo {

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 用户Id 唯一
     */
    private Long uid;
}
