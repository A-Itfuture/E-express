package com.itfuture.e.service;

import com.itfuture.e.pojo.TokenDTO;
import com.itfuture.e.pojo.vo.TableData;
import com.itfuture.e.pojo.vo.UserVo;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**用户业务接口
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/10 08:30
 */
public interface UserService {
    /**
     * 用于查询数据库所有用户（总数+日注册新增）
     * @return
     */
    List<Map<String,Long>> console();

    /**
     * 用于查询所有用户
     * @param limit 是否分页的标记
     * @param offset SQL的起始索引
     * @param pageNumber 页查询的数量
     * @return 用户的集合
     */
    TableData findList(boolean limit, Integer offset, Integer pageNumber);

    /**
     * 用户的添加
     * @param userVo 要录入的用户对象
     * @return 插入结果
     */
    boolean insert (UserVo userVo);

    /**
     * 根据手机号，查询用户信息
     * @param userPhone 手机号码
     * @return 用户的信息，不存在返回null
     */
    UserVo findByUserPhone(String userPhone);

    /**
     * 该手机号用户是否存在
     * @param userPhone
     * @return
     */
    UserVo exitByUserPhone(String userPhone);

    /**
     * 用户的修改
     * @param newUserVo 新的user对象
     * @return 修改的结果
     */
    boolean updateUser(UserVo newUserVo);

    /**
     * 根据用户id更新登陆时间
     * @param id
     */
    boolean updateLoginTime(Integer id);

    /**
     * 根据id删除用户
     * @param id 要删除的用户id
     * @return 删除的结果
     */
    boolean deleteUserById(int id);

    /**
     *登录发送验证码
     * @param userPhone
     * @return
     */
    boolean sendSms(String userPhone);

    /**
     * 用户登录
     * @param userPhoneAndCode
     * @return
     */
    String login(String userPhoneAndCode);

    /**
     *用户基本信息
     * @param token
     * @return
     */
    TokenDTO userInfo(String token);

    /**
     * 注销
     * @param token
     * @return
     */
    String logout(String token);

    /**
     * 更新发送验证码
     *
     * @param userPhone
     * @return
     */
    boolean sendUpdate(String userPhone);
}
