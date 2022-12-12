package com.itfuture.e.wx.controller;

import com.itfuture.e.intercept.NotControllerResponseAdvice;
import com.itfuture.e.pojo.TokenDTO;
import com.itfuture.e.pojo.vo.ResultVo;
import com.itfuture.e.pojo.vo.UserVo;
import com.itfuture.e.service.UserService;
import com.itfuture.e.util.JWTUtil;
import com.itfuture.e.util.RedisUtil;
import com.itfuture.e.valid.addUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/23 21:22
 */
@Api(tags = "微信端用户服务控制器")
@RestController
@RequestMapping("wxUser")
public class WxUserController {
    @Autowired
    private UserService userService;

    @ApiOperation("发送验证码")
    @PostMapping("/loginSms")
    @NotControllerResponseAdvice
    public boolean sendSms(@RequestBody String userPhone){
        return userService.sendSms(userPhone);
    }

    @ApiOperation("更新发送验证码")
    @PostMapping("/sendUpdate")
    @NotControllerResponseAdvice
    public boolean sendUpdate(@RequestBody String userPhone){
        return userService.sendUpdate(userPhone);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public String login(@RequestBody String userPhoneAndCode, HttpServletRequest request){
        return userService.login(userPhoneAndCode);
    }
    @ApiOperation("用户基本信息")
    @PostMapping("/userInfo")
    public TokenDTO userInfo(HttpServletRequest request){
        String authToken  = request.getHeader("Authorization");
        String token = authToken.substring("Bearer".length() + 1).trim();
        return userService.userInfo(token);
    }

    @ApiOperation("获取手机验证码")
    @PostMapping("/phoneCode")
    public String phoneCode(@RequestBody String phone){
        return RedisUtil.getValue("update"+phone);
    }



    @ApiOperation("更新认证用户信息")
    @PutMapping("/updateInfoUser")
    public boolean updateInfoUser(@ApiParam("更改用户信息的实体") @Validated(addUser.class) @RequestBody UserVo userVo){
        return userService.updateUser(userVo);
    }

    @ApiOperation("用户注销登录")
    @DeleteMapping("/logout")
    public String logout(HttpServletRequest request){
        String authToken  = request.getHeader("Authorization");
        String token = authToken.substring("Bearer".length() + 1).trim();
        return userService.logout(token);
    }


}
