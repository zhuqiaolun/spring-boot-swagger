package com.demon.springbootswagger.database.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demon.springbootswagger.database.dao.SystemSwaggerTagsMapper;
import com.demon.springbootswagger.database.entity.SystemSwaggerTags;
import com.demon.springbootswagger.database.entity.SystemSwaggerUrl;
import com.demon.springbootswagger.database.service.SystemSwaggerTagsService;
import com.demon.springbootswagger.database.service.SystemSwaggerUrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
public class SystemSwaggerTagsServiceImpl extends ServiceImpl<SystemSwaggerTagsMapper, SystemSwaggerTags> implements SystemSwaggerTagsService {

    @Resource
    private SystemSwaggerUrlService systemSwaggerUrlService;

    @Override
    public Page<Map<String, Object>> getSwaggerTagsListPage(long current, long size, Map<String, Object> params) {
        // 新建分页
        IPage<Map<String,Object>> page = new Page<>(current, size);
        return this.baseMapper.selectSystemSwaggerTagsListPage(page, params);
    }

    @Override
    public JSONObject getSwaggerTagsMapById(String siId) {
        JSONObject swaggerTagsMap = new JSONObject(true);
        if(StringUtils.isNotBlank(siId)){
            QueryWrapper<SystemSwaggerTags> systemSwaggerTagsQueryWrapper = new QueryWrapper<>();
            systemSwaggerTagsQueryWrapper.setEntity(new SystemSwaggerTags().setStProject(siId));
            List<SystemSwaggerTags> systemSwaggerTags = this.list(systemSwaggerTagsQueryWrapper);
            if(systemSwaggerTags != null && systemSwaggerTags.size() > 0){
                systemSwaggerTags.forEach(swaggerTags -> {
                    swaggerTagsMap.put(String.valueOf(swaggerTags.getStId()),swaggerTags.getStName());
                });
            }
        }
        return swaggerTagsMap;
    }

    @Override
    public void deleteTagsById(String stId) {
        this.removeById(stId);
        systemSwaggerUrlService.remove(new QueryWrapper<SystemSwaggerUrl>().setEntity(new SystemSwaggerUrl().setSuTags(stId)));
    }

}
