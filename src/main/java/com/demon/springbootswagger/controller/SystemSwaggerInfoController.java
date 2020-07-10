package com.demon.springbootswagger.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demon.springbootswagger.database.entity.SystemSwaggerInfo;
import com.demon.springbootswagger.database.service.SystemSwaggerInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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

}
