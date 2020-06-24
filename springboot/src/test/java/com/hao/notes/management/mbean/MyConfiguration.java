package com.hao.notes.management.mbean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

@SpringBootApplication
@EnableMBeanExport // registers with the JMX Agent all components annotated with the @ManagedResource
class MyConfiguration {

    @Value("${jmx.rmi.host:0.0.0.0}")
    private String rmiHost;

    @Value("${jmx.rmi.port:1099}")
    private Integer rmiPort;

    /*
     * https://stackoverflow.com/questions/29412072/how-to-access-spring-boot-jmx-remotely
     */

    @Bean
    public RmiRegistryFactoryBean rmiRegistry() {
        final RmiRegistryFactoryBean rmiRegistryFactoryBean = new RmiRegistryFactoryBean();
        rmiRegistryFactoryBean.setPort(rmiPort);
        rmiRegistryFactoryBean.setAlwaysCreate(true);
        return rmiRegistryFactoryBean;
    }

    @Bean
    @DependsOn("rmiRegistry")
    public ConnectorServerFactoryBean connectorServerFactoryBean() throws Exception {
        final ConnectorServerFactoryBean connectorServerFactoryBean = new ConnectorServerFactoryBean();
        connectorServerFactoryBean.setObjectName("connector:name=rmi");
        String serviceUrl = String.format("service:jmx:rmi://%s:%s/jndi/rmi://%s:%s/jmxrmi",
                rmiHost, rmiPort, rmiHost, rmiPort);
        System.err.println(serviceUrl);
        connectorServerFactoryBean.setServiceUrl(serviceUrl);
        return connectorServerFactoryBean;
    }


}
