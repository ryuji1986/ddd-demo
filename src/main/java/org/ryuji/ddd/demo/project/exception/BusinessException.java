package org.ryuji.ddd.demo.project.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.ryuji.ddd.demo.project.common.ErrorCode;

/**
 * 业务公用的Exception.
 * 继承自RuntimeException,
 *
 * @author liuxi
 */
@Slf4j
public class BusinessException extends RuntimeException {
    /**
     * 异常错误码
     */
    @Getter
    protected Integer code = ErrorCode.BUSSINESS_ERROR;

    public BusinessException() {
        super();
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(int code, String message, Throwable e) {
        super(message, e);
        this.code = code;
    }
}
