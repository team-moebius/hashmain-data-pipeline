package com.moebius.frontend;

import com.moebius.backend.BackendContextLoader;
import com.moebius.frontend.configuration.WebFrontConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = { BackendContextLoader.class, WebFrontConfiguration.class})
public class MoebiusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoebiusApplication.class, args);
    }
}
