package com.netflix.springdatabugs;

import org.springframework.boot.SpringApplication;

public class TestSpringDataBugsApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringDataBugsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
