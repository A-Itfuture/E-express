package com.itfuture.e.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.itfuture.e.Exception.BusinessException;
import com.itfuture.e.pojo.TokenDTO;
import com.itfuture.e.pojo.vo.ResultCode;
import com.itfuture.e.util.JWTUtil;
import com.itfuture.e.util.RedisUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * JWT拦截器以及拦截处理
 *
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/15 11:34
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "jwt")
@PropertySource(value = {"classpath:application.yml"})
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${verify}")
    private String[] verify;
    @Value(("${skip}"))
    private String[] skip;
    private static final int THRESHOLD = 60 * 5;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //用户拦截器
        registry.addInterceptor(
                        new HandlerInterceptor() {
                            @Override
                            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                                //如果不是映射到方法直接通过
                                if (!(handler instanceof HandlerMethod)) {
                                    return true;
                                }

                                //请求中获取token
                                String authToken = request.getHeader("Authorization");
                                String token = null;
                                TokenDTO tokenInfo = null;
                                String idKey = null;
                                try {
                                    token = authToken.substring("Bearer".length() + 1).trim();
                                    tokenInfo = JWTUtil.getTokenInfo(token);
                                    idKey = String.valueOf(tokenInfo.getId());
                                } catch (Exception e) {
                                    throw new BusinessException(ResultCode.FAILED, "身份过期，请重新登录！");
                                }

                                //1判断请求是否有效
                                if(tokenInfo.getStatus()==3){//管理员
                                    String key = "admin"+idKey;
                                    if (RedisUtil.getValue(key) == null || !RedisUtil.getValue(key).equals(token)) {
                                        throw new BusinessException(ResultCode.FAILED, "身份过期，请重新登录！");
                                    }
                                    //2判断是否续期
                                    if (RedisUtil.getExpireTime(key) < 60*60*24) {
                                        RedisUtil.saveValue(key, token, 60*24*3, TimeUnit.MINUTES);
                                    }
                                }else {//用户、快递员

                                    if (RedisUtil.getValue(idKey) == null || !RedisUtil.getValue(idKey).equals(token)) {
                                        throw new BusinessException(ResultCode.FAILED, "身份过期，请重新登录！");
                                    }
                                    //2判断是否续期
                                    if (RedisUtil.getExpireTime(idKey) < THRESHOLD) {
                                        RedisUtil.saveValue(idKey, token, 10, TimeUnit.MINUTES);
                                    }
                                }
                                return true;
                            }

                        })
                // 需要拦截的请求
                .addPathPatterns(verify)
                // 需要放行的请求
                .excludePathPatterns(skip);
    }
}
