package com.itfuture.e.controller;

import com.itfuture.e.intercept.NotControllerResponseAdvice;
import com.itfuture.e.pojo.vo.ExpressVo;
import com.itfuture.e.pojo.vo.ResultVo;
import com.itfuture.e.service.ExpressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**快递管理控制器
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/06 15:48
 */
@Api(tags = "快递管理控制器")
@RestController("/express")
public class ExpressController {
    @Autowired
    private ExpressService expressService;

    @ApiOperation("查询快递总量以及当天新增快递数量")
    @GetMapping("/expressConsole")
    public List<Map<String, Long>> expressConsole(){
        return expressService.console();
    }

    @ApiOperation("分页获取所有快递信息")
    @GetMapping("/expressListPage")
    public ResultVo listPage(@RequestParam(required = false,defaultValue = "0") Integer offset,
                             @RequestParam(required = false,defaultValue = "5") Integer pageNumber){
        return new ResultVo(expressService.findList(true,offset,pageNumber));
    }

    @ApiOperation("获取所有快递信息")
    @GetMapping("/expressList")
    public ResultVo list(){
        return new ResultVo(expressService.findList(false,0,0));
    }

    @ApiOperation("根据快递单号查询快递信息")
    @GetMapping("/findByNumber/{number}")
    public ExpressVo findByNumber(@ApiParam("查询条件：快递单号") @PathVariable("number") String number){
        //TODO 前端保证number不为空
        return expressService.findByNumber(number);
    }

    @ApiOperation("根据取件码查询快递信息")
    @PostMapping("/findByCode")
    public ExpressVo findByCode(@ApiParam("查询条件：取件码") @RequestBody String code){
        //TODO 前端保证code不为空
        return expressService.findByCode(code);
    }

    @ApiOperation("根据用户手机号码，查询他所有的快递信息")
    @PostMapping("/findByUserPhone")
    public List<ExpressVo> findByUserPhone(@ApiParam("查询条件:用户号码") @RequestBody String userPhone){
        //TODO 前端保证code不为空
        return expressService.findByUserPhone(userPhone);
    }

    @ApiOperation("根据录入人手机号码，查询录入的所有记录")
    @PostMapping("/findBySysPhone")
    public List<ExpressVo> findBySysPhone(@ApiParam("查询条件:录入人手机号码") @RequestBody String sysPhone){
        return expressService.findBySysPhone(sysPhone);
    }

    @ApiOperation("根据用户手机号码和指定状态，查询他所有的快递信息")
    @PostMapping("/findByUserPhoneAndStatus/{status}")
    public List<ExpressVo> findByUserPhoneAndStatus(@ApiParam("查询条件:用户手机号码") @RequestBody String userPhone,@PathVariable("status") int status){
        return expressService.findByUserPhoneAndStatus(userPhone,status);
    }

    @ApiOperation("快递的录入")
    @PostMapping("/addExpress")
    public ResultVo addCourier(@ApiParam("快递信息实体")  @RequestBody ExpressVo expressVo){
        return new ResultVo(expressService.insert(expressVo));
    }


    @ApiOperation("快递的修改")
    @PutMapping("/updateExpress")
    public boolean updateExpress(@ApiParam("更改快递信息的实体")  @RequestBody ExpressVo expressVo){
        return expressService.update(expressVo);
    }


    @ApiOperation("更改快递的状态为1，表示取件完成")
    @PutMapping("/updateStatus")
    public boolean updateStatus(@ApiParam("需更改快递的取件码")  @RequestBody String code){
        return expressService.updateStatus(code);
    }

    @ApiOperation("根据id，删除单个快递信息")
    @DeleteMapping("/deleteExpress/{id}")
    @NotControllerResponseAdvice
    public boolean  deleteExpress(@PathVariable("id") Integer id) {
        return expressService.deleteExpressById(id);
    }

}
