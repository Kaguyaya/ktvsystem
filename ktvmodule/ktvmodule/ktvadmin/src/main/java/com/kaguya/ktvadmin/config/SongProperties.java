package com.kaguya.ktvadmin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "songs")
public class SongProperties {
    /*
    歌曲类型
     */
    private String[] songTypes;
    /*
    歌曲语言
     */
    private Map<String,String> songLanguage;
}
