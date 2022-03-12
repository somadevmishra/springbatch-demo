package com.batch.demo.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("demo")
@EnableConfigurationProperties
@Data
public class EmployeePropertyConfig {

    private String inputFolder;
    private String filename;
}
