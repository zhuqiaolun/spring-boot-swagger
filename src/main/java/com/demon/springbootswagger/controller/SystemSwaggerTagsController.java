package com.demon.springbootswagger.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demon.springbootswagger.database.entity.SystemSwaggerInfo;
import com.demon.springbootswagger.database.entity.SystemSwaggerTags;
import com.demon.springbootswagger.database.service.SystemSwaggerInfoService;
import com.demon.springbootswagger.database.service.SystemSwaggerTagsService;
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

    @RequestMapping("getSwaggerTags")
    public ModelAndView getSwaggerTags(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<SystemSwaggerInfo> systemSwaggerInfos = systemSwaggerInfoService.list();
            modelAndView.addObject("systemSwaggerInfos",systemSwaggerInfos);
            String stId = request.getParameter("stId");
            if(StringUtils.isNotBlank(stId)){
                SystemSwaggerTags systemSwaggerTags = systemSwaggerTagsService.getById(stId);
                modelAndView.addObject("stProject",systemSwaggerTags.getStProject());
                modelAndView.addObject("systemSwaggerTags",systemSwaggerTags);
            }else {
                modelAndView.addObject("systemSwaggerTags",new SystemSwaggerTags());
            }
            modelAndView.setViewName("swagger-tags/tags-add");
            return modelAndView;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PostMapping(value = "submitSwaggerTags")
    public @ResponseBody ResponseBean submitSwaggerTags(@RequestBody JSONObject jsonParam) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String stId = jsonParam.getString("stId");
            //参数
            SystemSwaggerTags systemSwaggerTags = new SystemSwaggerTags();
            systemSwaggerTags.setStProject(jsonParam.getString("stProject"));
            systemSwaggerTags.setStName(jsonParam.getString("stName"));
            systemSwaggerTags.setStDescription(jsonParam.getString("stDescription"));
            systemSwaggerTags.setStRemark(jsonParam.getString("stRemark"));
            if(StringUtils.isBlank(stId)){
                systemSwaggerTags.setStCreatetime(new Date());
                //返回值
                responseBean.setSuccess(systemSwaggerTagsService.save(systemSwaggerTags));
                responseBean.setData("新增操作成功");
            }else{
                systemSwaggerTags.setStId(Integer.valueOf(stId));
                //返回值
                responseBean.setSuccess(systemSwaggerTagsService.updateById(systemSwaggerTags));
                responseBean.setData("修改操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setData(e.getMessage());
        }
        return responseBean;
    }

    @PostMapping(value = "delSwaggerTags")
    public @ResponseBody ResponseBean delSwaggerTags(@RequestBody JSONObject jsonParam) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String stId = jsonParam.getString("stId");
            systemSwaggerTagsService.deleteTagsById(stId);
            //返回值
            responseBean.setSuccess(true);
            responseBean.setData("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setData(e.getMessage());
        }
        return responseBean;
    }

}
