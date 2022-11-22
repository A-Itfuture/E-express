package com.itfuture.e.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itfuture.e.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**定义user表格的操作规范
 * @Description: 使用注解
 * @Author 王小虎
 * @Version 1.0
 * @Date Created in 2022-10-24 16:41
 */
public interface UserDao extends BaseMapper<User> {

    /**
     * 用于查询数据库所有用户（总数+日注册新增）
     * @return
     */
    @Select("SELECT COUNT(ID) data_size,COUNT(TO_DAYS(create_time)=TO_DAYS(NOW()) OR NULL) data_day FROM user")
    List<Map<String,Long>> console();

    /**
     * 用于分页查询所有用户
     * @param offset SQL的起始索引
     * @param pageNumber 页查询的数量
     * @return 用户的集合
     */
    @Select("SELECT * FROM user LIMIT #{offset},#{pageNumber}")
    List<User> findAllLimit(int offset, int pageNumber);

    /**
     * 根据手机号，查询用户信息
     * @param userPhone 手机号码
     * @return 用户的信息，不存在返回null
     */
    @Select("SELECT * FROM user WHERE user_phone=#{userPhone}")
    User findByUserPhone(String userPhone);

    //
    //boolean updateNoPhone(int id, User newUser);
    //
    ///**
    // * 根据用户名更新登陆时间
    // * @param userPhone
    // * @param date
    // */
    //void updateLoginTime(String userPhone, Date date);



}
