package com.itfuture.e.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itfuture.e.Exception.BusinessException;
import com.itfuture.e.dao.UserDao;
import com.itfuture.e.pojo.User;
import com.itfuture.e.pojo.UserMapperFactory;
import com.itfuture.e.pojo.vo.ResultCode;
import com.itfuture.e.pojo.vo.TableData;
import com.itfuture.e.pojo.vo.UserVo;
import com.itfuture.e.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/10 11:35
 */
@Slf4j
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
    public List<Map<String,Long>> console(){
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
        List<User> list;
        if (limit){
            list = userDao.findAllLimit(offset, pageNumber);
        }else {
            list  = userDao.selectList(Wrappers.<User>query());
        }

        List<UserVo> userVoList = userMapperFactory.getMapperFacade().mapAsList(list, UserVo.class);

        List<Map<String, Long>> console = console();
        Integer total = console.get(0).get("data_size").intValue();
        //4.    将集合封装为 bootstrap-table识别的格式
        TableData<UserVo> data = new TableData<>();
        data.setRows(userVoList);
        data.setTotal(total);
        return data;
    }
    /**
     * 插入用户
     * @param userVo 要录入的用户对象
     * @return 插入结果
     */
    @Override
    public boolean insert (UserVo userVo){
        User user = userMapperFactory.getMapperFacade().map(userVo, User.class);
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return userDao.insert(user)!=0;
    }
    /**
     * 根据手机号，查询用户信息
     * @param userPhone 手机号码
     * @return 用户的信息，不存在返回null
     */
    @Override
    public  UserVo findByUserPhone(String userPhone){
        User user = userDao.findByUserPhone(userPhone);
        if (user==null){
            throw new BusinessException(ResultCode.FAILED,"查无此人，手机号未注册");
        }
        return userMapperFactory.getMapperFacade().map(user,UserVo.class);
    }

    /**
     * 用户的修改
     *
     * @param newUserVo 新的userVo对象
     * @return 修改的结果
     */
    @Override
    public boolean updateUser(UserVo newUserVo) {
        User user = userMapperFactory.getMapperFacade().map(newUserVo, User.class);
        //查询该用户
        User oldUser = userDao.selectById(user.getId());
        if(oldUser.equals(user)){
            log.info("相等");
        }
        if (oldUser!=null && oldUser.equals(user)){
            throw new BusinessException(ResultCode.SUCCESS,"检测到用户未作任何修改！");
        }
        return userDao.updateById(user)!=0;
    }

    /**
     * 根据用户id更新登陆时间
     *
     * @param id
     */
    @Override
    public boolean updateLoginTime(Integer id) {
        User user = userDao.selectById(id);
        if (user == null){
            throw new BusinessException(ResultCode.FAILED,"id对应用户不存在");
        }
        //更新登陆时间
        user.setLoginTime(new Timestamp(System.currentTimeMillis()));
        return userDao.updateById(user)!=0;
    }

    /**
     * 根据id删除用户
     * @param id 要删除的用户id
     * @return 删除的结果
     */
    @Override
    public  boolean deleteUserById(int id){
        return userDao.deleteById(id)!=0;
    }
}
