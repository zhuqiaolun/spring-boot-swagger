package com.demon.springbootswagger.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    String consumesPrefix;

    String producesPrefix;

    List<String> consumes;

    List<String> produces;

    public String getConsumesList(JSONObject jsonParam) {
        List<String> consumesList = new ArrayList<>();
        this.getConsumes().forEach(sc -> {
            if (jsonParam.getBooleanValue(this.getConsumesPrefix() + sc)) {
                consumesList.add(sc);
            }
        });
        return JSONArray.toJSONString(consumesList);
    }

    public String getProducesList(JSONObject jsonParam) {
        List<String> producesList = new ArrayList<>();
        this.getConsumes().forEach(sc -> {
            if (jsonParam.getBooleanValue(this.getProducesPrefix() + sc)) {
                producesList.add(sc);
            }
        });
        return JSONArray.toJSONString(producesList);
    }
}
