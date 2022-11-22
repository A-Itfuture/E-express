package com.itfuture.e.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itfuture.e.pojo.Express;

import java.util.List;
import java.util.Map;

/**
 * 快件表操作
 *
 * @author： wxh
 * @version：v1.0
 * @date： 2022/10/10 08:30
 */
public interface ExpressDao extends BaseMapper<Express> {
    /**
     * 用于查询数据库所有快递（总数+新增），待取件快递（总数+新增）
     *
     * @return [{size:总数，day:新增},{size:总数，day:新增}]
     */
    List<Map<String, Long>> console();

    /**
     * 用于查询所有快递
     *
     * @param offset     SQL的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    List<Express> findAllLimit(int offset, int pageNumber);

    /**
     * 根据快单号查询快递信息
     *
     * @param number 快递单号
     * @return 快递的信息，不存在返回null
     */
    Express findByNumber(String number);

    /**
     * 根据取件码，查询快递信息
     *
     * @param code 取件码
     * @return 快递的信息
     */
    Express findByCode(String code);

    /**
     * 根据手机号，查询快递信息
     *
     * @param userPhone 手机号码
     * @return 快递的信息
     */
    List<Express> findByUserPhone(String userPhone);
    /**
     * 根据录入人手机号，查询快递信息
     * @param sysPhone 录入人手机号码
     * @return 快递的信息，不存在返回null
     */
     List<Express> findBySysPhone(String sysPhone);

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @param status 状态码
     * @return 查询的快递信息列表
     */
    List<Express> findByUserPhoneAndStatus(String userPhone,int status);



    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要修改的快递单号
     * @return 修改的结果
     */
    boolean updateStatus(String code);


}

