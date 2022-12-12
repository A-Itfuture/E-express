package com.itfuture.e.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itfuture.e.pojo.TokenDTO;
import com.itfuture.e.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**JWT工具类
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/30 09:26
 */
@Slf4j
public class JWTUtil {
    //加密secret
    private static final String SECRET ="It@Future";//密钥，一定不要报漏 例如：ASD@Dik 越复杂越好
    //过期时间：jwtToken的默认有效时间 单位分钟
    private static final Integer TIME_OUT_HOUR = 24*2;
    //需要重新生成的天数 如果token的时间超过这个 则重新生成token
    private static final Integer NEED_CREATE_HOUR = 24*1;

    /**
     * 生成token
     * @param tokenDTO
     * @return
     */
    public static String createToken(TokenDTO tokenDTO){
        try {
            //头部信息
            HashMap<String, Object> header = new HashMap<>(2);
            header.put("Type","Jwt");
            header.put("alg","HS256");

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR,TIME_OUT_HOUR);
            String token = JWT.create()
                    .withHeader(header)
                    .withClaim("token", JSON.toJSONString(tokenDTO))// 设置载荷信息
                    .withExpiresAt(calendar.getTime())
                    .sign(Algorithm.HMAC256(SECRET));//私钥和加密算法
            return token;
        } catch (Exception e) {
            log.error("create token occur error, error is:{}", e);
            return null;
        }
    }


    /**
     * 验证token
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取token信息 如果token有误则返回null
     * @param token
     * @return
     */
    public static DecodedJWT verifyToken(String token){
        try {
            return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        }catch (Exception e) {
           return null;
        }
    }

    /**
     * 获取token信息 如果token有误则返回null
     * @param token
     * @return
     */
    public static TokenDTO getTokenInfo(String token){
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            String tokenInfo = decodedJWT.getClaim("token").asString();
            return JSON.parseObject(tokenInfo,TokenDTO.class);
        }catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取用户ID
     * @param decodedJWT
     * @return
     */
    public static Integer getUserId(DecodedJWT decodedJWT){
        return decodedJWT.getClaim("userId").asInt();
    }
    /**
     * 获取用户名
     * @param decodedJWT
     * @return
     */
    public static String getUserName(DecodedJWT decodedJWT){
        return decodedJWT.getClaim("userName").asString();
    }

    /**
     * 验证是否修改过密码
     * @param decodedJWT
     * @param user
     * @return
     */
    public static boolean isUpdatedPassword(DecodedJWT decodedJWT,User user){
        String oldPwd = decodedJWT.getClaim("key").asString();
        String newPwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        return !oldPwd.equals(newPwd);
    }


    /**
     * 是否需要重新生成token （为了延续token时长）
     * @param decodedJWT
     * @return
     */
    public static boolean needCreate(DecodedJWT decodedJWT) {
        Date timeoutDate = decodedJWT.getExpiresAt();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, TIME_OUT_HOUR - NEED_CREATE_HOUR);
        if (timeoutDate.before(calendar.getTime())) {
            return true;
        }
        return false;

    }


}
