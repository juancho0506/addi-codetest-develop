package com.addi.codetest.addicodetestdevelop.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "wiremock")
@Data

public class WiremockTestConfig {

    private String host;
    private Integer port;
}
