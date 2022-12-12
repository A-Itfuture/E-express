package com.itfuture.e.controller;


import com.itfuture.e.pojo.Eadmin;
import com.itfuture.e.pojo.TokenDTO;
import com.itfuture.e.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**管理员控制器
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/04 10:34
 */
@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ApiOperation("管理员登录后台系统")
    @PostMapping("/loginAdmin")
    public String login(@ApiParam("用户名密码封装实体") @Validated @RequestBody Eadmin eadmin, HttpServletRequest request){
        String ip = request.getRemoteAddr();//远程ip
        return adminService.login(eadmin,ip);
    }

    @ApiOperation("管理员基本信息")
    @PostMapping("/adminInfo")
    public TokenDTO adminInfo(HttpServletRequest request){
        String authToken  = request.getHeader("Authorization");
        String token = authToken.substring("Bearer".length() + 1).trim();
        return adminService.adminInfo(token);
    }

    @ApiOperation("管理员注销登录")
    @DeleteMapping("/exitLogin")
    public String exitLogin(HttpServletRequest request){
        String authToken  = request.getHeader("Authorization");
        String token = authToken.substring("Bearer".length() + 1).trim();
        return adminService.exitLogin(token);
    }
}
