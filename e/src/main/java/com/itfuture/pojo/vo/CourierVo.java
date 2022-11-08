package com.itfuture.pojo.vo;


import lombok.Data;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/06 15:33
 */
@Data
public class CourierVo {
    private int id;
    private String userName;
    private String userPhone;
    private String password;
    private String createTime;
    private String loginTime;
    private String cardId;
    private String express_Count;
}
