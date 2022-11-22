package com.itfuture.e.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itfuture.e.pojo.Eadmin;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**定义eadmin的操作规范
 * @Description:
 * @Author 王小虎
 * @Version 1.0
 * @Date Created in 2022-10-24 16:41
 */
public interface AdminDao extends BaseMapper<Eadmin> {

    /**
     * 管理员根据用户名查询
     * @param userName 登录数据的实体
     * @return Eadmin对象
     */
    @Select("SELECT password FROM eadmin WHERE user_name =#{userName}")
    List<String> findByUserName(String userName);
}
