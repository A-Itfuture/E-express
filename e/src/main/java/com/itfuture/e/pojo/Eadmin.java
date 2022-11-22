package com.itfuture.e.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/06 14:09
 */
@Data
@TableName("eadmin")
public class Eadmin {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String loginIp;
    private Timestamp loginTime;
    private Timestamp createTime;
}
