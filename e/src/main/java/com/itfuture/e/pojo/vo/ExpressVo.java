package com.itfuture.e.pojo.vo;

import lombok.Data;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/06 15:33
 */
@Data
public class ExpressVo {
    private int id;
    private String number;
    private String userName;
    private String userPhone;
    private String company;
    private String code;
    private String inTime;
    private String outTime;
    private String status;
    private String sysPhone;
}
