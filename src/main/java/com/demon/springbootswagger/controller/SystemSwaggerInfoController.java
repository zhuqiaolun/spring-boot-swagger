package com.demon.springbootswagger.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demon.springbootswagger.database.entity.SystemSwaggerInfo;
import com.demon.springbootswagger.database.service.SystemSwaggerInfoService;
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

/**
 * <p>
 * swagger 信息 前端控制器
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Controller
@RequestMapping(value = "swagger")
public class SystemSwaggerInfoController {

    @Resource
    private SystemSwaggerInfoService systemSwaggerInfoService;

    /**
     * 列表
     *GD
     * @return 返回
     * @throws Exception 异常
     */
    @PostMapping(value = "listPage")
    @ResponseBody
    public JSONObject getListPage(@RequestBody JSONObject jsonParam) throws Exception {
        JSONObject responseBean = new JSONObject();
        try {
            String pageIndex = jsonParam.getString("pageIndex");
            String pageCount = jsonParam.getString("pageCount");
            String siTitle = jsonParam.getString("siTitle");
            //分页
            IPage<SystemSwaggerInfo> systemSwaggerInfoPage = new Page<>(Long.parseLong(pageIndex), Long.parseLong(pageCount));
            QueryWrapper<SystemSwaggerInfo> systemSwaggerInfoQueryWrapper = new QueryWrapper<>();
            if(StringUtils.isNotBlank(siTitle)){
                systemSwaggerInfoQueryWrapper.like("si_title",siTitle);
            }
            systemSwaggerInfoPage = systemSwaggerInfoService.page(systemSwaggerInfoPage, systemSwaggerInfoQueryWrapper);
            //返回值
            responseBean.put("code",0);
            responseBean.put("msg","查询成功");
            responseBean.put("count",systemSwaggerInfoPage.getTotal());
            responseBean.put("data",systemSwaggerInfoPage.getRecords());
            return responseBean;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @RequestMapping("getSwaggerInfo")
    public ModelAndView getSwaggerInfo(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        try {
            String siId = request.getParameter("siId");
            if(StringUtils.isNotBlank(siId)){
                SystemSwaggerInfo systemSwaggerInfo = systemSwaggerInfoService.getById(siId);
                modelAndView.addObject("systemSwaggerInfo",systemSwaggerInfo);
            }else {
                modelAndView.addObject("systemSwaggerInfo",new SystemSwaggerInfo());
            }
            modelAndView.setViewName("swagger-info/info-add");
            return modelAndView;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PostMapping(value = "submitSwaggerInfo")
    public @ResponseBody ResponseBean submitSwaggerInfo(@RequestBody JSONObject jsonParam) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String siId = jsonParam.getString("siId");
            //参数
            SystemSwaggerInfo systemSwaggerInfo = new SystemSwaggerInfo();
            systemSwaggerInfo.setSiTitle(jsonParam.getString("title"));
            systemSwaggerInfo.setSiDescription(jsonParam.getString("description"));
            systemSwaggerInfo.setSiVersion(jsonParam.getString("version"));
            systemSwaggerInfo.setSiSchemeshttp(jsonParam.getBooleanValue("schemes_http"));
            systemSwaggerInfo.setSiSchemeshttps(jsonParam.getBooleanValue("schemes_https"));
            systemSwaggerInfo.setSiServerhost(jsonParam.getString("server_host"));
            systemSwaggerInfo.setSiServerport(Integer.valueOf(jsonParam.getString("server_port")));
            systemSwaggerInfo.setSiServerpath(jsonParam.getString("server_path"));
            systemSwaggerInfo.setSiSecuritydefinitions(jsonParam.getString("security_definitions"));
            systemSwaggerInfo.setSiContactName(jsonParam.getString("contact_name"));
            systemSwaggerInfo.setSiContactEmail(jsonParam.getString("contact_email"));
            systemSwaggerInfo.setSiContactUrl(jsonParam.getString("contact_url"));
            if(StringUtils.isBlank(siId)){
                systemSwaggerInfo.setSiCreatetime(new Date());
                //返回值
                responseBean.setSuccess(systemSwaggerInfoService.save(systemSwaggerInfo));
                responseBean.setData("新增操作成功");
            }else{
                systemSwaggerInfo.setSiId(Integer.valueOf(siId));
                //返回值
                responseBean.setSuccess(systemSwaggerInfoService.updateById(systemSwaggerInfo));
                responseBean.setData("修改操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setData(e.getMessage());
        }
        return responseBean;
    }


    @PostMapping(value = "delSwaggerInfo")
    public @ResponseBody ResponseBean delSwaggerInfo(@RequestBody JSONObject jsonParam) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String siId = jsonParam.getString("siId");
            //返回值
            systemSwaggerInfoService.deleteInfoById(siId);
            responseBean.setSuccess(true);
            responseBean.setData("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setData(e.getMessage());
        }
        return responseBean;
    }
}
