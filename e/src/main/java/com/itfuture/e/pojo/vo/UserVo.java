package com.itfuture.e.pojo.vo;

import com.itfuture.e.valid.addUser;
import com.itfuture.e.valid.findUser;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/06 15:32
 */
@Data
public class UserVo {
    private int id;
    private String userName;
    @NotNull(message = "用户手机号不能为空",groups = {addUser.class})
    @NotNull(message = "用户手机号不能为空",groups = {findUser.class})
    private String userPhone;
    private String password;
    private String createTime;
    private String loginTime;
    private String cardId;
}
