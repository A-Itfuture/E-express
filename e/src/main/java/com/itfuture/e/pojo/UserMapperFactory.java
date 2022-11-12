package com.itfuture.e.pojo;

import com.itfuture.e.pojo.vo.UserVo;
import com.itfuture.e.util.DateFormatUtil;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**user转userVo的转换器
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/11 08:29
 */
@Component
public class UserMapperFactory {

    @Autowired
    private MapperFactory mapperFactory;

    public MapperFacade getMapperFacade(){
        mapperFactory.getConverterFactory().registerConverter("loginTimeConvert", new BidirectionalConverter<Timestamp,String>() {
            @Override
            public String convertTo(Timestamp timestamp, Type<String> type, MappingContext mappingContext) {
                return timestamp==null?"null": DateFormatUtil.format(timestamp);
            }

            @Override
            public Timestamp convertFrom(String s, Type<Timestamp> type, MappingContext mappingContext) {
                return null;
            }
        });

        mapperFactory.getConverterFactory().registerConverter("createTimeConvert", new BidirectionalConverter<Timestamp,String>() {
            @Override
            public String convertTo(Timestamp timestamp, Type<String> type, MappingContext mappingContext) {
                return DateFormatUtil.format(timestamp);
            }

            @Override
            public Timestamp convertFrom(String s, Type<Timestamp> type, MappingContext mappingContext) {
                return null;
            }
        });

        mapperFactory.classMap(User.class, UserVo.class)
                .fieldMap("createTime","createTime").converter("createTimeConvert").add()
                .fieldMap("loginTime","loginTime").converter("loginTimeConvert").add()
                .byDefault()
                .register();

        return mapperFactory.getMapperFacade();
    }
}
