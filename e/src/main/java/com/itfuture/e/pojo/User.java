package com.itfuture.e.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/04 11:28
 */
@Data
@TableName(value = "user")
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String userName;
    private String userPhone;
    private String password;
    private Timestamp createTime;
    private Timestamp loginTime;
    private String cardId;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(userName, user.userName) && Objects.equals(userPhone, user.userPhone) && Objects.equals(password, user.password) && Objects.equals(createTime, user.createTime) && Objects.equals(loginTime, user.loginTime) && Objects.equals(cardId, user.cardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userPhone, password, createTime, loginTime, cardId);
    }
}
