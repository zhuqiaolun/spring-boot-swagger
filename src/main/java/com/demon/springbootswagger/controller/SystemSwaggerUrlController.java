package com.demon.springbootswagger.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demon.springbootswagger.database.service.SystemSwaggerInfoService;
import com.demon.springbootswagger.database.service.SystemSwaggerUrlService;
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
 * swagger 的URL 配置 前端控制器
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Controller
@RequestMapping(value = "config")
public class SystemSwaggerUrlController {

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

    @PostMapping(value = "delConfig")
    public @ResponseBody ResponseBean delConfig(@RequestBody JSONObject jsonParam) {
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
