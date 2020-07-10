package com.demon.springbootswagger.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demon.springbootswagger.database.service.SystemSwaggerInfoService;
import com.demon.springbootswagger.database.service.SystemSwaggerTagsService;
import com.demon.springbootswagger.support.ResponseBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * swagger 标签 前端控制器
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Controller
@RequestMapping(value = "tags")
public class SystemSwaggerTagsController {

    @Resource
    private SystemSwaggerInfoService systemSwaggerInfoService;

    @Resource
    private SystemSwaggerTagsService systemSwaggerTagsService;

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
            Long pageIndex = jsonParam.getLongValue("pageIndex");
            Long pageCount = jsonParam.getLongValue("pageCount");
            String stProjectId = jsonParam.getString("stProjectId");
            //项目
            JSONArray siProjects = systemSwaggerInfoService.getInfoListMap();
            //传递参数
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("stProjectId",StringUtils.isNotBlank(stProjectId)?stProjectId:"");
            Page<Map<String, Object>> swaggerTagsListPage = systemSwaggerTagsService.getSwaggerTagsListPage(pageIndex, pageCount, map);
            //返回值
            responseBean.put("code",0);
            responseBean.put("msg","查询成功");
            responseBean.put("count",swaggerTagsListPage.getTotal());
            responseBean.put("data",swaggerTagsListPage.getRecords());
            responseBean.put("stProjectId",stProjectId);
            responseBean.put("stProjects",siProjects);
            return responseBean;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PostMapping(value = "list")
    public @ResponseBody ResponseBean getTagsList(@RequestBody JSONObject jsonParam) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String suProjectId = jsonParam.getString("suProjectId");
            responseBean.setData(systemSwaggerTagsService.getSwaggerTagsMapById(suProjectId));
            responseBean.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setData(e.getMessage());
        }
        return responseBean;
    }

}
