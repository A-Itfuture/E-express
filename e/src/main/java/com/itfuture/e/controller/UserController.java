package com.itfuture.e.controller;


import com.itfuture.e.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/06 15:49
 */
@Api(tags = "用户管理控制器")
@RestController("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("查询用户总量以及当天新增用户量")
    @GetMapping("/console")
    public ResponseEntity console(HttpServletRequest req, HttpServletResponse resp){
        List<Map<String, Long>> data = userService.console();
        return ResponseEntity.ok(data);
    }

    @ApiOperation("获取所有用户信息")
    @GetMapping("/list")
    public ResponseEntity list(Integer offset,Integer pageNumber,HttpServletRequest request){
        return ResponseEntity.ok(userService.findList(true,offset,pageNumber));
    }

    //@PostMapping("/addUser")
    //public String insert(HttpServletRequest request, HttpServletResponse response){
    //    //1 获取前端传参
    //    String username = request.getParameter("userName");
    //    String userPhone = request.getParameter("userPhone");
    //    String cardId = request.getParameter("cardId");
    //    String password = request.getParameter("passWord");
    //    User user = new User(username, userPhone, password, cardId);
    //    //2 调用Service
    //    boolean flag = UserService.insert(user);
    //    Message msg = new Message();
    //    //3 封装结果信息
    //    if(flag){
    //        //录入成功
    //        msg.setStatus(0);
    //        msg.setResult("添加用户成功!");
    //    }else{
    //        //录入失败
    //        msg.setStatus(-1);
    //        msg.setResult("添加用户失败!");
    //    }
    //    //4 转Json响应
    //    String json = JSONUtil.toJSON(msg);
    //    return json;
    //}
    //
    //@ResponseBody("/find")
    //public String find(HttpServletRequest request,HttpServletResponse response){
    //    String oldPhone = request.getParameter("oldPhone");
    //    User user = UserService.findByUserPhone(oldPhone);
    //    Message msg = new Message();
    //    if(user == null){
    //        msg.setStatus(-1);
    //        msg.setResult("手机号不存在");
    //    }else{
    //        msg.setStatus(0);
    //        msg.setResult("查询成功");
    //        msg.setData(user);
    //    }
    //    String json = JSONUtil.toJSON(msg);
    //    return json;
    //}
    //
    //@ResponseBody("/update")
    //public String update(HttpServletRequest request,HttpServletResponse response){
    //    //获取参数
    //    String id = request.getParameter("id");
    //    String oldPhone = request.getParameter("oldPhone");
    //    String userName = request.getParameter("userName");
    //    String userPhone = request.getParameter("userPhone");
    //    String password = request.getParameter("password");
    //    String cardId = request.getParameter("cardId");
    //    User user = new User(userName, userPhone, password, cardId);
    //    boolean flag = false;
    //    // 调用service
    //    if (oldPhone!=null){
    //        if (oldPhone.equals(userPhone)){
    //            flag = UserService.updateNoPhone(Integer.parseInt(id), user);
    //        }else {
    //            flag = UserService.update(Integer.parseInt(id), user);
    //        }
    //    }
    //    Message msg = new Message();
    //    if (flag){
    //        msg.setStatus(0);
    //        msg.setResult("修改成功");
    //    }else {
    //        msg.setStatus(-1);
    //        msg.setResult("修改失败");
    //    }
    //
    //    //转Json
    //    String json = JSONUtil.toJSON(msg);
    //    return json;
    //}
    //
    //@DeleteMapping("/deleteUser")
    //public ResponseEntity delete(HttpServletRequest request,HttpServletResponse response){
    //    int id = Integer.parseInt(request.getParameter("id"));
    //    boolean flag = userService.delete(id);
    //    return new ResponseEntity.status();
    //}
}
