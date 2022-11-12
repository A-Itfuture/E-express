package com.itfuture.e.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itfuture.e.pojo.Courier;
import org.springframework.stereotype.Repository;


/**定义courier表格的操作规范
 * @Description:
 * @Author 王小虎
 * @Version 1.0
 * @Date Created in 2022-10-24 16:41
 */
@Repository
public interface CourierDao extends BaseMapper<Courier> {

    ///**
    // * 用于查询数据库所有快递员（总数+日注册新增）
    // * @return
    // */
    //List<Map<String,Integer>> console();
    //
    ///**
    // * 用于查询所有快递员
    // * @param limit 是否分页的标记
    // * @param offset SQL的起始索引
    // * @param pageNumber 页查询的数量
    // * @return 快递员的集合
    // */
    //List<Courier> findAll(boolean limit, int offset, int pageNumber);
    //
    ///**
    // * 根据手机号，查询快递员信息
    // * @param userPhone 手机号码
    // * @return 快递员的信息，不存在返回null
    // */
    // Courier findByUserPhone(String userPhone);
    //
    ///**
    // * 新增快递员
    // * @param courier 要录入的快递员对象
    // * @return 插入结果
    // */
    // //boolean insert (Courier courier);
    //
    ///**
    // * 快递员的修改
    // * @param id 要修改的快递员id
    // * @param newCourier 新的Courier对象
    // * @return 修改的结果
    // */
    //boolean update(int id, Courier newCourier);
    //
    //boolean updateNoPhone(int id, Courier newCourier);
    //
    ///**
    // * 根据快递员登录更新登陆时间
    // * @param userPhone
    // * @param date
    // */
    //void updateLoginTime(String userPhone, Date date);
    //
    //
    ///**
    // * 根据id删除快递员
    // * @param id 要删除的快递员id
    // * @return 删除的结果
    // */
    //boolean delete(int id);


}
