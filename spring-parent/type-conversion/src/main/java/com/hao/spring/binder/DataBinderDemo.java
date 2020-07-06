package com.hao.spring.binder;

import com.google.common.collect.ImmutableMap;
import com.hao.spring.pojo.Person;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.validation.DataBinder;

public class DataBinderDemo {

    public static void main(String[] args) {
        _test1();
        //_test2();
    }

    private static PropertyValues _pvs(Map<String, Object> pvsMap) {
        return new MutablePropertyValues(pvsMap);
    }

    private static ConversionService _conversionService() {
        DefaultFormattingConversionService conversionService =
                new DefaultFormattingConversionService(true);
        return conversionService;
    }

    private static DataBinder _dateBinder(Object obj) {
        DataBinder binder = new DataBinder(obj);
        binder.setConversionService(_conversionService());
        return binder;
    }



    private static BeanWrapper _beanWrapper(Object obj) {
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        bw.setConversionService(_conversionService());
        bw.setAutoGrowNestedPaths(true);
        return bw;
    }

    private static void _test2() {
        Person person = new Person();
        BeanWrapper bw = _beanWrapper(person);
        ((DefaultFormattingConversionService) bw.getConversionService())
               .addFormatter(new DateFormatter("yyyy-MM-dd"));
        bw.setPropertyValue("now", new Date());
        System.err.println("person: " + person);
    }

    private static void _test1() {
        Person person = new Person();
        DataBinder binder = _dateBinder(person);
        binder.bind(_pvs(ImmutableMap.of("birthday", "2007-12-03T10:15:30.00Z")));
        System.err.println("person: " + person);

    }
}
