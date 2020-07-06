package com.hao.spring.binder;

import com.hao.spring.pojo.Person;
import java.util.Map;
import static org.junit.Assert.*;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;

/**
 * ServletRequestDataBinder 一般不会直接使用，而是使用更强的子类 ExtendedServletRequestDataBinder ，
 * 它用于把 URI template variables 参数添加进来用于绑定。
 * 它会去从 request 的 HandlerMapping.uriTemplateVariables 这个属性里查找到值出来用于绑定。
 */

/**
 * 处理步骤：
 *
 * 1. BeanWrapper调用setPropertyValue()给属性赋值，传入的value值都会交给convertForProperty()方法根据get方法的返回值类型进行转换~（比如此处为Date类型）
 * 2. 委托给this.typeConverterDelegate.convertIfNecessary进行类型转换（比如此处为string->Date类型）
 * 3. 先this.propertyEditorRegistry.findCustomEditor(requiredType, propertyName);找到一个合适的PropertyEditor（显然此处我们没有自定义Custom处理Date的PropertyEditor，返回null）
 * 4. 回退到使用ConversionService，显然此处我们也没有设置，返回null
 * 5. 回退到使用默认的editor = findDefaultEditor(requiredType);（注意：此处只根据类型去找了，因为上面说了默认不处理了Date，所以也是返回null）
 * 6l 最终的最终，回退到Spring对Array、Collection、Map的默认值处理问题，最终若是String类型，都会调用BeanUtils.instantiateClass(strCtor, convertedValue)也就是有参构造进行初始化~~~(请注意这必须是String类型才有的权利)
 * 所以本例中，到最后一步就相当于new Date("Sat Jul 20 11:00:22 CST 2019")，因为该字符串是标准的时间日期串，所以是阔仪的，也就是endTest是能被正常赋值的~

 */
public class ServletRequestDataBinderDemo {

    public static void main(String[] args) {
        testWebRequest();
    }

    static private void testWebRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addParameter("name", "Ryan");
        request.addParameter("age", "35");

        // 1 可以表示 true
        request.addParameter("vip", "1");

        // List
        request.addParameter("alias", new String[] {"Ruan", "Hao"});

        // Map
        request.addParameter("bodyInfo['weight']", "90");
        request.addParameter("bodyInfo['height']", "184");

        Person person = new Person();
        ServletRequestDataBinder binder = _servletRequestDataBinder(person);
        binder.bind(request);
        assertEquals("Ryan", person.getName());
        assertEquals(2, person.getAlias().size());
        assertTrue(person.getVip());
        assertEquals(2, person.getBodyInfo().size());
        System.err.println("[testWebRequest] person: " + person);
    }

    static private PropertyValues _pvs(Map<String, Object> pvsMap) {
        return new MutablePropertyValues(pvsMap);
    }

    static private ServletRequestDataBinder _servletRequestDataBinder(Object target) {
        ServletRequestDataBinder binder = new ServletRequestDataBinder(target);
        binder.setConversionService(new DefaultFormattingConversionService());
        return binder;
    }
}
