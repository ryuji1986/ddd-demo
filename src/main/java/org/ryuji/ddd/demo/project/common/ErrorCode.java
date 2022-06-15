package org.ryuji.ddd.demo.project.common;

/***
 * @author liuxi
 */
public class ErrorCode {
    /*======================公共业务状态码======================*/
    /**
     * 请求成功
     */
    public static final int SUCCESS = 2000;

    /**
     * 业务异常，默认的异常场景
     */
    public static final int BUSSINESS_ERROR = 5000;

    /**
     * 无效的Token
     */
    public static final int INVALID_TOKEN = 5100;

    /**
     * 被封用户、设备或者ip
     */
    public static final int BE_FORBIDDEN = 5200;

    /**
     * 微服务调用异常
     */
    public static final int CALL_SERVICE_ERROR = 6000;
}
