package com.moebius.frontend;

import com.moebius.backend.BackendContextLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = { BackendContextLoader.class })
public class MoebiusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoebiusApplication.class, args);
    }
}
