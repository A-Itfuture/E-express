package com.itfuture.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itfuture.pojo.Express;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author 王小虎
 * @Version 1.0
 * @Date Created in 2021-12-31 14:50
 */
public interface ExpressDao extends BaseMapper<Express  > {
    /**
     * 用于查询数据库所有快递（总数+新增），待取件快递（总数+新增）
     * @return [{size:总数，day:新增},{size:总数，day:新增}]
     */
    List<Map<String,Integer>> console();

    /**
     * 用于查询所有快递
     * @param limit 是否分页的标记
     * @param offset SQL的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    List<Express> findAll(boolean limit, int offset, int pageNumber);

    /**
     * 根据快单号查询快递信息
     * @param number 快递单号
     * @return 快递的信息，不存在返回null
     */
    Express findByNumber(String number);
    /**
     * 根据取件码，查询快递信息
     * @param code 取件码
     * @return 快递的信息，不存在返回null
     */
    Express findByCode(String code);
    /**
     * 根据手机号，查询快递信息
     * @param userPhone 手机号码
     * @return 快递的信息，不存在返回null
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
    public List<Express> findByUserPhoneAndStatus(String userPhone,int status);
    /**
     * 快递的录入
     * @param e 要录入的快递对象
     * @return 录入结果
     */
     //boolean insert(Express e) throws DuplicateKeyException;

    /**
     * 快递的修改
     * @param id 要修改的快递id
     * @param newExpress 新的快递对象
     * @return 修改的结果
     */
     boolean update(int id,Express newExpress);
    /**
     * 更改快递的状态为1，表示取件完成
     * @param number 要修改的快递单号
     * @return 修改的结果
     */
     boolean updateStatus(String code);

    /**
     * 根据id删除快递
     * @param id 要删除的快递id
     * @return 删除的结果
     */
     boolean delete(int id);

}

