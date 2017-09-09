package com.kanghua.helloworld;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloworldApplication {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Greeter greeter = (Greeter) context.getBean("greeter");
        System.out.println((greeter.getGreeting()));
    }
}
