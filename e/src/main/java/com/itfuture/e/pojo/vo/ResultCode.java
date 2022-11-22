package com.itfuture.e.pojo.vo;

import lombok.Getter;

/**状态码枚举类
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/14 15:30
 */
@Getter
public enum ResultCode implements StatusCode{
    SUCCESS(200,"请求成功"),
    FAILED(500,"请求失败"),
    VALIDATE_ERROR(502,"参数校验失败");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
