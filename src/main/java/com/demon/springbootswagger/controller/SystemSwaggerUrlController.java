package com.demon.springbootswagger.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demon.springbootswagger.config.SwaggerConfig;
import com.demon.springbootswagger.database.entity.SystemSwaggerInfo;
import com.demon.springbootswagger.database.entity.SystemSwaggerUrl;
import com.demon.springbootswagger.database.service.SystemSwaggerInfoService;
import com.demon.springbootswagger.database.service.SystemSwaggerUrlService;
import com.demon.springbootswagger.support.ResponseBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * swagger 的URL 配置 前端控制器
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Controller
@RequestMapping(value = "url")
public class SystemSwaggerUrlController {

    @Resource
    private SwaggerConfig swaggerConfig;

    @Resource
    private SystemSwaggerInfoService systemSwaggerInfoService;

    @Resource
    private SystemSwaggerUrlService systemSwaggerUrlService;

    /**
     * 列表
     * @return 返回
     * @throws Exception 异常
     */
    @PostMapping(value = "listPage")
    @ResponseBody
    public JSONObject getListPage(@RequestBody JSONObject jsonParam) throws Exception {
        JSONObject responseBean = new JSONObject();
        try {
            //页面参数
            Long pageIndex = jsonParam.getLongValue("pageIndex");
            Long pageCount = jsonParam.getLongValue("pageCount");
            String suProjectId = jsonParam.getString("suProjectId");
            String suTags = jsonParam.getString("suTags");
            String suOperationid = jsonParam.getString("suOperationid");
            String suMethod = jsonParam.getString("suMethod");
            //项目
            JSONArray siProjects = systemSwaggerInfoService.getInfoListMap();
            //传递参数
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("suProjectId",StringUtils.isNotBlank(suProjectId)?suProjectId:"");
            map.put("suTags",StringUtils.isNotBlank(suTags)?suTags:"");
            map.put("suOperationid",StringUtils.isNotBlank(suOperationid)?suOperationid:"");
            map.put("suMethod",StringUtils.isNotBlank(suMethod)?suMethod:"");
            Page<Map<String, Object>> swaggerUrlListPage = systemSwaggerUrlService.getSwaggerUrlListPage(pageIndex, pageCount, map);
            //返回值
            responseBean.put("code",0);
            responseBean.put("msg","查询成功");
            responseBean.put("count",swaggerUrlListPage.getTotal());
            responseBean.put("data",swaggerUrlListPage.getRecords());
            responseBean.put("suProjectId",suProjectId);
            responseBean.put("suProjects",siProjects);
            return responseBean;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @RequestMapping("getSwaggerUrl")
    public ModelAndView getSwaggerUrl(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<SystemSwaggerInfo> systemSwaggerInfos = systemSwaggerInfoService.list();
            modelAndView.addObject("systemSwaggerInfos",systemSwaggerInfos);
            String suId = request.getParameter("suId");
            if(StringUtils.isNotBlank(suId)){
                SystemSwaggerUrl systemSwaggerUrl = systemSwaggerUrlService.getById(suId);
                modelAndView.addObject("suProject",systemSwaggerUrl.getSuProject());
                modelAndView.addObject("systemSwaggerUrl",systemSwaggerUrl);
            }else {
                modelAndView.addObject("systemSwaggerUrl",new SystemSwaggerUrl());
            }
            modelAndView.setViewName("swagger-url/url-add");
            return modelAndView;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PostMapping(value = "submitSwaggerUrl")
    public @ResponseBody ResponseBean submitSwaggerUrl(@RequestBody JSONObject jsonParam) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String suId = jsonParam.getString("suId");
            //参数
            SystemSwaggerUrl systemSwaggerUrl = new SystemSwaggerUrl();
            systemSwaggerUrl.setSuProject(jsonParam.getString("suProject"));
            systemSwaggerUrl.setSuTags(jsonParam.getString("suTags"));
            systemSwaggerUrl.setSuOperationid(jsonParam.getString("suOperationid"));
            systemSwaggerUrl.setSuUrl(jsonParam.getString("suUrl"));
            systemSwaggerUrl.setSuMethod(jsonParam.getString("suMethod"));
            systemSwaggerUrl.setSuSummary(jsonParam.getString("suSummary"));
            systemSwaggerUrl.setSuDescription(jsonParam.getString("suDescription"));
            systemSwaggerUrl.setSuDeprecated(jsonParam.getBooleanValue("suDeprecated"));
            systemSwaggerUrl.setSuConsumes(JSONArray.toJSONString(swaggerConfig.getConsumes()));
            systemSwaggerUrl.setSuProduces(JSONArray.toJSONString(swaggerConfig.getProduces()));
            systemSwaggerUrl.setSuParameters(jsonParam.getString("suParameters"));
            systemSwaggerUrl.setSuResponses(jsonParam.getString("suResponses"));
            systemSwaggerUrl.setSuSecurity(jsonParam.getString("suSecurity"));
            if(StringUtils.isBlank(suId)){
                systemSwaggerUrl.setSuCreatetime(new Date());
                //返回值
                responseBean.setSuccess(systemSwaggerUrlService.save(systemSwaggerUrl));
                responseBean.setData("新增操作成功");
            }else{
                systemSwaggerUrl.setSuId(Integer.valueOf(suId));
                //返回值
                responseBean.setSuccess(systemSwaggerUrlService.updateById(systemSwaggerUrl));
                responseBean.setData("修改操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setData(e.getMessage());
        }
        return responseBean;
    }

    @PostMapping(value = "delSwaggerUrl")
    public @ResponseBody ResponseBean delSwaggerUrl(@RequestBody JSONObject jsonParam) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String suId = jsonParam.getString("suId");
            if(systemSwaggerUrlService.removeById(suId)){
                responseBean.setSuccess(true);
                responseBean.setData("删除成功");
            }else {
                responseBean.setData("删除ID不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setData(e.getMessage());
        }
        return responseBean;
    }

}
