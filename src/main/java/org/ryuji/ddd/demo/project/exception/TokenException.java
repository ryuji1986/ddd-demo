package org.ryuji.ddd.demo.project.exception;

import lombok.Getter;
import org.ryuji.ddd.demo.project.common.ErrorCode;

/**
 * @program: showyou-core
 * @description: token异常
 * @author: LiuXi
 * @create: 2021-03-18 10:30
 */
public class TokenException extends BusinessException {
    /**
     * 异常错误码
     */
    @Getter
    private Integer code = ErrorCode.INVALID_TOKEN;

    public TokenException() {
        super();
    }

    public TokenException(String msg) {
        super(msg);
    }

    public TokenException(Throwable cause) {
        super(cause);
    }

    public TokenException(int code, String message, Throwable e) {
        super(code, message, e);
        this.code = code;
    }
}