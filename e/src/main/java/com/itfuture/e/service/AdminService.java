package com.itfuture.e.service;


import com.itfuture.e.pojo.Eadmin;
import com.itfuture.e.pojo.TokenDTO;

/**管理员接口
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/04 10:34
 */
public interface AdminService {

    /**
     * 登录
     * @param eadmin
     * @return 管理员admin实例对象
     */
    String login(Eadmin eadmin,String ip);

    /**
     * 记录管理员登录时间和ip
     * @param eadmin
     * @param ip
     */
    boolean addLoginTimeAndIp(Eadmin eadmin,String ip);

    /**
     * 获取登录管理员信息
     * @param token
     * @return
     */
    TokenDTO adminInfo(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    String exitLogin(String token);
}
