package com.itfuture.e.service;


import com.itfuture.e.pojo.vo.CourierVo;
import com.itfuture.e.pojo.vo.TableData;

import java.util.List;
import java.util.Map;

/**快递员业务接口
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/10 08:30
 */
public interface CourierService {
    /**
     * 用于查询数据库所有快递员（总数+日注册新增）
     * @return
     */
    List<Map<String,Long>> console();

    /**
     * 用于查询所有快递员
     * @param limit 是否分页的标记
     * @param offset SQL的起始索引
     * @param pageNumber 页查询的数量
     * @return 表格封装实体
     */
    TableData findList(boolean limit, Integer offset, Integer pageNumber);

    /**
     * 根据手机号，查询快递员信息
     * @param userPhone 手机号码
     * @return 快递员的信息，不存在返回null
     */
    CourierVo findByUserPhone(String userPhone);

    /**
     * 快递员的修改
     * @param courierVo 要录入的快递员对象
     * @return 插入结果
     */
    boolean insert (CourierVo courierVo);


    /**
     * 快递员的修改
     * @param newCourierVo 新的courier对象
     * @return 修改的结果
     */
    boolean updateCourier(CourierVo newCourierVo);

    /**
     * 根据id更新登陆时间
     * @param id
     */
    boolean updateLoginTime(Integer id);


    /**
     * 根据id删除快递员
     * @param id 要删除的快递员id
     * @return 删除的结果
     */
    boolean deleteCourierById(int id);

    CourierVo exitByUserPhone(String userPhone);
}
