package com.reliaquest.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource({"classpath:properties/server-${spring.profiles.active}.properties"})
@ConfigurationProperties(prefix = "api")
@Component
public class ServerConfig {
    private String serverUrl;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
