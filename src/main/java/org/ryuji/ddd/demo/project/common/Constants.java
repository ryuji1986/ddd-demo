package org.ryuji.ddd.demo.project.common;

/**
 * @program: showyou-core
 * @description: 全局常量
 * @author: LiuXi
 * @create: 2021-03-19 15:25
 */
public interface Constants {
    /**
     * 登录结果
     */
    interface LoginResult {
        // 登录成功
        int SUCCESS = 0;
        // 未注册
        int UNREGISTER = 1;
        // 验证码失败
        int CAPACHA_ERROR = 2;
        // 密码验证失败
        int PASSWORD_ERROR = 3;
        // 刚注册，需完善性别
        int JUST_REGISTER = 4;
        // 用户已注销
        int LOG_OFF = 5;

        // 未知错误
        int UNKNOWN_ERROR = -1;
    }

    //线程池数量
    interface ThreadPool {
        int TASK_NUM = 100;//定时任务线程池数量

        int TEN_IM_ACCOUNT_SYNC_NUM = 100;//腾讯im账号同步线程池数量
        int TEN_IM_SEND_MSG_NUM = 100;//腾讯im发送私聊消息程池数量
        int TEN_IM_SEND_MSG_BATCH_NUM = 100;//腾讯im发送批量私聊消息线程池数量
        int TEN_IM_CREATE_GROUP_NUM = 100;//腾讯im创建群组线程池数量
        //int TEN_IM_SEND_GROUP_MSG_NUM = 100;//腾讯im发送群组消息线程池数量
        //int TEN_IM_SEND_GROUP_NOTICE_NUM = 100;//腾讯im发送群组通知线程池数量
        int TEN_IM_NOT_NEED_LIMIT_NUM = 100;//腾讯im不需要限流的操作线程池数量

        int SEND_SYSTEM_MSG_NUM = 100;//发送系统消息线程池数量
    }

    //缓存key
    interface CacheKey {
        String SMS_CODE = "SmsCode:";//验证码

        String USER_INFO = "UserInfo:";//用户信息
        String USER_INFO_TOKEN = "UserToken";//用户信息token属性
        String USER_INFO_NAME = "UserName";//用户信息name属性

        //日程相关
        String EVENT_INFO = "EventInfo:";   //日程详情
        String EVENT_USER_FORCE = "EventUserForce:"; //用户关注日程
    }

    interface Value {
        long MINUTE = 60;//分
        long HOUR = 60 * 60;//小时
        long DAY = 60 * 60 * 24;//天
        long WEEK = 60 * 60 * 24 * 7;//周
        int DAY_INT = 60 * 60 * 24;//天 int格式
    }

    //过期值
    interface ExpireValue {
        long ALI_OSS_STS_SIGN = 15 * Value.MINUTE;//阿里oss sts签名过期时间
        long SMS_VALID = 5 * Value.MINUTE;//短信验证过期时间
        long TEN_IM_USER_SIGN = 8 * Value.DAY;//腾讯IM用户签名过期时间
        long TEN_IM_USER_SIGN_CACHE = 7 * Value.DAY;//腾讯IM用户签名缓存过期时间
        int JWT_TOKEN = 8 * Value.DAY_INT;//JWT TOKEN过期时间
        long JWT_TOKEN_CACHE = 7 * Value.DAY;//JWT TOKEN缓存过期时间
        long AGO_TOKEN = Value.DAY;//声网TOKEN过期时间
    }

    /**
     * 支付渠道
     */
    interface PayChannel {
        // 未知
        int UNKNOWN = -1;

        // 支付宝app
        int ALIPAY_APP = 0;
        // 支付宝h5
        int ALIPAY_H5 = 1;
        // 支付宝二维码
        int ALIPAY_QR = 2;

        // 微信app
        int WECHAT_APP = 10;
        // 微信二维码
        int WECHAT_NATIVE = 11;
        // 微信公众号
        int WECHAT_JSAPI = 12;
        // 微信h5
        int WECHAT_H5 = 13;
        // 小程序
        int WECHAT_MINI_PROGRAM = 14;

        // 苹果支付
        int APPLE_PAY = 20;

    }

    /**
     * 支付宝订单交易状态
     */
    interface AlipayTradeStatus {
        // 交易创建，等待买家付款
        int WAIT_BUYER_PAY = 0;
        // 交易支付成功
        int TRADE_SUCCESS = 1;
        // 未付款交易超时关闭，或支付完成后全额退款
        int TRADE_CLOSED = 2;
        // 交易结束，不可退款
        int TRADE_FINISHED = 3;
    }


    /**
     * 微信API地址
     */
    interface WxPayApiUrl {
        String HOST =  "https://api.mch.weixin.qq.com";
        String APP = "/v3/pay/transactions/app";
        String NATIVE = "/v3/pay/transactions/native";
    }

    interface RequestHeaderKey {
        String TOKEN = "Authorization";
    }

    interface RequestAttributeKey {
        String UID = "uid";
        String DEVICEID = "devicdId";
        String JWTINFO = "jwtInfo";
    }

}