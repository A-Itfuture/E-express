package com.itfuture.e.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**自定义条件注解
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/14 22:39
 */
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR,ElementType.PARAMETER,ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ListValueConstraintValidator.class})
public @interface ListValue {
    //配置路径，后端传递信息
    String message() default "{com.itfuture.e.valid.ListValue.message}";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //自定义一个类型来存放数据（数组）
    int[] values() default {};

}
