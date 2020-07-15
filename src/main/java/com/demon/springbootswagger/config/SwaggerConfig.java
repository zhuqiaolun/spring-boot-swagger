package com.demon.springbootswagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: SwaggerConfig
 * @Description: swagger的参数配置
 * @Author: Demon
 * @Date: 2020/7/15 16:02
 */
@Component
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerConfig {

    List<String> consumes;

    List<String> produces;

}
