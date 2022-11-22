package com.itfuture.e.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**自定义显示状态
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/14 22:49
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {
    //set存储
    private Set<Integer> set = new HashSet<>();

    //初始化数据
    //listValue拿到的是注解中的数据
    @Override
    public void initialize(ListValue constraintAnnotation) {
        //拿到注解中自定义的数据，且是数组型的
        int[] values = constraintAnnotation.values();
        //放在数组里，遍历判断
        for(int value:values){
            set.add(value);
        }
    }

    //判断数据是否相同
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if(set.contains(integer)){
            return true;
        }
        return false;
    }
}
