package org.ryuji.ddd.demo.project.com;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ryuji.ddd.demo.application.JwtInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTool {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private Integer expire;

    @Value("${jwt.isuser}")
    private String isuser;

    @Getter
    private String tokenHeader = "Authorization";

    /**
     * token 过期时间: 10天
     * Calendar.SECOND;
     */
    public static final int calendarField = Calendar.DATE;

//    public String getToken(UserBasicEntity userBasicEntity) {
//        String token = JWT.create()
//                .withAudience(String.valueOf(userBasicEntity.getUserId()))
//                .withExpiresAt(DateUtils.addSeconds(new Date(), expire))
//                .sign(Algorithm.HMAC256(secret));
//        return token;
//    }


  /*  public String getToken(Long userId) {
        String token = JWT.create()
                .withAudience(String.valueOf(userId))
                .withExpiresAt(DateUtils.addSeconds(new Date(), expire))
                .sign(Algorithm.HMAC256(secret));
        return token;
    }*/

    /* */

    /**
     * 获取token
     *
     * @param jwtInfo
     * @return
     *//*
    public String getToken(Long userId) {
        return getToken(userId, null);
    }*/
    public String getToken(JwtInfo jwtInfo) {
        return getToken(jwtInfo.getUid(), jwtInfo.getDeviceId());
    }

    /**
     * 获取token
     *
     * @param uid
     * @param deviceId
     * @return
     */
    public String getToken(Long uid, String deviceId) {
        // 超时时间设置
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendarField, expire);
        Date expiresDate = calendar.getTime();

        log.info("用户[userId={},openId={}]过期时间为：[{}]", uid, deviceId, expiresDate);

        String token = createJwtToken(uid, deviceId, expiresDate, isuser, secret);
        return token;
    }

    /**
     * 返回JwtInfo信息
     *
     * @param token
     * @return
     */
    public JwtInfo getJwtInfo(String token) {
        JwtInfo info = decryptToken(token,secret);
        log.info("JwtInfo输出内容为：{}", JSON.toJSONString(info));
        return info;
    }

    public boolean verifyToken(String token) {
        return verifyToken(token, secret);
    }



    /**
     * 签发加密Token
     *
     * @param uid         用户id
     * @param deviceId    设备id
     * @param isuser      签发主体
     * @param expiresDate 过期时间
     * @param secret      密钥
     * @return
     */
    private String createJwtToken(Long uid, String deviceId, Date expiresDate, String isuser, String secret) {
        // header Map
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256");
        headerMap.put("typ", "JWT");

        String token = JWT.create().withHeader(headerMap) //header
                .withClaim("iss", isuser) // payload
                .withClaim("aud", "app")
                .withClaim("uid", uid)
                .withClaim("deviceId", StringUtils.isEmpty(deviceId) ? null : deviceId)
                // sign time
                .withIssuedAt(new Date())
                // expire time
                .withExpiresAt(expiresDate)
                //signature
                .sign(Algorithm.HMAC256(secret));

        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     */
    private JwtInfo decryptToken(String token, String secret) {
        JwtInfo info = null;

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claimMap = jwt.getClaims();
        info = new JwtInfo();

        Claim claimUserId = claimMap.get("uid");
        if (claimUserId != null && claimUserId.asLong() != null) {
            info.setUid(claimUserId.asLong());
        }
        Claim claimDeviceId = claimMap.get("deviceId");
        if (claimDeviceId != null && StringUtils.isNotEmpty(claimDeviceId.asString())) {
            info.setDeviceId(claimDeviceId.asString());
        }
        log.info("JwtInfo输出内容为：{}", JSON.toJSONString(info));
        return info;
    }

    /**
     * 校验token是否过期
     *
     * @param token
     * @return true:未过期 false：已过期
     */
    private boolean verifyToken(String token, String secret) {
        boolean ret = false;
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            jwtVerifier.verify(token);
            ret = true;
        } catch (Exception e) {
            log.error("校验token是否过期：异常{}，{}", e.getMessage(), e);
        }
        return ret;
    }
}
