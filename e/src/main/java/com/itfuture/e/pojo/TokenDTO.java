package com.itfuture.e.pojo;

import lombok.Data;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/30 16:32
 */
@Data
public class TokenDTO {
    private int id;
    private String userName;
    private String userPhone;
    private Long gmtCreate;
    private int status;//身份：0用户，1是快递员

    @Override
    public String toString() {
        return "TokenDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", status=" + status +
                '}';
    }
}