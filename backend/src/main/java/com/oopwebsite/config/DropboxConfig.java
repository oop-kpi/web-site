package com.oopwebsite.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropboxConfig {
    @Value("${dropbox.access_token}")
    private String accessToken;

    @Bean
    public DbxClientV2 dropboxClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("oop-file-storage").build();
        return new DbxClientV2(config, accessToken);
    }
}
