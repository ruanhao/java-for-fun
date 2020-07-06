package com.hao.spring.binder;

import com.google.common.collect.ImmutableMap;
import com.hao.spring.pojo.Person;
import java.util.Map;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.web.bind.WebDataBinder;

public class WebDataBinderDemo {

    public static void main(String[] args) {
        _testDefaultValue();
        _testEmptyValue();
    }

    static void _testEmptyValue() {
        Person person = new Person();
        WebDataBinder binder = _webDataBinder(person);

        binder.bind(_pvs(ImmutableMap.of(
            "_alias", ""
        )));
        assert person.getAlias().size() == 0;

    }

    static void _testDefaultValue() {
        Person person = new Person();
        WebDataBinder binder = _webDataBinder(person);
        binder.bind(_pvs(ImmutableMap.of(
                "!name", "DefaultName",
                "!age", "99",
                "age", "30"
        )));
        assert person.getName().equals("DefaultName");
        assert person.getAge() == 30;
    }

    private static PropertyValues _pvs(Map<String, Object> pvsMap) {
        return new MutablePropertyValues(pvsMap);
    }

    static private WebDataBinder _webDataBinder(Object obj) {
        WebDataBinder webDataBinder = new WebDataBinder(obj);
        //webDataBinder.setConversionService(new DefaultFormattingConversionService());
        return webDataBinder;
    }
}
