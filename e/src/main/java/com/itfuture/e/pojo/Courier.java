package com.itfuture.e.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

/**快递员实体
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/04 11:29
 */
@Data
@TableName("courier")
public class Courier {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String userName;
    private String userPhone;
    private String password;
    private Timestamp createTime;
    private Timestamp loginTime;
    private String cardId;
    private String expressCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Courier courier = (Courier) o;
        return id == courier.id && Objects.equals(userName, courier.userName) && Objects.equals(userPhone, courier.userPhone) && Objects.equals(password, courier.password) && Objects.equals(createTime, courier.createTime) && Objects.equals(loginTime, courier.loginTime) && Objects.equals(cardId, courier.cardId) && Objects.equals(expressCount, courier.expressCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userPhone, password, createTime, loginTime, cardId, expressCount);
    }
}
