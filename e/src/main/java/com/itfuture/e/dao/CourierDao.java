package com.itfuture.e.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itfuture.e.pojo.Courier;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**定义courier表格的操作规范
 * @Description:
 * @Author 王小虎
 * @Version 1.0
 * @Date Created in 2022-10-24 16:41
 */
public interface CourierDao extends BaseMapper<Courier> {

    /**
     * 用于查询数据库所有快递员（总数+日注册新增）
     * @return
     */
    @Select("SELECT COUNT(ID) data_size,COUNT(TO_DAYS(create_time)=TO_DAYS(NOW()) OR NULL) data_day FROM courier")
    List<Map<String,Long>> console();

    /**
     * 用于查询所有快递员
     * @param offset SQL的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递员的集合
     */
    @Select("SELECT * FROM courier LIMIT #{offset},#{pageNumber}")
    List<Courier> findAllLimit(int offset, int pageNumber);

    /**
     * 根据手机号，查询快递员信息
     * @param userPhone 手机号码
     * @return 快递员的信息，不存在返回null
     */
    @Select("SELECT * FROM courier WHERE user_phone=#{userPhone}")
    Courier findByUserPhone(String userPhone);



}
