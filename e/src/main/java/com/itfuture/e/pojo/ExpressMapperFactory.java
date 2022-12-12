package com.itfuture.e.pojo;

import com.itfuture.e.pojo.vo.ExpressVo;
import com.itfuture.e.util.DateFormatUtil;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**express转expressVo的转换器
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/11 08:29
 */
@Component
public class ExpressMapperFactory {

    private static MapperFactory mapperFactory;

    static {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter("inTimeConvert", new BidirectionalConverter<Timestamp,String>() {
            @Override
            public String convertTo(Timestamp timestamp, Type<String> type, MappingContext mappingContext) {
                return timestamp==null?"null": DateFormatUtil.format(timestamp);
            }

            @Override
            public Timestamp convertFrom(String s, Type<Timestamp> type, MappingContext mappingContext) {
                return null;
            }
        });

        mapperFactory.getConverterFactory().registerConverter("outTimeConvert", new BidirectionalConverter<Timestamp,String>() {
            @Override
            public String convertTo(Timestamp timestamp, Type<String> type, MappingContext mappingContext) {
                return timestamp==null?"未出库":DateFormatUtil.format(timestamp);
            }

            @Override
            public Timestamp convertFrom(String s, Type<Timestamp> type, MappingContext mappingContext) {
                return null;
            }
        });

        mapperFactory.getConverterFactory().registerConverter("statusConvert", new BidirectionalConverter<Integer,String>() {

            @Override
            public String convertTo(Integer integer, Type<String> type, MappingContext mappingContext) {
                return integer==0?"待取件":"已取件";
            }

            @Override
            public Integer convertFrom(String s, Type<Integer> type, MappingContext mappingContext) {
                return "0".equals(s)?0:1;
            }
        });

        mapperFactory.classMap(Express.class, ExpressVo.class)
                .fieldMap("inTime","inTime").converter("inTimeConvert").add()
                .fieldMap("outTime","outTime").converter("outTimeConvert").add()
                .fieldMap("status","status").converter("statusConvert").add()
                .byDefault()
                .register();
    }

    public MapperFacade getMapperFacade(){
        return mapperFactory.getMapperFacade();
    }
}
