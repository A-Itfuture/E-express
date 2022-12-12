package com.itfuture.e.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itfuture.e.Exception.BusinessException;
import com.itfuture.e.dao.UserDao;
import com.itfuture.e.pojo.TokenDTO;
import com.itfuture.e.pojo.User;
import com.itfuture.e.pojo.UserMapperFactory;
import com.itfuture.e.pojo.vo.CourierVo;
import com.itfuture.e.pojo.vo.ResultCode;
import com.itfuture.e.pojo.vo.TableData;
import com.itfuture.e.pojo.vo.UserVo;
import com.itfuture.e.service.CourierService;
import com.itfuture.e.service.UserService;
import com.itfuture.e.util.JWTUtil;
import com.itfuture.e.util.RandomUtil;
import com.itfuture.e.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private CourierService courierService;

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
        if(exitByUserPhone(userVo.getUserPhone())!=null){
            throw new BusinessException(ResultCode.FAILED,"添加失败，该手机号已注册");
        }
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

    @Override
    public UserVo exitByUserPhone(String userPhone) {
        User user = userDao.findByUserPhone(userPhone);
        if (user==null){
            return null;
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
        //查询该用户
        User oldUser = userDao.selectById(newUserVo.getId());
        if(oldUser==null){
            throw new BusinessException(ResultCode.FAILED,"用户不存在，请先注册");
        }

        //手机号变动
        if (!newUserVo.getUserPhone().equals(oldUser.getUserPhone())){
            if(findByUserPhone(newUserVo.getUserPhone())!=null){
                throw new BusinessException(ResultCode.FAILED,"更新失败，改手机号已注册");
            }
        }

        User user = userMapperFactory.getMapperFacade().map(newUserVo, User.class);

        if(oldUser.equals(user)){
            log.info("相等");
        }
        if (oldUser.equals(user)){
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

    /**
     * 登录发送验证码
     *
     * @param userPhone
     * @return
     */
    @Override
    public boolean sendSms(String userPhone) {
        //TODO: 实际发送验证
        String code = RandomUtil.getCode()+"";
        //boolean flag = SMSUtil.loginSMS(userPhone, code);
        boolean flag = true;
        if(!flag) {
            throw new BusinessException(ResultCode.VALIDATE_ERROR, "验证码下发失败,请检查手机号或稍后再试");
        }
        RedisUtil.saveValue(userPhone,code,5, TimeUnit.MINUTES);
        return flag;
    }
    /**
     * 更新发送验证码
     *
     * @param userPhone
     * @return
     */
    @Override
    public boolean sendUpdate(String userPhone) {
        //TODO: 实际发送验证
        String code = RandomUtil.getCode()+"";
        //boolean flag = SMSUtil.loginSMS(userPhone, code);
        boolean flag = true;
        if(!flag) {
            throw new BusinessException(ResultCode.VALIDATE_ERROR, "验证码下发失败,请检查手机号或稍后再试");
        }
        RedisUtil.saveValue("update"+userPhone,code,5, TimeUnit.MINUTES);
        return flag;
    }

    /**
     * 用户登录
     *
     * @param userPhoneAndCode:phone和code的封装
     * @return
     */
    @Override
    @Transactional
    public String login(String userPhoneAndCode) {
        JSONObject jsonObject = JSON.parseObject(userPhoneAndCode);
        String userPhone = (String) jsonObject.get("userPhone");
        String userCode = (String) jsonObject.get("code");
        String sysCode = RedisUtil.getValue(userPhone);//系统发送的code,在redis里
        TokenDTO tokenDTO = new TokenDTO();//token
        String token =null;
        if(sysCode == null){
            throw new BusinessException(ResultCode.FAILED,"手机号码未获取短信或已失效");
        }else if(sysCode.equals(userCode)){
            //这里手机号码和短信一致 , 登陆成功
            CourierVo courier = courierService.exitByUserPhone(userPhone);
            UserVo user =null;
            if(courier!=null){
                //快递员
                courierService.updateLoginTime(courier.getId());
                //生成token
                tokenDTO.setId(courier.getId());
                tokenDTO.setUserPhone(courier.getUserPhone());
                tokenDTO.setUserName(courier.getUserName());
                tokenDTO.setGmtCreate(System.currentTimeMillis());
                tokenDTO.setStatus(1);
                token = JWTUtil.createToken(tokenDTO);
                //存入redis
                RedisUtil.saveValue(String.valueOf(courier.getId()),token,5,TimeUnit.MINUTES);
            }else{
                //用户
                user= exitByUserPhone(userPhone);
                //新用户就添加
                if (user==null){
                    user = new UserVo();
                    user.setUserPhone(userPhone);
                    user.setUserName("--");
                    user.setPassword("--");
                    user.setCardId("--");
                    if (insert(user)){
                        user= findByUserPhone(userPhone);
                    }
                }
                updateLoginTime(user.getId());

                //生成token
                tokenDTO.setId(user.getId());
                tokenDTO.setUserPhone(user.getUserPhone());
                tokenDTO.setUserName(user.getUserName());
                tokenDTO.setGmtCreate(System.currentTimeMillis());
                tokenDTO.setStatus(0);
                token = JWTUtil.createToken(tokenDTO);
                //存入redis
                RedisUtil.saveValue(String.valueOf(user.getId()),token,5,TimeUnit.MINUTES);

            }
        }else{
            //这里是验证码不一致 , 登陆失败
            throw new BusinessException(ResultCode.FAILED,"验证码不一致,请检查");
        }

        return token;
    }


    /**
     * 用户基本信息
     * 0用户，1是快递员
     * @param token
     * @return
     */
    @Override
    public TokenDTO userInfo(String token) {
        TokenDTO tokenInfo = JWTUtil.getTokenInfo(token);
        if (tokenInfo==null){
            throw new BusinessException(ResultCode.FAILED,"身份过期，请重新登录！");
        }
        return tokenInfo;
    }

    /**
     * 注销
     *
     * @param token
     * @return
     */
    @Override
    public String logout(String token) {
        TokenDTO tokenInfo = userInfo(token);
        //1.    销毁jwt有效性
        RedisUtil.deleteValue(String.valueOf(tokenInfo.getId()));
        return "已注销";
    }
}
