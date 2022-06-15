package org.ryuji.ddd.demo.project.handler;


import lombok.extern.slf4j.Slf4j;
import org.ryuji.ddd.demo.project.common.ErrorCode;
import org.ryuji.ddd.demo.project.common.Result;
import org.ryuji.ddd.demo.project.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liuxi
 * @date 2020/1/22
 */
@Slf4j
@ControllerAdvice(annotations = {RestController.class})
public class RestExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    ResponseEntity<?> ValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {

        int code = ErrorCode.BUSSINESS_ERROR;
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String errorMessage = fieldErrors.stream().findFirst().map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage())).orElse(null);
        return genExceptionResponse(code, errorMessage);
    }

    /**
     * 通用异常处理方法
     *
     * @param e 通用异常对象
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    ResponseEntity<?> businessExceptionHandler(HttpServletRequest req, Exception e) {

        int code = ErrorCode.BUSSINESS_ERROR;
        String errorMessage = "";

        Class c = e.getClass();

        if (c.isAssignableFrom(IllegalArgumentException.class)) {
            errorMessage = e.getMessage();
        } else if (c.isAssignableFrom(BusinessException.class)) {
            errorMessage = e.getMessage();
            code = ((BusinessException) e).getCode();
        } else {
            errorMessage = e.getLocalizedMessage();
        }
        StringBuffer requestURL = req.getRequestURL();
        String url = requestURL.toString();
        log.error("请求url：{} ,异常message：{}", url, errorMessage);
        e.printStackTrace();
        return genExceptionResponse(code, errorMessage);
    }

    private ResponseEntity genExceptionResponse(int statusCode, String message) {
        Result r = Result.build(statusCode, message, null);
        return ResponseEntity.status(HttpStatus.OK).body(r);
    }

    private ResponseEntity genExceptionResponse(Object obj) {
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }
}
