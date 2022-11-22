package com.itfuture.e.Exception;


import com.itfuture.e.pojo.vo.StatusCode;
import lombok.Getter;

/**统一异常处理
 * 自定义 异常类
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/14 16:35
 */
@Getter
public class BusinessException extends RuntimeException{

    private int code;
    private String msg;

    /**
     * 手动设置异常
     * @param statusCode
     * @param message
     */
    public BusinessException(StatusCode statusCode, String message) {
        // message用于用户设置抛出错误详情
        super(message);
        // 状态码
        this.code = statusCode.getCode();
        // 状态码配套的msg
        this.msg = statusCode.getMsg();
    }


    ///**
    // * 默认异常使用APP_ERROR状态码
    // * @param message
    // */
    //public BusinessException(String message) {
    //    super(message);
    //    this.code = AppCode.APP_ERROR.getCode();
    //    this.msg = AppCode.APP_ERROR.getMsg();
    //}

}
