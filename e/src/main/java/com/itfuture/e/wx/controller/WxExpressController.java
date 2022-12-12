package com.itfuture.e.wx.controller;

import com.itfuture.e.pojo.TokenDTO;
import com.itfuture.e.pojo.vo.ExpressVo;
import com.itfuture.e.pojo.vo.ResultVo;
import com.itfuture.e.service.ExpressService;
import com.itfuture.e.util.JWTUtil;
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
 * @date： 2022/11/23 20:47
 */
@Api(tags = "微信端快递服务控制器")
@RestController
@RequestMapping("/wxExpress")
public class WxExpressController {
    @Autowired
    private ExpressService expressService;

    @ApiOperation("根据用户手机号码，查询他所有的快递信息")
    @PostMapping("/findExpressByUserPhone")
    public List<ExpressVo> findByUserPhone(@ApiParam("查询条件:用户号码") @RequestBody String userPhone){
        //TODO 前端保证code不为空
        return expressService.findByUserPhone(userPhone);
    }

    @ApiOperation("根据用户手机号码，查询他所有的未取件快递信息")
    @PostMapping("/userExpressList")
    public List<ExpressVo> findByUserPhoneAndStatus(@ApiParam("查询条件:用户手机号码") @RequestBody String userPhone){
        return expressService.findByUserPhoneAndStatus(userPhone,0);
    }

    @ApiOperation("根据快递单号查询快递信息")
    @PostMapping("/findByNumberAndUserPhone/{number}")
    public ExpressVo findByNumberAndUserPhone(@ApiParam("查询条件：快递单号") @PathVariable("number") String number,@RequestBody String userPhone){
        //TODO 前端保证number不为空
        return expressService.findByNumberAndUserPhone(number,userPhone);
    }

    @ApiOperation("根据快递取件码查询快递信息")
    @GetMapping("/findByCode/{code}")
    public ExpressVo findByCode(@ApiParam("查询条件：快递取件码") @PathVariable("code") String code){
        //TODO 前端保证number不为空
        return expressService.findByCode(code);
    }

    @ApiOperation("快递的录入")
    @PostMapping("/addWxExpress")
    public ResultVo addCourier(@ApiParam("快递信息实体")  @RequestBody ExpressVo expressVo, HttpServletRequest request){
        String authToken  = request.getHeader("Authorization");
        String token = authToken.substring("Bearer".length() + 1).trim();
        return new ResultVo(expressService.insert(expressVo));
    }

    @ApiOperation("更改快递的状态为1，表示取件完成")
    @PutMapping("/updateStatus")
    public ResultVo updateStatus(@ApiParam("需更改快递的取件码")  @RequestBody String code){
        return new ResultVo(200,"取件成功",expressService.updateStatus(code));
    }
}
