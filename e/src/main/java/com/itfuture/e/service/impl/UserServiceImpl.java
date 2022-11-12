package com.itfuture.e.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itfuture.e.mapper.UserDao;
import com.itfuture.e.pojo.User;
import com.itfuture.e.pojo.UserMapperFactory;
import com.itfuture.e.pojo.vo.TableData;
import com.itfuture.e.pojo.vo.UserVo;
import com.itfuture.e.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/10 11:35
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Autowired
    private UserMapperFactory userMapperFactory;

    /**
     * 用于查询数据库所有用户（总数+日注册新增）
     * @return
     */
    @Override
    public List<Map<String,Integer>> console(){
        return userDao.console();
    }

    /**
     * 用于查询所有用户
     * @param limit 是否分页的标记
     * @param offset SQL的起始索引
     * @param pageNumber 页查询的数量
     * @return 用户的集合
     */
    @Override
    public TableData findList(boolean limit, Integer offset, Integer pageNumber) {
        List<User> list = new ArrayList<>();
        if (limit){
            list = userDao.findAllLimit(offset, pageNumber);
        }else {
            list  = userDao.selectList(Wrappers.<User>query());
        }

        //for (User user:list){
        //    String createTime = DateFormatUtil.format(user.getCreateTime());
        //    String loginTime = user.getLoginTime()==null?"null":DateFormatUtil.format(user.getLoginTime());
        //    UserVo user2 = new BootStrapTableUser(user.getId(), user.getUserName(), user.getUserPhone(), user.getPassword(), createTime, loginTime);
        //    list2.add(user2);
        //}

        List<UserVo> userVoList = userMapperFactory.getMapperFacade().mapAsList(list, UserVo.class);

        List<Map<String, Integer>> console = console();
        Integer total = console.get(0).get("data_size");
        //4.    将集合封装为 bootstrap-table识别的格式
        TableData<UserVo> data = new TableData<>();
        data.setRows(userVoList);
        data.setTotal(total);
        return data;
    }
    //
    ///**
    // * 根据手机号，查询用户信息
    // * @param userPhone 手机号码
    // * @return 用户的信息，不存在返回null
    // */
    //@Override
    //public  User findByUserPhone(String userPhone){
    //    return userDao.findByUserPhone(userPhone);
    //}
    //
    ///**
    // * 用户的修改
    // * @param user 要录入的用户对象
    // * @return 插入结果
    // */
    //@Override
    //public boolean insert (User user){
    //    return userDao.insert(user)!=0;
    //}
    //
    ///**
    // * 用户的修改
    // * @param id 要修改的用户id
    // * @param newUser 新的user对象
    // * @return 修改的结果
    // */
    //@Override
    //public boolean update(int id,User newUser){
    //    return userDao.update(id,newUser);
    //}
    //@Override
    //public boolean updateNoPhone(int id,User newUser){
    //    return userDao.updateNoPhone(id,newUser);
    //}
    ///**
    // * 根据用户名更新登陆时间
    // * @param userPhone
    // * @param date
    // */
    //@Override
    //public void updateLoginTime(String userPhone, Date date){
    //    userDao.updateLoginTime(userPhone,date);
    //}
    //
    //
    ///**
    // * 根据id删除用户
    // * @param id 要删除的用户id
    // * @return 删除的结果
    // */
    //@Override
    //public  boolean delete(int id){
    //    return userDao.deleteById(id)!=0;
    //}
}
