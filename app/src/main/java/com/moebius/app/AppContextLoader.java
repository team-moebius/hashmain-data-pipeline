package com.moebius.app;

import com.moebius.backend.BackendContextLoader;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = BackendContextLoader.class)
public class AppContextLoader {
}
