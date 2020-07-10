package com.demon.springbootswagger.database.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demon.springbootswagger.database.entity.SystemSwaggerTags;

import java.util.Map;

/**
 * <p>
 * swagger 标签 服务类
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
public interface SystemSwaggerTagsService extends IService<SystemSwaggerTags> {

    /**
     * 查询 （分页）
     * @param current 当前页
     * @param size  数量
     * @param params 参数
     * @return 返回
     */
    Page<Map<String,Object>> getSwaggerTagsListPage(long current, long size, Map<String,Object> params );

    /**
     * 获取项目下的ID
     * @param siId  项目
     * @return 返回 map  keu为id
     */
    JSONObject getSwaggerTagsMapById(String siId);
}
