package org.ryuji.ddd.demo.infrastructure.event.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.ryuji.ddd.demo.application.JwtInfo;
import org.ryuji.ddd.demo.project.annotation.PassToken;
import org.ryuji.ddd.demo.project.com.JwtTool;
import org.ryuji.ddd.demo.project.common.Constants;
import org.ryuji.ddd.demo.project.exception.TokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


@Lazy
@Component
public class JwtHandlerInterceptor implements HandlerInterceptor {

    private static final Logger loggger = LoggerFactory.getLogger(JwtHandlerInterceptor.class);

    @Autowired
    private JwtTool jwt;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 从 http 请求头中取出 token
        String token = request.getHeader(Constants.RequestHeaderKey.TOKEN);
//        loggger.info("Jwt拦截器中token的值为：[{}]", token);
        if (StringUtils.isEmpty(token)) {
            throw new TokenException("Token不能为空值！");
        }
        //校验token是否过期,如果过期直接抛异常返回前端
        boolean flag = jwt.verifyToken(token);
//        loggger.info("Jwt拦截器中flag的值为：{}", flag);
        if (!flag) {
            throw new TokenException("Token已过期！");
        }

        JwtInfo jwtInfo = jwt.getJwtInfo(token);
        Long uid = jwtInfo.getUid();
        String deviceId = jwtInfo.getDeviceId();

        request.setAttribute(Constants.RequestAttributeKey.JWTINFO, jwtInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
