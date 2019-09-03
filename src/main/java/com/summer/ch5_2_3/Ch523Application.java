package com.summer.ch5_2_3;

import com.summer.ch5_2_3.proprltbean.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
//@EnableConfigurationProperties(Book.class)
public class Ch523Application {

    @Value("${book.author}")
    private String bookAuthor;

    @Value("${book.name}")
    private String bookName;

    @Autowired
    private Book book;

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

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Ch523Application.class);
//        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
