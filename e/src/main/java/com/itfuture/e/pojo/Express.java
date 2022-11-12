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
@TableName("express")
public class Express {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String number;
    private String userName;
    private String userPhone;
    private String company;
    private String code;
    private Timestamp inTime;
    private Timestamp outTime;
    private int status;
    private String sysPhone;
}
