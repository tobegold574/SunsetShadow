package com.demo.xiying;

//springboot，qi dong!
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.demo.xiying.mapper")
public class xiyingApplication {
    public static void main(String[] args){
        SpringApplication.run(com.demo.xiying.xiyingApplication.class,args);
    }
}
