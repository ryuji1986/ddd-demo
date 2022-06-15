package org.ryuji.ddd.demo.infrastructure.event.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.ryuji.ddd.demo.project.annotation.AccessLimit;
import org.ryuji.ddd.demo.project.com.RedisApi;
import org.ryuji.ddd.demo.project.exception.BusinessException;
import org.ryuji.ddd.demo.project.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @program: showyou-core
 * @description: 限流拦截器
 * @author: LiuXi
 * @create: 2022-02-20 17:09
 */
@Slf4j
public class AccessLimtInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisApi redisApi;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean needLimit = false;
        try {
            if (handler instanceof HandlerMethod) {

                String ip = IpUtil.getIpAddr(request);

                HandlerMethod hm = (HandlerMethod) handler;
                AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
                if (null == accessLimit) {
                    return true;
                }
                int seconds = accessLimit.seconds();
                int maxCount = accessLimit.maxCount();

                String key = request.getContextPath() + ":" + request.getServletPath() + ":" + ip;
                String value = redisApi.get(key);
                Integer count = Integer.parseInt(Optional.ofNullable(value).orElse("0"));

                if (null == count || -1 == count) {
                    redisApi.set(key, 1 + "", seconds);
                    return true;
                }

                if (count < maxCount) {
                    redisApi.incr(key, 1L);
                    return true;
                } else {
                    needLimit = true;
                }
            }
        } catch (Exception e) {
            log.error("接口限流数据获取失败，跳过限流判断....");
            e.printStackTrace();
        }

        if (needLimit) {
            throw new BusinessException("用户请求过于频繁！");
        }

        return true;
    }

}