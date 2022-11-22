package com.itfuture.e.intercept;

import com.itfuture.e.Exception.BusinessException;
import com.itfuture.e.pojo.vo.ResultCode;
import com.itfuture.e.pojo.vo.ResultVo;
import com.itfuture.e.pojo.vo.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**统一异常的拦截
 * AOP 拦截所有 controller，然后异常的时候统一拦截处理
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/14 11:49
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler({BindException.class})
    public ResultVo MethodArgumentNotValidExceptionHander(BindException e){
        //从异常获取信息
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new ResultVo(ResultCode.VALIDATE_ERROR,objectError.getDefaultMessage());
    }

    @ExceptionHandler({BusinessException.class, MethodArgumentNotValidException.class})
    public ResultVo BusinessException(BusinessException e){
        //需集成日志框架
        log.error(e.getMessage(),e);
        return new ResultVo(e.getCode(),e.getMsg(),e.getMessage());
    }

}
