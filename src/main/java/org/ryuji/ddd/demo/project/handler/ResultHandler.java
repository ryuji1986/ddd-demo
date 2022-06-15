package org.ryuji.ddd.demo.project.handler;

import lombok.SneakyThrows;
import org.ryuji.ddd.demo.project.common.ErrorCode;
import org.ryuji.ddd.demo.project.common.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;


@ControllerAdvice(annotations = {RestController.class})
public class ResultHandler implements ResponseBodyAdvice {

    private static final Class[] ANNOS = {
            RequestMapping.class,
            GetMapping.class,
            PostMapping.class,
            DeleteMapping.class,
            PutMapping.class
    };

    /**
     * 对所有RestController的接口方法进行拦截
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        AnnotatedElement element = returnType.getAnnotatedElement();
        return Arrays.stream(ANNOS).anyMatch(anno -> anno.isAnnotation() && element.isAnnotationPresent(anno));
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter methodParameter, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (methodParameter.getMethodAnnotation(ResponseBody.class) != null) {
            return body;
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Object out;
        if (body == null) {
            out = Result.build(ErrorCode.SUCCESS, "empty body", null);
        } else if (body instanceof String) {
            out = body;
        } else if (body instanceof Result) {
            out = body;
        } else {
            out = Result.build(ErrorCode.SUCCESS, "ok", body);
        }

        return out;
    }

}