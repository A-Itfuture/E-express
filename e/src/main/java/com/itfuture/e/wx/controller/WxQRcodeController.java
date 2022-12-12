package com.itfuture.e.wx.controller;

import com.itfuture.e.pojo.vo.ExpressVo;
import com.itfuture.e.service.ExpressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/23 20:56
 */
@Api(tags = "微信端扫码取件服务控制器")
@RestController
@RequestMapping("/wxQRCode")
public class WxQRcodeController {
    @Autowired
    private ExpressService expressService;

    @ApiOperation("二维码内容生成")
    @PostMapping("/createQRCode/{type}")
    public String createQRCode(@RequestBody String code, @PathVariable("type") String type, HttpServletRequest request){
        String authToken  = request.getHeader("Authorization");
        String token = authToken.substring("Bearer".length() + 1).trim();
        return expressService.createQRCode(code,type,token);
    }


    @ApiOperation("根据录入人手机号码，查询录入的所有记录")
    @PostMapping("/findBySysPhone")
    public List<ExpressVo> findBySysPhone(@ApiParam("查询条件:录入人手机号码") @RequestBody String sysPhone){
        return expressService.findBySysPhone(sysPhone);
    }

}
