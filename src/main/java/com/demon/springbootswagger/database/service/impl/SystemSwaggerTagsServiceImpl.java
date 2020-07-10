package com.demon.springbootswagger.database.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demon.springbootswagger.database.dao.SystemSwaggerTagsMapper;
import com.demon.springbootswagger.database.entity.SystemSwaggerTags;
import com.demon.springbootswagger.database.service.SystemSwaggerTagsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * swagger 标签 服务实现类
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Service
public class SystemSwaggerTagsServiceImpl extends ServiceImpl<SystemSwaggerTagsMapper, SystemSwaggerTags> implements SystemSwaggerTagsService {

    @Override
    public Page<Map<String, Object>> getSwaggerTagsListPage(long current, long size, Map<String, Object> params) {
        // 新建分页
        IPage<Map<String,Object>> page = new Page<>(current, size);
        return this.baseMapper.selectSystemSwaggerTagsListPage(page, params);
    }

    @Override
    public JSONObject getSwaggerTagsMapById(String siId) {
        QueryWrapper<SystemSwaggerTags> systemSwaggerTagsQueryWrapper = new QueryWrapper<>();
        systemSwaggerTagsQueryWrapper.setEntity(new SystemSwaggerTags().setStProject(siId));
        JSONObject swaggerTagsMap = new JSONObject(true);
        List<SystemSwaggerTags> systemSwaggerTags = this.list(systemSwaggerTagsQueryWrapper);
        if(systemSwaggerTags != null && systemSwaggerTags.size() > 0){
            systemSwaggerTags.forEach(swaggerTags -> {
                swaggerTagsMap.put(String.valueOf(swaggerTags.getStId()),swaggerTags.getStName());
            });
        }
        return swaggerTagsMap;
    }

}
