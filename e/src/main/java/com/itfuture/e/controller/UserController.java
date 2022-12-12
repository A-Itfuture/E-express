package com.itfuture.e.controller;


import com.itfuture.e.intercept.NotControllerResponseAdvice;
import com.itfuture.e.pojo.vo.ResultVo;
import com.itfuture.e.pojo.vo.TableData;
import com.itfuture.e.pojo.vo.UserVo;
import com.itfuture.e.valid.addUser;
import com.itfuture.e.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**用户管理控制器
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/06 15:49
 */
@Api(tags = "用户管理控制器")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("查询用户总量以及当天新增用户量")
    @GetMapping("/userConsole")
    public List<Map<String, Long>> userConsole(){
        return userService.console();
    }

    @ApiOperation("分页获取所有用户信息")
    @GetMapping("/userListPage")
    @NotControllerResponseAdvice
    public TableData listPage(@RequestParam(required = false,defaultValue = "0") Integer offset,
                              @RequestParam(required = false,defaultValue = "5") Integer pageNumber){
        return userService.findList(true,offset,pageNumber);
    }

    @ApiOperation("获取所有用户信息")
    @GetMapping("/userList")
    public ResultVo list(){
        return new ResultVo(userService.findList(false,0,0));
    }

    @ApiOperation("新增用户")
    @PostMapping("/addUser")
    public ResultVo addUser(@ApiParam("用户信息实体") @Validated(addUser.class) @RequestBody UserVo userVo){
        return new ResultVo(userService.insert(userVo));
    }

    @ApiOperation("根据用户手机号查询用户")
    @PostMapping("/findUserByUserPhone")
    public UserVo findByUserPhone(@ApiParam("查询条件：手机号") @RequestBody  String oldPhone){
        //TODO 前端保证oldPhone不为空
        log.info(oldPhone);
        return userService.findByUserPhone(oldPhone);
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/updateUser")
    public boolean updateUser(@ApiParam("更改用户信息的实体") @Validated(addUser.class) @RequestBody UserVo userVo){
        return userService.updateUser(userVo);
    }

    @ApiOperation("更新用户登陆时间")
    @PutMapping("/updateUserLoginTime/{id}")
    public boolean updateLoginTime(@ApiParam("更新的用户id") @PathVariable("id") Integer id){
        return userService.updateLoginTime(id);
    }

    @ApiOperation("根据id删除用户")
    @DeleteMapping("/deleteUser/{id}")
    @NotControllerResponseAdvice
    public boolean  deleteUser(@PathVariable("id") Integer id){
        return userService.deleteUserById(id);
    }
}
