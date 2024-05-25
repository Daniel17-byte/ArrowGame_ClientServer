package org.arrowgame.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

@SpringBootApplication
@EnableDiscoveryClient
public class ServerApplication {
    public static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    public static String path = context.getBean("myProperties", Properties.class).getProperty("path");
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
