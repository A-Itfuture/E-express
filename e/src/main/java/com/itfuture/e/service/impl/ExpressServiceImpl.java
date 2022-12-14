package com.itfuture.e.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itfuture.e.Exception.BusinessException;
import com.itfuture.e.dao.ExpressDao;
import com.itfuture.e.pojo.Express;
import com.itfuture.e.pojo.ExpressMapperFactory;
import com.itfuture.e.pojo.TokenDTO;
import com.itfuture.e.pojo.vo.*;
import com.itfuture.e.service.ExpressService;
import com.itfuture.e.sms.TxSmsTemplate;
import com.itfuture.e.util.JWTUtil;
import com.itfuture.e.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 快件业务接口实现类
 *
 * @author： wxh
 * @version：v1.0
 * @date： 2022/10/10 09:30
 */
@Service
public class ExpressServiceImpl implements ExpressService {
    @Resource
    private ExpressDao expressDao;
    @Autowired
    private ExpressMapperFactory expressMapperFactory;
    @Autowired
    private TxSmsTemplate txSmsTemplate;

    /**
     * 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    @Override
    public List<Map<String, Long>> console() {
        return expressDao.console();
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的标记，true表示分页。false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    @Override
    public TableData findList(boolean limit, Integer offset, Integer pageNumber) {
        List<Express> list;
        if (limit) {
            list = expressDao.findAllLimit(offset, pageNumber);
        } else {
            list = expressDao.selectList(Wrappers.<Express>query());
        }

        List<ExpressVo> expressVoList = expressMapperFactory.getMapperFacade().mapAsList(list, ExpressVo.class);

        List<Map<String, Long>> console = console();
        Integer total = console.get(0).get("data1_size").intValue();
        //4.    将集合封装为 bootstrap-table识别的格式
        TableData<ExpressVo> data = new TableData<>();
        data.setRows(expressVoList);
        data.setTotal(total);
        return data;
    }

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息
     */
    @Override
    public ExpressVo findByNumber(String number) {
        Express express = expressDao.findByNumber(number);
        if (express == null) {
            throw new BusinessException(ResultCode.FAILED, "无此单号信息");
        }
        return expressMapperFactory.getMapperFacade().map(express, ExpressVo.class);
    }

    /**
     * 根据number查询自己快递信息
     *
     * @param number
     * @param userPhone
     * @return
     */
    @Override
    public ExpressVo findByNumberAndUserPhone(String number, String userPhone) {
        Express express = expressDao.findByNumber(number);
        if (express == null) {
            throw new BusinessException(ResultCode.FAILED, "无此单号信息");
        }
        if(!userPhone.equals(express.getUserPhone())){
            throw new BusinessException(ResultCode.FAILED, "您无此单号信息");
        }
        return expressMapperFactory.getMapperFacade().map(express, ExpressVo.class);
    }

    /**
     * 根据快递取件码，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息
     */
    @Override
    public ExpressVo findByCode(String code) {
        Express express = expressDao.findByCode(code);
        if (express == null) {
            throw new BusinessException(ResultCode.FAILED, "无此取件码信息");
        }
        return expressMapperFactory.getMapperFacade().map(express, ExpressVo.class);
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息列表
     */
    @Override
    public List<ExpressVo> findByUserPhone(String userPhone) {
        List<Express> expressList = expressDao.findByUserPhone(userPhone);
        if (expressList == null || expressList.isEmpty()) {
            throw new BusinessException(ResultCode.FAILED, "无此手机号的快递信息");
        }
        return expressMapperFactory.getMapperFacade().mapAsList(expressList, ExpressVo.class);
    }

    /**
     * 根据录入人手机号码，查询录入的所有记录
     *
     * @param sysPhone 手机号码
     * @return 查询的快递信息列表
     */
    @Override
    public List<ExpressVo> findBySysPhone(String sysPhone) {
        List<Express> expressList = expressDao.findBySysPhone(sysPhone);
        if (expressList == null || expressList.isEmpty()) {
            throw new BusinessException(ResultCode.FAILED, "无此手机号录入的快递信息");
        }
        return expressMapperFactory.getMapperFacade().mapAsList(expressList, ExpressVo.class);
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @param status    状态码
     * @return 查询的快递信息列表
     */
    @Override
    public List<ExpressVo> findByUserPhoneAndStatus(String userPhone, int status) {
        List<Express> expressList = expressDao.findByUserPhoneAndStatus(userPhone,status);
        if (expressList == null || expressList.isEmpty()) {
            throw new BusinessException(ResultCode.FAILED, "无此手机号相关的快递信息");
        }
        return expressMapperFactory.getMapperFacade().mapAsList(expressList, ExpressVo.class);
    }

    /**
     * 快递的录入
     *
     * @param expressVo 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    @Override
    public boolean insert(ExpressVo expressVo) {

        if(findByNumber(expressVo.getNumber())!=null){
            throw new BusinessException(ResultCode.FAILED,"单号重复！");
        }
        Express express = expressMapperFactory.getMapperFacade().map(expressVo, Express.class);
        //生成了取件码
        express.setCode(RandomUtil.getCode()+"");
        express.setInTime(new Timestamp(System.currentTimeMillis()));
        express.setStatus(0);
        //TODO 获取录取人手机号
        //express.setSysPhone();
        boolean flag = false;
        flag = expressDao.insert(express) != 0;
        if (flag){
            //TODO 发送取件短信
            //txSmsTemplate.sendMesModel(express.getUserPhone(),express.getCode());
        }else {
            throw new BusinessException(ResultCode.FAILED,"录入失败！");
        }
        return flag;
    }

    /**
     * 快递的修改
     *
     * @param newExpressVo 新的快递对象（number，company,username,userPhone）
     * @return 修改的结果，true表示成功，false表示失败
     * <p>
     * 逻辑 BUG ,
     */
    @Transactional
    @Override
    public boolean update(ExpressVo newExpressVo) {
        Express e = expressDao.selectById(newExpressVo.getId());
        boolean update=false;
        //Express e = expressDao.findByNumber(newExpressVo.getNumber());
        if (newExpressVo.getUserPhone()!=null && !newExpressVo.getUserPhone().equals(e.getUserPhone())){
            expressDao.deleteById(newExpressVo.getId());
            return insert(newExpressVo);
        }else {
            //如果已经出库的快递不允许修改！
            Express express = expressMapperFactory.getMapperFacade().map(newExpressVo, Express.class);
            update = expressDao.updateById(express) != 0;
            if (express.getStatus()==1 && express.getStatus()!=e.getStatus()){
                update= expressDao.updateStatus(e.getCode());
            }

        }
        return update;
    }

    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要修改的快递取件码
     * @return 修改的结果，true表示成功，false表示失败
     */
    @Override
    public boolean updateStatus(String code) {
        boolean flag = false;
        flag = expressDao.updateStatus(code);
        if (!flag){
            throw new BusinessException(ResultCode.VALIDATE_ERROR,"取件码不存在,请用户更新二维码");
        }
        return flag;
    }

    /**
     * 根据id，删除单个快递信息
     *
     * @param id 要删除的快递id
     * @return 删除的结果，true表示成功，false表示失败
     */
    @Override
    public boolean deleteExpressById(int id) {
        return expressDao.deleteById(id) != 0;
    }

    /**
     * 二维码内容生成
     *
     * @param code
     * @param type
     * @return
     */
    @Override
    public String createQRCode(String code, String type,String token) {
        String userPhone =null;
        String qRCodeContent = null;
        if("express".equals(type)){
            //快递二维码:被扫后,展示单个快递的信息
            //code
            qRCodeContent = "express_"+code;
        }else {
            //TODO 用户二维码:被扫后,快递员(柜子)端展示用户所有快递
            //userPhone
            //User wxUser = new User();
            //wxUser.setUserPhone("13279679928");
            TokenDTO tokenInfo = JWTUtil.getTokenInfo(token);

            userPhone = tokenInfo.getUserPhone();
            qRCodeContent = "userPhone_"+userPhone;
        }
        return qRCodeContent;
    }

}
