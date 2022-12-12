package com.itfuture.e.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itfuture.e.Exception.BusinessException;
import com.itfuture.e.dao.CourierDao;
import com.itfuture.e.pojo.Courier;
import com.itfuture.e.pojo.UserMapperFactory;
import com.itfuture.e.pojo.vo.CourierVo;
import com.itfuture.e.pojo.vo.ResultCode;
import com.itfuture.e.pojo.vo.TableData;
import com.itfuture.e.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 快递员业务接口实现类
 *
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/10 21:57
 */
@Service
public class CourierServiceImpl implements CourierService {
    @Resource
    private CourierDao courierDao;
    @Autowired
    private UserMapperFactory userMapperFactory;

    /**
     * 用于查询数据库所有快递员（总数+日注册新增）
     *
     * @return
     */
    @Override
    public List<Map<String, Long>> console() {
        return courierDao.console();
    }

    /**
     * 用于查询所有快递员
     *
     * @param limit      是否分页的标记
     * @param offset     SQL的起始索引
     * @param pageNumber 页查询的数量
     * @return 表格封装实体
     */
    @Override
    public TableData findList(boolean limit, Integer offset, Integer pageNumber) {
        List<Courier> list;
        if (limit) {
            list = courierDao.findAllLimit(offset, pageNumber);
        } else {
            list = courierDao.selectList(Wrappers.<Courier>query());
        }

        List<CourierVo> courierVoList = userMapperFactory.getMapperFacade().mapAsList(list, CourierVo.class);

        List<Map<String, Long>> console = console();
        Integer total = console.get(0).get("data_size").intValue();
        //4.    将集合封装为 bootstrap-table识别的格式
        TableData<CourierVo> data = new TableData<>();
        data.setRows(courierVoList);
        data.setTotal(total);
        return data;
    }

    /**
     * 根据手机号，查询快递员信息
     *
     * @param userPhone 手机号码
     * @return 快递员的信息，不存在返回null
     */
    @Override
    public CourierVo findByUserPhone(String userPhone) {
        Courier courier = courierDao.findByUserPhone(userPhone);
        if (courier == null) {
            throw new BusinessException(ResultCode.FAILED, "查无此人，手机号未注册");
        }
        return userMapperFactory.getMapperFacade().map(courier, CourierVo.class);
    }
    @Override
    public CourierVo exitByUserPhone(String userPhone) {
        Courier courier = courierDao.findByUserPhone(userPhone);
        if (courier == null) {
           return null;
        }
        return userMapperFactory.getMapperFacade().map(courier, CourierVo.class);
    }

    /**
     * 快递员的增加
     *
     * @param courierVo 要录入的快递员对象
     * @return 插入结果
     */
    @Override
    public boolean insert(CourierVo courierVo) {
        //不用作手机号过滤是因为：在输入手机后就作是否可用检查
        Courier courier = userMapperFactory.getMapperFacade().map(courierVo, Courier.class);
        courier.setExpressCount("0");
        courier.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return courierDao.insert(courier)!=0;
    }

    /**
     * 快递员的修改
     *
     * @param newCourierVo 新的courier对象
     * @return 修改的结果
     */
    @Override
    public boolean updateCourier(CourierVo newCourierVo) {
        Courier courier = userMapperFactory.getMapperFacade().map(newCourierVo, Courier.class);
        //查询该用户
        Courier oldCourier = courierDao.selectById(courier.getId());
        if (oldCourier!=null && oldCourier.equals(courier)){
            throw new BusinessException(ResultCode.SUCCESS,"检测到用户未作任何修改！");
        }
        return courierDao.updateById(courier)!=0;
    }

    /**
     * 根据id更新登陆时间
     *
     * @param id
     */
    @Override
    public boolean updateLoginTime(Integer id) {
        Courier courier =courierDao.selectById(id);
        if (courier == null){
            throw new BusinessException(ResultCode.FAILED,"id对应用户不存在");
        }
        //更新登陆时间
        courier.setLoginTime(new Timestamp(System.currentTimeMillis()));
        return courierDao.updateById(courier)!=0;
    }

    /**
     * 根据id删除快递员
     *
     * @param id 要删除的快递员id
     * @return 删除的结果
     */
    @Override
    public boolean deleteCourierById(int id) {
        return courierDao.deleteById(id)!=0;
    }
}
