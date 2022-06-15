package org.ryuji.ddd.demo.project.common;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author liuxi
 */
@Data
public class Result<T> {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;


    /**
     * 具体内容
     */
    private T data;


    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static <T> Result<T> build(int code, String message, T object) {
        return new Result<T>(code, message, object);
    }
}
