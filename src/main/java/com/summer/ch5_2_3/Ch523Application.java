package com.summer.ch5_2_3;

import com.summer.ch5_2_3.encodingfilter.HttpEncodingProperties;
import com.summer.ch5_2_3.proprltbean.Book;
import com.summer.hello.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CharacterEncodingFilter;

@RestController
@SpringBootApplication
//允许HttpEncodingProperties类注入属性
@EnableConfigurationProperties(HttpEncodingProperties.class)
//当CharacterEncodingFilter在类路径下
@ConditionalOnClass(CharacterEncodingFilter.class)
//当spring.http.encoding属性值等于enabled的情况下，如果没有默认为true
@ConditionalOnProperty(prefix = "spring.http.encoding", value = "enabled", matchIfMissing = true)
public class Ch523Application {

    @Value("${book.author}")
    private String bookAuthor;

    @Value("${book.name}")
    private String bookName;

    @Autowired
    private Book book;

    @Autowired
    private HttpEncodingProperties httpEncodingProperties;

    @Autowired(required = false)
    private HelloService helloService;


    //当容器中没有这个Bean的时候新建该bean
    @Bean
    @ConditionalOnMissingBean(CharacterEncodingFilter.class)
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
        filter.setEncoding(this.httpEncodingProperties.getCharset().name());
        filter.setForceEncoding(this.httpEncodingProperties.isForce());
        return filter;
    }

    @RequestMapping(value = "/")
    public String index() {
        return "Hello Spring Boot";
    }

    @RequestMapping(value = "/prop")
    public String prop() {
        return "bookAuthor:" + bookAuthor + "   bookName:" + bookName;
    }

    @RequestMapping(value = "/propToBean")
    public String propToBean() {
        return "bookAuthor1:" + book.getAuthor() + "   bookName1:" + book.getName();
    }

    @RequestMapping(value = "/encoding")
    public String encoding() {
        return "charset:" + httpEncodingProperties.getCharset() + "   force:" + httpEncodingProperties.isForce();
    }

    @RequestMapping(value = "/hello")
    public String hello() {
        return "msg:" + helloService.sayHello();
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Ch523Application.class);
//        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
