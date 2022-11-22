package com.itfuture.e.controller;

import com.itfuture.e.intercept.NotControllerResponseAdvice;
import com.itfuture.e.pojo.vo.CourierVo;
import com.itfuture.e.pojo.vo.ResultVo;
import com.itfuture.e.service.CourierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/06 15:48
 */
@Slf4j
@Api(tags = "快递员管理控制器")
@RestController("/courier")
public class CourierController {
    @Autowired
    private CourierService courierService;

    @ApiOperation("查询快递员总量以及当天新增快递员量")
    @GetMapping("/courierConsole")
    public List<Map<String, Long>> console(){
        return courierService.console();
    }

    @ApiOperation("分页获取所有快递员信息")
    @GetMapping("/courierListPage")
    public ResultVo listPage(@RequestParam(required = false,defaultValue = "0") Integer offset,
                             @RequestParam(required = false,defaultValue = "5") Integer pageNumber){
        return new ResultVo(courierService.findList(true,offset,pageNumber));
    }

    @ApiOperation("获取所有快递员信息")
    @GetMapping("/courierList")
    public ResultVo list(){
        return new ResultVo(courierService.findList(false,0,0));
    }

    @ApiOperation("新增快递员")
    @PostMapping("/addCourier")
    public ResultVo addCourier(@ApiParam("快递员信息实体")  @RequestBody CourierVo courierVo){
        return new ResultVo(courierService.insert(courierVo));
    }

    @ApiOperation("根据用户手机号查询快递员")
    @PostMapping("/findCourierByUserPhone")
    public CourierVo findByUserPhone(@ApiParam("查询条件：手机号") @RequestBody String oldPhone){
        //TODO 前端保证oldPhone不为空
        log.info(oldPhone);
        return courierService.findByUserPhone(oldPhone);
    }

    @ApiOperation("更新快递员信息")
    @PutMapping("/updateCourier")
    public boolean updateCourier(@ApiParam("更改快递员信息的实体")  @RequestBody CourierVo courierVo){
        return courierService.updateCourier(courierVo);
    }

    @ApiOperation("更新快递员登陆时间")
    @PutMapping("/updateCourierLoginTime/{id}")
    public boolean updateLoginTime(@ApiParam("更新的快递员用户id") @PathVariable("id") Integer id){
        return courierService.updateLoginTime(id);
    }

    @ApiOperation("根据id删除快递员")
    @DeleteMapping("/deleteCourier/{id}")
    @NotControllerResponseAdvice
    public boolean  deleteCourier(@PathVariable("id") Integer id){
        return courierService.deleteCourierById(id);
    }
}
