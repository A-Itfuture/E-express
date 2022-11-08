package com.itfuture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itfuture.pojo.Eadmin;

import java.util.Date;

/**定义eadmin表格的操作规范
 * @Description:
 * @Author 王小虎
 * @Version 1.0
 * @Date Created in 2022-10-24 16:41
 */
public interface BaseAdminDao extends BaseMapper<Eadmin> {

    /**
     * 根据用户名更新登陆时间和登录Ip
     * @param username
     * @param date
     * @param ip
     */
    void updateLoginTime(String username, Date date, String ip);

    /**
     * 管理员根据用户名和密码登录
     * @param username 账号
     * @param password 密码
     * @return 登录的结果：true：表示登录成功
     */
    boolean login(String username,String password);
}
