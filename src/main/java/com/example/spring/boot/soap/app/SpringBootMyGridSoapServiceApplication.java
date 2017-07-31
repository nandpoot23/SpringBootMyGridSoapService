package com.example.spring.boot.soap.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;

import com.example.spring.boot.soap.exception.FrameworkError;
import com.example.spring.boot.soap.exception.SoapExceptionResolverMapper;

/* @Configuration annotation indicates that a class declares one or more @Bean methods and may be 
 * processed by the spring container to generate bean definitions and service requests for those 
 * beans at runtime. Extending the WsConfigurerAdapter is optional. 
 *
 */

@SuppressWarnings("deprecation")
@SpringBootApplication
@Configuration
@ComponentScan(basePackages = { "com.example", "com.sample.soap.xml.dm" })
@EnableWs
@EnableAspectJAutoProxy
public class SpringBootMyGridSoapServiceApplication extends WsConfigurerAdapter {

    @Autowired
    @Qualifier("MessageSource")
    private ResourceBundleMessageSource messageSource;

    public static void main(String[] args) {
        System.out.println("Start");
        SpringApplication.run(SpringBootMyGridSoapServiceApplication.class, args);
        System.out.println("End");
    }

    /**
     * This is the base URL which will be exposed for soap proxies. This bean
     * allow to bind the soap dispatcher servlet
     * 
     * @param applicationContext
     * @return
     */

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/DmService/*");
    }

    /**
     * This bean is use to bind the definition of 1000 wsdl version
     * 
     * @return def as SimpleWsdl11Definition
     */

    @Bean(name = "1.0")
    public SimpleWsdl11Definition simpletWsdl11Definition() {
        SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
        wsdl11Definition.setWsdl(dmSchema());
        return wsdl11Definition;
    }

    @Bean
    public Resource dmSchema() {
        return new ClassPathResource("wsdl/1.0/mathsdbservice.wsdl");
    }

    /**
     * This bean use to resolve the framework exception.
     * 
     * @return soapExceptionResolverMapper as SoapExceptionResolverMapper
     */

    @Bean
    public SoapExceptionResolverMapper frameworkExceptionResolver() {
        SoapExceptionResolverMapper soapExceptionResolverMapper = new SoapExceptionResolverMapper();
        soapExceptionResolverMapper.setOrder(Integer.MIN_VALUE);
        soapExceptionResolverMapper.registerResolver(FrameworkError.class,
                new com.example.spring.boot.soap.exception.FrameworkExceptionSoapFaultResolver(messageSource));
        soapExceptionResolverMapper.registerResolver(RuntimeException.class,
                new com.example.spring.boot.soap.exception.FrameworkExceptionSoapFaultResolver(messageSource));
        return soapExceptionResolverMapper;
    }

}
