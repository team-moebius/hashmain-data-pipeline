package com.moebius.api;

import com.moebius.backend.BackendContextLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = { BackendContextLoader.class })
public class MoebiusApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoebiusApiApplication.class, args);
    }
}
