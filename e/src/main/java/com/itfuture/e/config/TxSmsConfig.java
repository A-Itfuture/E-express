package com.itfuture.e.config;

import com.itfuture.e.sms.TxProperties;
import com.itfuture.e.sms.TxSmsTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**短信发送自动配置类
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/11 20:29
 */
@Configuration
@EnableConfigurationProperties({TxProperties.class})
public class TxSmsConfig {
    /*
     * 创建发送短信的工具类
     * 将TxProperties对象注入到容器中
     * 要配置CommonsAutoConfiguration到resources/META-INF/spring.factories中
     * */
    @Bean
    public TxSmsTemplate txSmsTemplate(TxProperties txProperties) {
        return new TxSmsTemplate(txProperties);
    }
}
