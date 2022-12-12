package com.itfuture.e.intercept;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itfuture.e.Exception.BusinessException;
import com.itfuture.e.pojo.vo.ResultCode;
import com.itfuture.e.pojo.vo.ResultVo;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**统一响应 封装
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/14 14:51
 */
@RestControllerAdvice(basePackages = {"com.itfuture.e.controller","com.itfuture.e.wx.controller"})
public class ControllerResponseAdvice implements ResponseBodyAdvice<Object> {
    /**
     * 当返回类型：是ResultVo就不包装；当不等与 ResultVo 时才进行调用 beforeBodyWrite 方法
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        //response是ResultVo类型，或者注释了NotControllerResponseAdvice都不进行包装
        return !(methodParameter.getParameterType().isAssignableFrom(ResultVo.class)
                || methodParameter.hasMethodAnnotation(NotControllerResponseAdvice.class));
    }

    /**
     * 除了 String 的返回值有点特殊，无法直接封装成 json，我们需要进行特殊处理，
     * 其他的直接 new ResultVo(data)
     * @param data
     * @param returnType
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //string不能直接包装
        if(returnType.getGenericParameterType().equals(String.class)){
            ObjectMapper objectMapper = new ObjectMapper();
            //将数据包装在ResultVo里，转换为json返回
            try {
                return objectMapper.writeValueAsString(new ResultVo(data));
            } catch (JsonProcessingException e) {
                throw new BusinessException(ResultCode.VALIDATE_ERROR,e.getMessage());
            }
        }
        //否则，直接包装为resultVo返回
        return new ResultVo(data);
    }
}
