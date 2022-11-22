package com.itfuture.e.pojo.vo;

import lombok.Data;

/**响应视图类：封装响应结果
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/14 15:28
 */
@Data
public class ResultVo {
    /**
     * 状态码
     */
    private int code;

    /**
     * 状态信息
     */
    private String msg;

    /**
     * 返回的数据对象
     */
    private Object data;

    /**
     * 手动返回状态码、信息、数据对象
     * @param code
     * @param msg
     * @param data
     */
    public ResultVo(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 默认返回成功状态，数据对象
     * @param data
     */
    public ResultVo(Object data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.data = data;
    }

    /**
     * 返回指定状态、数据对象
     * @param statusCode
     * @param data
     */
    public ResultVo(StatusCode statusCode,Object data){
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    /**
     * 只返回状态
     * @param statusCode
     */
    public ResultVo(StatusCode statusCode){
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = null;
    }
}
