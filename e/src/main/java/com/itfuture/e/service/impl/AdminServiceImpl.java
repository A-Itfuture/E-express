package com.itfuture.e.service.impl;

import com.itfuture.e.Exception.BusinessException;
import com.itfuture.e.dao.AdminDao;
import com.itfuture.e.pojo.Eadmin;
import com.itfuture.e.pojo.TokenDTO;
import com.itfuture.e.pojo.vo.ResultCode;
import com.itfuture.e.service.AdminService;
import com.itfuture.e.util.JWTUtil;
import com.itfuture.e.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 管理员接口实现
 *
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/18 13:19
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;

    /**
     * 登录
     *
     * @param eadmin
     * @return 管理员admin实例对象
     */
    @Override
    public String login(Eadmin eadmin,String ip) {
        List<String> passwordList = adminDao.findByUserName(eadmin.getUserName());

        if (passwordList == null || passwordList.isEmpty()) {
;            throw new BusinessException(ResultCode.VALIDATE_ERROR, "用户名错误，无此管理员");
        }

        if (!passwordList.contains(eadmin.getPassword())) {
            throw new BusinessException(ResultCode.VALIDATE_ERROR, "密码输入错误");
        }
        //用户名密码无误，就记录本次登录
        Eadmin adminUser = adminDao.findByUser(eadmin.getUserName(), eadmin.getPassword());
        if (adminUser==null) {
            throw new BusinessException(ResultCode.VALIDATE_ERROR, "管理员信息匹配失败");
        }
        eadmin.setPassword(null);
        boolean flag = addLoginTimeAndIp(eadmin,ip);
        eadmin.setLoginIp(ip);
        if (!flag){
            throw new BusinessException(ResultCode.VALIDATE_ERROR, "管理员登录失败");
        }
        //TODO 处理用户鉴权问题
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setId(adminUser.getId());
        tokenDTO.setUserName(adminUser.getUserName());
        //管理员没有手机号，因此这里用户名充当手机号
        tokenDTO.setUserPhone(adminUser.getUserName());
        tokenDTO.setStatus(3);
        String token = JWTUtil.createToken(tokenDTO);
        RedisUtil.saveValue("admin"+tokenDTO.getId(),token,60*24*3, TimeUnit.MINUTES);//3天需登录认证
        return token;
    }

    /**
     * 更新记录登录时间和ip
     *
     * @param eadmin
     * @param ip
     */
    @Override
    public boolean addLoginTimeAndIp(Eadmin eadmin, String ip) {
        eadmin.setLoginTime(new Timestamp(System.currentTimeMillis()));
        eadmin.setLoginIp(ip);
        return adminDao.insert(eadmin) != 0;
    }

    /**
     * 获取登录管理员信息
     *
     * @param token
     * @return
     */
    @Override
    public TokenDTO adminInfo(String token) {
        TokenDTO tokenInfo = JWTUtil.getTokenInfo(token);
        if (tokenInfo==null){
            throw new BusinessException(ResultCode.FAILED,"身份过期，请重新登录！");
        }
        return tokenInfo;
    }

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @Override
    public String exitLogin(String token) {
        TokenDTO tokenInfo = adminInfo(token);
        //1.    销毁jwt有效性
        RedisUtil.deleteValue("admin"+tokenInfo.getId());
        return "已注销";
    }
}
