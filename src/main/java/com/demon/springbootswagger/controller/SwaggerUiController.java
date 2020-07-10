package com.demon.springbootswagger.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demon.springbootswagger.database.entity.SystemSwaggerInfo;
import com.demon.springbootswagger.database.entity.SystemSwaggerTags;
import com.demon.springbootswagger.database.entity.SystemSwaggerUrl;
import com.demon.springbootswagger.database.service.SystemSwaggerInfoService;
import com.demon.springbootswagger.database.service.SystemSwaggerTagsService;
import com.demon.springbootswagger.database.service.SystemSwaggerUrlService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: SwaggerController
 * @Description: swagger请求
 * @Author: Demon
 * @Date: 2020/6/30 11:23
 */
@Controller
@RequestMapping(value = "swagger-ui")
public class SwaggerUiController {

    @Resource
    private SystemSwaggerInfoService systemSwaggerInfoService;

    @Resource
    private SystemSwaggerTagsService systemSwaggerTagsService;

    @Resource
    private SystemSwaggerUrlService systemSwaggerUrlService;

    @GetMapping(value = "index{siId}")
    public String index(@PathVariable String siId, Model model) {
        model.addAttribute("index","index"+siId);
        return "swagger-ui/swagger-ui";
    }

    /**
     * swagger 加载初始数据
     * @return 返回
     */
    @GetMapping(value = "index{siId}/data.json")
    public @ResponseBody
    Object getJsonData(@PathVariable String siId) throws JsonProcessingException {
        //查询DB数据
        SystemSwaggerInfo systemSwaggerInfo =  systemSwaggerInfoService.getById(siId);
        QueryWrapper<SystemSwaggerTags> systemSwaggerTagsQueryWrapper = new QueryWrapper<>();
        systemSwaggerTagsQueryWrapper.setEntity(new SystemSwaggerTags().setStProject(siId));
        List<SystemSwaggerTags> systemSwaggerTagsList = systemSwaggerTagsService.list(systemSwaggerTagsQueryWrapper);
        JSONObject swaggerTagsMap = new JSONObject(true);
        if(systemSwaggerTagsList != null && systemSwaggerTagsList.size() > 0){
            systemSwaggerTagsList.forEach(swaggerTags -> swaggerTagsMap.put(String.valueOf(swaggerTags.getStId()),swaggerTags.getStName()));
        }
        QueryWrapper<SystemSwaggerUrl> systemSwaggerUrlQueryWrapper = new QueryWrapper<>();
        systemSwaggerUrlQueryWrapper.setEntity(new SystemSwaggerUrl().setSuProject(siId));
        List<SystemSwaggerUrl> systemSwaggerUrlList = systemSwaggerUrlService.list(systemSwaggerUrlQueryWrapper);
        //组装数据
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ObjectNode dataJson = jsonNodeFactory.objectNode();
        dataJson.put("swagger", "2.0");
        ObjectNode info = dataJson.putObject("info");
        info.put("termsOfService", "http://swagger.io/terms/")
            .put("title", systemSwaggerInfo.getSiTitle())
            .put("version ", systemSwaggerInfo.getSiVersion())
            .put("description", systemSwaggerInfo.getSiDescription())
            .putObject("contact")
                .put("name",systemSwaggerInfo.getSiContactName())
                .put("url",systemSwaggerInfo.getSiContactUrl())
                .put("email", systemSwaggerInfo.getSiContactEmail());
        info.putObject("license")
            .put("name","Apache 2.0")
            .put("url","http://www.apache.org/licenses/LICENSE-2.0.html")
        ;
        dataJson.put("host", systemSwaggerInfo.getSiServerhost() + ":" + systemSwaggerInfo.getSiServerport());
        dataJson.put("basePath", "/" + systemSwaggerInfo.getSiServerpath());
        dataJson.putArray("tags").addAll(getTags(systemSwaggerTagsList));
        dataJson.set("schemes", new ObjectMapper().readValue(systemSwaggerInfo.getSiSchemes(), ArrayNode.class));
        dataJson.putObject("paths").setAll(getPaths(systemSwaggerUrlList,swaggerTagsMap));
        ObjectNode securityDefinitions = dataJson.putObject("securityDefinitions");
        securityDefinitions.putObject("api_key").put("type", "apiKey").put("name", "api_key").put("in", "header");
        return dataJson;
    }

    /**
     * 添加 swagger 标签
     */
    private ArrayNode getTags(List<SystemSwaggerTags> systemSwaggerTagsList) {
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ArrayNode arrayNode = jsonNodeFactory.arrayNode();
       if(systemSwaggerTagsList != null && systemSwaggerTagsList.size() > 0){
            systemSwaggerTagsList.forEach(systemSwaggerTags ->{
                ObjectNode objectNode = jsonNodeFactory.objectNode();
                objectNode.put("name", systemSwaggerTags.getStName()).put("description", systemSwaggerTags.getStDescription());
                arrayNode.add(objectNode);
            });
        }
        return arrayNode;
    }

    /**
     * 添加 swagger 请求
     */
    private ObjectNode getPaths(  List<SystemSwaggerUrl> systemSwaggerUrlList,JSONObject swaggerTagsMap) {
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ObjectNode paths = jsonNodeFactory.objectNode();

        if (systemSwaggerUrlList != null && systemSwaggerUrlList.size() > 0) {
            systemSwaggerUrlList.forEach(systemSwaggerUrl -> {
                try {
                    ObjectNode jsonNodes = paths.putObject("/" + systemSwaggerUrl.getSuUrl()).putObject(systemSwaggerUrl.getSuMethod());
                    jsonNodes.putArray("tags").add(swaggerTagsMap.getString(systemSwaggerUrl.getSuTags()));
                    jsonNodes.put("summary", systemSwaggerUrl.getSuSummary());
                    jsonNodes.put("description", systemSwaggerUrl.getSuDescription());
                    jsonNodes.put("operationId", systemSwaggerUrl.getSuOperationid());
                    if (StringUtils.isNotBlank(systemSwaggerUrl.getSuConsumes())) {
                        jsonNodes.set("consumes",new ObjectMapper().readValue(systemSwaggerUrl.getSuConsumes(), ArrayNode.class));
                    }
                    if (StringUtils.isNotBlank(systemSwaggerUrl.getSuProduces())) {
                        jsonNodes.set("produces",new ObjectMapper().readValue(systemSwaggerUrl.getSuProduces(), ArrayNode.class));
                    }
                    if (StringUtils.isNotBlank(systemSwaggerUrl.getSuParameters())) {
                        jsonNodes.putArray("parameters").addAll(new ObjectMapper().readValue(systemSwaggerUrl.getSuParameters(), ArrayNode.class));
                    }
                    jsonNodes.set("responses", new ObjectMapper().readTree(systemSwaggerUrl.getSuResponses()));
                    jsonNodes.put("deprecated", systemSwaggerUrl.getSuDeprecated());
                    if (StringUtils.isNotBlank(systemSwaggerUrl.getSuSecurity())) {
                        jsonNodes.putArray("security").addAll(new ObjectMapper().readValue(systemSwaggerUrl.getSuSecurity(), ArrayNode.class));
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
        return paths;
    }

}
