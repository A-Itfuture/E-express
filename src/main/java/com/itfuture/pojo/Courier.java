package com.itfuture.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/04 11:29
 */
@Data
@TableName("courier")
public class Courier {
    private int id;
    private String userName;
    private String userPhone;
    private String password;
    private Timestamp createTime;
    private Timestamp loginTime;
    private String cardId;
    private String express_Count;
}
