package com.itfuture.e.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/04 11:28
 */
@Data
@TableName("user")
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String userName;
    private String userPhone;
    private String password;
    private Timestamp createTime;
    private Timestamp loginTime;
    private String cardId;
}
