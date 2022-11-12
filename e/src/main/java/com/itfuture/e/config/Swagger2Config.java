package com.itfuture.e.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/12 19:41
 */
@Configuration
public class Swagger2Config {
    @Bean
    public Docket webApiConfig(){
        //添加head参数start
        ArrayList<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("001")
                .description("it")
                .defaultValue("1")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        pars.add(tokenPar.build());
        //...添加其他用户

        //添加head参数end
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                //可以测试请求头中：输入token
                .apis(RequestHandlerSelectors.basePackage("com.itfuture.e.controller"))
                //.apis(RequestHandlerSelectors.withClassAnnotation(ApiOperation.class))
                //过滤掉admin路径下的所有页面
                //.paths(Predicates.and(PathSelectors.regex("/sms/.*")))
                //过滤掉所有error或error.*页面
                //.paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("快递E栈后台服务-API文档")
                .description("本文档描述了快递E栈后台服务功能接口定义")
                .version("1.0")
                .contact(new Contact("itfuture","http://www.baidu.com","itfuture03@163.com"))
                .build();
    }

    private ApiInfo adminApiInfo(){

        return new ApiInfoBuilder()
                .title("后台管理系统-API文档")
                .description("本文档描述了后台管理系统微服务接口定义")
                .version("1.0")
                .contact(new Contact("atguigu", "http://atguigu.com", "512111559@qq.com"))
                .build();
    }
}
